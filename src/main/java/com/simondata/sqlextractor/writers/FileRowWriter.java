package com.simondata.sqlextractor.writers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class FileRowWriter implements RowWriter {

    private final Logger logger = LoggerFactory.getLogger(FileRowWriter.class);

    static String ENCODING = "UTF-8";

    protected PrintWriter writer = null;

    public abstract void writeRow(Map row);

    public int writeRows(List<Map> rows) {
        AtomicInteger counter = new AtomicInteger();
        for (Map row : rows) {
            this.writeRow(row);
            counter.getAndIncrement();
        }
        return counter.intValue();
    }

    public void open(String filename) {
        try {
            this.writer = new PrintWriter(new File(filename), ENCODING);
            logger.info("Opening file: " + filename);
            this.postOpenHook();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void open(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream);
        this.postOpenHook();
    }

    public void openStdOut() {
        this.open(System.out);
    }

    protected void postOpenHook() {

    }

    protected void postCloseHook() {

    }

    void flush() {
        if (this.writer != null) {
            this.writer.flush();
        }
    }

    public void close() {
        this.flush();
        if (this.writer != null) {
            this.writer.close();
        }
        this.writer = null;
        this.postCloseHook();
    }
}
