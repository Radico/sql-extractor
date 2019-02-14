package com.simondata.sqlextractor.clients;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.StatementConfiguration;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.simondata.sqlextractor.writers.RowHandler;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public abstract class AbstractSQLClient implements SQLClient {

    protected SQLParams params;
    private QueryParams queryParams;

    private final Logger logger = LoggerFactory.getLogger(SQLClient.class);

    AbstractSQLClient(SQLParams params) {

        this.params = params;
        this.queryParams = QueryParams.getDefaultQueryParams();
    }

    AbstractSQLClient(SQLParams params, QueryParams queryParams) {
        this.params = params;
        this.queryParams = queryParams;
    }

    abstract protected DataSource initDataSource();

    abstract protected String getDriverName();

    @Override
    public void setQueryParams(QueryParams queryParams) {
        this.queryParams = queryParams;
    }

    private StatementConfiguration getDefaultStatementConfiguration() {
        return new StatementConfiguration.Builder()
                .fetchSize(this.queryParams.getFetchSize())
                .queryTimeout(this.queryParams.getTimeout())
                .maxRows(this.queryParams.getMaxRows())
                .build();
    }

    @Override
    public List<Map<String, Object>> queryAsList(String queryText) {
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
    public int queryWithHandler(String queryText, RowHandler handler) {
        logger.debug("Querying for: " + queryText);
        try {
            DbUtils.loadDriver(this.getDriverName());
            DataSource ds = this.initDataSource();
            StatementConfiguration sc = this.getDefaultStatementConfiguration();
            CustomQueryRunner cqr = new CustomQueryRunner(ds, sc, handler);
            return cqr.query(queryText);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void printRows(String queryText) {
        logger.debug("Querying for: " + queryText);
        try {
            for (Map row : this.queryAsList(queryText)) {
                System.out.println(row);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
