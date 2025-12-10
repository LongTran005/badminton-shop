# 🏸 Badminton Shop

E-commerce platform for badminton equipment with stringing service.

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Contributing](#contributing)

## ✨ Features

### Customer Features
- 🛒 Shopping cart with guest support
- 🎯 Product filtering and search
- 🏷️ Coupon and promotions
- 📦 Order tracking
- ⭐ Product reviews and ratings
- 🔔 Price drop notifications
- 🎾 **Stringing service** - Special feature for racket stringing

### Admin Features
- 📊 Dashboard with analytics
- 📦 Product management
- 👥 Customer management
- 📋 Order management
- 🧵 **Stringing workflow** - Assign and track stringing jobs
- 🎫 Coupon management
- 📝 Content management (Blog, Banners)

## 🛠 Tech Stack

- **Backend**: Spring Boot 3.2.x, Java 17
- **Frontend**: Thymeleaf, Bootstrap 5, jQuery
- **Database**: MySQL 8.0
- **Security**: Spring Security 6, OAuth2 (Google)
- **Caching**: Caffeine
- **Email**: Spring Mail
- **File Storage**: Cloudinary / Local
- **Payment**: VNPay, MoMo, ZaloPay
- **API Docs**: SpringDoc OpenAPI (Swagger)

## 📁 Project Structure

```
badminton-shop/
├── src/
│   ├── main/
│   │   ├── java/com/badmintonshop/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/
│   │   │   │   ├── admin/       # Admin controllers
│   │   │   │   ├── customer/    # Customer controllers
│   │   │   │   └── api/         # REST API controllers
│   │   │   ├── dto/
│   │   │   │   ├── request/     # Request DTOs
│   │   │   │   └── response/    # Response DTOs
│   │   │   ├── entity/          # JPA entities
│   │   │   │   └── enums/       # Enum types
│   │   │   ├── exception/       # Custom exceptions
│   │   │   ├── mapper/          # Object mappers
│   │   │   ├── repository/      # JPA repositories
│   │   │   ├── security/        # Security classes
│   │   │   ├── service/         # Business logic
│   │   │   │   └── impl/        # Service implementations
│   │   │   ├── util/            # Utility classes
│   │   │   ├── validation/      # Custom validators
│   │   │   ├── event/           # Event classes
│   │   │   └── scheduler/       # Scheduled tasks
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── admin/       # Admin templates
│   │       │   ├── customer/    # Customer templates
│   │       │   ├── fragments/   # Reusable fragments
│   │       │   ├── email/       # Email templates
│   │       │   └── error/       # Error pages
│   │       ├── static/
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   ├── images/
│   │       │   └── vendor/
│   │       ├── messages/        # i18n messages
│   │       ├── db/migration/    # Database migrations
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   └── test/
│       └── java/com/badmintonshop/
├── logs/
├── uploads/
├── pom.xml
├── .gitignore
└── README.md
```

## 🚀 Getting Started

### Prerequisites

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- (Optional) Docker & Docker Compose

### Configuration

1. Clone the repository:
```bash
git clone https://github.com/your-team/badminton-shop.git
cd badminton-shop
```

2. Create MySQL database:
```sql
CREATE DATABASE badminton_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. Update database credentials in `application-dev.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/badminton_shop
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. (Optional) Configure OAuth2 for Google Login:
```properties
spring.security.oauth2.client.registration.google.client-id=your-client-id
spring.security.oauth2.client.registration.google.client-secret=your-client-secret
```

## 🗄 Database Setup

Run the MySQL schema file:
```bash
mysql -u root -p badminton_shop < badminton_shop_mysql_full.sql
```

Or use Flyway/Liquibase for migrations (recommended for team).

## ▶️ Running the Application

### Development Mode

```bash
# Using Maven
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Or using Maven Wrapper
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Production Mode

```bash
# Build
mvn clean package -Pprod -DskipTests

# Run
java -jar -Dspring.profiles.active=prod target/badminton-shop-1.0.0-SNAPSHOT.jar
```

### Access the Application

- **Customer Site**: http://localhost:8080
- **Admin Panel**: http://localhost:8080/admin
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## 📚 API Documentation

Swagger UI is available at `/swagger-ui.html` (disabled in production).

## 🧪 Testing

```bash
# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
```

## 👥 Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Commit changes: `git commit -m 'Add some feature'`
3. Push to branch: `git push origin feature/your-feature`
4. Open a Pull Request

### Code Style

- Follow Java naming conventions
- Use Lombok to reduce boilerplate
- Write unit tests for services
- Document public APIs

## 📝 License

This project is proprietary. All rights reserved.

---

Made with ❤️ by Badminton Shop Team
