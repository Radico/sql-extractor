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

    private PrintWriter writer = null;

    JsonLOutputWriter() {
        this.gson = new Gson();
    }

    JsonLOutputWriter(String filename) {
        try {
            this.writer = new PrintWriter(new File(filename), ENCODING);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    String toJson(Map input) {
        return this.gson.toJson(input);
    }

    void writeRow(Map row) {
        this.writer.println(this.toJson(row));
        this.writer.flush();
    }

    void writeQuery(List results, OutputStream os) {
        this.writeQueryToWriter(results, new PrintWriter(os));
    }

    int writeQuery(List results, String filename) {
        try {
            logger.info("Opening " + filename + " with encoding " + ENCODING);
            PrintWriter writer = new PrintWriter(new File(filename), ENCODING);
            return this.writeQueryToWriter(results, writer);
        } catch (FileNotFoundException | UnsupportedEncodingException e)   {
            e.printStackTrace();
            return 0;
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
