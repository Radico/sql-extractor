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
    BIGQUERY;

    static SqlEngine byName(String name) {
        return EnumUtils.getEnum(SqlEngine.class, name.toUpperCase());
    }

}
