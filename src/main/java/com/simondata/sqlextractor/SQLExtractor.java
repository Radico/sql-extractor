package com.simondata.sqlextractor;

import com.simondata.sqlextractor.clients.SqlEngine;
import com.simondata.sqlextractor.writers.FileOutputFormat;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SQLExtractor {

    private final SqlEngine engine;
    private final String host;
    private final Integer port;
    private final String database;

    public SQLExtractor(SqlEngine engine, String host, Integer port, String database, String username, String password) {
        this.engine = engine;
        this.host = host;
        this.port = port;
        this.database = database;
    }

    public List<Map<String, Object>> queryAsList() {
        return null;
    }

    public void queryAsResponse(Function<String, Object> callback) {

    }

    public File queryToFile(FileOutputFormat outputFormat) {
        return null;
    }



}
