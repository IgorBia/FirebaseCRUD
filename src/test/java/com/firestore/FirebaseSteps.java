package com.firestore;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import io.cucumber.java.en.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FirebaseSteps {

    Firestore db;
    DocumentSnapshot snapshot;

    /**
     * Initializes a mocked Firestore instance for use in step definitions.
     */
    @Given("the Firestore database is mocked")
    public void mock_firestore_db() {
        db = mock(Firestore.class);
    }

    /**
     * Sets up a mock for an existing document in a given collection with a given ID.
     */
    @Given("the collection {string} contains a document with id {string}")
    public void mock_firestore_document(String collection, String id) throws Exception {
        CollectionReference mockCollection = mock(CollectionReference.class);
        DocumentReference mockDocRef = mock(DocumentReference.class);
        ApiFuture<DocumentSnapshot> mockFuture = mock(ApiFuture.class);
        snapshot = mock(DocumentSnapshot.class);

        when(db.collection(collection)).thenReturn(mockCollection);
        when(mockCollection.document(id)).thenReturn(mockDocRef);
        when(mockDocRef.get()).thenReturn(mockFuture);
        when(mockFuture.get()).thenReturn(snapshot);
    }

    /**
     * Sets up a scenario where the document does not exist in the specified collection.
     */
    @Given("the collection {string} does not contain a document with id {string}")
    public void mock_missing_document(String collection, String id) throws Exception {
        CollectionReference mockCollection = mock(CollectionReference.class);
        DocumentReference mockDocRef = mock(DocumentReference.class);
        ApiFuture<DocumentSnapshot> mockFuture = mock(ApiFuture.class);
        snapshot = mock(DocumentSnapshot.class);

        when(db.collection(collection)).thenReturn(mockCollection);
        when(mockCollection.document(id)).thenReturn(mockDocRef);
        when(mockDocRef.get()).thenReturn(mockFuture);
        when(mockFuture.get()).thenReturn(snapshot);

        when(snapshot.exists()).thenReturn(false);
    }

    /**
     * Requests a document from Firestore using the provided ID and collection.
     */
    @When("I request the document with id {string} from collection {string}")
    public void request_document(String id, String collection) throws Exception {
        snapshot = FirestoreCRUD.getRecord(collection, id, db);
    }

    /**
     * Verifies the document was successfully retrieved.
     */
    @Then("the document should be retrieved successfully")
    public void verify_document_retrieval() {
        assertNotNull(snapshot);
    }

    DocumentReference mockDocRef;
    Map<String, Object> testData;

    /**
     * Adds a document with specified ID and key=value data string to a collection.
     * Data string is expected to be in format: "key1=value1,key2=value2"
     */
    @When("I add a document with id {string} and data {string} to collection {string}")
    public void add_document(String id, String dataString, String collection) throws Exception {
        CollectionReference mockCollection = mock(CollectionReference.class);
        mockDocRef = mock(DocumentReference.class);
        ApiFuture<WriteResult> mockFuture = mock(ApiFuture.class);

        when(db.collection(collection)).thenReturn(mockCollection);
        when(mockCollection.document(id)).thenReturn(mockDocRef);
        when(mockDocRef.set(any(Map.class))).thenReturn(mockFuture);
        when(mockFuture.get()).thenReturn(mock(WriteResult.class));

        // Parse data from key=value string to map
        testData = new HashMap<>();
        for (String pair : dataString.split(",")) {
            String[] kv = pair.split("=");
            testData.put(kv[0].trim(), kv[1].trim());
        }

        FirestoreCRUD.setRecord(collection, testData, db, id);
    }

    /**
     * Verifies that a document with given ID exists in the specified collection.
     */
    @Then("the document with id {string} should exist in collection {string}")
    public void verify_document_exists(String id, String collection) throws Exception {
        snapshot = mock(DocumentSnapshot.class);

        CollectionReference mockCollection = db.collection(collection);
        DocumentReference mockDocRef = mockCollection.document(id);
        ApiFuture<DocumentSnapshot> mockFuture = mock(ApiFuture.class);

        when(mockDocRef.get()).thenReturn(mockFuture);
        when(mockFuture.get()).thenReturn(snapshot);
        when(snapshot.exists()).thenReturn(true);

        DocumentSnapshot doc = FirestoreCRUD.getRecord(collection, id, db);
        assertNotNull(doc);
        assertTrue(doc.exists());
    }

    WriteResult deleteResult;

    /**
     * Deletes an existing document by ID from a given collection.
     */
    @When("I delete the document with id {string} from collection {string}")
    public void delete_document(String id, String collection) throws Exception {
        CollectionReference mockCollection = mock(CollectionReference.class);
        DocumentReference mockDocRef = mock(DocumentReference.class);
        DocumentSnapshot mockSnapshot = mock(DocumentSnapshot.class);
        ApiFuture<DocumentSnapshot> getFuture = mock(ApiFuture.class);
        ApiFuture<WriteResult> deleteFuture = mock(ApiFuture.class);

        when(db.collection(collection)).thenReturn(mockCollection);
        when(mockCollection.document(id)).thenReturn(mockDocRef);

        // Simulate document exists before delete
        when(mockDocRef.get()).thenReturn(getFuture);
        when(getFuture.get()).thenReturn(mockSnapshot);
        when(mockSnapshot.exists()).thenReturn(true);

        deleteResult = mock(WriteResult.class);
        when(mockDocRef.delete()).thenReturn(deleteFuture);
        when(deleteFuture.get()).thenReturn(deleteResult);

        FirestoreCRUD.deleteRecord(collection, id, db);
    }

    /**
     * Verifies that the document was deleted.
     */
    @Then("the document with id {string} should no longer exist in collection {string}")
    public void document_should_not_exist(String id, String collection) throws Exception {
        CollectionReference mockCollection = db.collection(collection);
        DocumentReference mockDocRef = mockCollection.document(id);
        ApiFuture<DocumentSnapshot> mockFuture = mock(ApiFuture.class);
        DocumentSnapshot deletedSnapshot = mock(DocumentSnapshot.class);

        when(mockDocRef.get()).thenReturn(mockFuture);
        when(mockFuture.get()).thenReturn(deletedSnapshot);
        when(deletedSnapshot.exists()).thenReturn(false);

        DocumentSnapshot result = FirestoreCRUD.getRecord(collection, id, db);
        assertFalse(result.exists(), "Expected document to be deleted, but it still exists.");
    }

    Map<String, Object> updatedData;

    /**
     * Modifies a document
     */
    @When("I modify the document with id {string} in collection {string}")
    public void modify_document(String id, String collection) throws Exception {
        CollectionReference mockCollection = db.collection(collection);
        DocumentReference mockDocRef = mockCollection.document(id);

        updatedData = new HashMap<>();
        updatedData.put("make", "Honda");
        updatedData.put("model", "Civic");
        updatedData.put("color", "Red");

        ApiFuture<WriteResult> mockFuture = mock(ApiFuture.class);
        when(mockDocRef.set(updatedData)).thenReturn(mockFuture);
        when(mockFuture.get()).thenReturn(null);

        FirestoreCRUD.updateRecord(collection, id, updatedData, db);

        DocumentSnapshot updatedSnapshot = mock(DocumentSnapshot.class);
        ApiFuture<DocumentSnapshot> getFuture = mock(ApiFuture.class);
        when(mockDocRef.get()).thenReturn(getFuture);
        when(getFuture.get()).thenReturn(updatedSnapshot);

        when(updatedSnapshot.exists()).thenReturn(true);
        when(updatedSnapshot.getData()).thenReturn(updatedData);

        snapshot = updatedSnapshot;
    }

    /**
     * Validates that the document has been updated with the expected data.
     */
    @Then("the document should be updated successfully")
    public void verify_document_updated() {
        assertNotNull(snapshot);
        assertTrue(snapshot.exists());
        assertEquals("Honda", snapshot.getData().get("make"));
        assertEquals("Civic", snapshot.getData().get("model"));
        assertEquals("Red", snapshot.getData().get("color"));
    }

    /**
     * Ensures that document retrieval fails (document does not exist).
     */
    @Then("the document retrieval should fail")
    public void document_retrieval_should_fail() {
        assertFalse(snapshot.exists(), "Document should not exist");
    }

    Exception deletionException;

    /**
     * Tries to delete a non-existing document and expects an exception.
     */
    @When("I try to delete the document with id {string} from collection {string}")
    public void try_to_delete_nonexistent_document(String id, String collection) throws Exception {
        try {
            CollectionReference mockCollection = db.collection(collection);
            DocumentReference mockDocRef = mockCollection.document(id);

            DocumentSnapshot mockSnapshot = mock(DocumentSnapshot.class);
            ApiFuture<DocumentSnapshot> mockFuture = mock(ApiFuture.class);
            when(mockDocRef.get()).thenReturn(mockFuture);
            when(mockFuture.get()).thenReturn(mockSnapshot);
            when(mockSnapshot.exists()).thenReturn(false);

            FirestoreCRUD.deleteRecord(collection, id, db);
        } catch (Exception e) {
            deletionException = e;
        }
    }

    /**
     * Asserts that an exception was thrown when trying to delete a missing document.
     */
    @Then("the deletion should fail")
    public void deletion_should_fail() {
        assertNotNull(deletionException);
        assertInstanceOf(IllegalStateException.class, deletionException);
        assertEquals("Document does not exist, cannot delete.", deletionException.getMessage());
    }
}
