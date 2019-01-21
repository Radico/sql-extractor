import org.junit.Test;
import com.simondata.sqlextractor.writers.JsonLRowWriter;

import java.io.ByteArrayOutputStream;
import java.util.*;

import static org.junit.Assert.*;

public class JsonLRowWriterTest {

    @Test
    public void toJson() throws Exception {
        Map<String, Object> test1 = new HashMap<>();
        test1.put("a", "abc");
        test1.put("b", 1);
        JsonLRowWriter subject = new JsonLRowWriter();
        assertEquals("{\"a\":\"abc\",\"b\":1}", subject.toJson(test1));
    }

    @Test
    public void writeQuery() throws Exception {

        Map<String, Object> test1 = new HashMap<>();
        test1.put("a", "abc");
        test1.put("b", 1);
        Map<String, Object> test2 = new HashMap<>();
        test2.put("a", "jkl");
        test2.put("b", 2);

        List<Map> input = Arrays.asList(test1, test2);
        JsonLRowWriter subject = new JsonLRowWriter();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        subject.open(baos);

        int lines = subject.writeRows(input);
        subject.close();
        assertEquals(
                "{\"a\":\"abc\",\"b\":1}\n{\"a\":\"jkl\",\"b\":2}\n",
                baos.toString(JsonLRowWriter.ENCODING)
        );
        assertEquals(2, lines);

    }
}