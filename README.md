# Vacation Booking Platform

A full-stack travel itinerary planner for vacation and excursion booking, built with a Spring Boot REST API, Angular frontend, and MySQL for persistent order management.

## Features

- **Vacation Package Browsing** – Search, filter, and explore vacation packages from multiple destinations.
- **Excursions & Add-ons** – Select excursions/add-ons for each vacation to customize your trip.
- **Shopping Cart** – Add multiple vacation packages and excursions, adjust quantities, and remove items.
- **Customer Checkout & Order Confirmation** – Seamless checkout process with order tracking and user-friendly confirmation.
- **Order History** – View all past bookings and details for each trip.
- **Validation & Error Handling** – Comprehensive input validation and clear error messages across all forms.
- **Sample Data Loaded Automatically** – The backend comes pre-populated with demo vacation packages and customers on startup.

## Tech Stack

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![Angular](https://img.shields.io/badge/Angular-DD0031?logo=angular&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)

- **Backend**: Java, Spring Boot, Spring Data JPA, REST API
- **Frontend**: Angular, TypeScript, RxJS, Bootstrap
- **Infrastructure**: MySQL, Maven, Docker

## Key Implementation Details

### Business Logic
- Shopping cart with support for multiple vacations/excursions per booking.
- Atomic checkout: processes bookings in a transaction and generates an order tracking number.
- Input validation for customer data, trip dates, vacation selection, and excursions.
- Sample data loader for demo customers, vacations, and excursions.

### Architecture
- **Backend**: Controllers (REST endpoints), Service layer (business logic), DAO layer (JPA repositories), Entities (ORM models), DTOs for REST payloads, centralized exception handling.
- **Frontend**: Angular component-based SPA, client-side routing, reusable services for API calls, RxJS for state management, Bootstrap for styling.

### Data Persistence
- Normalized MySQL schema for vacations, excursions, customers, carts, orders, order items, and regions.
- Spring Data JPA/Hibernate handles object-relational mapping.
- Seed data provided for demo and testing.

## Potential Improvements
- Add user authentication (register/login) supporting admin and customer roles.
- Vacation image gallery integration (Unsplash API or uploads).
- Enhanced mobile responsiveness and dark mode support.
- Add favorites/wishlist functionality and user-provided reviews.
- Internationalization (multi-language support).

## Author

**Cody Holm**

- GitHub: [@CodyHolm](https://github.com/CodyHolm)
