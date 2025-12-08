graph TD
%% Actors
Client([Client App / Postman])

    %% Gateway Layer
    subgraph "Edge Layer"
        Gateway[API Gateway <br/> Port: 8080]
    end

    %% Microservices Layer
    subgraph "Microservices Layer"
        Product[Product Service <br/> Port: 8082]
        Customer[Customer Service <br/> Port: 8083]
        Policy[Policy Service <br/> Port: 8084]
        Payment[Payment Service <br/> Port: 8085]
        Claim[Claim Service <br/> Port: 8086]
    end

    %% Database Layer
    subgraph "Data Persistence Layer"

        ProductDB[(Product DB)]
        CustomerDB[(Customer DB)]
        PolicyDB[(Policy DB)]
        PaymentDB[(Payment DB)]
        ClaimDB[(Claim DB)]
    end

    %% Routing Connections
    Client -->|HTTP / JSON| Gateway

    Gateway -->|/products| Product
    Gateway -->|/customers| Customer
    Gateway -->|/policies| Policy
    Gateway -->|/payments| Payment
    Gateway -->|/claims| Claim

    %% Inter-Service Communication (WebClient)
    Policy -.->|Get Price| Product
    Policy -.->|Validate User| Customer
    Payment -.->|Activate Policy| Policy
    Claim -.->|Check Status| Policy

    %% Database Connections

    Product --- ProductDB
    Customer --- CustomerDB
    Policy --- PolicyDB
    Payment --- PaymentDB
    Claim --- ClaimDB

    %% Styling
    
    class Product,Customer,Policy,Payment,Claim service;
    class ProductDB,CustomerDB,PolicyDB,PaymentDB,ClaimDB db;
    class Gateway gateway;