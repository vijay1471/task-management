# 📌 Task Management Application

A RESTful Task Management API built using **Java 17** and **Spring Boot 3**. This application allows users to create, update, delete, and retrieve tasks with support for **pagination, sorting, and filtering**.

---

## 🚀 Features

* Create a new task
* Retrieve all tasks (with pagination, sorting, filtering)
* Retrieve a task by ID
* Update an existing task
* Delete a task
* Validation for future due dates
* H2 in-memory database for easy testing

---

## 🛠️ Tech Stack

* Java 17
* Spring Boot 3
* Spring Data JPA
* H2 Database
* Hibernate Validator
* Mockito (for testing)

---

## 📦 Dependencies

Make sure the following dependencies are included:

### Gradle

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-validation'

    runtimeOnly 'com.h2database:h2'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

---

## ▶️ How to Run the Application

1. Clone the repository
2. Navigate to project directory
3. Run the application:

### Using Gradle

```
./gradlew bootRun
```

### Or from IDE

Run the main class annotated with `@SpringBootApplication`

---

## 🌐 API Endpoints

Base URL:

```
/api/tasks
```

### 🔹 Create Task

```
POST /api/tasks
```

### 🔹 Get All Tasks (Pagination + Filtering)

```
GET /api/tasks
```

#### Optional Headers:

* `page` → Page number (default: 0)
* `size` → Page size (default: 10)
* `status` → Filter by task status (e.g., PENDING, COMPLETED)

#### Default Sorting:

* Sorted by `dueDate`

---

### 🔹 Get Task by ID

```
GET /api/tasks/{id}
```

---

### 🔹 Update Task

```
PUT /api/tasks/{id}
```

---

### 🔹 Delete Task

```
DELETE /api/tasks/{id}
```

#### Response:

* `204 No Content` on successful deletion

---

## 🧾 Sample Request JSON

```
{
    "title": "Test",
    "description": "sample test description",
    "status": "PENDING",
    "dueDate": "2026-04-12"
}
```

---

## 🧪 Testing

* Use **Postman** or **cURL** to test APIs
* H2 Console available at:

```
http://localhost:8080/h2-console
```

### H2 Login Details:

* JDBC URL: `jdbc:h2:mem:taskdb`
* Username: `sa`
* Password: (leave empty)

---

## ⚠️ Validations

* `dueDate` must be in format: `yyyy-MM-dd`
* `dueDate` must be a future date

---

## 📌 Notes

* Pagination and filtering headers are optional
* If not provided, default values will be used
* Application uses in-memory DB → data resets on restart

---

## 🚀 Future Enhancements

* Add authentication (JWT)
* Add Swagger/OpenAPI documentation
* Persistent database (MySQL/PostgreSQL)
* Advanced filtering & sorting

---

## 👨‍💻 Author

Developed as part of a Spring Boot practice project for building REST APIs with proper validation, pagination, and transaction management.

---
