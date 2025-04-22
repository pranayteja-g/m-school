# M-School - School Management System 🏫

A backend application developed using **Spring Boot** that handles student and employee data securely through **role-based access**. Designed with clean architecture, modularity, and security as priorities.

---

## 🚀 Features

- 🔐 **JWT Authentication**: Secure login for students and employees
- 🎓 **Student Module**: View fees, results, and basic profile access
- 👨‍🏫 **Employee Module**: Manage salaries, update student details
- 🧾 **Fee Management**
- 📊 **Exam Result Management**
- 💼 **Salary Management**
- ⚙️ **Custom Exception Handling**
- 🔧 **Maven Build System**
- 📁 Clean MVC Structure with DTOs

---

## 🧰 Tech Stack

- Java
- Spring Boot
- Spring Security (JWT)
- Maven
- REST API

---

## 🔐 Security

- Implemented JWT-based authentication
- Configured CORS settings
- Filter chains via `JwtFilter`
- Central `SecurityConfig` to control endpoint access

---

## 🏁 Getting Started

```bash
# Clone the project
git clone https://github.com/your-username/m-school.git

# Build the app
./mvnw clean install

# Run the app
./mvnw spring-boot:run
