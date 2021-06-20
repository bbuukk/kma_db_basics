package db;

public class SqlTest {
    public static void main(String[] args) {
        SqlOps sqlOps = new SqlOps();
        sqlOps.init();

        System.out.println(sqlOps.getAllReaders());
        System.out.println(sqlOps.getReader(1));
        System.out.println("--------------");

        System.out.println(sqlOps.getAllAuthors());
        System.out.println(sqlOps.getAuthor(1));
        System.out.println("--------------");


        System.out.println(sqlOps.getAllAuthorships());
        System.out.println(sqlOps.getAuthorship(1, 1));
        System.out.println("--------------");


        System.out.println(sqlOps.getAllBelongs());
        System.out.println(sqlOps.getBelongs(1, 1));
        System.out.println("--------------");


        System.out.println(sqlOps.getAllBooks());
        System.out.println(sqlOps.getBook(1));
        System.out.println("--------------");


        System.out.println(sqlOps.getAllBookInstances());
        System.out.println(sqlOps.getBookInstance(1));
        System.out.println("--------------");


        System.out.println(sqlOps.getAllBookReaders());
        System.out.println(sqlOps.getBookReader(1, 1));
        System.out.println("--------------");


        System.out.println(sqlOps.getAllCatalogs());
        System.out.println(sqlOps.getCatalog(1));


        sqlOps.close();
    }
}
