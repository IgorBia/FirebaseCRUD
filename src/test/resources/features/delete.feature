Feature: Deleting a document from Firestore database

  Scenario: Successfully deleting a document
    Given the Firestore database is mocked
    And the collection "cars" contains a document with id "789"
    When I delete the document with id "789" from collection "cars"
    Then the document with id "789" should no longer exist in collection "cars"

  Scenario: Failing to delete a non-existent document
    Given the Firestore database is mocked
    And the collection "cars" does not contain a document with id "999"
    When I try to delete the document with id "999" from collection "cars"
    Then the deletion should fail

