# Firestore CRUD

## Project Overview

A console-based Java application for performing basic CRUD operations on documents Firestore. 

## Table of Contents

1. [Architecture](#architecture)
2. [Prerequisites](#prerequisites)
3. [Installation and Configuration](#installation-and-configuration)
4. [Usage](#usage)
5. [License](#license)

---

## Architecture

* **DatabaseConnector** (interface): Defines the contract for connecting to any database.
* **FirebaseConnector** (class): Implements a connection to Firestore using a service account JSON key.
* **FirestoreCRUD** (class): Provides static methods for CRUD operations:

    * `getRecord` – retrieve a document
    * `setRecord` – create or overwrite a document
    * `updateRecord` – partially modify a document
    * `deleteRecord` – delete a document
* **Main** (class): Entry point for the application, handles console menu and user interaction.

---

## Prerequisites

* Java 11 or higher
* Maven
* Firebase Account
* JSON key for Firestore access

---

## Installation and Configuration

1. **Place the service account key into the [paw1-60c53-firebase-adminsdk-fbsvc-977fe1d667.json](database_key.json) file.**

2. **Build the project** with Maven:

   ```bash
   mvn clean package
   ```

---

## Usage

1. Run the JAR file from the terminal:

   ```bash
   java -jar target/FireBaseCRUD.jar
   ```
2. Select an option from the console menu:

    1. Create a new document
    2. Show a document by ID
    3. Delete a document by ID
    4. Update a document by ID
    5. Exit the program

Each operation requires specifying the collection name and document ID. For create and update operations, you must also provide the fields: `make`, `model`, `color`, and `rims`.

---


## License

This project is licensed under the MIT License. 
