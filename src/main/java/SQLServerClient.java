
import java.util.List;
import java.util.Map;

import com.microsoft.sqlserver.jdbc.*;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLServerClient extends AbstractSQLClient {

    private final Logger logger = LoggerFactory.getLogger(SQLServerClient.class);

    private static final int DEFAULT_PORT = 1433;
    private static final String DEFAULT_HOST = "localhost";

    private SQLParams params;

    public SQLServerClient(SQLParams params) {
        this.params = params;
    }

    private String getSqlServerConnectionUrl(SQLParams params) {
        return String.format(
                "jdbc:sqlserver://%s:%d;databaseName=%s;integratedSecurity=true;",
                params.getHost(DEFAULT_HOST), params.getPort(DEFAULT_PORT), params.getDatabase());
    }

    @Override
    public List<Map<String, Object>> query(String queryText) {

        logger.info("Querying for: " + queryText);
        try {
            DbUtils.loadDriver("com.microsoft.sqlserver.jdbc.Driver");
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser(params.getUser());
            ds.setPassword(params.getPassword());
            ds.setServerName(params.getHost());
            ds.setPortNumber(params.getPort(DEFAULT_PORT));
            ds.setDatabaseName(params.getDatabase());
            QueryRunner queryRunner = new QueryRunner(ds);
            MapListHandler handler = new MapListHandler();
            return queryRunner.query(queryText, handler);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}
