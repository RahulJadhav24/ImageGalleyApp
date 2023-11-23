# Imgur Search Sample
Android Application sample based on Mvvm, Koin, LiveData, Paging, Room & Coroutines. It requires an [Imgur.com](https://imgur.com/) API Client ID which has to be added in [Constants.kt]

## It contains
1. **Home Page**
  * Search bar to search images via Imgur Api.
  * List of the images retrieved by remote Api shown on screen with pagination.
  * Once Search result get, it will be stored in local database. 
  * App also works on offline database. Previously searched contents can be loaded from local database.
  * Option to switch list content display with 1. Grid and 2. List
  * Internet check handled while searching contents. Retry option is given to re-fetch response.


## How it Works
When text is typed in search view data is fetched from database. If data is not available in local database network call is made and inserted into local database and reflected back in UI. 
When image list is scrolled and all the values from database is reflected in UI another Network call is made to fetch the next page data and insert it into the database, and this process goes on. 
Each search is stored in key value pairs to get data which is searched before instantly.

## Features
1. ***MVVM*** architecture pattern
2. Offline support
3. Search content displayed with option of 1. Grid and 2. List.
4. Option to retry when network call fails
5. Design compatible to both phone and tablet
6. Infinite scroll via Paging
7. 250ms debounce on search

## Libraries Used
 `Android Architecture Components`
* LiveData
* ViewModel
* Paging

 `Dependency Injection`
* Koin

 `Asynchronous Programming`
* Kotlin Coroutines

`Image Loading`
* Glide
 




