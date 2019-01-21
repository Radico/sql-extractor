package com.simondata.sqlextractor.clients;

import com.simondata.sqlextractor.writers.RowHandler;

import java.util.List;
import java.util.Map;

public interface SQLClient {

    List<Map<String, Object>> queryAsList(String queryText);

    int queryWithHandler(String queryText, RowHandler handler);

    void printRows(String queryText);
}
