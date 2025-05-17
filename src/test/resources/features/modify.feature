Feature: Modify a document in Firestore database
  Scenario: Successfully modifying a document
    Given the Firestore database is mocked
    And the collection "cars" contains a document with id "321"
    When I modify the document with id "321" in collection "cars"
    Then the document should be updated successfully
