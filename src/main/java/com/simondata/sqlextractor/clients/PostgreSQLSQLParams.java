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

import java.util.Properties;

/**
 * PostgreSQLSQLParams
 * Use in place of SQLParams when working with PostgreSQL.
 */
public class PostgreSQLSQLParams extends SQLParams {
    private Boolean ssl;
    private Boolean binaryTransfer;
    private String kerberosServerName;
    private String sslCert;
    private String sslKey;
    private String sslMode;
    private String sslPassword;
    private String sslRootCert;

    /**
     * Base Constructor
     * @param host
     * @param port
     * @param user
     * @param password
     * @param database
     */
    public PostgreSQLSQLParams(String host, Integer port, String user, String password, String database) {
        super(host, port, user, password, database);
    }

    /**
     * Constructor with custom properties.
     * @param host
     * @param port
     * @param user
     * @param password
     * @param database
     * @param customProperties
     */
    public PostgreSQLSQLParams(
            String host,
            Integer port,
            String user,
            String password,
            String database,
            Properties customProperties) {
        super(host, port, user, password, database, customProperties);
    }

    public static PostgreSQLSQLParams initEngineParams(SQLParams params) {
        return new PostgreSQLSQLParams(
                params.getHost(),
                params.getPort(),
                params.getUser(),
                params.getPassword(),
                params.getDatabase(),
                params.getCustomProperties()
        );
    }

    public Boolean getSsl() {
        return getCustomBooleanParameter(this.ssl, "ssl");
    }

    public Boolean getBinaryTransfer() {
        return getCustomBooleanParameter(this.binaryTransfer, "binaryTransfer");
    }

    public String getKerberosServerName() {
        return getCustomStringParameter(this.kerberosServerName, "kerberosServerName");
    }

    public String getSslCert() {
        return getCustomStringParameter(this.sslCert, "sslCert");
    }

    public String getSslKey() {
        return getCustomStringParameter(this.sslKey, "sslKey");
    }

    public String getSslMode() {
        return getCustomStringParameter(this.sslMode, "sslMode");
    }

    public String getSslPassword() {
        return getCustomStringParameter(this.sslMode, "sslPassword");
    }

    public String getSslRootCert() {
        return getCustomStringParameter(this.sslRootCert, "sslRootCert");
    }

}
