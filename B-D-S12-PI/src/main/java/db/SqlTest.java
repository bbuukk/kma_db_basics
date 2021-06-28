package db;

import java.sql.SQLException;

public class SqlTest {
    public static void main(String[] args) throws SQLException {
        SqlOps sqlOps = new SqlOps();
        System.out.println(sqlOps.getBookReaderRepository().getBookReadersForUnavailableInstances(1));
    }

}
