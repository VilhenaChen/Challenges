# Top Music Album Challenge

This project consists of building a mobile application that displays the current Top 100 albums using the iTunes API.
The app fetches real-time data from the API and presents it in a clean, user-friendly interface, allowing users to explore trending albums and view detailed information for each one.
Although offline support was not part of the original challenge requirements, the application was enhanced to cache data locally, enabling it to function without an internet connection.
⏱️ **Time constraint:** The entire application was designed and implemented within a 6-hour time limit.

----------

## 🛠 Tech Stack

-   **Kotlin** – Main programming language
    
-   **Retrofit** – Network communication with the iTunes API

-   **Glide** – Efficient image loading and caching
    
-   **Dagger 2** – Dependency Injection
    
-   **Room** – Local database for offline support
    
-   **JUnit** – Unit testing
    
-   **Mockito** – Mocking framework
    

----------

## 🏗 Architecture & Patterns

-   **MVVM (Model–View–ViewModel)** architecture
    
-   **Repository Pattern**

----------

## 🚀 Possible Improvements

Given more time, the following improvements could be implemented:

-   **Enhanced UI/UX**
    
    -   Improve visual design and overall user experience
        
    -   Add a _Swipe-to-Refresh_ layout to allow users to manually refresh the album list
        
-   **Database Expiration Policy**
    
    -   Implement a cache invalidation strategy (e.g., time-based expiration)
        
    -   Automatically refresh data after a defined period to ensure up-to-date content
        
-   **Favorites Feature**
    
    -   Allow users to mark albums as favorites
        
    -   Provide a dedicated screen to manage and view saved albums
        
    -   This feature was also suggested during live presentation
