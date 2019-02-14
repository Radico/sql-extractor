package com.simondata.sqlextractor.clients;

import com.simondata.sqlextractor.writers.KeyCaseFormat;

public class FormattingParams {
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
}
