package db;

import java.sql.SQLException;

public class SqlTest {
    public static void main(String[] args) throws SQLException {
        SqlOps sqlOps = new SqlOps();
        try {
            sqlOps.formReport("Report.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
