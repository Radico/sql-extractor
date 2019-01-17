import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface SQLClient {

    List<Map<String, Object>> queryAsList(String queryText);

    Stream<Map<String, Object>> queryAsStream(String queryText);

    void printRows(String queryText);
}
