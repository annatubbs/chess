package requestandresultobjects;

import dataobject.GameData;

import java.util.ArrayList;

public class ListGamesResult {
    private final ArrayList<GameData> gameList;

    public ListGamesResult(ArrayList<GameData> gameList) {
        this.gameList = gameList;
    }

    public ArrayList<GameData> getGameList() {
        ArrayList<GameData> listCopy = new ArrayList<>();

        for (GameData data : gameList) {
            listCopy.add(data.deepCopy());
        }

        return listCopy;
    }
}
