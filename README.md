# Insurance Microservices - Sinarmas Assessment

A distributed Microservices built with Spring Boot, demonstrating Insurance Policy Lifecycle 
(Product Selection → Customer Profile → Policy Issuance → Payment Processing → Claims).

## Tech Stack
* **Java 17**
* **Spring Boot 3.5.8**
* **Spring Cloud Gateway** (Reactive)
* **Spring Web & WebFlux**
* **Spring Data JPA** (Hibernate)
* **MySQL** (Dockerized)
* **Docker & Docker Compose**
* **Gradle** (Multi-module build)

## Service Architecture
The system consists of **6 distinct microservices** and **1 shared library**:

| Service | Port (Docker) | Port (Host) | Description                                         |
| :--- |:--------------|:------------|:----------------------------------------------------|
| **API Gateway** | `8080`        | `8080`      | Single entry point, routes traffic.                 |
| **Product Service** | `8080`        | `8082`      | Catalog of insurance plans (Health, Vehicle, Life). |
| **Customer Service** | `8080`        | `8083`      | User profiles (NIK, Address).                       |
| **Policy Service** | `8080`        | `8084`      | Manages sales and active policies.                  |
| **Payment Service** | `8080`        | `8085`      | Transaction processing.                             |
| **Claim Service** | `8080`        | `8086`      | Validation and submission of insurance claims.      |
| **Common Lib** | N/A           | N/A         | Shared DTOs, Utils, and Constants.                  |

---

## API Endpoints

### 1. Product Service
**Base Path:** `/api/v1/product`

| Method   | Endpoint              |
|:---------|:----------------------|
| `GET`    | `Get all Products`    |
| `GET`    | `Get Product Details` |
| `PUT`    | `Update Products`     |
| `POST`   | `Add Product`         |
| `DELETE` | `Delete Product`      |

### 2. Customer Service
**Base Path:** `/api/v1/customer`

| Method | Endpoint               |
|:-------|:-----------------------| 
| `GET`  | `Get Customer Details` |
 | `POST`| `Add Customer`      |

### 3. Policy Service
**Base Path:** `/api/v1/policy`

| Method | Endpoint             |
|:-------|:---------------------| 
| `GET`  | `Get Policy Details` |
| `POST` | `Add Policy`         |
| `PUT`  | `Activate Policy`    |

### 4. Payment Service
**Base Path:** `/api/v1/payment`

| Method | Endpoint                             |
|:-------|:-------------------------------------| 
| `GET`  | `Get Payment receipt`                |
| `POST` | `Submit Payment to activate Policy ` |


### 5. Claim Service
**Base Path:** `/api/v1/claim`


| Method | Endpoint                 |
|:-------|:-------------------------| 
| `GET`  | `Get Claim details`      |
| `POST` | `Submit Claims proposal` |

