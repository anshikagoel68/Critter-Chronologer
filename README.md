# Critter Chronologer

A RESTful scheduling service for a pet care company, built with **Java 21** and **Spring Boot 3.3**. It allows staff to manage customers, their pets, employees, and care schedules through a clean layered API.

---

## Features

- Register and manage **customers** and their pets
- Register **employees** with defined skills and weekly availability
- Create **care schedules** assigning employees to pets on specific dates
- Query schedules by pet, employee, or customer
- Find available employees for a requested date and skill set
- MySQL for production; in-memory H2 for tests

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.3.5 |
| Persistence | Spring Data JPA / Hibernate |
| Database (prod) | MySQL 8 |
| Database (test) | H2 (in-memory) |
| Build Tool | Maven |
| Utilities | Google Guava 32 |
| Testing | Spring Boot Test (JUnit) |

---

## Project Structure

```
critter/
└── src/
    ├── main/java/com/udacity/jdnd/course3/critter/
    │   ├── CritterApplication.java        # Entry point
    │   ├── CritterController.java
    │   ├── entity/                        # JPA entities
    │   │   ├── Customer.java
    │   │   ├── Employee.java
    │   │   ├── Pet.java
    │   │   └── Schedule.java
    │   ├── user/                          # User DTOs & controller
    │   │   ├── UserController.java
    │   │   ├── CustomerDTO.java
    │   │   ├── EmployeeDTO.java
    │   │   ├── EmployeeRequestDTO.java
    │   │   └── EmployeeSkill.java         # Enum: PETTING, WALKING, FEEDING, MEDICATING, SHAVING
    │   ├── pet/                           # Pet DTOs & controller
    │   │   ├── PetController.java
    │   │   ├── PetDTO.java
    │   │   └── PetType.java               # Enum: CAT, DOG, LIZARD, BIRD, FISH, SNAKE, RABBIT, COW, OTHER
    │   ├── schedule/                      # Schedule DTOs & controller
    │   │   ├── ScheduleController.java
    │   │   └── ScheduleDTO.java
    │   ├── service/                       # Business logic
    │   │   ├── CustomerService.java
    │   │   ├── EmployeeService.java
    │   │   ├── PetService.java
    │   │   └── ScheduleService.java
    │   ├── repository/                    # Spring Data JPA repositories
    │   │   ├── CustomerRepository.java
    │   │   ├── EmployeeRepository.java
    │   │   ├── PetRepository.java
    │   │   └── ScheduleRepository.java
    │   ├── mapping/
    │   │   └── DataMapper.java            # DTO ↔ Entity conversion
    │   └── exception/
    │       └── ResourceNotFoundException.java
    └── test/java/.../CritterFunctionalTest.java
```

---

## Prerequisites

- Java 21+
- Maven 3.8+
- MySQL 8 running locally

---

## Getting Started

### 1. Create the database

```sql
CREATE DATABASE critter;
```

### 2. Configure credentials

Edit `src/main/resources/application.properties`:

```properties
server.port=8082

spring.datasource.url=jdbc:mysql://localhost:3306/critter?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=create
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.show-sql=true
```

> `ddl-auto=create` will drop and recreate tables on every startup. Change to `update` or `validate` for a persistent setup.

### 3. Build and run

```bash
cd critter
mvn spring-boot:run
```

The server starts on **http://localhost:8082**.

---

## API Reference

### Users — `/user`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/user/customer` | Register a new customer |
| `GET` | `/user/customer` | List all customers |
| `GET` | `/user/customer/pet/{petId}` | Find the owner of a pet |
| `POST` | `/user/employee` | Register a new employee |
| `POST` | `/user/employee/{employeeId}` | Get employee by ID |
| `PUT` | `/user/employee/{employeeId}` | Update employee availability |
| `GET` | `/user/employee/availability` | Find employees available for a date + skill set |

### Pets — `/pet`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/pet` | Register a new pet |
| `GET` | `/pet/{petId}` | Get a pet by ID |
| `GET` | `/pet` | List all pets |
| `GET` | `/pet/owner/{ownerId}` | List pets belonging to an owner |

### Schedules — `/schedule`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/schedule` | Create a new schedule |
| `GET` | `/schedule` | List all schedules |
| `GET` | `/schedule/pet/{petId}` | Schedules for a specific pet |
| `GET` | `/schedule/employee/{employeeId}` | Schedules for a specific employee |
| `GET` | `/schedule/customer/{customerId}` | Schedules for a specific customer |

---

## Data Model

**Employee skills** (`EmployeeSkill` enum): `PETTING`, `WALKING`, `FEEDING`, `MEDICATING`, `SHAVING`

**Pet types** (`PetType` enum): `CAT`, `DOG`, `LIZARD`, `BIRD`, `FISH`, `SNAKE`, `RABBIT`, `COW`, `OTHER`

**Employee availability** is stored per day of week (`java.time.DayOfWeek`).

**Schedule** links many employees ↔ many pets on a given date, along with the set of activities (skills) to be performed.

### Database Tables

| Table | Purpose |
|---|---|
| `customer` | Customer records |
| `pet` | Pet records (FK to customer) |
| `employee` | Employee records |
| `employee_skill` | Skills per employee (collection table) |
| `employee_availability` | Available days per employee (collection table) |
| `schedule` | Schedule records |
| `schedule_employee` | Many-to-many: schedule ↔ employee |
| `schedule_pet` | Many-to-many: schedule ↔ pet |
| `schedule_activity` | Activities per schedule (collection table) |

---

## Running Tests

Tests use an in-memory H2 database and do not require MySQL.

```bash
mvn test
```

Test configuration is in `src/test/resources/application.properties`. The functional test suite (`CritterFunctionalTest.java`) covers all major endpoints end-to-end.

---

## API Testing with Postman

A Postman collection is included at:

```
src/main/resources/Udacity.postman_collection.json
```

Import it into Postman to explore and manually test all endpoints.
