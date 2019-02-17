package com.simondata.sqlextractor.writers;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class CSVRowWriter extends FileRowWriter {

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

    private void writeHeadersOnce(Map row) {
        if (!this.headersWritten && this.shouldWriteHeaders) {
            this.orderedKeys = Lists.newArrayList(row.keySet());
            this.writer.println(String.join(this.delimiter, this.orderedKeys));
            this.headersWritten = true;
        }
    }

    @Override
    public void writeRow(Map row) {
        this.writeHeadersOnce(row);
    }

}
