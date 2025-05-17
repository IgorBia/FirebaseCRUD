Feature: Retrieving a document from Firestore Database

  Scenario: Successfully retrieving a document
    Given the Firestore database is mocked
    And the collection "cars" contains a document with id "123"
    When I request the document with id "123" from collection "cars"
    Then the document should be retrieved successfully

  Scenario: Failing to retrieve a non-existent document
    Given the Firestore database is mocked
    And the collection "cars" does not contain a document with id "999"
    When I request the document with id "999" from collection "cars"
    Then the document retrieval should fail
