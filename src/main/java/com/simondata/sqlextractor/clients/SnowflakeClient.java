package com.simondata.sqlextractor.clients;

import javax.sql.DataSource;
import net.snowflake.client.jdbc.SnowflakeBasicDataSource;

public class SnowflakeClient extends AbstractSQLClient {

    SnowflakeClient(SQLParams params) {
        super(params);
    }

    SnowflakeClient(SQLParams params, QueryParams queryParams) {
        super(params, queryParams);
    }

    @Override
    protected DataSource initDataSource() {
        SnowflakeBasicDataSource snowflakeDS = new SnowflakeBasicDataSource();
        snowflakeDS.setDatabaseName(this.params.getDatabase());
        snowflakeDS.setUser(this.params.getUser());
        snowflakeDS.setPassword(this.params.getPassword());
        snowflakeDS.setPortNumber(this.params.getPort());
        return snowflakeDS;
    }

    @Override
    protected String getDriverName() {
        return "net.snowflake.client.jdbc.SnowflakeDriver";
    }
}
