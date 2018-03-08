import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OutputWriter {

    private Gson gson;

    public OutputWriter() {
        this.gson = new Gson();
    }

    public void writeQuery(List<Map<String, ?>> results) {

        Stream<String> json = results.stream().map(this.gson::toJson);
        try (PrintWriter pw = new PrintWriter("output.txt", "UTF-8")) {
            json.forEachOrdered(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
