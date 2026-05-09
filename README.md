#  Wildlife Conservation Management System

A backend REST API built with Spring Boot for managing wildlife rangers, animal species, and field observations in a nature reserve.

---

# Project Overview

Wildlife rangers working in the field need a reliable system to record what they see animal sightings, poaching incidents, injured animals. Conservation managers need to monitor trends and get alerts when endangered species are spotted. This system solves both problems.

---

# Technologies Used

| Technology | Purpose |
|---|---|
| Spring Boot 3.2 | Core framework |
| Spring Data JPA + Hibernate | Database ORM |
| H2 In-Memory Database | Embedded database (no installation needed) |
| Spring Security + JWT | Authentication and authorization |
| Spring AOP | Logging and performance monitoring |
| Springdoc OpenAPI (Swagger) | API documentation |
| JUnit 5 + Mockito | Unit testing |
| Lombok | Reduce boilerplate code |
| Maven | Dependency management |

---

## 📦 Project Structure

```
com.wildlife.conservation/
├── entity/          → JPA entities (Ranger, Species, Observation)
├── repository/      → Spring Data JPA interfaces
├── service/         → Business logic layer
├── controller/      → REST API endpoints
├── dto/             → Data Transfer Objects
├── exception/       → Custom exceptions and Global error handler
├── aspect/          → AOP logging and performance aspects
├── config/          → Security and Swagger configuration
└── security/        → JWT token provider and filter
```

---

## 🚀 How to Run

### Prerequisites
- Java 17 or above
- Spring Tool Suite (STS) or any IDE
- No database installation needed H2 is embedded

### Steps
1. Clone or download this repository
2. Open in STS: `File → Open → select the project folder`
3. Right-click project → `Maven → Update Project` → OK
4. Right-click project → `Run As → Spring Boot App`
5. Wait for console to show: **Wildlife Conservation System Started**

---

## 🌐 Quick Access URLs

| Page | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| H2 Database Console | http://localhost:8080/h2-console |
| API Documentation | http://localhost:8080/api-docs |

**H2 Console login:**
- JDBC URL: `jdbc:h2:mem:wildlifedb`
- Username: `sa`
- Password: *(leave blank)*

---

## 🔐 Default Login Credentials

| Role | Email | Password |
|---|---|---|
| ADMIN | admin@wildlife.com | admin123 |
| RANGER | john@wildlife.com | ranger123 |

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | /api/auth/login | Login and get JWT token |

### Species
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | /api/species | All | Get all species |
| GET | /api/species/{id} | All | Get one species |
| GET | /api/species/endangered | All | Get endangered only |
| POST | /api/species | Admin | Create species |
| PUT | /api/species/{id} | Admin | Update species |
| DELETE | /api/species/{id} | Admin | Delete species |

### Rangers
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | /api/rangers | All | Get all rangers |
| GET | /api/rangers/{id} | All | Get one ranger |
| POST | /api/rangers | Admin | Register ranger |
| PUT | /api/rangers/{id} | Admin | Update ranger |
| DELETE | /api/rangers/{id} | Admin | Delete ranger |

### Observations
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | /api/observations | All | Get all observations |
| GET | /api/observations/{id} | All | Get one observation |
| POST | /api/observations | All | Record observation |
| PUT | /api/observations/{id} | All | Update observation |
| DELETE | /api/observations/{id} | Admin | Delete observation |

### Reports
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | /api/reports/summary?start=...&end=... | Admin | Summary with alerts |

---

## 🧪 Testing

Run all tests in STS:
- Right-click project → `Run As → JUnit Test`
- All tests use H2 in-memory database — no setup needed

Test classes:
- `RangerServiceTest` — Service layer unit tests
- `ObservationServiceTest` — Report logic and service tests  
- `ObservationControllerTest` — Controller layer with MockMvc

---

## 🔒 How Authentication Works

1. Call `POST /api/auth/login` with email and password
2. Copy the `token` from the response
3. Add to every request: `Authorization: Bearer <token>`
4. Token expires after 24 hours

---

## 📊 Summary Report Feature

```json
{
  "startDate": "2026-01-01 00:00",
  "endDate": "2026-12-31 23:59",
  "observationsPerSpecies": {
    "Bengal Tiger": 3,
    "Spotted Deer": 7
  },
  "endangeredAlerts": [
    "ALERT: Endangered species 'Bengal Tiger' sighted at Zone A"
  ],
  "totalObservations": 10
}
```

---

## ⚙️ AOP Features

- **LoggingAspect** — Logs every service method call automatically
- **PerformanceAspect** — Tracks execution time, warns if over 1 second

---

## 👥 Built By

Advanced Java / Spring Boot Course Project — 2026
