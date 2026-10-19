package server;

// useless!!!

public class GameIDGenerator {

    private int lastID;

    public GameIDGenerator() {
        lastID = 0;
    }

    public int generateID() {
        lastID++;
        return lastID;
    }
}
