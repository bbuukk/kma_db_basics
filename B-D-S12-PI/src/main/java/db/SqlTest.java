package db;

public class SqlTest {
    public static void main(String[] args) {
        SqlOps sqlOps = new SqlOps();
        System.out.println(sqlOps.getAllReadersNotReadAuthor("shevchenko"));
        System.out.println(sqlOps.getNumReadersByCityWIthPrice(" > 10"));
        System.out.println(sqlOps.getNumBooksByAuthorForCategory("Novel"));
        System.out.println(sqlOps.getAllReadersbyReaderAndCategory("Chervonenko Pavliv Yurich", "Science fiction"));
    }
}
