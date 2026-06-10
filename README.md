# 🔗 Kotlin Multiplatform URL Shortener

This is a web application designed to instantly transform long, unwieldy URLs into clean, shareable aliases.
This project uses a Kotlin Multiplatform (KMP) architecture to share data serialization logic seamlessly between the backend server and the frontend user interface.

---

## 📺 Demo

https://github.com/user-attachments/assets/39d2dbd8-be99-49b0-8fa0-4dbd326d09a5

---

## 🛠️ Tech Stack

This project is structured as a **full-stack monorepo**
*   **Backend:** Kotlin JVM, Ktor Server 3.5.0 (Netty engine)
*   **Frontend:** Kotlin/JS browser app with Kotlin-React wrappers
*   **Shared Logic:** Kotlinx Serialization for JSON data exchange
*   **Build System:** Gradle (Kotlin DSL)

---

## ✨ Features

*   **Fast Shortening:**  Instantly convert long URLs into compact, shareable links.
*   **Unified Codebase:**  Shares API model definitions between frontend and backend.
*   **Modern UI:** Single-page frontend built with Kotlin-wrapped React components.
*   **Optimized Pipeline:** Pre-configured Webpack task dependencies prevent task-graph compilation errors during rapid local development.

---

## 📂 Project Structure
```text
├── build.gradle.kts      # Monolith KMP build configuration
├── src
│   ├── commonMain         # A common Kotlin module shared by both the client and server without code duplication.
│   ├── jvmMain            # Ktor backend server
│   └── jsMain             # Kotlin/JS + React frontend application
```

---

## ⚡ Quick Start

### Prerequisites

*   **JDK 8** or higher (Target configured to JVM 1.8)
*   Gradle installed

### 1. Clone the Repository
```bash
git clone https://github.com/mhng-fp/webApp.git
cd webApp
```

### 2. Built the Development Environment
Compile the JS frontend, bundle assets via Webpack, and assemble the final server fat-JAR:
```bash
./gradlew clean jvmJar
```

### 3. Run the Backend Server
Launch the Ktor application locally on your machine:
```bash
./gradlew jvmRun
```

---

### 4. Launch the Application
open this on your browser:
```text
http://localhost:8080/
```

---

## 🔌 API Endpoints

| Method | Endpoint           | Description |
| :--- |:-------------------| :--- |
| `POST` | `/shorten`         | Accepts long URL, returns short code payload |
| `GET` | `/shorten/shortid` | Redirects user to the original long URL destination |
| `GET` | `/`                | Serves the bundled Kotlin/JS React frontend app |
