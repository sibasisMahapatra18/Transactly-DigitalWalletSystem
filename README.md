# 💼 Digital Wallet Backend – Spring Boot + PostgreSQL

This is a **backend-only** digital wallet system that simulates real-world digital transactions, built using **Spring Boot** and **PostgreSQL**. It supports secure user authentication, wallet funding, peer-to-peer payments, product purchasing, and transaction tracking. Currency conversion is also supported through a live external API.

There’s no UI here — just a clean, well-structured API meant to showcase backend engineering, authentication, and integration practices.

---

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

---

## 🔐 Authentication

All protected routes require **Basic Authentication**.

Clients should pass this HTTP header:
Authorization: Basic <base64(username:password)>


Passwords are securely hashed using **BCrypt** and never stored in plaintext.

---

## 🌐 Currency Conversion

To support currency conversion, this app integrates with [https://currencyapi.com](https://currencyapi.com). The wallet internally uses **INR** as the base currency, and users can optionally fetch their balance in another currency like `USD`, `EUR`, etc.

---

## 🛠️ Tech Stack

| Layer         | Technology               |
|---------------|--------------------------|
| Language      | Java                     |
| Framework     | Spring Boot              |
| ORM           | Spring Data JPA + Hibernate |
| Database      | PostgreSQL               |
| Auth Method   | Basic Auth + BCrypt      |
| API Docs      | Swagger (optional)       |
| Build Tool    | Maven                    |

---

## 📦 API Reference

### 1. Register a User  
**POST** `/register`  
No auth required.

```json
{
  "username": "ashu",
  "password": "hunter2"
}
Success: 201 Created

2. Fund Wallet
POST /fund
Requires Basic Auth.

json

{
  "amt": 10000
}
Success Response:

json

{
  "balance": 10000
}
3. Pay Another User
POST /pay
Transfer funds to another registered user.

json

{
  "to": "priya",
  "amt": 100
}
Success Response:

json

{
  "balance": 9900
}
Error Response:

json

{
  "error": "Insufficient funds or user does not exist"
}
4. Check Wallet Balance
GET /bal?currency=USD
Returns balance in INR or any supported currency.

Response:

json

{
  "balance": 120.35,
  "currency": "USD"
}
5. Transaction History
GET /stmt
Returns all user transactions in reverse chronological order.

Response:

json

[
  { "kind": "debit", "amt": 100, "updated_bal": 9900, "timestamp": "2025-06-09T10:00:00Z" },
  { "kind": "credit", "amt": 10000, "updated_bal": 10000, "timestamp": "2025-06-09T09:00:00Z" }
]
6. Add a Product
POST /product
Requires Basic Auth.

json

{
  "name": "Wireless Mouse",
  "price": 599,
  "description": "2.4 GHz wireless mouse with USB receiver"
}
Success:

json

{
  "id": 1,
  "message": "Product added"
}
7. List All Products
GET /product
Public route — no auth needed.

json

[
  {
    "id": 1,
    "name": "Wireless Mouse",
    "price": 599,
    "description": "2.4 GHz wireless mouse with USB receiver"
  }
]
8. Buy a Product
POST /buy
Deducts wallet balance to purchase a product.

json

{
  "product_id": 1
}
Success Response:

json
Copy
Edit
{
  "message": "Product purchased",
  "balance": 9301
}
Failure:

json
Copy
Edit
{
  "error": "Insufficient balance or invalid product"
}
🚀 Getting Started
1. Clone the Repository
bash
Copy
Edit
git clone https://github.com/YOUR_USERNAME/DigitalWalletSystem.git
cd DigitalWalletSystem
2. Configure the Database
Update your application.properties file:

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/walletdb
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
3. Run the Application
bash
Copy
Edit
mvn spring-boot:run
4. Access the API
Test via:

Postman

Swagger UI (at http://localhost:8080/swagger-ui.html if enabled)

📌 Constraints & Notes
- Must use relational database — PostgreSQL used here.

- No use of in-memory storage — all data is persisted.

- No unstructured NoSQL (like MongoDB).

- Passwords are hashed and never exposed.

- External currency API used for live INR→Currency rates.

📄 Example API Call (Fund)
http
Copy
Edit
POST /fund
Authorization: Basic base64(username:password)
Content-Type: application/json

{
  "amt": 5000
}
✨ Future Enhancements
- JWT-based authentication

- Product ownership & purchase receipts

- Transaction filters (e.g., only debits or credits)

- Dockerize the application for deployment

- Scheduled transfers / auto-pay features

👤 Author
Created by Sibasis Mahapatra
If you found this helpful or want to contribute, feel free to fork or open an issue.
