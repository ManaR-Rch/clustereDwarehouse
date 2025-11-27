# 💱 ClusteredData Warehouse – FX Deals

<div align="center">



**A robust solution for centralized forex transaction management**

[Features](#-features) • [Installation](#-installation) • [API](#-api) • [Testing](#-testing)

</div>

---

## 📋 Overview

Professional **Spring Boot** application designed to manage a data warehouse of forex transactions (FX Deals) for financial information systems like Bloomberg.

The system ensures **integrity**, **traceability**, and **reliability** of real-time currency exchange data with a scalable, containerized architecture.

### ✨ Features

- 📥 **Deal import** with comprehensive business field validation
- 🔒 **Duplicate prevention** via unique identifier
- ✅ **Rigorous validation**: ISO formats, amounts, currencies
- 💾 **PostgreSQL persistence** with ACID transactions
- 🚨 **Advanced error handling** with explicit messages
- 🐳 **Production-ready Docker** deployment
- 🧪 **Unit testing** with JUnit 5 and Mockito

---

## 🏗️ Architecture

```
┌─────────────┐      ┌──────────────┐      ┌─────────────┐
│   Client    │─────▶│  Spring Boot │─────▶│ PostgreSQL  │
│   (REST)    │◀─────│   REST API   │◀─────│   Database  │
└─────────────┘      └──────────────┘      └─────────────┘
```

**Tech Stack**

| Technology | Version | Role |
|------------|---------|------|
| Java | 17 | Primary language |
| Spring Boot | 3.1+ | Backend framework |
| PostgreSQL | 15 | Database |
| Docker | Latest | Containerization |
| Maven | 3.8+ | Dependency management |
| JUnit 5 | 5.9+ | Unit testing |

---

## 🚀 Installation

### Prerequisites

- Docker Desktop installed
- Git
- (Optional) Java 17 and Maven for local development

### Quick Start

```bash
# 1. Clone the repository
git clone https://github.com/ManaR-Rch/clustered-data-warehouse.git
cd clustered-data-warehouse

# 2. Launch with Docker Compose
docker-compose up --build

# 3. API available at
# http://localhost:8080
```

### Local Setup (without Docker)

```bash
# 1. Start PostgreSQL locally on port 5432

# 2. Create the database
createdb fxdeals

# 3. Build and run the application
mvn clean install
mvn spring-boot:run
```

---

## 📡 API

### Main Endpoint

#### **POST** `/api/deals`

Import a new forex deal into the system.

**Request Body**

```json
{
  "dealId": "FX20240101001",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2024-01-15T10:30:00Z",
  "amount": 1000.50
}
```

**Success Response (201 Created)**

```json
{
  "id": 1,
  "dealId": "FX20240101001",
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "timestamp": "2024-01-15T10:30:00Z",
  "amount": 1000.50
}
```

**Error Response (400 Bad Request)**

```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "status": 400,
  "error": "Validation Failed",
  "message": "Deal ID already exists"
}
```

### Field Validation

| Field | Type | Constraints |
|-------|------|-------------|
| `dealId` | String | Required, unique |
| `fromCurrency` | String | ISO 4217 code (3 letters) |
| `toCurrency` | String | ISO 4217 code (3 letters) |
| `timestamp` | ISO DateTime | ISO-8601 format |
| `amount` | Decimal | > 0 |

### cURL Examples

```bash
# Import a valid deal
curl -X POST http://localhost:8080/api/deals \
  -H "Content-Type: application/json" \
  -d '{
    "dealId": "FX20240115001",
    "fromCurrency": "GBP",
    "toCurrency": "JPY",
    "timestamp": "2024-01-15T14:25:00Z",
    "amount": 5000.00
  }'

# Duplicate attempt (returns 400)
curl -X POST http://localhost:8080/api/deals \
  -H "Content-Type: application/json" \
  -d '{
    "dealId": "FX20240115001",
    "fromCurrency": "GBP",
    "toCurrency": "JPY",
    "timestamp": "2024-01-15T14:25:00Z",
    "amount": 5000.00
  }'
```

---

## 🧪 Testing

```bash
# Run all tests
mvn test

# Tests with coverage report
mvn clean verify

# Test specific class
mvn test -Dtest=FxDealServiceTest
```

**Test Coverage**
- Service layer unit tests
- Repository integration tests
- REST endpoint validation
- Error case handling

---

## 🐳 Docker Configuration

**Container Structure**

```yaml
services:
  postgres:
    - Port: 5432
    - Database: fxdeals
    - User: postgres
  
  app:
    - Port: 8080
    - Depends on: postgres
```

**Useful Docker Commands**

```bash
# Start in detached mode
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down

# Clean volumes and images
docker-compose down -v --rmi all
```

---

## 📁 Project Structure

```
clustered-data-warehouse/
├── src/
│   ├── main/
│   │   ├── java/com/progressoft/fxdeals/
│   │   │   ├── controller/      # REST Controllers
│   │   │   ├── service/         # Business Logic
│   │   │   ├── repository/      # Data Access
│   │   │   ├── model/           # Entities
│   │   │   └── exception/       # Error Handling
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Unit tests
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

---

## 🔧 Configuration

**application.properties**

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/fxdeals
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Server
server.port=8080
```

---

## 🤝 Contributing

Contributions are welcome!

1. Fork the project
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## 👤 Author

**Your Name**

- GitHub: [@your-username](https://github.com/ManaR-Rch)
- LinkedIn: [Your Profile](https://www.linkedin.com/in/manar-marchoube-a955a9337/)

---

<div align="center">

**⭐ Don't forget to star the project if you found it useful!**

Made with ❤️ for Bloomberg Data Warehouse

</div>