package com.simondata.sqlextractor.clients;

import com.informix.jdbcx.IfxDataSource;
import com.informix.util.InformixUrl;

import javax.sql.DataSource;

/**
 * Untested.
 */
public class InformixSQLClient extends AbstractSQLClient {
    private static final int DEFAULT_PORT = 1526;
    private static final String DEFAULT_HOST = "localhost";

    InformixSQLClient(SQLParams params) {
        super(params);
    }

    @Override
    protected DataSource initDataSource() {
        IfxDataSource ifxDataSource = new IfxDataSource();
        ifxDataSource.setDatabaseName(this.params.getDatabase());
        ifxDataSource.setUser(this.params.getUser());
        ifxDataSource.setPassword(this.params.getPassword());
        ifxDataSource.setIfxIFXHOST(this.params.getHost(DEFAULT_HOST));
        ifxDataSource.setPortNumber(this.params.getPort(DEFAULT_PORT));
        return ifxDataSource;
    }

    @Override
    protected String getDriverName() {
        return "com.informix.jdbc.IfxDriver";
    }
}