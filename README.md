# Travel Itinerary Planner

**[GitHub](https://github.com/codyholm/travel-itinerary-planner)**

A full-stack travel itinerary planner for vacation and excursion booking. Spring Boot REST API backend with MySQL, Angular 17 frontend with responsive Bootstrap UI.

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
![Angular](https://img.shields.io/badge/Angular-DD0031?logo=angular&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=white)


- **Backend**: Java 17, Spring Boot 3.4, Spring Data JPA, Spring Data REST
- **Frontend**: Angular 17, TypeScript, Bootstrap 5
- **Database**: MySQL 8.0 (via Docker)
- **Build**: Maven

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
- User profile management and wishlist functionality
- User authentication (register/login) with admin and customer roles
- Order history viewing
- Enhanced search and filtering
- Vacation image gallery integration

---

## Author

**Cody Holm**

- GitHub: [@CodyHolm](https://github.com/CodyHolm)
- Portfolio: [codyholm.com](https://codyholm.com)
