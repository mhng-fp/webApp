# 🔗 Kotlin Multiplatform URL Shortener

A modern, lightweight, and high-performance URL shortener application. This project uses a Kotlin Multiplatform (KMP) architecture to share data serialization logic seamlessly between the backend server and the frontend user interface.

---

## 🛠️ Tech Stack

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
git clone https://github.com
cd webApp
```

### 1. Build the Entire Application
Compile the JS frontend, bundle assets via Webpack, and assemble the final server fat-JAR:
```bash
./gradlew clean jvmJar
```

### 2. Run the Backend Server
Launch the Ktor application locally on your machine:
```bash
./gradlew jvmRun
```

---

## 🔌 API Endpoints (Planned / Default)

| Method | Endpoint    | Description |
| :--- |:------------| :--- |
| `POST` | `/shorten`  | Accepts long URL, returns short code payload |
| `GET` | `/{id}`     | Redirects user to the original long URL destination |
| `GET` | `/`         | Serves the bundled Kotlin/JS React frontend app |
