# FreelanceApp

## Overview

FreelanceApp is a platform where clients can post IT projects and freelancers can apply,
collaborate, and get reviewed, similar to Upwork or Freelancer.com.

---

## Features

### Authentication
- JWT-based authentication with role-based access control
- Two roles: **CLIENT** and **FREELANCER**
- Secure password hashing with BCrypt

### Projects
- Clients can create, view, and delete projects
- Public project listing (no login required)
- Project status lifecycle: `OPEN → IN_PROGRESS → COMPLETED / CANCELED`

### Applications
- Freelancers can apply to open projects with a proposal
- Clients can accept or reject applications
- Duplicate application prevention

### Contracts
- Automatically created when a client accepts an application
- Contract lifecycle: `ACTIVE → COMPLETED / CANCELED`
- Clients can set end dates and complete or cancel contracts

### Reviews
- Both client and freelancer can leave a review after contract completion
- Scores from 1 to 5
- Freelancer rating automatically updated after each client review

---

## Tech Stack

### Backend
| Technology | Purpose |
|---|---|
| Java 17 | Programming language |
| Spring Boot 3.x | Application framework |
| Spring Security | Authentication & authorization |
| Spring Data JPA | Database access layer |
| Hibernate | ORM |
| JWT (jjwt 0.12.3) | Token-based authentication |
| MapStruct | Object mapping |
| Lombok | Boilerplate reduction |
| PostgreSQL | Relational database |
| Docker | Database containerization |

### Frontend
| Technology | Purpose |
|---|---|
| Angular 17+ | Frontend framework |
| TypeScript | Programming language |
| Angular Material | UI component library |
| RxJS | Reactive programming |

### Database Schema
<img width="2181" height="1267" alt="FreelanceDB" src="https://github.com/user-attachments/assets/f0a0b2be-fb76-48b7-bb8f-b2afb0b03c40" />

---

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- Docker Desktop
- Maven

### 1. Clone the repository
```bash
git clone https://github.com/Mirela89/FreelanceApp.git
cd FreelanceApp
```

### 2. Install frontend dependencies
```bash
cd frontend
npm install
cd ..
```

### 3. Start all services
```bash
.\start.ps1
```

This script will:
- Start the PostgreSQL Docker container
- Wait for the database to be ready
- Start the Spring Boot backend
- Start the Angular frontend

| Service  | URL                   |
|----------|-----------------------|
| Frontend | http://localhost:4200 |
| Backend  | http://localhost:8081 |

### 4. Stop all services
Press `Ctrl+C` in the terminal where the script is running.