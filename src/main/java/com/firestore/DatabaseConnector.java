package com.firestore;

/**
 * Interface defining the contract for database connectors capable of establishing connections.
 */
public interface DatabaseConnector {
    /**
     * Establishes and returns a database connection as an AutoCloseable resource.
     * @return an AutoCloseable representing the database connection
     */
    public AutoCloseable connect();
}
