https://github.com/user-attachments/assets/a671cd01-0f4e-4edb-844f-313582f304ac

# Project Requirements
Before running the app please put your token in the local.properties file like FIREBASE_DOWNLOAD_TOKEN=YOUR_TOKEN.

# Project Overview
This project is a Jetpack Compose application that displays a searchable list of log entries. Users can search for specific logs and view detailed information about each entry on a separate screen. The logs are grouped by date and a severity indicator in the header visualizes the distribution of log severity levels within the corresponding filtered results.
The app is built with a modern Android tech stack, leveraging dependency injection, a modular architecture, and network communication for data fetching.

# Architecture
The application follows a clean architecture and a modular project structure, separating the codebase into distinct modules based on functionality. This approach enhances scalability, testability, and team collaboration.

# Modularization
- app: The main application module that orchestrates the other modules. 
- core: Contains foundational code shared across the application, including: common: Utility classes and shared resources. network: Handles all API interactions using Retrofit and OkHttp. testing: Test utilities and shared testing dependencies. threading: Manages coroutine dispatchers.
- feature: Houses feature-specific code, with each feature being self-contained. home: Manages the log list and search screen. detail: Manages the log detail screen.

# Core Libraries
Jetpack Compose: For a declarative UI. Hilt: For dependency injection, simplifying the management of dependencies. Retrofit: A type-safe HTTP client for making API calls. Kotlinx Serialization: A powerful serialization library for parsing JSON data from the network. Coroutines: For asynchronous programming and managing background tasks. Jetpack Navigation: To manage in-app navigation between screens.








