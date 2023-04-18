# ApartmentManager

## App features
* Showing a list of create PINs.
* Possibility to create a new PIN.
* Possibility to remove PIN.

## Packages
* createpin - contains UI and ViewModel of Create Pin features
* data.appartmentpin - data layer of the app
  * local - contains local resources as model and local data source
  * mapper - functions to map models
  * model - domain model
  * repository
* di - contains dependencies, RealmDatabase and Repository modules
* home - contains UI and ViewModel of Home (List of PINs) features
* navigation - data connected with navigation between screens
* ui.theme - all objects connected with UI like: Color, Theme and Type
* utils - utils classes
* test - contains CreatePinViewModel tests

## Build with
* MVI architecture
* Kotlin
* Jetpack Compose
* Realm database
* Dagger/Hilt
* Kotlin Coroutines
* Kotlin Flow
* Android Jetpack ViewModel
* Material3
* Navigation Compose
* mockk
* junit
