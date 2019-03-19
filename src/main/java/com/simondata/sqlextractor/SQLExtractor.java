/**
 * Copyright 2019-present, Simon Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simondata.sqlextractor;

import com.simondata.sqlextractor.clients.*;
import com.simondata.sqlextractor.writers.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class SQLExtractor {

    private final SQLClient sqlClient;
    private FormattingParams formattingParams;

    /**
     * Constructor
     */
    public SQLExtractor(
            SqlEngine engine, String host, Integer port, String database, String username, String password
    ) {
        this(
                engine,
                new SQLParams(host, port, username, password, database),
                FormattingParams.getDefaultFormattingParams()
        );
    }

    public SQLExtractor(SqlEngine engine, SQLParams sqlParams) {
        this(engine, sqlParams, FormattingParams.getDefaultFormattingParams());
    }

    /**
     * Primary constructor
     */
    public SQLExtractor(SqlEngine engine, SQLParams sqlParams, FormattingParams formattingParams) {
        this.sqlClient = ClientFactory.makeSQLClient(engine, sqlParams);
        this.formattingParams = formattingParams;
    }

    /**
     * If you need to implement your own SQLClient
     * @param sqlClient
     */
    public SQLExtractor(SQLClient sqlClient) {
        this.sqlClient = sqlClient;
        this.formattingParams = FormattingParams.getDefaultFormattingParams();
    }

    /**
     * Execute a query and return an arraylist of Maps
     *
     * @param sql
     * @return
     */
    public List<Map<String, Object>> queryAsList(String sql) {
        return this.queryAsList(sql, QueryParams.getDefaultQueryParams());
    }

    /**
     * Execute a query and return an arraylist of Maps
     *
     * @param sql
     * @param queryParams
     * @return
     */
    public List<Map<String, Object>> queryAsList(String sql, QueryParams queryParams) {
        this.sqlClient.setQueryParams(queryParams);
        return this.sqlClient.queryAsList(sql);
    }

    /**
     * @param sql
     * @param callback
     * @return Number of rows
     */
    public Integer queryWithCallback(String sql, Function<Map<String, Object>, ?> callback) {
        return queryWithCallback(sql, callback, QueryParams.getDefaultQueryParams());
    }

    /**
     * @param sql
     * @param callback
     * @param queryParams
     * @return Number of rows
     */
    public Integer queryWithCallback(
            String sql,
            Function<Map<String, Object>, ?> callback,
            QueryParams queryParams
    ) {
        this.sqlClient.setQueryParams(queryParams);
        CallbackRowWriter writer = new CallbackRowWriter(callback);
        RowHandler rh = new RowHandler(writer, queryParams.getLogFrequency(), this.formattingParams);
        return this.sqlClient.queryWithHandler(sql, rh);
    }

    private static FileRowWriter getRowWriter(FileOutputFormat outputFormat) {
        if (outputFormat == FileOutputFormat.JSON) {
            return new JsonLRowWriter();
        } else if (outputFormat == FileOutputFormat.CSV) {
            return new CSVRowWriter();
        }
        return null;
    }

    public File queryToFile(String sql, File file, FileOutputFormat outputFormat) {
        return this.queryToFile(
                sql,
                file,
                outputFormat,
                QueryParams.getDefaultQueryParams()
        );
    }

    public File queryToFile(
            String sql,
            File file,
            FileOutputFormat outputFormat,
            QueryParams queryParams
    ) {
        this.sqlClient.setQueryParams(queryParams);
        FileRowWriter writer = SQLExtractor.getRowWriter(outputFormat);
        try {
            writer.open(file);
            RowHandler rh = new RowHandler(writer, queryParams.getLogFrequency(), this.formattingParams);
            this.sqlClient.queryWithHandler(sql, rh);
        } finally {
            writer.close();
        }
        return file;
    }
}
