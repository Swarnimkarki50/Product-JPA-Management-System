# Product JPA Management System

A Spring Boot web application for managing product inventory with JPA, Thymeleaf, and file upload support.

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.6**
- **Spring Data JPA**
- **Thymeleaf** (server-side templates)
- **H2 Database** (development) / **PostgreSQL** (production)
- **Gradle** (build tool)
- **Lombok**

## Features

- Create, read, update, and delete products
- Upload product images
- Search products by name
- View product details
- Responsive UI with modern CSS design

## Prerequisites

- Java 21+
- Gradle (or use the included `gradlew` wrapper)

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/Swarnimkarki50/Product-JPA-Management-System.git
   cd Product-JPA-Management-System
   ```

2. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

3. **Access the app**
   Open [http://localhost:8083](http://localhost:8083)

## API Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/` | Home page |
| GET | `/products` | Redirect to product list |
| GET | `/products/list` | List all products |
| GET | `/products/search?keyword=` | Search products by name |
| GET | `/products/new` | Show create product form |
| POST | `/products/save` | Save new product |
| GET | `/products/view/{id}` | View product details |
| GET | `/products/edit/{id}` | Show edit product form |
| POST | `/products/update/{id}` | Update existing product |
| GET | `/products/delete/{id}` | Delete a product |

## Configuration

Default configuration uses an H2 file-based database stored in `./data/productdb`. For production, set the `spring.profiles.active=render` to use PostgreSQL.

## Deploy to Render

[![Deploy to Render](https://render.com/images/deploy-to-render-button.svg)](https://render.com/deploy)

The project includes a `render.yaml` for easy deployment to Render.

## Build

```bash
./gradlew clean bootJar -x test
```

The JAR file will be created at `build/libs/productjpa-0.0.1-SNAPSHOT.jar`.
