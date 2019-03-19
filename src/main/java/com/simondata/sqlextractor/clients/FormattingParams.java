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

package com.simondata.sqlextractor.clients;

import com.simondata.sqlextractor.writers.KeyCaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormattingParams implements InputParams {
    private final static Logger logger = LoggerFactory.getLogger(InputParams.class);

    private KeyCaseFormat keyCaseFormat;

    public FormattingParams() {
    }

    public void setKeyCaseFormat(String keyCaseFormat) {
        this.keyCaseFormat = getKeyCaseFormat(keyCaseFormat);
    }

    public void setKeyCaseFormat(KeyCaseFormat keyCaseFormat) {
        this.keyCaseFormat = keyCaseFormat;
    }

    public KeyCaseFormat getKeyCaseFormat() {
        return this.keyCaseFormat;
    }

    private static KeyCaseFormat getKeyCaseFormat(String input) {
        if (input == null) {
            return KeyCaseFormat.DEFAULT;
        }
        if (input.toLowerCase().contains("camel")) {
            return KeyCaseFormat.CAMEL_CASE;
        }
        if (input.toLowerCase().contains("snake")) {
            return KeyCaseFormat.SNAKE_CASE;
        }
        return KeyCaseFormat.DEFAULT;
    }

    public static FormattingParams getDefaultFormattingParams() {
        return new FormattingParams();
    }

    @Override
    public void logValues() {
        logger.info("Key Case Format: " + this.getKeyCaseFormat().name());
    }
}
