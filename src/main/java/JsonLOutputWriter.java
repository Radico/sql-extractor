import com.google.gson.Gson;

import java.io.*;
import java.util.List;
import java.util.Map;

public class JsonLOutputWriter {

    static String ENCODING = "UTF-8";

    private Gson gson;
    private String encoding;

    JsonLOutputWriter() {
        this.gson = new Gson();
    }

    String toJson(Map<String, Object> input) {
        return this.gson.toJson(input);
    }

    void writeQuery(List<Map<String, Object>> results, OutputStream os) {
        this.writeQueryToWriter(results, new PrintWriter(os));
    }

    public void writeQuery(List<Map<String, Object>> results, String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename), ENCODING)) {
            this.writeQueryToWriter(results, writer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void writeQueryToWriter(List<Map<String, Object>> results, PrintWriter writer) {
        results.stream().
                map(this::toJson).
                forEach(writer::println);
        writer.flush();
    }
}