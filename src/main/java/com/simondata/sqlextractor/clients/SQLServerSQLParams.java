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
 * SQLServerSQLParams
 *
 * A set of params that can be sent to the SQLServer client
 */
public class SQLServerSQLParams extends SQLParams {
    private Boolean encrypt = null;
    private Boolean trustServerCertificate = null;
    private String hostNameInCertificate = null;
    private String accessToken = null;
    private String authentication = null;
    private String authenticationScheme = null;
    private String columnEncryptionSetting = null;
    private String failoverPartner = null;
    private Boolean integratedSecurity= null;
    private String trustStore = null;
    private String trustStorePassword = null;
    private String trustStoreType = null;

    /**
     * Params for SQLServer
     * @param host
     * @param port
     * @param user
     * @param password
     * @param database
     */
    public SQLServerSQLParams(String host, Integer port, String user, String password, String database) {
        super(host, port, user, password, database);
    }

    public SQLServerSQLParams(
            String host, Integer port, String user, String password, String database, Properties customProperties
    ) {
        super(host, port, user, password, database, customProperties);
    }

    public static SQLServerSQLParams initEngineParams(SQLParams sqlParams) {
        return new SQLServerSQLParams(
                sqlParams.getHost(),
                sqlParams.getPort(),
                sqlParams.getUser(),
                sqlParams.getPassword(),
                sqlParams.getDatabase(),
                sqlParams.getCustomProperties()
        );
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public Boolean getEncrypt() {
        if (this.encrypt != null) {
            return this.encrypt;
        } else {
            return Boolean.parseBoolean(
                    (String) this.getCustomProperties().getOrDefault("encrypt", "false"));
        }
    }

    public void setTrustServerCertificate(boolean trustServerCertificate) {
        this.trustServerCertificate = trustServerCertificate;
    }

    public Boolean getTrustServerCertificate() {
        if (this.trustServerCertificate != null) {
            return this.trustServerCertificate;
        } else {
            return Boolean.parseBoolean(
                    (String) this.getCustomProperties().getOrDefault(
                            "trustServerCertificate", "false"));
        }
    }

    public void setHostNameInCertificate(String hostNameInCertificate) {
        this.hostNameInCertificate = hostNameInCertificate;
    }

    public String getHostNameInCertificate() {
        return this.getCustomStringParameter(this.hostNameInCertificate, "hostNameInCertificate");
    }

    /**
     * Set SQLServer access token
     * @param accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Get SQLServer access token
     * @return the SQLServer access token.
     */
    public String getAccessToken() {
        return this.getCustomStringParameter(this.accessToken, "accessToken");
    }

    /**
     * Set SQLServer authentication.
     * @param authentication
     */
    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    /**
     * Get SQLServer authentication.
     * @return the SQLServer authentication.
     */
    public String getAuthentication() {
        return this.getCustomStringParameter(this.authentication, "authentication");
    }

    /**
     * Set AuthenticationScheme
     * @param authenticationScheme
     */
    public void setAuthenticationScheme(String authenticationScheme) {
        this.authenticationScheme = authenticationScheme;
    }

    /**
     * Get SQLServer authentication scheme
     * @return the sqlserver authentication scheme.
     */
    public String getAuthenticationScheme() {
        return this.getCustomStringParameter(this.authenticationScheme, "authenticationScheme");
    }

    public void setColumnEncryptionSetting(String columnEncryptionSetting) {
        this.columnEncryptionSetting = columnEncryptionSetting;
    }

    public String getColumnEncryptionSetting() {
        return this.getCustomStringParameter(this.columnEncryptionSetting, "columnEncryptionSetting");
    }

    public void setFailoverPartner(String failoverPartner) {
        this.failoverPartner = failoverPartner;
    }

    public String getFailoverPartner() {
        return this.getCustomStringParameter(this.failoverPartner, "failoverPartner");
    }

    public void setIntegratedSecurity(Boolean integratedSecurity) {
        this.integratedSecurity = integratedSecurity;
    }

    public Boolean getIntegratedSecurity() {
        return this.getCustomBooleanParameter(this.integratedSecurity, "integratedSecurity");
    }

    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

    public String getTrustStore() {
        return this.getCustomStringParameter(this.trustStore, "trustStore");
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public String getTrustStorePassword() {
        return this.getCustomStringParameter(this.trustStorePassword, "trustStorePassword");
    }

    /**
     * Set SQLServer trust store type.
     * @param trustStoreType SQLServer trust store type.
     */
    public void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    /**
     * Get SQLServer trust store type.
     * @return SQLServer trust store type.
     */
    public String getTrustStoreType() {
        return this.getCustomStringParameter(this.trustStoreType, "trustStoreType");
    }
}
