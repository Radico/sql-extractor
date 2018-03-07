
import java.sql.*;

import com.microsoft.sqlserver.jdbc.*;

public class SQLServer {

    private SQLServerParams params;

    public SQLServer(SQLServerParams params) {

        this.params = params;
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

            // Execute a stored procedure that returns some data.
            callableStatement = conn.prepareCall("{call dbo.uspGetEmployeeManagers(?)}");
            callableStatement.setInt(1, 50);
            resultSet = callableStatement.executeQuery();



            // Create and execute an SQL statement that returns some data.
            String SQL = "SELECT Title, DocumentSummary " +
                    "FROM Production.Document";
            statement = conn.createStatement();

            while (resultSet.next()) {
                System.out.println("EMPLOYEE: " + resultSet.getString("LastName") +
                        ", " + resultSet.getString("FirstName"));
                System.out.println("MANAGER: " + resultSet.getString("ManagerLastName") +
                        ", " + resultSet.getString("ManagerFirstName"));
                System.out.println();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
            }

            if (callableStatement != null) try {
                callableStatement.close();
            } catch (Exception e) {
            }

            if (conn != null) try {
                conn.close();
            } catch (Exception e) {
            }

            System.exit(1);
        }
    }


    public static void main(String[] args) {
    }

}
