import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class AbstractSQLClient implements SQLClient {

    private final Logger logger = LoggerFactory.getLogger(SQLServerClient.class);

    @Override
    public void printRows(String queryText) {
        logger.info("Querying for: " + queryText);
        try {
            for (Map row : this.query(queryText)) {
                System.out.println(row);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
