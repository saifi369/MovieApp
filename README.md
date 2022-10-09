# MovieApp

This app uses TMDB's multi search API to fetch polymorphic json response.

Application shows the different media types returned from the API in their respective categories.

In this particular, cache doesn't make much sense because user will always have to get the results from remote server.

But just to showcase the implementation, MovieApp uses SharedPreferences as cache. I've decided to use Shared Preferences over retrofit's cache mechanish because it gives me control over the whole cache process.

## Built With

App is built with Test Driven Development (TDD) approach, clean architecture and uses data layer as library module.

- Jetpack Compose - Google's latest declarative UI toolkit
- Kotlin - Programming language for Android
- Hilt-Dagger - Standard library to incorporate Dagger dependency injection into an Android application.
- Retrofit -  A HTTP client.
- Coroutines - For asynchronous
- Kotlin StateFlow & SharedFlow - Data objects that notify views.
- ViewModel - Stores UI-related data that isn't destroyed on UI changes.
- ComposeDestination - KSP based Library for navigation in Jetpack Compose that internally uses Android's own Navigation Component

## Note
Run the `./gradlew kspDebugKotlin` command to generate Navigation Graph and Destination. 