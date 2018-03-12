import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;

public class JsonLOutputWriter {

    private final Logger logger = LoggerFactory.getLogger(JsonLOutputWriter.class);

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
        try {
            PrintWriter writer = new PrintWriter(new File(filename), ENCODING);
            logger.info("Opening " + filename + " with encoding " + ENCODING);
            this.writeQueryToWriterIterate(results, writer);
        } catch (FileNotFoundException e)   {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void writeQueryToWriterIterate(List<Map<String, Object>> results, PrintWriter writer) {
        int count = 0;
        for (Map<String, Object> result : results) {
            writer.println(this.toJson(result));
            count++;
            if (count % 10000 == 0) {
                logger.info("Written " + count + " lines...");
            }
        }
        writer.flush();
        logger.info("Flushed writer.");
    }

    private void writeQueryToWriter(List<Map<String, Object>> results, PrintWriter writer) {
        results.stream().
                map(this::toJson).
                forEach(writer::println);
        writer.flush();
        logger.info("Flushed writer.");
    }
}
