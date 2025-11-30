# Vacation Booking Platform

**[GitHub](https://github.com/codyholm/travel-itinerary-planner)**

A travel itinerary planner for vacation and excursion booking, featuring a Spring Boot REST API backend with MySQL for persistent order management.

---

## Features

- **Vacation Packages** – Browse vacation packages from multiple destinations with images and pricing.
- **Excursions & Add-ons** – Each vacation includes selectable excursions to customize trips.
- **Shopping Cart** – Support for multiple vacation packages and excursions per booking.
- **Customer Checkout** – Checkout process with order tracking number generation.
- **Validation & Error Handling** – Input validation with clear error responses.
- **Sample Data** – Database comes pre-populated with demo vacations, excursions, and customers.

---

## Tech Stack

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)

- **Backend**: Java 17, Spring Boot 3.4, Spring Data JPA, Spring Data REST
- **Database**: MySQL 8.0 (via Docker)
- **Build**: Maven

---

## Getting Started

### Prerequisites
- Java 17+
- Docker & Docker Compose

### Run Locally

1. **Start the database**
   ```bash
   docker-compose up -d
   ```

2. **Start the backend**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Test the API**
   - Vacations: http://localhost:8080/api/vacations
   - Excursions: http://localhost:8080/api/excursions
   - Countries: http://localhost:8080/api/countries
   - Checkout: `POST http://localhost:8080/api/checkout/purchase`

---

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/vacations` | List all vacation packages |
| GET | `/api/vacations/{id}` | Get vacation details |
| GET | `/api/excursions` | List all excursions |
| GET | `/api/countries` | List all countries |
| GET | `/api/divisions` | List all divisions (states/provinces) |
| POST | `/api/checkout/purchase` | Place an order |

---

## Project Structure

```
src/main/java/com/example/demo/
├── controllers/      # REST endpoints
├── services/         # Business logic
├── dao/              # JPA repositories
├── entities/         # Database models
├── config/           # Spring configuration
└── bootstrap/        # Sample data loader
```

---

## Key Implementation Details

### Architecture
- **Controllers**: REST endpoints for checkout operations
- **Services**: Business logic with transactional order processing
- **Repositories**: Spring Data JPA with auto-generated CRUD endpoints
- **Entities**: JPA models with proper relationships (OneToMany, ManyToMany)

### Data Model
- **Vacation** → has many **Excursions**
- **Customer** → belongs to **Division** → belongs to **Country**
- **Cart** → has many **CartItems** → each links a Vacation + selected Excursions

---

## Potential Improvements
- Add Angular frontend for complete user experience
- User authentication (register/login) with admin and customer roles
- Order history viewing
- Enhanced search and filtering
- Vacation image gallery integration

---

## Author

**Cody Holm**

- GitHub: [@CodyHolm](https://github.com/CodyHolm)
- Portfolio: [codyholm.com](https://codyholm.com)
