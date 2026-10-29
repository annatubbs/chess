package dataObject;

public class DbSetupInfo {
    private final String name;
    private final String dbUsername;
    private final String dbPassword;
    private final String connectionUrl;

    public DbSetupInfo(String name, String dbUsername, String dbPassword, String connectionUrl) {
        this.name = name;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        this.connectionUrl = connectionUrl;
    }

    public String getName() {
        return name;
    }
}
