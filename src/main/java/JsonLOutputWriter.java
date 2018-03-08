import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Stream;

public class JsonLOutputWriter {

    private Gson gson;
    private String encoding;

    public JsonLOutputWriter() {
        this.gson = new Gson();
        this.encoding = "UTF-8";
    }

    public void writeQuery(List<?> results, String filename) {
        this.writeQuery(results, new File(filename));
    }

    public void writeQuery(List<?> results, File file) {

        Stream<String> json = results.stream().map(this.gson::toJson);
        try (PrintWriter pw = new PrintWriter(file, this.encoding)) {
            json.forEachOrdered(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
