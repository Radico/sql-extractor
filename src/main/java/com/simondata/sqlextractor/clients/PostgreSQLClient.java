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
        PostgreSQLSQLParams postgresParams = PostgreSQLSQLParams.initEngineParams(this.params);
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUser(postgresParams.getUser());
        ds.setServerName(postgresParams.getHost(DEFAULT_HOST));
        ds.setPassword(postgresParams.getPassword());
        ds.setDatabaseName(postgresParams.getDatabase());
        ds.setPortNumber(postgresParams.getPort(DEFAULT_PORT));
        ds.setReadOnly(true);

        // Custom params
        if (postgresParams.getSsl() != null) {
            ds.setSsl(postgresParams.getSsl());
        }
        if (postgresParams.getBinaryTransfer() != null) {
            ds.setBinaryTransfer(postgresParams.getBinaryTransfer());
        }
        if (postgresParams.getKerberosServerName() != null) {
            ds.setKerberosServerName(postgresParams.getKerberosServerName());
        }
        if (postgresParams.getSslCert() != null) {
            ds.setSslCert(postgresParams.getSslCert());
        }
        if (postgresParams.getSslKey() != null) {
            ds.setSslKey(postgresParams.getSslKey());
        }
        if (postgresParams.getSslMode() != null) {
            ds.setSslMode(postgresParams.getSslMode());
        }
        if (postgresParams.getSslPassword() != null) {
            ds.setSslPassword(postgresParams.getSslPassword());
        }
        if (postgresParams.getSslRootCert() != null) {
            ds.setSslRootCert(postgresParams.getSslRootCert());
        }
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "org.postgresql.jdbc.Driver";
    }

}
