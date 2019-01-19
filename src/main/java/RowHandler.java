import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;


public class RowHandler {

    private static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();
    private ResultSet resultSet;
    private JsonLOutputWriter writer;

    public RowHandler(ResultSet resultSet, JsonLOutputWriter writer) {
        this.resultSet = resultSet;
        this.writer = writer;
    }

    int handle() throws SQLException {
        AtomicInteger counter = new AtomicInteger();
        while (this.resultSet.next()) {
            writer.writeRow(this.handleRow());
            counter.getAndIncrement();
        }
        return counter.intValue();
    }

    protected Map<String, Object> handleRow() throws SQLException {
        return this.ROW_PROCESSOR.toMap(this.resultSet);
    }

}
