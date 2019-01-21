package com.simondata.sqlextractor.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(ClientFactory.class);

    public static SQLClient makeSQLClient(String sqlType, SQLParams params) {
        SQLClient client = null;
        switch (sqlType) {
            case "ATHENA":
                client = new AwsAthenaClient(params);
                break;
            case "SQLSERVER":
            case "MSSQL":
                client = new SQLServerClient(params);
                break;
            case "MARIADB":
            case "MYSQL":
                client = new MySQLClient(params);
                break;
            case "POSTGRES":
            case "POSTGRESQL":
                client = new PostgreSQLClient(params);
                break;
            case "REDSHIFT":
                client = new RedshiftClient(params);
                break;
            case "ORACLE":
            case "SYBASE":
            case "INFORMIX":
            case "ACCESS":
            case "INTERBASE":
            case "FIREBIRD":
            case "IBMDB2":
            case "DB2":
            case "BIGQUERY":
                logger.error("Not supported yet.");
                break;
            default:
                logger.error("Invalid or Unknown DB type.");
                break;
        }
        return client;
    }
}
