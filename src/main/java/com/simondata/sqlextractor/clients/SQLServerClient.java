package com.simondata.sqlextractor.clients;

import com.microsoft.sqlserver.jdbc.*;

import javax.sql.DataSource;

public class SQLServerClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 1433;
    private static final String DEFAULT_HOST = "localhost";

    public SQLServerClient(SQLParams params) {
        super(params);
    }

    @Override
    public DataSource initDataSource() {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(params.getUser());
        ds.setPassword(params.getPassword());
        ds.setServerName(params.getHost(DEFAULT_HOST));
        ds.setPortNumber(params.getPort(DEFAULT_PORT));
        ds.setDatabaseName(params.getDatabase());
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "com.microsoft.sqlserver.jdbc.Driver";
    }

}
