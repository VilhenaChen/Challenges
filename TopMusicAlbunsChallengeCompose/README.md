# Top Music Album Challenge Compose

**Note:** *This is the compose version of the [Top Music Album Challenge](https://github.com/VilhenaChen/Challenges/tree/main/TopMusicAlbunsChallenge)*

This project consists of building a mobile application that displays the current Top 100 albums using the iTunes API.
The app fetches real-time data from the API and presents it in a clean, user-friendly interface, allowing users to explore trending albums and view detailed information for each one.
Although offline support was not part of the original challenge requirements, the application was enhanced to cache data locally, enabling it to function without an internet connection.
Also this is my first experience with Compose, so the UI was completely redone to make proper use of all the available compose components.
⏱️ **Time constraint:** The entire application was designed and implemented within a 6-hour time limit.

----------

## 🛠 Tech Stack

-   **Kotlin** – Main programming language

-    **Compose** – For designing the UI
    
-   **Retrofit** – Network communication with the iTunes API

-   **Coil** – Efficient image loading and caching
    
-  **Hilt** – Dependency Injection
    
-   **Room** – Local database for offline support

 -   **Compose Navigation** – For using NavHost and Routes for efficente navigation between screens

----------

## 🏗 Architecture & Patterns

-   **MVVM (Model–View–ViewModel)** architecture
    
-   **Repository Pattern**

----------

## 🚀 Possible Improvements

Given more time, the following improvements could be implemented:

-   **Enhanced UI/UX**
        
    -   Add a _Swipe-to-Refresh_ layout to allow users to manually refresh the album list
        
-   **Database Expiration Policy**
    
    -   Implement a cache invalidation strategy (e.g., time-based expiration)
        
    -   Automatically refresh data after a defined period to ensure up-to-date content
