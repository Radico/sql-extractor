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


public class RowHandler {
    private final Logger logger = LoggerFactory.getLogger(RowHandler.class);

    private static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();
    private RowWriter writer;
    private int logFrequency;
    private Function<String, String> keyTransform;

    public RowHandler(RowWriter writer) {
        this.writer = writer;
        this.logFrequency = -1;
        this.keyTransform = Function.identity();
    }

    public RowHandler(RowWriter writer, int logFrequency, FormattingParams formattingParams) {
        this(writer);
        this.logFrequency = logFrequency;
        this.keyTransform = TextFormat.getFunctionByKeyFormat(formattingParams.getKeyCaseFormat());
    }

    public int handle(ResultSet rs) throws SQLException {
        AtomicInteger counter = new AtomicInteger();
        while (rs.next()) {
            writer.writeRow(this.handleRow(rs));
            counter.getAndIncrement();
            if (this.logFrequency > 0 && counter.get() % this.logFrequency == 0) {
                logger.info("Handling " + counter.get() + " rows...");
            }
        }
        return counter.intValue();
    }

    protected Map<String, Object> handleRow(ResultSet rs) throws SQLException {
        return this.ROW_PROCESSOR.toMap(rs).entrySet().stream().collect(
                Collectors.toMap(e -> this.keyTransform.apply(e.getKey()), Map.Entry::getValue)
        );
    }


}
