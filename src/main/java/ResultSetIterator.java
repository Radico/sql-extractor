import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResultSetIterator implements Iterator {

    private ResultSet rs;
    private List<String> cols;
    private boolean hasNext;

    public ResultSetIterator(ResultSet rs) throws SQLException {
        this.rs = rs;
        this.cols = SQLServer.getColumnNames(rs);
        this.hasNext = true;
    }

    private Map rowToMap() throws SQLException {
        Map row = new HashMap();
        int i = 1;
        for (String col : this.cols) {
            row.put(col, this.rs.getObject(i));
            i++;
        }
        return row;
    }

    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override
    public Object next() {
        try {
            if (this.rs.next()) {
                return this.rowToMap();
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
