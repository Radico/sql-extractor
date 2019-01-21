package com.simondata.sqlextractor.writers;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class JsonLRowWriter implements RowWriter {

    private final Logger logger = LoggerFactory.getLogger(JsonLRowWriter.class);

    static String ENCODING = "UTF-8";

    private Gson gson;

    private PrintWriter writer = null;

    public JsonLRowWriter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        this.gson = gsonBuilder.create();
    }

    /**
     * Convenience constructor
     * @param filename
     */
    JsonLRowWriter(String filename) {
        this();
        this.open(filename);
    }

    String toJson(Map input) {
        return this.gson.toJson(input);
    }

    public void writeRow(Map row) {
        this.writer.println(this.toJson(row));
    }

    public int writeRows(List<Map> rows) {
        AtomicInteger counter = new AtomicInteger();
        for (Map row : rows) {
            this.writeRow(row);
            counter.getAndIncrement();
        }
        return counter.intValue();
    }

    void printRow(Map row) {
        System.out.println(this.toJson(row));
    }

    public void open(String filename) {
        try {
            this.writer = new PrintWriter(new File(filename), ENCODING);
            logger.info("Opening file: " + filename);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    void open(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream);
    }

    public void openStdOut() {
        this.open(System.out);
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
    }
}
