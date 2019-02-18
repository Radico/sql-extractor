package com.simondata.sqlextractor.writers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class CallbackRowWriter implements RowWriter {

    private Function<Map<String, Object>, ?> callback;

    public CallbackRowWriter(Function<Map<String, Object>, ?> callback) {
        this.callback = callback;
    }

    @Override
    public void writeRow(Map<String, Object> row) {
        this.callback.apply(row);
    }

    @Override
    public int writeRows(List<Map<String, Object>> rows) {
        AtomicInteger counter = new AtomicInteger();
        rows.forEach(row -> {
            this.writeRow(row);
            counter.getAndIncrement();
        });
        return counter.intValue();
    }

    @Override
    public void close() {

    }
}
