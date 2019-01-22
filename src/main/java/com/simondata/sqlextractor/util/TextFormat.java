package com.simondata.sqlextractor.util;

import com.simondata.sqlextractor.writers.KeyCaseFormat;
import org.apache.commons.text.CaseUtils;

import java.util.function.Function;

public class TextFormat {

    public static Function<String, String> getFunctionByKeyFormat(KeyCaseFormat keyCaseFormat) {
        switch (keyCaseFormat) {
            case CAMEL_CASE:
                return TextFormat::toCamelCase;
            case SNAKE_CASE:
                return TextFormat::toSnakeCase;
            case DEFAULT:
                return Function.identity();
            default:
                return Function.identity();
        }
    }

    public static String toCamelCase(String input) {
        return CaseUtils.toCamelCase(toSnakeCase(input), false, '_');
    }

    public static String toSnakeCase(String value) {
        char sep = '_';
        if (value == null) {
            return null;
        }
        StringBuilder result = new StringBuilder(value.length() + 10);
        boolean underscoreWritten = true;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if ((ch >= 'A') && (ch <= 'Z')) {
                if (!underscoreWritten) {
                    result.append(sep);
                }
            }
            if (ch == ' ') {
                result.append(sep);
                underscoreWritten = true;
            } else {
                result.append(Character.toLowerCase(ch));
                underscoreWritten = (ch == sep);
            }
        }
        return result.toString();
    }

}
