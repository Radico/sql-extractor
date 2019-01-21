package com.simondata.sqlextractor.clients;

import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;

public class MySQLClient extends AbstractSQLClient {

    public MySQLClient(SQLParams params) {
        super(params);
    }

    @Override
    protected DataSource initDataSource() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setUser(this.params.getUser());
        ds.setServerName(this.params.getHost());
        ds.setPassword(this.params.getPassword());
        ds.setDatabaseName(this.params.getDatabase());
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "com.mysql.jdbc.Driver";
    }
}
