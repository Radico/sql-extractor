
import java.util.List;
import java.util.Map;

import com.microsoft.sqlserver.jdbc.*;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class SQLServerClient extends AbstractSQLClient {

    private static final int DEFAULT_PORT = 1433;
    private static final String DEFAULT_HOST = "localhost";

    SQLServerClient(SQLParams params) {
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
