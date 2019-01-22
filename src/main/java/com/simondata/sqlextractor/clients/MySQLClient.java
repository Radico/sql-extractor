package com.simondata.sqlextractor.clients;

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;

public class MySQLClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 3306;
    private static final String DEFAULT_HOST = "localhost";

    public MySQLClient(SQLParams params) {
        super(params);
    }

    @Override
    protected DataSource initDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUser(this.params.getUser());
        ds.setServerName(this.params.getHost(DEFAULT_HOST));
        ds.setPort(this.params.getPort(DEFAULT_PORT));
        ds.setPassword(this.params.getPassword());
        ds.setDatabaseName(this.params.getDatabase());
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "com.mysql.jdbc.Driver";
    }
}
