https://github.com/user-attachments/assets/a671cd01-0f4e-4edb-844f-313582f304ac

# 🛠️ Project Requirements

Before running the app, you need to add your Firebase token to your local properties file. Open your `local.properties` file in the root directory and append the following line:

FIREBASE_DOWNLOAD_TOKEN=YOUR_TOKEN


# 📝 Project Overview

This project is a modern Jetpack Compose application designed to display a searchable list of log entries. 

### Key Features
* Log Search: Users can filter and search for specific logs in real time.
* Detail View: Tap on any log entry to view contextual data on a dedicated detail screen.
* Smart Grouping & Analytics: Logs are dynamically grouped by date. A custom severity indicator in the header visualizes the distribution of log severity levels within your filtered results.

The app is built with a modern Android tech stack, leveraging dependency injection, a modular architecture, and network communication for data fetching.


# 🏗️ Architecture & Modularization

The application follows a clean architecture and a modular project structure, separating the codebase into distinct modules based on functionality. This approach enhances scalability, testability, and team collaboration.

### Module Breakdown

* app:
  * The main application module that orchestrates the other modules.
* core:
  * common: Utility classes and shared resources.
  * network: Handles all API interactions using Retrofit and OkHttp.
  * testing: Test utilities and shared testing dependencies.
  * threading: Manages coroutine dispatchers.
* feature:
  * home: Manages the log list and search screen.
  * detail: Manages the log detail screen.


# 📚 Core Libraries

| Library | Purpose |
| :--- | :--- |
| Jetpack Compose | For a declarative UI. |
| Hilt | For dependency injection, simplifying the management of dependencies. |
| Retrofit | A type-safe HTTP client for making API calls. |
| Kotlinx Serialization | A powerful serialization library for parsing JSON data from the network. |
| Coroutines & Flow | For asynchronous programming and managing background tasks. |
| Jetpack Navigation | To manage in-app navigation between screens. |






