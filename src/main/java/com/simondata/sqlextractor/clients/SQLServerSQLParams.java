package com.simondata.sqlextractor.clients;

public class SQLServerSQLParams extends SQLParams {
    private Boolean encrypt = null;
    private Boolean trustServerCertificate = null;

    public SQLServerSQLParams(String host, Integer port, String user, String password, String database) {
        super(host, port, user, password, database);
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public Boolean getEncrypt() {
        return this.encrypt;
    }

    public void setTrustServerCertificate(boolean trustServerCertificate) {
        this.trustServerCertificate = trustServerCertificate;
    }

    public Boolean getTrustServerCertificate() {
        return this.trustServerCertificate;
    }
}
