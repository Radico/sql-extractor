import java.util.List;
import java.util.Map;

public interface SQLClient {

    List<Map<String, Object>> query(String queryText);

    void printRows(String queryText);
}
