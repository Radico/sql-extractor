
import java.util.List;
import java.util.Map;

import com.microsoft.sqlserver.jdbc.*;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLServerClient implements SQLClient {

    private final Logger logger = LoggerFactory.getLogger(SQLServerClient.class);

    private SQLParams params;

    public SQLServerClient(SQLParams params) {
        this.params = params;
    }

    public List<Map<String, Object>> query(String queryText) {

        logger.info("Querying for: " + queryText);
        try {
            DbUtils.loadDriver("com.microsoft.sqlserver.jdbc.Driver");
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser(params.getUser());
            ds.setPassword(params.getPassword());
            ds.setServerName(params.getHost());
            ds.setPortNumber(params.getPort());
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

    public void printRows(String queryText) {
        logger.info("Querying for: " + queryText);
        try {
            DbUtils.loadDriver("com.microsoft.sqlserver.jdbc.Driver");
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser(params.getUser());
            ds.setPassword(params.getPassword());
            ds.setServerName(params.getHost());
            ds.setPortNumber(params.getPort());
            ds.setDatabaseName(params.getDatabase());
            QueryRunner queryRunner = new QueryRunner(ds);
            MapListHandler handler = new MapListHandler();

            logger.debug(queryText);
            for (Map row : queryRunner.query(queryText, handler)) {
                System.out.println(row);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            e.printStackTrace();
        }
    }


}
