# Travel Itinerary Planner

A full-stack vacation booking application built with Spring Boot and Angular.

---

## Features

- **Browse Vacations** - Responsive grid of 8 vacation packages with images
- **Excursion Selection** - Add optional excursions to each vacation with live pricing
- **Shopping Cart** - Manage selected vacations and excursions
- **Checkout** - Reactive form with country/state dropdowns and validation
- **Order Confirmation** - Tracking number generation after successful purchase

---

## Tech Stack

**Backend:**
- Spring Boot 3.4.5 (Java 17)
- Spring Data JPA & REST
- MySQL 8.0 (Docker)
- Jakarta Bean Validation

**Frontend:**
- Angular 17 (standalone components)
- Bootstrap 5
- TypeScript (strict mode)
- RxJS (reactive state management)

## Setup & Run

### Prerequisites
- Docker Desktop installed
- Node.js 18+ and npm
- Java 17+

### Backend

1. Start MySQL:
```bash
docker-compose up -d
```

2. Run Spring Boot:
```bash
./mvnw.cmd spring-boot:run
```

Backend runs at `http://localhost:8080/api`

### Frontend

1. Install dependencies:
```bash
cd frontend
npm install
```

2. Start development server:
```bash
npm start
```

Frontend runs at `http://localhost:4200`

## Usage
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
