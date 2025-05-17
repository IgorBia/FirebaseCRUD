package com.firestore;

import com.google.cloud.firestore.Firestore;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Command-line application entry point for interacting with Firestore through a menu-driven interface.
 */
public class Main {

    static Scanner scanner = new Scanner(System.in);

    /**
     * Prints the main CRUD menu to standard output.
     */
    public static void displayMenu(){
        System.out.println("----------MENU----------");
        System.out.println("1. Create a new document");
        System.out.println("2. Show a document by id");
        System.out.println("3. Delete a document by id");
        System.out.println("4. Update a document by id");
        System.out.println("5. Exit");
    }

    /**
     * Executes the given CRUD command by interacting with Firestore.
     *
     * @param command the menu option chosen by the user (as String: "1"–"5")
     * @param db      the Firestore instance to operate on
     * @throws ExecutionException   if any Firestore operation fails during execution
     * @throws InterruptedException if the current thread is interrupted while waiting for Firestore
     */
    public static void executeCommand(String command,Firestore db) throws ExecutionException, InterruptedException {

        String collection,id;

        switch (command) {
            case "1":
                HashMap<String, Object> document = new HashMap<>();
                System.out.println("Please enter the collection name of the document");
                collection = scanner.next();
                System.out.println("Please enter the id of the document");
                id = scanner.next();
                document.put("id", id);
                System.out.println("Please enter the make of the document");
                document.put("make", scanner.next());
                System.out.println("Please enter the model of the document");
                document.put("model", scanner.next());
                System.out.println("Please enter the color of the document");
                document.put("color", scanner.next());
                System.out.println("Please enter the rims of the document");
                document.put("rims", scanner.next());
                FirestoreCRUD.setRecord(collection,document,db,id);
                break;
            case "2":
                System.out.println("Please enter the collection name of the document");
                collection = scanner.next();
                System.out.println("Please enter the id of the document");
                id = scanner.next();
                System.out.println(FirestoreCRUD.getRecord(collection,id,db));
                break;
            case "3":
                System.out.println("Please enter the collection name of the document");
                collection = scanner.next();
                System.out.println("Please enter the id of the document");
                id = scanner.next();
                FirestoreCRUD.deleteRecord(collection,id,db);
                break;
            case "4":
                document = new HashMap<>();
                System.out.println("Please enter the collection name of the document");
                collection = scanner.next();
                System.out.println("Please enter the id of the document");
                id = scanner.next();
                document.put("id", id);
                System.out.println("Please enter the make of the document");
                document.put("make", scanner.next());
                System.out.println("Please enter the model of the document");
                document.put("model", scanner.next());
                System.out.println("Please enter the color of the document");
                document.put("color", scanner.next());
                System.out.println("Please enter the rims of the document");
                document.put("rims", scanner.next());
                FirestoreCRUD.setRecord(collection,document,db,id);
                break;
                default:
                    System.out.println("Invalid command");

        }
    }

/**
 * Main method to start the Firestore CRUD CLI application.
 * @param args command-line arguments (unused)
 * @throws ExecutionException if a Firestore operation fails
 * @throws InterruptedException if the thread is interrupted during Firestore operations
 */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FirebaseConnector connector = new FirebaseConnector();
        Firestore db = connector.connect();

        while(true){
            displayMenu();
            String choice = scanner.next();
            if(choice.equals("5")){
                break;
            }
            executeCommand(choice,db);
        }
    }
}
