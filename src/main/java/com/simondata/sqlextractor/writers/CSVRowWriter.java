package com.simondata.sqlextractor.writers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVRowWriter extends FileRowWriter {

    private static String QUOTE = "\"";

    private List<String> orderedKeys;

    private CharSequence delimiter;

    private boolean headersWritten;

    private boolean shouldWriteHeaders;

    public CSVRowWriter() {
        this.delimiter = ",";
        this.shouldWriteHeaders = true;
    }

    public CSVRowWriter(CharSequence delimiter) {
        this.delimiter = delimiter;
        this.shouldWriteHeaders = true;
    }

    public CSVRowWriter(CharSequence delimiter, boolean shouldWriteHeaders) {
        this.delimiter = delimiter;
        this.shouldWriteHeaders = shouldWriteHeaders;
    }

    @Override
    protected void postCloseHook() {
        this.headersWritten = false;
    }

    @Override
    protected void postOpenHook() {
        this.headersWritten = false;
    }

    private void writeHeadersOnce(Map<String, Object> row) {
        if (!this.headersWritten && this.shouldWriteHeaders) {
            this.orderedKeys = row.keySet()
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            this.writer.println(String.join(this.delimiter, this.orderedKeys));
            this.headersWritten = true;
        }
    }

    private String quoteString(String input) {
        return QUOTE + input.replace(QUOTE, "\"\"") + QUOTE;
    }

    private String handleValue(Object inputObj) {
        if (inputObj == null) {
            return "";
        } else if (! (inputObj instanceof String)) {
            return inputObj.toString();
        } else {
            String inputStr = (String) inputObj;
            if (inputStr.contains(System.lineSeparator())
                    || inputStr.contains(this.delimiter)
                    || inputStr.contains(QUOTE)) {
                return quoteString(inputStr);
            } else {
                return inputStr;
            }
        }
    }

    private String rowToString(Map<String, Object> row) {
        return this.orderedKeys.stream()
                .map(row::get)
                .map(this::handleValue)
                .collect(Collectors.joining(this.delimiter));
    }

    @Override
    public void writeRow(Map<String, Object> row) {
        this.writeHeadersOnce(row);
        this.writer.println(rowToString(row));
    }

}
