package com.simondata.sqlextractor.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SQLParams implements InputParams {

    private final static Logger logger = LoggerFactory.getLogger(InputParams.class);

    private String host;
    private Integer port;
    private String user;
    private String password;
    private String database;
    private Properties customProperties;

    public SQLParams(String host, Integer port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    public SQLParams(
            String host, Integer port, String user, String password, String database, Properties customProperties) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        this.customProperties = customProperties;
    }

    protected String getCustomStringParameter(String instanceVar, String key) {
        if (instanceVar != null) {
            return instanceVar;
        } else {
            return this.getCustomProperties().getProperty(key);
        }
    }

    protected Boolean getCustomBooleanParameter(Boolean instanceVar, String key) {
        if (instanceVar != null) {
            return instanceVar;
        } else if (this.getCustomProperties().getProperty(key) == null) {
            return null;
        } else {
            return Boolean.parseBoolean(this.getCustomProperties().getProperty(key));
        }
    }

    public String getHost() {
        return this.host;
    }

    public String getHost(String defaultHost) {
        if (this.host == null) {
            return defaultHost;
        } else {
            return this.host;
        }
    }

    public Integer getPort() {
        return this.port;
    }

    public Integer getPort(int defaultPort) {
        if (this.port == null) {
            return defaultPort;
        } else {
            return port;
        }
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDatabase() {
        return this.database;
    }

    public Properties getCustomProperties() {
        return this.customProperties;
    }

    @Override
    public void logValues() {
        logger.info("User: " + this.getUser());
        logger.info("Port: " + this.getPort());
        logger.info("Database: " + this.getDatabase());
        for (String name : this.customProperties.stringPropertyNames()) {
            logger.info(name + ":" + this.customProperties.getProperty(name));
        }
    }
}
