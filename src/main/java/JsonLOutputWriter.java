import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;


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

    void openToStdOut() {
        this.writer = new PrintWriter(System.out);
    }

    void open(OutputStream out) {
        this.writer = new PrintWriter(out);
    }

    void open(String filename) {
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
    }

    int writeRows(List<Map<String, Object>> rows) {
        AtomicInteger counter = new AtomicInteger(0);
        for (Map row : rows) {
            this.writeRow(row);
            counter.getAndIncrement();
        }
        return counter.intValue();
    }

    int writeRows(Stream<Map<String, Object>> rows) {
        AtomicInteger counter = new AtomicInteger(0);
        rows.map(this::toJson).forEach(row -> {
            counter.getAndIncrement();
            this.writer.println(row);
        });
        return counter.intValue();
    }

    private void flush() {
        if (this.writer != null) {
            this.writer.flush();
        }
    }

    void close() {
        this.flush();
        this.writer = null;
    }

    int writeQueryToFile(Stream<Map<String, Object>> results, String filename) {
        int result;
        try {
            this.open(filename);
            result = this.writeRows(results);
        } finally {
            this.close();
        }
        return result;
    }

    int writeQueryToStream(List results, OutputStream os) {
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
