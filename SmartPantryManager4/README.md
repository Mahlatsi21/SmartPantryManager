# Smart Pantry Manager

## Application Description

Smart Pantry Manager is a Java-based Android application designed to help users manage pantry ingredients and reduce food waste.

The application allows users to:

* Add pantry ingredients.
* View all pantry ingredients.
* Edit existing pantry ingredients.
* Delete pantry ingredients.
* Record ingredient quantity, unit and optional expiry date.
* View ingredients that are approaching their expiry date.
* View recipe suggestions based strictly on available pantry ingredients.
* View recipe details, including required ingredients and preparation instructions.
* Manage application settings.

The application uses strict recipe matching. A recipe is suggested only when all of its required ingredients are available in the pantry in sufficient quantities. Partial matches are not displayed.

The application contains 20 sample recipes stored in the database.

## Technologies Used

* Java
* Android Studio
* Android SDK
* SQLite
* SQLiteOpenHelper
* RecyclerView
* Android Intents
* Git and GitHub

## Database Choice

SQLite was selected because it is built into the Android platform and provides a lightweight local database suitable for an application such as Smart Pantry Manager.

SQLite allows the application to store pantry ingredients and recipe information locally on the device. The data remains available when the application is closed and reopened.

The application uses `SQLiteOpenHelper` to create and manage the database.

The database contains tables for:

* Pantry items
* Recipes
* Recipe ingredients

## Main Application Screens

The application contains the following main screens:

1. Pantry List
2. Add/Edit Ingredient
3. Suggested Recipes
4. Recipe Detail
5. Settings
6. Expiring Soon

## Recipe Matching

The recipe matching feature compares the ingredients stored in the user's pantry with the ingredients required by each recipe.

For a recipe to be displayed:

* Every required ingredient must be available.
* The pantry quantity must meet or exceed the required quantity.
* Basic unit conversions are supported where applicable.
* Simple singular and plural ingredient names are handled.

If the pantry does not contain enough ingredients for any recipe, the application displays a message indicating that no recipes match the available ingredients.

## Input Validation

The application validates ingredient information before saving it.

Validation includes:

* Ingredient name cannot be empty.
* Quantity cannot be empty.
* Quantity must be a valid number.
* Quantity must be greater than zero.
* Unit cannot be empty.
* Expiry date must use the `YYYY-MM-DD` format when provided.

## Setup and Run Instructions

### Requirements

To run the application, install:

* Android Studio
* Android SDK
* Java Development Kit (JDK)
* An Android emulator or compatible Android device

The project was developed and tested using Android Studio and the Android Emulator.

### Steps

1. Clone or download the Smart Pantry Manager project from GitHub.
2. Open the project in Android Studio.
3. Allow Android Studio to complete the Gradle sync.
4. Ensure an Android emulator is available.
5. Start the emulator.
6. Build the project.
7. Run the application using Android Studio.

The application starts at the Smart Pantry Manager main screen.

## Database Setup

No separate database server is required.

The SQLite database is created automatically when the application is first installed and launched.

Sample recipe data is inserted automatically into the database.

## GitHub Repository

The project source code and development history are maintained in the public GitHub repository:

**Smart Pantry Manager**

`https://github.com/Mahlatsi21/SmartPantryManager`

The repository contains the Android project source code and Git commit history.

## Project Structure

Important project packages include:

* `com.example.smartpantrymanager` for application activities.
* `database` for database and data-access classes.
* `model` for application data models.

The application uses separate activities for the main application functions and uses RecyclerView with custom adapters to display pantry and recipe information.

## Author

Smart Pantry Manager was developed as an individual Mobile App Development 700 practical assignment.
