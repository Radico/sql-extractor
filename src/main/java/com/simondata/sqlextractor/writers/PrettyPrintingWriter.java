package com.simondata.sqlextractor.writers;

import java.util.Map;
import java.util.stream.Collectors;

public class PrettyPrintingWriter extends FileRowWriter {

    @Override
    public void writeRow(Map<String, Object> row) {
        System.out.println(String.join("\t|\t",
                row.values().stream().map(Object::toString)
                        .collect(Collectors.toList())));
    }
}
