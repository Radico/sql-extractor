package com.simondata.sqlextractor.clients;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

public class SQLParams implements InputParams {

    private final static Logger logger = LoggerFactory.getLogger(InputParams.class);

    protected String host;
    protected Integer port;
    protected String user;
    protected String password;
    protected String database;
    protected Properties customProperties;

    public SQLParams(String host, Integer port, String user, String password, String database) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.database = database;
        this.customProperties = new Properties();
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
        } else {
            return getPropertyAsBoolean(key);
        }
    }

    public String getHost() {
        return this.host;
    }

    public String getHost(String defaultHost) {
        return defaultIfNull(this.host, defaultHost);
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return this.port;
    }

    public Integer getPort(int defaultPort) {
        return defaultIfNull(this.port, defaultPort);
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public Properties getCustomProperties() {
        return this.customProperties;
    }

    public boolean hasProperty(String propertyName) {
        return this.customProperties.containsKey(propertyName);
    }

    public Boolean getPropertyAsBoolean(String propertyName) {
        return BooleanUtils.toBooleanObject(this.customProperties.getProperty(propertyName));
    }

    public Integer getPropertyAsInteger(String propertyName) {
        String prop = this.customProperties.getProperty(propertyName);
        if (prop == null) {
            return null;
        } else {
            return Integer.parseInt(prop);
        }
    }

    public String getPropertyAsString(String propertyName) {
        return this.customProperties.getProperty(propertyName);
    }

    @Override
    public void logValues() {
        logger.info("User: " + this.getUser());
        logger.info("Password: <not shown>");
        logger.info("Port: " + this.getPort());
        logger.info("Database: " + this.getDatabase());
        for (String name : this.customProperties.stringPropertyNames()) {
            logger.info(name + ":" + this.customProperties.getProperty(name));
        }
    }
}
