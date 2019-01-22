package com.simondata.sqlextractor.clients;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class PostgreSQLClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 5432;
    private static final String DEFAULT_HOST = "localhost";

    public PostgreSQLClient(SQLParams params) {
        super(params);
    }

    @Override
    public DataSource initDataSource() {
        /*
        TODO can use connection pooling in future.
         */
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUser(this.params.getUser());
        ds.setServerName(this.params.getHost(DEFAULT_HOST));
        ds.setPassword(this.params.getPassword());
        ds.setDatabaseName(this.params.getDatabase());
        ds.setPortNumber(this.params.getPort(DEFAULT_PORT));
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "org.postgresql.jdbc.Driver";
    }

}
