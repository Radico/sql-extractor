/*
Copyright 2019-present, Simon Data, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at:
http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.simondata.sqlextractor.writers;
import com.google.gson.*;
import java.util.Map;

/**
 * JsonLRowWriter
 */
public class JsonLRowWriter extends FileRowWriter {

    static String ENCODING = "UTF-8";

    private Gson gson;

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

    String toJson(Map<String, Object> input) {
        return this.gson.toJson(input);
    }

    public void writeRow(Map<String, Object> row) {
        this.writer.println(this.toJson(row));
    }

}
