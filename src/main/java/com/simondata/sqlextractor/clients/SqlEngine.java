package com.simondata.sqlextractor.clients;

import org.apache.commons.lang3.EnumUtils;

public enum SqlEngine {
    ATHENA,
    SQLSERVER,
    MYSQL,
    POSTGRESQL,
    ORACLE,
    INFORMIX,
    REDSHIFT,
    BIGQUERY,
    SNOWFLAKE;

    private static String normalizeName(String name) {
        String result;
        switch (name.toUpperCase()) {
            case "AZURE":
            case "MSSQL":
                result = SQLSERVER.name();
                break;
            case "MARIADB":
                result = MYSQL.name();
                break;
            case "POSTGRES":
                result = POSTGRESQL.name();
                break;
            default:
                result = name.toUpperCase();
                break;
        }
        return result;
    }

    public static SqlEngine byName(String name) {
        return EnumUtils.getEnum(SqlEngine.class, normalizeName(name));
    }

}
