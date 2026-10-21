package dataaccess;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends Exception {
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable ex) {
        super(message, ex);
    }

    public static class GameIDGenerator {

        private int lastID;

        public GameIDGenerator() {
            lastID = 0;
        }

        public int generateID() {
            lastID++;
            return lastID;
        }
    }
}
