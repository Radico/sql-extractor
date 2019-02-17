package com.simondata.sqlextractor.writers;

import com.google.gson.*;

import java.io.*;
import java.util.Map;

public class JsonLRowWriter extends FileRowWriter {

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
     *
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

}
