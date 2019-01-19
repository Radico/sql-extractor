
import org.apache.commons.dbutils.AbstractQueryRunner;
import org.apache.commons.dbutils.StatementConfiguration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

class CustomQueryRunner extends AbstractQueryRunner {

    CustomQueryRunner(DataSource ds, StatementConfiguration stmtConfig) {
        super(ds, stmtConfig);
    }

    ResultSet query(String sql) throws SQLException {
        Connection conn = this.prepareConnection();
        return this.query(conn, true, sql);
    }

    private ResultSet query(Connection conn, boolean closeConn, String sql, Object... params)
            throws SQLException {
        if (conn == null) {
            throw new SQLException("Null connection");
        }

        if (sql == null) {
            if (closeConn) {
                close(conn);
            }
            throw new SQLException("Null SQL statement");
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.prepareStatement(conn, sql);
            this.fillStatement(stmt, params);
            rs = this.wrap(stmt.executeQuery());
        } catch (SQLException e) {
            this.rethrow(e, sql, params);

        } finally {
            try {
                close(rs);
            } finally {
                close(stmt);
                if (closeConn) {
                    close(conn);
                }
            }
        }
        return rs;
    }
}
