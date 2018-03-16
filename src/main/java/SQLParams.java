
public class SQLParams {

    private String host;
    private Integer port;
    private String user;
    private String password;
    private String database;

    public SQLParams(String host, Integer port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        if (this.port == null) {
            return 1433;
        } else {
            return port;
        }
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public String getSqlServerConnectionUrl() {
        return String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;integratedSecurity=true;",
                this.getHost(), this.getPort(), this.getDatabase());
    }

    public String getRedshiftURL() {
        return String.format("jdbc:redshift://%s:%s/%s", this.getHost(), this.getPort(), this.getDatabase());
    }


}
