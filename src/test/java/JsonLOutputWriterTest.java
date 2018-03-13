import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

public class JsonLOutputWriterTest {

    @Test
    public void toJson() throws Exception {
        Map<String, Object> test1 = new HashMap<>();
        test1.put("a", "abc");
        test1.put("b", 1);
        JsonLOutputWriter subject = new JsonLOutputWriter();
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

        List<Map<String, Object>> input = Arrays.asList(test1, test2);
        JsonLOutputWriter subject = new JsonLOutputWriter();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        subject.writeQuery(input, baos);
        assertEquals(
                "{\"a\":\"abc\",\"b\":1}\n{\"a\":\"jkl\",\"b\":2}\n",
                baos.toString(JsonLOutputWriter.ENCODING)
        );

    }

    @Test
    public void writeQueryFile() throws Exception {

        Map<String, Object> test1 = new HashMap<>();
        test1.put("a", "abc");
        test1.put("b", 1);
        Map<String, Object> test2 = new HashMap<>();
        test2.put("a", "jkl");
        test2.put("b", 2);

        List<Map<String, Object>> input = Arrays.asList(test1, test2);
        JsonLOutputWriter subject = new JsonLOutputWriter();
        String filename = "test_file.json";
        subject.writeQuery(input, filename);
        Path path = Paths.get(filename);
        assertEquals(
                "{\"a\":\"abc\",\"b\":1}\n{\"a\":\"jkl\",\"b\":2}\n",
                new String(Files.readAllBytes(Paths.get("test_file.json")))
        );
        Files.delete(path);

    }

}