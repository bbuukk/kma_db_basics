package db;

public class SqlTest {
    public static void main(String[] args) {
        SqlOps sqlOps = new SqlOps();
        System.out.println(sqlOps.getAuthorRepository().getAllAuthors());

    }
}
