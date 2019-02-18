package com.simondata.sqlextractor.writers;

import java.util.List;
import java.util.Map;

public interface RowWriter {

    void writeRow(Map<String, Object> row);

    int writeRows(List<Map<String, Object>> row);

    void close();
}
