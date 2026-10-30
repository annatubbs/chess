package dataaccess;

import dataobject.*;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class SQLDataTransfer implements DataTransfer {

    public SQLDataTransfer() {
        try {
            configureDb();
        } catch (Exception e) {
            System.out.println("Error: creating database connection. " + e.getMessage());
        }
    }

    // Authorization Methods
    public boolean containsAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM authDataTable WHERE authToken=?");
            preparedStatement.setString(1, authToken);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next(); // if not null, there is an entry for that username, return true; if null, return false
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM authDataTable WHERE authToken=?");
            preparedStatement.setString(1, authToken);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return new AuthData(rs.getString("username"), rs.getString("authToken"));
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void addAuth(AuthData authData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO authDataTable (authToken, username) VALUES (?,?)");
            preparedStatement.setString(1, authData.getAuthToken());
            preparedStatement.setString(2, authData.getUsername());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void removeAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM authDataTable WHERE authToken=?");
            preparedStatement.setString(1, authToken);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    // User Methods
    public boolean containsUsername(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM userDataTable WHERE username=?");
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next(); // if not null, there is an entry for that username, return true; if null, return false
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public UserData getUser(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM userDataTable WHERE username=?");
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return new UserData(rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"));
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void addUser(UserData userData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO userDataTable (username,password,email) VALUES (?,?,?)");
            preparedStatement.setString(1, userData.getUsername());
            preparedStatement.setString(2, userData.getPassword());
            preparedStatement.setString(3, userData.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    // Game Methods
    public void clearData() throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (Statement statement = conn.createStatement()) {
                statement.executeUpdate("DROP TABLE if EXISTS authDataTable");
                statement.executeUpdate("DROP TABLE if EXISTS userDataTable");
                statement.executeUpdate("DROP TABLE if EXISTS gameDataTable");
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
            configureDb();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public boolean containsGameID(int gameID) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM gameDataTable WHERE gameID=?");
            preparedStatement.setInt(1, gameID);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next(); // if not null, there is an entry for that gameID, return true; if null, return false
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public Set<Integer> getGameIDKeySet() throws DataAccessException {
        Set<Integer> gameIDSet = new HashSet<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM gameDataTable");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                gameIDSet.add(rs.getInt("gameID"));
            }
            return gameIDSet;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public GameData getGameData(int gameID) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "SELECT * FROM gameDataTable WHERE gameID=?");
            preparedStatement.setInt(1, gameID);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            return new GameData(rs.getInt("gameID"), rs.getString("blackUsername"),
                    rs.getString("whiteUsername"), rs.getString("gameName"));
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void addGameData(GameData gameData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO gameDataTable (gameID,blackUsername,whiteUsername,gameName) VALUES (?,?,?,?)");
            preparedStatement.setInt(1, gameData.getGameID());
            preparedStatement.setString(2, gameData.getBlackUsername());
            preparedStatement.setString(3, gameData.getWhiteUsername());
            preparedStatement.setString(4, gameData.getGameName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void updateGameData(GameData gameData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE gameDataTable SET blackUsername=?, whiteUsername=? WHERE gameID=?");
            preparedStatement.setString(1, gameData.getBlackUsername());
            preparedStatement.setString(2, gameData.getWhiteUsername());
            preparedStatement.setInt(3, gameData.getGameID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    // Database Setup
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  authDataTable (
              `username` varchar(256) NOT NULL,
              `authToken` varchar(256) NOT NULL PRIMARY KEY
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS  userDataTable (
              `username` varchar(256) NOT NULL PRIMARY KEY,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL
            );
            """,
            """
            CREATE TABLE IF NOT EXISTS  gameDataTable (
              `gameID` INTEGER NOT NULL PRIMARY KEY,
              `blackUsername` varchar(256),
              `whiteUsername` varchar(256),
              `gameName` varchar(256) NOT NULL
            );
            """
    };

    private void configureDb() throws Exception {
        DatabaseManager.createDatabase();

        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error creating db " + e.getMessage());
        }
    }
}
