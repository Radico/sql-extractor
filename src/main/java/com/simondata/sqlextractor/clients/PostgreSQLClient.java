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
        PostgreSQLSQLParams postgresParams = (PostgreSQLSQLParams) this.params;
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUser(postgresParams.getUser());
        ds.setServerName(postgresParams.getHost(DEFAULT_HOST));
        ds.setPassword(postgresParams.getPassword());
        ds.setDatabaseName(postgresParams.getDatabase());
        ds.setPortNumber(postgresParams.getPort(DEFAULT_PORT));

        // Custom params
        ds.setSsl(postgresParams.getSsl());
        ds.setBinaryTransfer(postgresParams.getBinaryTransfer());
        ds.setKerberosServerName(postgresParams.getKerberosServerName());
        ds.setSslCert(postgresParams.getSslCert());
        ds.setSslKey(postgresParams.getSslKey());
        ds.setSslMode(postgresParams.getSslMode());
        ds.setSslPassword(postgresParams.getSslPassword());
        ds.setSslRootCert(postgresParams.getSslRootCert());
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "org.postgresql.jdbc.Driver";
    }

}
