package db;

import db.entities.BookInstance;

import java.sql.SQLException;

public class SqlTest {
    public static void main(String[] args) throws SQLException {
        SqlOps sqlOps = new SqlOps();
        sqlOps.getBookInstanceRepository().insert(new BookInstance(12, 7));
    }

}
