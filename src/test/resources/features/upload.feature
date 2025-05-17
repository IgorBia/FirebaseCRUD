Feature: Uploading a document to Firestore Database

  Scenario: Successfully adding a new document
    Given the Firestore database is mocked
    When I add a document with id "456" and data "make=Toyota,model=Corolla" to collection "cars"
    Then the document with id "456" should exist in collection "cars"