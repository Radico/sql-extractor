package com.simondata.sqlextractor.writers;

import java.util.List;
import java.util.Map;

public interface RowWriter {

    void writeRow(Map row);

    int writeRows(List<Map> row);

    void close();
}
