package services;

import dataaccess.GameData;

import java.util.ArrayList;
import java.util.Map;

public class ListGamesResult {
    private ArrayList<GameData> gameList; //* dt

    public ListGamesResult() {
        gameList = new ArrayList<GameData>();
    }

    public ArrayList<GameData> getGameList() {
        ArrayList<GameData> listCopy = new ArrayList<>();

        for (GameData data : gameList) {
            listCopy.add(data.deepCopy());
        }

        return listCopy;
    }
}
