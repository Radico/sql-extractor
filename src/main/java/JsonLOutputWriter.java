import com.google.gson.*;
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
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        this.gson = gsonBuilder.create();
    }

    JsonLOutputWriter(String filename) {
        this();
        try {
            this.writer = new PrintWriter(new File(filename), ENCODING);
            logger.info("opening file + " + filename);
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

    void printRow(Map row) {
        System.out.println(this.toJson(row));
    }

    int writeQuery(List results, OutputStream os) {
        return this.writeQueryToWriter(results, new PrintWriter(os));
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
