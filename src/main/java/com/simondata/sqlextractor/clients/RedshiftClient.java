package com.simondata.sqlextractor.clients;

import com.amazon.redshift.jdbc42.DataSource;


public class RedshiftClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 5439;

    public RedshiftClient(SQLParams params) {
        super(params);
    }

    private String getRedshiftURL() {
        return String.format("jdbc:redshift://%s:%s/%s",
                this.params.getHost(), this.params.getPort(DEFAULT_PORT), this.params.getDatabase()
        );
    }

    @Override
    public javax.sql.DataSource initDataSource() {
        DataSource ds = new DataSource();
        ds.setUserID(params.getUser());
        ds.setPassword(params.getPassword());
        ds.setURL(this.getRedshiftURL());
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "com.amazon.redshift.jdbc42.Driver";
    }
}
