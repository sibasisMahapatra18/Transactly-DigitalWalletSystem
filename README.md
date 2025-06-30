# 💼 Digital Wallet Backend – Spring Boot + Neon Postgres

This is a **backend-only** digital wallet system that simulates real-world digital transactions, built using **Spring Boot** and **Neon Postgres**. It supports secure user authentication, wallet funding, peer-to-peer payments, product purchasing, and transaction tracking. Currency conversion is also supported through a live external API.

There’s no UI here — just a clean, well-structured API meant to showcase backend engineering, authentication, and integration practices.

## 🎯 Features

- ✅ User registration with password hashing  
- ✅ Basic Authentication (username:password)  
- ✅ Deposit funds into your wallet  
- ✅ Transfer money to another user  
- ✅ View current wallet balance (with optional currency conversion)  
- ✅ Complete transaction history  
- ✅ Product catalog (add + list)  
- ✅ Product purchasing using wallet funds  
- ✅ External API integration (CurrencyAPI)

## 🔐 Authentication

All protected routes require **Basic Authentication**.  
Clients must send this HTTP header:

```

Authorization: Basic \<base64(username\:password)>

````

Passwords are securely hashed using **BCrypt** and never stored in plaintext.

## 🌐 Currency Conversion

To support currency conversion, this app integrates with [https://currencyapi.com](https://currencyapi.com). The wallet internally uses **INR** as the base currency, and users can optionally fetch their balance in another currency like `USD`, `EUR`, etc.

## 🛠️ Tech Stack

| Layer         | Technology               |
|---------------|--------------------------|
| Language      | Java                     |
| Framework     | Spring Boot              |
| ORM           | Spring Data JPA + Hibernate |
| Database      | Neon Postgres            |
| Auth Method   | Basic Auth + BCrypt      |
| API Docs      | Postman                  |
| Build Tool    | Maven                    |

## 📦 API Reference

### 1. Register a User  
**POST** `/register`  
No auth required.
```json
{
  "username": "ashu",
  "password": "hunter2"
}
````

**Success:** `201 Created`

### 2. Fund Wallet

**POST** `/fund`
Requires Basic Auth.

```json
{
  "amt": 10000
}
```

**Success Response:**

```json
{
  "balance": 10000
}
```

### 3. Pay Another User

**POST** `/pay`
Transfer funds to another registered user.

```json
{
  "to": "priya",
  "amt": 100
}
```

**Success Response:**

```json
{
  "balance": 9900
}
```

**Error Response:**

```json
{
  "error": "Insufficient funds or user does not exist"
}
```

### 4. Check Wallet Balance

**GET** `/bal?currency=USD`
Returns balance in INR or any supported currency.

```json
{
  "balance": 120.35,
  "currency": "USD"
}
```

### 5. Transaction History

**GET** `/stmt`
Returns all user transactions in reverse chronological order.

```json
[
  { "kind": "debit", "amt": 100, "updated_bal": 9900, "timestamp": "2025-06-09T10:00:00Z" },
  { "kind": "credit", "amt": 10000, "updated_bal": 10000, "timestamp": "2025-06-09T09:00:00Z" }
]
```

### 6. Add a Product

**POST** `/product`
Requires Basic Auth.

```json
{
  "name": "Wireless Mouse",
  "price": 599,
  "description": "2.4 GHz wireless mouse with USB receiver"
}
```

**Success:**

```json
{
  "id": 1,
  "message": "Product added"
}
```

### 7. List All Products

**GET** `/product`
Public route — no auth needed.

```json
[
  {
    "id": 1,
    "name": "Wireless Mouse",
    "price": 599,
    "description": "2.4 GHz wireless mouse with USB receiver"
  }
]
```

### 8. Buy a Product

**POST** `/buy`
Deducts wallet balance to purchase a product.

```json
{
  "product_id": 1
}
```

**Success Response:**

```json
{
  "message": "Product purchased",
  "balance": 9301
}
```

**Failure:**

```json
{
  "error": "Insufficient balance or invalid product"
}
```

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/DigitalWalletSystem.git
cd DigitalWalletSystem
```

### 2. Configure the Database

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/walletdb
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

### 4. Access the API

Use Postman or Swagger at:
`http://localhost:8080/swagger-ui.html`

## 📌 Constraints & Notes

* ✅ Use a structured SQL database (PostgreSQL is used here)
* ❌ Do not use in-memory or NoSQL databases like MongoDB
* ✅ Passwords are stored securely (BCrypt)
* ✅ Clean JSON response format on all APIs
* ✅ Edge cases like insufficient balance or invalid users are handled with proper HTTP status codes

## 📄 Example API Call (Fund Wallet)

```http
POST /fund
Authorization: Basic base64(username:password)
Content-Type: application/json

{
  "amt": 5000
}
```

## ✨ Future Enhancements

* Switch to JWT Auth for stateless sessions
* Add product ownership & purchase receipts
* Allow downloading transaction statements as PDF
* Dockerize for containerized deployment
* Add role-based access for admin/product APIs

## 📜 License

This project is intended for demonstration and educational purposes only.

## 👤 Author

Created by Sibasis Mahapatra.
If you found this useful, feel free to fork, improve, or reuse.

