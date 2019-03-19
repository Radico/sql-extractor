/*
Copyright 2019-present, Simon Data, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at:
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

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
            case "AWSATHENA":
                result = ATHENA.name();
                break;
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
            case "INFORMIX":
                result = INFORMIX.name();
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
