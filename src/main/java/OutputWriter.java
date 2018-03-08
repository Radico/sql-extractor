import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputWriter {


    public String writeResultSet(ResultSet rs) {
        try {
            List<String> cols = SQLServer.getColumnNames(rs);
            while(rs.next()) {
                Map row = new HashMap();
                for(String col : cols){
                    row.put(col, rs.getObject(col));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Files.wr

}
