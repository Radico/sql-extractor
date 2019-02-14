package com.simondata.sqlextractor.clients;

import java.util.Properties;

public class PostgreSQLSQLParams extends SQLParams {
    private Boolean ssl;
    private Boolean binaryTransfer;
    private String kerberosServerName;
    private String sslCert;
    private String sslKey;
    private String sslMode;
    private String sslPassword;
    private String sslRootCert;

    public PostgreSQLSQLParams(String host, Integer port, String user, String password, String database) {
        super(host, port, user, password, database);
    }

    public PostgreSQLSQLParams(String host, Integer port, String user, String password, String database, Properties customProperties) {
        super(host, port, user, password, database, customProperties);
    }

    private String safeGetCustomStringParameter(String instanceVar, String key) {
        if (instanceVar != null) {
            return instanceVar;
        } else {
            return this.getCustomProperties().getProperty(key);
        }
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
