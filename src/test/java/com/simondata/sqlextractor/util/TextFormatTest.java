package com.simondata.sqlextractor.util;

import org.junit.Test;

import static com.simondata.sqlextractor.util.TextFormat.toCamelCase;
import static com.simondata.sqlextractor.util.TextFormat.toSnakeCase;
import static org.junit.Assert.assertEquals;

public class TextFormatTest {

    @Test
    public void testToCamelCase() throws Exception {
        assertEquals("theRainInSpain", toCamelCase("the rain in spain"));
        assertEquals("theRainInSpain", toCamelCase("theRainInSpain"));
        assertEquals("theRainInSpain", toCamelCase("the_rain_in_spain"));
    }

    @Test
    public void testToSnakeCase() throws Exception {
        assertEquals("the_rain_in_spain", toSnakeCase("the rain in spain"));
        assertEquals("the_rain_in_spain", toSnakeCase("the_rain_in_spain"));
        assertEquals("the_rain_in_spain", toSnakeCase("theRainInSpain"));
    }

}
