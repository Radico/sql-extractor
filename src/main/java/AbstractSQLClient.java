import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public abstract class AbstractSQLClient implements SQLClient {

    SQLParams params;

    private final Logger logger = LoggerFactory.getLogger(SQLClient.class);

    AbstractSQLClient(SQLParams params) {
        this.params = params;
    }

    abstract protected DataSource initDataSource();

    abstract protected String getDriverName();

    @Override
    public List<Map<String, Object>> query(String queryText) {
        logger.debug("Querying for: " + queryText);
        try {
            DbUtils.loadDriver(this.getDriverName());
            QueryRunner queryRunner = new QueryRunner(this.initDataSource());
            MapListHandler handler = new MapListHandler();
            return queryRunner.query(queryText, handler);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void printRows(String queryText) {
        logger.debug("Querying for: " + queryText);
        try {
            for (Map row : this.query(queryText)) {
                System.out.println(row);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}