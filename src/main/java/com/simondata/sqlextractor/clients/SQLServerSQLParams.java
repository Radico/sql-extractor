package com.simondata.sqlextractor.clients;

import java.util.Properties;

public class SQLServerSQLParams extends SQLParams {
    private Boolean encrypt = null;
    private Boolean trustServerCertificate = null;
    private String hostNameInCertificate = null;
    private String accessToken = "";
    private String authentication = null;
    private String authenticationScheme = null;
    private String columnEncryptionSetting = null;
    private String failoverPartner = null;
    private Boolean integratedSecurity= null;

    public SQLServerSQLParams(String host, Integer port, String user, String password, String database) {
        super(host, port, user, password, database);
    }

    public SQLServerSQLParams(
            String host, Integer port, String user, String password, String database, Properties customProperties) {
        super(host, port, user, password, database, customProperties);
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
        if (this.hostNameInCertificate != null) {
            return this.hostNameInCertificate;
        } else {
            return this.getCustomProperties().getProperty("hostNameInCertificate");
        }
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        if (this.accessToken != null) {
            return this.accessToken;
        } else {
            return (String) this.getCustomProperties().getOrDefault("accessToken", "");
        }
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getAuthentication() {
        if (this.authentication != null) {
            return this.authentication;
        } else {
            return this.getCustomProperties().getProperty("authentication");
        }
    }

    public void setAuthenticationScheme(String authenticationScheme) {
        this.authenticationScheme = authenticationScheme;
    }

    public String getAuthenticationScheme() {
        if (this.authenticationScheme != null) {
            return this.authenticationScheme;
        } else {
            return this.getCustomProperties().getProperty("authenticationScheme");
        }
    }

    public void setColumnEncryptionSetting(String columnEncryptionSetting) {
        this.columnEncryptionSetting = columnEncryptionSetting;
    }

    public String getColumnEncryptionSetting() {
        if (this.columnEncryptionSetting != null) {
            return this.columnEncryptionSetting;
        } else {
            return this.getCustomProperties().getProperty("columnEncryptionSetting");
        }
    }

    public void setFailoverPartner(String failoverPartner) {
        this.failoverPartner = failoverPartner;
    }

    public String getFailoverPartner() {
        if (this.failoverPartner != null) {
            return this.failoverPartner;
        } else {
            return this.getCustomProperties().getProperty("failoverPartner");
        }
    }

    public void setIntegratedSecurity(Boolean integratedSecurity) {
        this.integratedSecurity = integratedSecurity;
    }

    public Boolean getIntegratedSecurity() {
        if (this.integratedSecurity != null) {
            return this.integratedSecurity;
        } else {
            String integratedSecurity = this.getCustomProperties().getProperty("integratedSecurity");
            if (integratedSecurity == null) {
                return null;
            } else {
                return Boolean.parseBoolean(integratedSecurity);
            }
        }
    }
}
