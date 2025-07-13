# 🛒 AI-Powered E-Commerce Product Management System with Smart Chatbot Support

## 📘 Executive Summary

The **AI-Powered E-Commerce Product Management System** is a feature-rich, intelligent backend platform built using **Spring Boot 3.5.3** and **Java 21**. It provides REST APIs for robust product and order management. Integrated with **Spring AI**, **OpenAI GPT-4o**, and **PGVector**, it enables smart chatbot-based customer support and semantic order/product search capabilities. The backend can be easily paired with any modern React frontend for a complete e-commerce experience.

---

## 📑 Table of Contents

* [Project Objective](#project-objective)
* [Core Features](#core-features)
* [Technologies Used](#technologies-used)
* [Dependencies](#dependencies)
* [System Architecture](#system-architecture)
* [API Specifications](#api-specifications)
* [AI Integrations](#ai-integrations)
* [PostgreSQL Extension Setup](#postgresql-extension-setup)
* [Installation Guide](#installation-guide)
* [Folder Structure (Backend)](#folder-structure-backend)

---

## 🎯 Project Objective

Build a smart, scalable e-commerce backend system that enables:

* Order processing and storage
* Smart chatbot for product and order assistance
* Semantic search and RAG using PGVector

---

## 🔧 Core Features

### ✅ Product & Order Modules

* CRUD operations on products
* Product image upload (manual only)
* Full order placement and tracking
* Store all products and orders semantically using PGVector

### 💬 Chatbot Module

* Ask product-related questions
* Query order status using order ID
* Built with RAG (Retrieval-Augmented Generation)

---

## 💡 Technologies Used

| Component      | Technology                            |
| -------------- | ------------------------------------- |
| Backend        | Spring Boot 3.5.3                    |
| ORM            | Hibernate, Spring Data JPA            |
| Database       | PostgreSQL + PGVector (Docker)        |
| AI Integration | Spring AI + OpenAI GPT-4o + Embedding |
| Frontend       | React.js (optional)                   |
| Containerization | Docker & Docker Compose             |
| Tools          | IntelliJ IDEA, VS Code, Postman       |
| Runtime        | Java 21                               |
| Build Tool     | Apache Maven                          |

---

## 📦 Dependencies

| Group ID                 | Artifact                                | Scope    |
| ------------------------ | --------------------------------------- | -------- |
| org.springframework.boot | spring-boot-starter-web                 | compile  |
| org.springframework.boot | spring-boot-starter-data-jpa            | compile  |
| org.springframework.boot | spring-boot-docker-compose              | runtime  |
| org.postgresql           | postgresql                              | runtime  |
| org.projectlombok        | lombok                                  | optional |
| org.springframework.ai   | spring-ai-starter-model-openai          | compile  |
| org.springframework.ai   | spring-ai-starter-vector-store-pgvector | compile  |

---

## 🧱 System Architecture

* **Controller Layer**: RESTful API exposure for Product, Order, and ChatBot
* **Service Layer**: Handles business logic + AI/VectorStore integration
* **Repository Layer**: JPA for PostgreSQL persistence
* **Entity Layer**: Product, Order, and OrderItem models

---

## 🔗 API Specifications

### Product APIs

| Endpoint                          | Method | Description       |
| --------------------------------- | ------ | ----------------- |
| `/api/products`                   | GET    | Get all products  |
| `/api/product/{id}`               | GET    | Get product by ID |
| `/api/product`                    | POST   | Add new product   |
| `/api/product/{id}`               | PUT    | Update product    |
| `/api/product/{id}`               | DELETE | Delete product    |
| `/api/products/search?keyword=xy` | GET    | Search products   |

### Order APIs

| Endpoint            | Method | Description       |
| ------------------- | ------ | ----------------- |
| `/api/orders/place` | POST   | Place a new order |
| `/api/orders`       | GET    | Get all orders    |

### ChatBot API

| Endpoint        | Method | Description                   |
| --------------- | ------ | ----------------------------- |
| `/api/chat/ask` | GET    | Ask questions to AI assistant |

---

## 🤖 AI Integrations

### Smart Chatbot (RAG)

* Built with `ChatClient` + `VectorStore`
* Retrieves context from PGVector and crafts helpful answers

---

## 🐳 Docker Configuration

### docker-compose.yml

```yaml
services:
  pgvector:
    image: 'pgvector/pgvector:pg16'
    environment:
      - 'POSTGRES_DB=telusko'
      - 'POSTGRES_PASSWORD=0076'
      - 'POSTGRES_USER=postgres'
    labels:
      - "org.springframework.boot.service-connection=postgres"
    ports:
      - '5432'
```

---

## 🧩 PostgreSQL Extension Setup (PGVector)

To enable semantic search:

1. **Enable Extensions**:

```sql
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
```

2. **Create Vector Table and Index**:

```sql
CREATE TABLE IF NOT EXISTS vector_store (
   id TEXT PRIMARY KEY,
   content TEXT,
   metadata JSONB,
   embedding VECTOR(1536)
);

CREATE INDEX IF NOT EXISTS vector_store_embedding_idx ON vector_store USING HNSW (embedding vector_cosine_ops);
```

3. **Spring Boot Setup** (in `application.properties`):

```properties
spring.sql.init.schema-locations=classpath:init/schema.sql
spring.sql.init.mode=always
spring.ai.openai.embedding.options.model=text-embedding-ada-002
```

---

## 🛠 Installation Guide

### Prerequisites
- Java 21 installed
- Docker Desktop running
- IntelliJ IDEA or any IDE

### 🚀 Quick Start

**Step 1:** Open the project in IntelliJ IDEA or your preferred IDE

**Step 2:** Ensure Docker Desktop is running

**Step 3:** Right-click on the root project directory and open terminal

**Step 4:** Start the PostgreSQL database with PGVector extension
```bash
docker-compose up -d
```

**Step 5:** Run the Spring Boot application
- Navigate to `SpringEcomApplication.java`
- Click the Run button or use `Ctrl+Shift+F10`

🎉 **Your AI-Powered E-Commerce Backend is now running!**

---

## 📂 Folder Structure (Backend)

```
com.telusko.springecom
├── config               # Configuration for AI, VectorStore
├── controller           # Product, Order, ChatBot APIs
├── model                # Entities & DTOs
├── repo                 # Spring JPA Repositories
├── service              # Business logic, AI integration
├── prompts              # RAG template file
└── SpringEcomApplication.java
```
---

## 👨‍💻 Author

Developed and maintained by **Telusko Team**.

---

> ⚙️ **Scalable. Intelligent. AI-Powered.** Ready for the next-gen E-Commerce experience.