package com.simondata.sqlextractor.clients;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class SqlEngineTest {

    @Test
    public void testByName() throws Exception {
        assertEquals(SqlEngine.SQLSERVER, SqlEngine.byName("mssql"));
        assertEquals(SqlEngine.SQLSERVER, SqlEngine.byName("sqlserver"));
        assertEquals(SqlEngine.SQLSERVER, SqlEngine.byName("sqlServer"));
    }

    @Test
    public void testEachName() {
        assertEquals(SqlEngine.ATHENA, SqlEngine.byName("athena"));
        assertEquals(SqlEngine.SQLSERVER, SqlEngine.byName("sqlserver"));
        assertEquals(SqlEngine.MYSQL, SqlEngine.byName("mysql"));
        assertEquals(SqlEngine.POSTGRESQL, SqlEngine.byName("postgresql"));
        assertEquals(SqlEngine.ORACLE, SqlEngine.byName("oracle"));
        assertEquals(SqlEngine.INFORMIX, SqlEngine.byName("informix"));
        assertEquals(SqlEngine.REDSHIFT, SqlEngine.byName("redshift"));
        assertEquals(SqlEngine.BIGQUERY, SqlEngine.byName("bigquery"));
        assertEquals(SqlEngine.SNOWFLAKE, SqlEngine.byName("snowflake"));
    }
}
