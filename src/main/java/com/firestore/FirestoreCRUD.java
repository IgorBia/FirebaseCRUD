package com.firestore;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Utility class providing static CRUD operations for Firestore collections.
 */
public class FirestoreCRUD {
    public FirestoreCRUD() {}

/**
 * Retrieves a document snapshot from the specified collection and document ID.
 * @param collection the Firestore collection name
 * @param id the document identifier
 * @param db the Firestore instance to operate on
 * @return the retrieved DocumentSnapshot
 * @throws ExecutionException if the get operation fails
 * @throws InterruptedException if the operation is interrupted
 */
    public static DocumentSnapshot getRecord(String collection,String id, @NotNull Firestore db) throws ExecutionException, InterruptedException {
        return db.collection(collection).document(id).get().get();
    }
/**
 * Creates or replaces a document in the specified collection with the provided data.
 * @param collection the Firestore collection name
 * @param data a map of field names to values for the document
 * @param db the Firestore instance to operate on
 * @param id the document identifier
 * @throws ExecutionException if the set operation fails
 * @throws InterruptedException if the operation is interrupted
 */
    public static void setRecord(String collection, Map<String, Object> data, @NotNull Firestore db, String id) throws ExecutionException, InterruptedException {
        db.collection(collection).document(id).set(data).get();
    }
/**
 * Deletes the specified document from the collection.
 * @param collection the Firestore collection name
 * @param id the document identifier
 * @param db the Firestore instance to operate on
 * @throws ExecutionException if the delete operation fails
 * @throws InterruptedException if the operation is interrupted
 */
    public static void deleteRecord(String collection, String id, @NotNull Firestore db) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collection).document(id);
        DocumentSnapshot snapshot = docRef.get().get();
        if (snapshot.exists()) {
            docRef.delete().get();
        } else {
            throw new IllegalStateException("Document does not exist, cannot delete.");
        }
    }
/**
 * Updates the specified document in the collection with the provided data.
 * @param collection the Firestore collection name
 * @param data a map of field names to updated values
 * @param id the document identifier
 * @param db the Firestore instance to operate on
 * @throws ExecutionException if the update operation fails
 * @throws InterruptedException if the operation is interrupted
 */
    public static void updateRecord(String collection, String id, Map<String, Object> data, @NotNull Firestore db) throws ExecutionException, InterruptedException {
        db.collection(collection).document(id).set(data).get();
    }
}
