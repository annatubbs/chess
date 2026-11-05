package dataobject;

import java.util.Set;
import java.util.TreeSet;

public class GameIDGenerator {

    private int lastID;

    public GameIDGenerator() {
        lastID = 0;
    }

    public GameIDGenerator(TreeSet<Integer> gameIDSet) { // continue where existing games' ids left off
        if (gameIDSet.isEmpty()) {
            lastID = 0;
        } else {
            lastID = gameIDSet.last();
        }
    }

    public int generateID() {
        lastID++;
        return lastID;
    }
}

