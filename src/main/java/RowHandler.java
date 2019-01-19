import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Result;


public class RowHandler {
    private final Logger logger = LoggerFactory.getLogger(JsonLOutputWriter.class);

    private static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();
    private JsonLOutputWriter writer;
    private int logFrequency;

    public RowHandler(JsonLOutputWriter writer) {
        this.writer = writer;
        this.logFrequency = -1;
    }

    public RowHandler(JsonLOutputWriter writer, int logFrequency) {
        this.writer = writer;
        this.logFrequency = logFrequency;
    }

    int handle(ResultSet rs) throws SQLException {
        AtomicInteger counter = new AtomicInteger();
        while (rs.next()) {
            writer.writeRow(this.handleRow(rs));
            counter.getAndIncrement();
            if (this.logFrequency > 0 && counter.get() % this.logFrequency == 0){
                logger.info("Handling " + counter.get() + " rows...");
            }
        }
        return counter.intValue();
    }

    protected Map<String, Object> handleRow(ResultSet rs) throws SQLException {
        return this.ROW_PROCESSOR.toMap(rs);
    }

}
