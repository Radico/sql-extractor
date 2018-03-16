import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class PostgreSQLClient extends AbstractSQLClient {

    PostgreSQLClient(SQLParams params) {
        super(params);
    }

    @Override
    public DataSource initDataSource() {
        /*
        TODO can use connection pooling in future.
         */
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setUser(this.params.getUser());
        ds.setServerName(this.params.getHost());
        ds.setPassword(this.params.getPassword());
        ds.setDatabaseName(this.params.getDatabase());
        return ds;
    }

    @Override
    protected String getDriverName() {
        return "org.postgresql.jdbc.Driver";
    }

}
