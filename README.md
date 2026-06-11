# 🔗 URL Shortener

This is a web application designed to instantly transform long, unwieldy URLs into clean, shareable aliases.
This project uses a Kotlin Multiplatform (KMP) architecture to share data serialization logic seamlessly between the backend server and the frontend user interface. It serves as a blueprint for full-stack Kotlin development.

## 🛠️ Tech Stack

*   **Backend:** Kotlin JVM, Ktor Server
*   **Frontend:** Kotlin/JS browser app with Kotlin-React wrappers
*   **Shared Logic:** Kotlinx Serialization for JSON data exchange
*   **Build System:** Gradle


## ✨ Features

*   **Fast Shortening:**  Instantly convert long URLs into compact, shareable links.
*   **Unified Codebase:**  Shares API model definitions between frontend and backend.
*   **Modern UI:** Single-page frontend built with Kotlin-wrapped React components.

## 📂 Project Structure
```text
├── build.gradle.kts      # KMP build configuration
├── src
│   ├── commonMain         # A common Kotlin module shared by both the client and server without code duplication.
│   ├── jvmMain            # Ktor backend server
│   └── jsMain             # Kotlin/JS + React frontend application
```


---

## ⚡ Quick Start

### Prerequisites

*   JDK8 or higher (Target configured to JVM 1.8)
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


---

## 📺 Demo



https://github.com/user-attachments/assets/b36fa1cc-22a7-4a87-841b-0824d2ff1384



https://github.com/user-attachments/assets/979f63a6-7391-44a9-a935-3a01f63c6254





---
