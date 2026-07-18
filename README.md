# MoviesCatalog

MoviesCatalog is a modern Android application built with Jetpack Compose and Clean Architecture that allows users to discover popular and trending movies, search for titles, watch trailers, and manage favorites.

## Features

* Browse Popular Movies
* Browse Trending Movies
* Search Movies
* Search History
* Movie Details
* Watch Movie Trailers (YouTube)
* Add/Remove Favorites
* Offline Caching with Room
* Pagination
* Multi-language Support
* Adult Content Filtering

## Tech Stack

* Kotlin
* Jetpack Compose
* MVVM
* Clean Architecture
* Hilt
* Room
* DataStore
* Retrofit
* Kotlin Coroutines & Flow
* Coil
* TMDB API

## Architecture

```text
Presentation
    ↓
Domain
    ↓
Data
    ├── Remote (TMDB API)
    └── Local (Room + DataStore)
```

## Screenshots

### Popular Movies

<p align="center">
  <img src="screenshots/popular_movies.png" width="220"/>
</p>


### Trending Movies

<p align="center">
  <img src="screenshots/trending_movies.png" width="220"/>
</p>


### Search

<p align="center">
  <img src="screenshots/search.png" width="220"/>
</p>



### Search history

<p align="center">
  <img src="screenshots/search_history.png" width="220"/>
</p>


### Movie Details

<p align="center">
  <img src="screenshots/movie_detail.png" width="220"/>
</p>


### Favorites

<p align="center">
  <img src="screenshots/favorites.png" width="220"/>
</p>


### Settings

<p align="center">
  <img src="screenshots/settings.png" width="220"/>
</p>


## Project Structure

```text
core/
    database/
    datastore/
    di/
    utils/

movies/
    data/
    domain/
    presentation/

navigation/
```

## Getting Started

1. Clone the repository:

```bash
git clone https://github.com/medmoan/MoviesCatalog.git
```

2. Open the project in Android Studio.

3. Add your TMDB API key in local.properties file:

```kotlin
TMDB_API_KEY=YOUR_API_KEY
```

4. Run the application.

## API

This project uses The Movie Database (TMDB) API:

https://developer.themoviedb.org/
