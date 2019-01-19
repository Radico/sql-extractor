import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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

    /**
     * Convenience constructor
     * @param filename
     */
    JsonLOutputWriter(String filename) {
        this();
        this.open(filename);
    }

    String toJson(Map input) {
        return this.gson.toJson(input);
    }

    void writeRow(Map row) {
        this.writer.println(this.toJson(row));
    }

    void printRow(Map row) {
        System.out.println(this.toJson(row));
    }

    void open(String filename) {
        try {
            this.writer = new PrintWriter(new File(filename), ENCODING);
            logger.info("Opening file: " + filename);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    void openStdOut() {
        this.writer = new PrintWriter(System.out);
    }

    void flush() {
        if (this.writer != null) {
            this.writer.flush();
        }
    }

    void close() {
        this.flush();
        this.writer = null;
    }
}
