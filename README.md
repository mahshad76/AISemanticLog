https://github.com/user-attachments/assets/a671cd01-0f4e-4edb-844f-313582f304ac

```
# 🛠️ Project Requirements

Before running the app, you need to add your Firebase token to your local properties file. Open your `local.properties` file in the root directory and append the following line:

FIREBASE_DOWNLOAD_TOKEN=YOUR_TOKEN

---

# 📝 Project Overview

This project is a modern Jetpack Compose application designed to display a searchable list of log entries. 

### Key Features
* Advanced Log Search: Users can filter and search for specific logs in real time.
* Detail View: Tap on any log entry to view comprehensive contextual data on a dedicated detail screen.
* Smart Grouping & Analytics: Logs are dynamically grouped by date. A custom severity indicator in the header visualizes the distribution of log severity levels within your filtered results.

The application is built on a robust, enterprise-grade Android stack leveraging modular architecture, strict dependency injection, and reliable network pipelines.

---

# 🏗️ Architecture & Modularization

The application strictly follows Clean Architecture principles and is broken down into self-contained, isolated feature and core modules. This enforces a separation of concerns, improves build times, and ensures high testability.

### Module Breakdown

* :app
  * The orchestrator module. It handles global application initialization, Hilt dependency graphs, and top-level navigation routing.
* :core
  * :common — Foundational utility classes, domain base models, and shared resources.
  * :network — Handles remote data sources, API definitions, and HTTP client configurations.
  * :testing — Shared test runners, Mocks, and common testing utilities.
  * :threading — Dispatches scoped Coroutine contexts throughout the app uniformly.
* :feature
  * :home — Manages the UI, ViewModels, and state management for the log listing and search screen.
  * :detail — Encapsulates the user experience, layout, and logic for inspecting individual log entries.

---

# 📚 Core Libraries

| Library | Purpose |
| :--- | :--- |
| Jetpack Compose | Modern, declarative UI framework for building responsive layouts. |
| Hilt | Compile-time dependency injection framework to simplify architecture and testing. |
| Retrofit + OkHttp | Type-safe HTTP client wrapper used to handle network communication and data fetching. |
| Kotlinx Serialization | Type-safe, compiler-plugin driven JSON parsing for network payloads. |
| Coroutines & Flow | First-class asynchronous programming paradigm for managing background tasks and non-blocking reactive data streams. |
| Jetpack Navigation | Type-safe Compose-destination routing to manage screen transitions seamlessly. |

```






