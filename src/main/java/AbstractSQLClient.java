import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.StatementConfiguration;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractSQLClient implements SQLClient {

    SQLParams params;

    private final Logger logger = LoggerFactory.getLogger(SQLClient.class);

    private final Integer FETCH_SIZE = 10000;

    AbstractSQLClient(SQLParams params) {
        this.params = params;
    }

    abstract protected DataSource initDataSource();

    abstract protected String getDriverName();

    private StatementConfiguration getDefaultStatementConfiguration() {
        return new StatementConfiguration.Builder().fetchSize(FETCH_SIZE).build();
    }

    @Override
    public List<Map<String, Object>> query(String queryText) {
        logger.debug("Querying for: " + queryText);
        try {
            DbUtils.loadDriver(this.getDriverName());
            DataSource ds = this.initDataSource();
            StatementConfiguration sc = this.getDefaultStatementConfiguration();
            QueryRunner queryRunner = new QueryRunner(ds, sc);
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
