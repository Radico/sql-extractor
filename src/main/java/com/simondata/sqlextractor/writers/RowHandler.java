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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.simondata.sqlextractor.clients.FormattingParams;
import com.simondata.sqlextractor.util.TextFormat;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.text.CaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.simondata.sqlextractor.util.TextFormat.getFunctionByKeyFormat;

/**
 * Process rows, apply formatting, and send to output.
 */
public class RowHandler {
    private final Logger logger = LoggerFactory.getLogger(RowHandler.class);

    private static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();
    private RowWriter writer;
    private int logFrequency;
    private FormattingParams formattingParams;

    public RowHandler(RowWriter writer) {
        this.writer = writer;
        this.logFrequency = -1;
        this.formattingParams = new FormattingParams();
    }

    public RowHandler(RowWriter writer, Integer logFrequency, FormattingParams formattingParams) {
        this(writer);
        if (logFrequency != null) {
            this.logFrequency = logFrequency;
        }
        if (formattingParams != null) {
            this.formattingParams = formattingParams;
        }
    }

    public int handle(ResultSet rs) throws SQLException {
        AtomicInteger counter = new AtomicInteger();
        Function<String, String> keyTransform = getFunctionByKeyFormat(this.formattingParams.getKeyCaseFormat());
        while (rs.next()) {
            writer.writeRow(this.handleRow(rs, keyTransform));
            counter.getAndIncrement();
            if (this.logFrequency > 0 && counter.get() % this.logFrequency == 0) {
                logger.info("Handling " + counter.get() + " rows...");
            }
        }
        return counter.intValue();
    }

    protected Map<String, Object> handleRow(ResultSet rs, Function<String, String> keyTransform) throws SQLException {
        if (this.formattingParams.getKeyCaseFormat() == KeyCaseFormat.DEFAULT) {
            return this.ROW_PROCESSOR.toMap(rs);
        } else {
            return this.ROW_PROCESSOR.toMap(rs).entrySet().stream().collect(
                    Collectors.toMap(e -> keyTransform.apply(e.getKey()), Map.Entry::getValue)
            );
        }
    }


}
