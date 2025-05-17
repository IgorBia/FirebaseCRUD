package com.firestore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Implementation of DatabaseConnector that connects to Google Firestore using a service account JSON file.
 */
public class FirebaseConnector implements DatabaseConnector {
    /**
     * Establishes and returns a Firestore instance using service account credentials.
     * @return a Firestore instance, or null if the connection fails
     */
    public Firestore connect(){
        try(FileInputStream serviceAccount = new FileInputStream("src/main/resources/database_key.json")){

            FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            System.out.println("Połączono");
            return firestoreOptions.getService();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
