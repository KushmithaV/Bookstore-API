# Bookstore API

A RESTful Spring Boot API for managing books, authors, and genres with H2 in-memory database, Spring Security authentication, and Swagger documentation.

## 🚀 Features

- **RESTful API** for Books, Authors, and Genres management
- **Spring Boot 3.2.0** with Java 17
- **H2 In-Memory Database** with web console
- **Spring Security** with HTTP Basic Authentication
- **Swagger UI** for interactive API documentation
- **Input Validation** with Bean Validation
- **Exception Handling** with custom error responses
- **JPA/Hibernate** for database operations

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- IntelliJ IDEA (recommended) or any Java IDE

## 🛠️ Setup Instructions

### 1. Clone/Download the Project
```bash
# If using git
git clone <repository-url>
cd bookstore-api

# Or extract the project files to your desired location
```

### 2. Import into IntelliJ IDEA
1. Open IntelliJ IDEA
2. Select **File > Open**
3. Navigate to the project folder and select it
4. Click **Open**
5. IntelliJ will automatically detect it as a Maven project

### 3. Load Maven Dependencies
1. IntelliJ will prompt you to "Load Maven Changes" - click **Yes**
2. Or click the **M** icon in the top-right corner
3. Wait for all dependencies to download

### 4. Run the Application
1. Navigate to `src/main/java/com/example/bookstore/BookstoreApplication.java`
2. Right-click on the file
3. Select **Run 'BookstoreApplication'**
4. The application will start on `http://localhost:8081`

## 🌐 API Endpoints

### Authors
- `GET /api/authors` - Get all authors
- `GET /api/authors/{id}` - Get author by ID
- `POST /api/authors` - Create new author
- `PUT /api/authors/{id}` - Update author
- `DELETE /api/authors/{id}` - Delete author

### Books
- `GET /api/books` - Get all books
- `GET /api/books/{id}` - Get book by ID
- `POST /api/books` - Create new book
- `PUT /api/books/{id}` - Update book
- `DELETE /api/books/{id}` - Delete book

### Genres
- `GET /api/genres` - Get all genres
- `GET /api/genres/{id}` - Get genre by ID
- `POST /api/genres` - Create new genre
- `PUT /api/genres/{id}` - Update genre
- `DELETE /api/genres/{id}` - Delete genre

## 🔐 Authentication

The API uses HTTP Basic Authentication:
- **Username**: `user`
- **Password**: `password`

### Public Endpoints (No Authentication Required)
- All `GET /api/**` endpoints
- `/h2-console/**` (H2 Database Console)

### Protected Endpoints (Authentication Required)
- All `POST`, `PUT`, `DELETE /api/**` endpoints
- `/swagger-ui/**` (Swagger UI)

## 📊 Database Access

### H2 Console
- **URL**: `http://localhost:8081/h2-console`
- **JDBC URL**: `jdbc:h2:mem:bookstoredb`
- **Username**: `sa`
- **Password**: `password`

## 📚 API Documentation

### Swagger UI
- **URL**: `http://localhost:8081/swagger-ui.html`
- **Authentication**: Use Basic Auth with `user`/`password`
- Interactive API documentation with request/response examples

### API Docs JSON
- **URL**: `http://localhost:8081/api-docs`

## 🧪 Testing the API

### Using Postman
1. Import the provided `Bookstore-API.postman_collection.json`
2. Configure Basic Authentication:
   - Go to **Authorization** tab
   - Select **Basic Auth**
   - Username: `user`
   - Password: `password`
3. Test the endpoints

### Using Swagger UI
1. Open `http://localhost:8081/swagger-ui.html`
2. Click **Authorize** button
3. Enter credentials: `user` / `password`
4. Click **Authorize**
5. Test endpoints directly from the UI

### Using curl
```bash
# Get all authors
curl -X GET http://localhost:8081/api/authors

# Create a new author (requires authentication)
curl -X POST http://localhost:8081/api/authors \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic dXNlcjpwYXNzd29yZA==" \
  -d '{"name": "J.K. Rowling"}'

# Create a new book
curl -X POST http://localhost:8081/api/books \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic dXNlcjpwYXNzd29yZA==" \
  -d '{
    "title": "Harry Potter and the Philosopher'\''s Stone",
    "isbn": "978-0747532699",
    "pageCount": 223,
    "authorId": 1
  }'
```

## 📁 Project Structure

```
bookstore-api/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── bookstore/
│       │               ├── BookstoreApplication.java
│       │               ├── config/
│       │               │   └── SecurityConfig.java
│       │               ├── controller/
│       │               │   ├── AuthorController.java
│       │               │   ├── BookController.java
│       │               │   └── GenreController.java
│       │               ├── entity/
│       │               │   ├── Author.java
│       │               │   ├── Book.java
│       │               │   └── Genre.java
│       │               ├── exception/
│       │               │   ├── GlobalExceptionHandler.java
│       │               │   └── ResourceNotFoundException.java
│       │               └── repository/
│       │                   ├── AuthorRepository.java
│       │                   ├── BookRepository.java
│       │                   └── GenreRepository.java
│       └── resources/
│           └── application.properties
├── pom.xml
├── Bookstore-API.postman_collection.json
└── README.md
```

## 🔧 Configuration

### Application Properties
The application is configured via `src/main/resources/application.properties`:

```properties
# Server Port
server.port=8081

# H2 Database Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Datasource Configuration
spring.datasource.url=jdbc:h2:mem:bookstoredb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Springdoc OpenAPI (Swagger) Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

## 🚨 Troubleshooting

### Port Already in Use
If you get "Port 8081 is already in use":
1. Change the port in `application.properties`:
   ```properties
   server.port=8082
   ```
2. Or kill the process using the port:
   ```bash
   # Windows
   netstat -ano | findstr :8081
   taskkill /PID <PID> /F
   
   # Linux/Mac
   lsof -ti:8081 | xargs kill -9
   ```

### Compilation Errors
If you encounter compilation errors:
1. Clean and rebuild:
   ```bash
   mvn clean compile
   ```
2. Refresh Maven dependencies in IntelliJ
3. Check Java version (should be 17+)

### Authentication Issues
- Ensure you're using the correct credentials: `user` / `password`
- For Swagger UI, click the "Authorize" button and enter credentials
- For Postman, configure Basic Authentication in the Authorization tab

## 📝 Sample Data

### Creating Sample Data via API

1. **Create Authors**:
```json
POST /api/authors
{
  "name": "J.K. Rowling"
}

POST /api/authors
{
  "name": "George R.R. Martin"
}
```

2. **Create Genres**:
```json
POST /api/genres
{
  "name": "Fantasy"
}

POST /api/genres
{
  "name": "Fiction"
}
```

3. **Create Books**:
```json
POST /api/books
{
  "title": "Harry Potter and the Philosopher's Stone",
  "isbn": "978-0747532699",
  "pageCount": 223,
  "authorId": 1
}
```

## 🎯 Next Steps

- Add more validation rules
- Implement pagination for large datasets
- Add search functionality
- Implement caching
- Add unit and integration tests
- Deploy to cloud platform

## 📄 License

This project is for educational purposes.

---

**Happy Coding! 🚀**

