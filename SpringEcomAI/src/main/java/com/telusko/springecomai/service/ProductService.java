package com.telusko.springecomai.service;

import com.telusko.springecomai.model.Product;
import com.telusko.springecomai.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private VectorStore vectorStore;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(int id) {
        return productRepo.findById(id).orElse(new Product(-1));
    }

    public Product addOrUpdateProduct(Product product, MultipartFile image) throws IOException {

        if (image != null && !image.isEmpty()) {
            product.setImageName(image.getOriginalFilename());
            product.setImageType(image.getContentType());
            product.setProductImage(image.getBytes());

        }

        Product savedProduct = productRepo.save(product);

        // Prepare content for semantic embedding (RAG)
        String contentToEmbed =String.format("""
         Product Name: %s
         Description: %s
         Brand: %s
         Category: %s
         Price: %.2f
         Release Date: %s
         Available: %s
         Stock: %d
        """,
                savedProduct.getName(),
                savedProduct.getDescription(),
                savedProduct.getBrand(),
                savedProduct.getCategory(),
                savedProduct.getPrice(),
                savedProduct.getReleaseDate(),
                savedProduct.isProductAvailable(),
                savedProduct.getStockQuantity()
        );

        // Create and add the semantic document to the vector store
        Document document = new Document(
                UUID.randomUUID().toString(),
                contentToEmbed,
                Map.of("productId", String.valueOf(savedProduct.getId()))
        );

        // Store product data in vector DB
        vectorStore.add(List.of(document));

        return savedProduct;
    }


    public void deleteProduct(int id) {
        productRepo.deleteById(id);
    }

    @Transactional
    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
