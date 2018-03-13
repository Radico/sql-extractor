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

    String toJson(Map input) {
        return this.gson.toJson(input);
    }

    void writeQuery(List results, OutputStream os) {
        this.writeQueryToWriter(results, new PrintWriter(os));
    }

    public int writeQuery(List results, String filename) {
        try {
            PrintWriter writer = new PrintWriter(new File(filename), ENCODING);
            logger.info("Opening " + filename + " with encoding " + ENCODING);
            return this.writeQueryToWriter(results, writer);
        } catch (FileNotFoundException e)   {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private int writeQueryToWriter(List results, PrintWriter writer) {
        int count = 0;
        for (Object result : results) {
            writer.println(this.toJson((Map) result));
            count++;
            if (count % 10000 == 0) {
                logger.info("Written " + count + " lines...");
            }
        }
        writer.flush();
        logger.info("Flushed writer.");
        return count;
    }
}
