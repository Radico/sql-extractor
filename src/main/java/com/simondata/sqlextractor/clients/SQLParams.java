package com.simondata.sqlextractor.clients;

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
        return this.host;
    }

    public String getHost(String defaultHost) {
        if (this.host == null) {
            return defaultHost;
        } else {
            return this.host;
        }
    }

    public Integer getPort() {
        return this.port;
    }

    public Integer getPort(int defaultPort) {
        if (this.port == null) {
            return defaultPort;
        } else {
            return port;
        }
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDatabase() {
        return this.database;
    }

}
