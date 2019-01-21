package com.simondata.sqlextractor.clients;

import javax.sql.DataSource;

public class AwsAthenaClient extends AbstractSQLClient {

    public AwsAthenaClient(SQLParams params) {
        super(params);
    }

    private String getUrl() {
        return "jdbc:awsathena://athena.{REGION}.amazonaws.com:443";
    }

    @Override
    protected DataSource initDataSource() {
        com.simba.athena.jdbc.DataSource ds = new com.simba.athena.jdbc.DataSource();
        ds.setUserID(this.params.getUser());
        ds.setURL(this.params.getHost());
        ds.setPassword(this.params.getPassword());
//        ds.setDatabaseName(this.params.getDatabase());
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "com.simba.athena.jdbc.Driver";
    }
}
