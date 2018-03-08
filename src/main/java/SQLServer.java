
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.microsoft.sqlserver.jdbc.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class SQLServer {

    private SQLServerParams params;

    public SQLServer(SQLServerParams params) {

        this.params = params;
    }

    public static List<String> getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        List<String> columns = new ArrayList<>(md.getColumnCount());
        for (int i = 1; i <= md.getColumnCount(); i++) {
            columns.add(md.getColumnName(i));
        }
        return columns;
    }


    public List<Map<String, Object>> query() {
        Connection conn = null;
        try {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser(params.getUser());
            ds.setPassword(params.getPassword());
            ds.setServerName(params.getHost());
            ds.setPortNumber(params.getPort());
            ds.setDatabaseName(params.getDatabase());
            conn = ds.getConnection();

            String SQL = "SELECT Title, DocumentSummary " +
                    "FROM Production.Document";

            QueryRunner queryRunner = new QueryRunner(ds);
            MapListHandler handler = new MapListHandler();
            return queryRunner.query(SQL, handler);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {

                }
            }

            System.exit(1);
        }
        return null;
    }


    public static void main(String[] args) {
    }

}
