
import java.sql.*;
import java.util.List;
import java.util.Map;

import com.microsoft.sqlserver.jdbc.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLServer implements SQLClient {

    final Logger logger = LoggerFactory.getLogger(SQLServer.class);

    private SQLParams params;

    public SQLServer(SQLParams params) {
        this.params = params;
    }

    public List<Map<String, Object>> query(String queryText) {
        Connection conn = null;
        try {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser(params.getUser());
            ds.setPassword(params.getPassword());
            ds.setServerName(params.getHost());
            ds.setPortNumber(params.getPort());
            ds.setDatabaseName(params.getDatabase());
            conn = ds.getConnection();

            QueryRunner queryRunner = new QueryRunner(ds);
            MapListHandler handler = new MapListHandler();

            logger.debug(queryText);

            return queryRunner.query(queryText, handler);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    logger.debug("Closing connection...");
                    conn.close();
                } catch (Exception e) {

                }
            }

            logger.debug("Exiting 1.");
            System.exit(1);
        }
        return null;
    }

}
