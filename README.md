# Firestore CRUD

## Project Overview

A console-based Java 21 application that performs basic CRUD operations on documents stored in Google Firestore using Firebase Admin SDK. The project includes a modular architecture and a full test suite to ensure maintainability and reliability.

## Table of Contents

- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Installation and Configuration](#installation-and-configuration)
- [Usage](#usage)
- [Testing](#testing)
- [License](#license)

## Architecture

- **DatabaseConnector (interface)**: Defines a generic contract for database operations.
- **FirebaseConnector (class)**: Handles the initialization of the Firebase App using a service account JSON key.
- **FirestoreCRUD (class)**: Contains static methods for:
    - `getRecord` – retrieve a document
    - `setRecord` – create or overwrite a document
    - `updateRecord` – partially update a document
    - `deleteRecord` – delete a document
- **Main (class)**: Provides a CLI-based menu for user interaction.

## Prerequisites

- Java 21
- Maven
- Firebase Account with Firestore enabled
- Firebase service account JSON key

## Installation and Configuration

1. Place your Firebase service account key in the resources directory to database_key.json file.
2. Build the project using Maven:
```bash
mvn clean package
```
## Usage
Run the application from the terminal:

```bash
java -jar target/FireBaseCRUD.jar
```

The CLI provides the following options:
- Create a new document
- Show a document by ID
- Delete a document by ID
- Update a document by ID
- Exit the program

Each operation requires a collection name and document ID. Create/Update operations also require the following fields: make, model, color, rims.

## Testing
The project includes a comprehensive test suite to validate application logic and integration.

### Tools and Frameworks

#### JUnit 5 
Used for unit and integration testing.

#### Mockito 
Used to mock Firestore operations, allowing isolated testing without connecting to the real database.

#### Cucumber (BDD) 
Enables behavior-driven development by writing feature files that describe expected functionality in plain English.

Tests can be run using:
```bash
mvn test
```

## License
This project is licensed under the MIT License.