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
