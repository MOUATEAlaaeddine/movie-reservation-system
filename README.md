# 🎬 Movie Reservation System

> A full-stack cinema booking system built with Spring Boot, featuring real-time seat reservation, JWT authentication, and AI-powered movie recommendations.

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 🚀 Features

### 🎯 Core Features
- ✅ **User Authentication** - Secure registration and login with BCrypt encryption
- ✅ **Role-Based Access Control** - Admin and User roles with different permissions
- ✅ **Movie Management** - Full CRUD operations for movies and genres
- ✅ **Seat Reservation** - Real-time seat booking with concurrency handling
- ✅ **Showtime Scheduling** - Dynamic movie schedules
- ✅ **Search & Filter** - Find movies by title, genre, or rating

### 🤖 Advanced Features
- 🔐 **JWT Authentication** - Token-based secure authentication
- 🎨 **RESTful API Design** - Clean, consistent API endpoints
- 📧 **Email Notifications** - Booking confirmations and reminders
- 🤖 **AI Recommendations** - OpenAI-powered movie suggestions
- 📊 **Admin Dashboard** - Analytics and reporting
- 🌐 **CORS Support** - Ready for frontend integration

---

## 🏗️ Architecture
```
┌─────────────┐
│   Client    │  (React/Vue/Mobile App)
└──────┬──────┘
       │ HTTP/REST
┌──────▼──────────────────────┐
│  Spring Boot Application    │
│  ┌────────────────────────┐ │
│  │ Controller Layer       │ │  API Endpoints
│  └───────┬────────────────┘ │
│  ┌───────▼────────────────┐ │
│  │ Service Layer          │ │  Business Logic
│  └───────┬────────────────┘ │
│  ┌───────▼────────────────┐ │
│  │ Repository Layer       │ │  Data Access
│  └───────┬────────────────┘ │
└──────────┼──────────────────┘
           │ JDBC
┌──────────▼──────────────────┐
│   PostgreSQL Database       │
└─────────────────────────────┘
```

---

## 🛠️ Tech Stack

**Backend:**
- Java 17
- Spring Boot 3.2.0
- Spring Security 6.x
- Spring Data JPA
- Hibernate

**Database:**
- PostgreSQL 14+

**Security:**
- BCrypt Password Encryption
- JWT Token Authentication

**Tools:**
- Maven
- Lombok
- SLF4J Logging

---

## 📦 Installation

### Prerequisites
- Java 17 or higher
- PostgreSQL 14+
- Maven 3.6+

### 1. Clone the repository
```bash
git clone https://github.com/MOUATEAlaaeddine/movie-reservation-system.git
cd movie-reservation-system
```

### 2. Configure Database
Create a PostgreSQL database:
```sql
CREATE DATABASE cinema_db;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cinema_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 3. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

---

## 📚 API Endpoints

### Authentication
```http
POST   /api/auth/register       # Register new user
POST   /api/auth/login          # User login
GET    /api/auth/users          # Get all users (Admin)
POST   /api/auth/promote        # Promote user to admin
```

### Movies
```http
GET    /api/movies              # Get all movies
GET    /api/movies/{id}         # Get movie by ID
POST   /api/movies              # Create movie (Admin)
PUT    /api/movies/{id}         # Update movie (Admin)
DELETE /api/movies/{id}         # Delete movie (Admin)
GET    /api/movies/search?title=  # Search movies
```

### Genres
```http
GET    /api/genres              # Get all genres
POST   /api/genres              # Create genre (Admin)
PUT    /api/genres/{id}         # Update genre (Admin)
DELETE /api/genres/{id}         # Delete genre (Admin)
```

### Reservations
```http
GET    /api/reservations        # Get user reservations
POST   /api/reservations        # Create reservation
DELETE /api/reservations/{id}   # Cancel reservation
```

📖 **Full API Documentation:** [API_DOCS.md](docs/API_DOCS.md)

---

## 🧪 Testing

### Quick Test
```bash
# Health check
curl http://localhost:8080/api/test/hello

# Register user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "alaa",
    "email": "alaa@cinema.com",
    "password": "password123"
  }'
```

### Run Tests
```bash
mvn test
```

---

## 📁 Project Structure
```
src/main/java/com/cinema/moviereservation/
├── config/              # Configuration classes
├── controller/          # REST API endpoints
│   ├── auth/
│   ├── movie/
│   └── reservation/
├── dto/                 # Data Transfer Objects
│   ├── request/
│   └── response/
├── entity/              # Database models
├── repository/          # Data access layer
├── service/             # Business logic
│   ├── auth/
│   ├── movie/
│   └── reservation/
├── exception/           # Error handling
├── security/            # JWT & authentication
└── util/                # Helper classes
```

---

## 🚀 Deployment

### Docker
```bash
docker build -t movie-reservation-system .
docker run -p 8080:8080 movie-reservation-system
```

### Heroku
```bash
heroku create movie-reservation-system
git push heroku main
```

📖 **Full Deployment Guide:** [DEPLOYMENT.md](docs/DEPLOYMENT.md)

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Alaa Eddine Mouate**
- GitHub: [@MOUATEAlaaeddine](https://github.com/MOUATEAlaaeddine)
- Email: alaa@cinema.com

---[Genre.java](src/main/java/com/cinema/moviereservation/entity/Genre.java)

## 🙏 Acknowledgments

- Spring Boot Team
- PostgreSQL Community
- OpenAI for AI recommendations

---

<div align="center">
  Made with ❤️ and ☕ by Alaa Eddine Mouate
</div>