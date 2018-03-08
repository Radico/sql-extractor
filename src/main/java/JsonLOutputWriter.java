import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class JsonLOutputWriter {

    private Gson gson;
    private String encoding;

    public JsonLOutputWriter() {
        this.gson = new Gson();
        this.encoding = "UTF-8";
    }

    public void writeQuery(List<Map<String, ?>> results, String filename) {

        Stream<String> json = results.stream().map(this.gson::toJson);
        try (PrintWriter pw = new PrintWriter(filename, this.encoding)) {
            json.forEachOrdered(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
