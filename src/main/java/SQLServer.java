
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.sqlserver.jdbc.*;

public class SQLServer {

    private SQLServerParams params;

    public SQLServer(SQLServerParams params) {

        this.params = params;
    }

    public static List<String> getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        List<String> columns = new ArrayList<>(md.getColumnCount());
        for(int i = 1; i <= md.getColumnCount(); i++){
            columns.add(md.getColumnName(i));
        }
        return columns;
    }


    public void query() {
        Connection conn = null;
        CallableStatement callableStatement = null;
        ResultSet resultSet = null;

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
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery(SQL);
            ResultSetMetaData md = resultSet.getMetaData();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                }
            }

            if (callableStatement != null) {
                try {
                    callableStatement.close();
                } catch (Exception e) {

                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {

                }
            }

            System.exit(1);
        }
    }


    public static void main(String[] args) {
    }

}
