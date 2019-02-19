package com.simondata.sqlextractor.clients;

public class SnowflakeSQLParams extends SQLParams {
    public SnowflakeSQLParams(String host, Integer port, String user, String password, String database) {
        super(host, port, user, password, database);
    }
}
