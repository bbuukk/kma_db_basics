package db;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.cj.jdbc.MysqlDataSource;
import db.entities.Reader;
import db.repos.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlOps {
    private Connection connection;

    private AuthorRepository authorRepository;
    private AuthorshipRepository authorshipRepository;
    private BelongsRepository belongsRepository;
    private BookInstanceRepository bookInstanceRepository;
    private BookReaderRepository bookReaderRepository;
    private BookRepository bookRepository;
    private CatalogRepository catalogRepository;
    private ReaderRepository readerRepository;

//    public static List<String> tableNames = Arrays.asList("Author", "Authorship","Belongs", "Book","BookInstance",
//            "BookReader","Catalog", "Reader");

    public SqlOps() {
        init();
    }

    private static MysqlDataSource getMySQLDataSource() {
        Properties props = new Properties();
        FileInputStream fis;
        MysqlDataSource mysqlDS = null;
        try {
            fis = new FileInputStream("db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }

    public void init() {
        MysqlDataSource dataSource = getMySQLDataSource();
        try {
            connection = dataSource.getConnection();
            System.out.print("Successfully connected: ");
            System.out.println(connection.getMetaData().getDatabaseProductName());

            authorRepository = new AuthorRepository(connection);
            authorshipRepository = new AuthorshipRepository(connection);
            belongsRepository = new BelongsRepository(connection);
            bookInstanceRepository = new BookInstanceRepository(connection);
            bookReaderRepository = new BookReaderRepository(connection);
            bookRepository = new BookRepository(connection);
            catalogRepository = new CatalogRepository(connection);
            readerRepository = new ReaderRepository(connection);

        } catch (SQLException e) {
            System.out.println("Can't get connection. Incorrect URL");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
            System.out.println("Successfully closed connection");
        } catch (SQLException e) {
            System.out.println("Can't close connection");
            e.printStackTrace();
        }
    }

    public AuthorRepository getAuthorRepository() {
        return authorRepository;
    }

    public AuthorshipRepository getAuthorshipRepository() {
        return authorshipRepository;
    }

    public BelongsRepository getBelongsRepository() {
        return belongsRepository;
    }

    public BookInstanceRepository getBookInstanceRepository() {
        return bookInstanceRepository;
    }

    public BookReaderRepository getBookReaderRepository() {
        return bookReaderRepository;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public CatalogRepository getCatalogRepository() {
        return catalogRepository;
    }

    public ReaderRepository getReaderRepository() {
        return readerRepository;
    }

//    public List<Reader> getAllReadersNotReadAuthor(){
//        try (Statement st = connection.createStatement();
//        ResultSet resultSet = st.executeQuery(
//                "Select * from mybd.Reader" +
//                "where id_r in (" +
//                        "Select id_r from BookReader" +
//                        "where date_return is null OR date_out > date_return);"
//        )){
//
//        }catch (SQLException e) {
//            System.out.println("Не вірний SQL запит");
//            e.printStackTrace();
//
//        }
//    }

    public List<Reader> getAllReadersNotReadAuthor(String authorName) {
        if (authorName.isEmpty()) throw new IllegalArgumentException();
        List<Reader> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet resultSet = st.executeQuery(
                     "SELECT * FROM mydb.Reader" +
                             " WHERE id_r not in(" +
                             "SELECT id_r FROM mydb.BookReader " +
                             "where id_i in(" +
                             "SELECT id_i from mydb.BookInstance " +
                             "WHERE ISBN in(" +
                             "SELECT ISBN from mydb.Authorship " +
                             "WHERE id_a = (SELECT id_a FROM mydb.Author " +
                             "WHERE name_a = '" + authorName + "'))))")) {

            while (resultSet.next()) {
                list.add(new Reader(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();

        }
        return list;
    }

    public List<CityReaderNum> getNumReadersByCityWIthPrice(String requirement) {
        if (requirement.isEmpty()) throw new IllegalArgumentException();
        List<CityReaderNum> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet resultSet = st.executeQuery(
                     "Select city_r, Count(id_r) as reader_number " +
                             "from mydb.Reader " +
                             "where id_r in ( " +
                             "select id_r " +
                             "from mydb.BookReader " +
                             "where id_i in ( " +
                             "select id_i " +
                             "from mydb.BookInstance " +
                             "where ISBN in ( " +
                             "select ISBN " +
                             "from mydb.Book " +
                             "where price " + requirement +
                             "))) group by city_r")) {

            while (resultSet.next()) {
                list.add(new CityReaderNum(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();

        }
        return list;
    }

    public List<AuthorBookNum> getNumBooksByAuthorForCategory(String nameCategory) {
        if (nameCategory.isEmpty()) throw new IllegalArgumentException();
        List<AuthorBookNum> list = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet resultSet = st.executeQuery(
                     "select mydb.Author.id_a, name_a, Count(ISBN) as book_number from mydb.Author inner join mydb.Authorship on Author.id_a = Authorship.id_a\n" +
                             "where ISBN in (\n" +
                             "\n" +
                             "select ISBN\n" +
                             "\n" +
                             "from mydb.Belongs\n" +
                             "\n" +
                             "where id_c in (\n" +
                             "\n" +
                             "select id_c\n" +
                             "\n" +
                             "from mydb.Catalog\n" +
                             "\n" +
                             "where name_c = '" + nameCategory + "' " +
                             "\n" +
                             ")\n" +
                             "\n" +
                             ")\n" +
                             "\n" +
                             "group by Author.id_a")) {

            while (resultSet.next()) {
                list.add(new AuthorBookNum(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();

        }
        return list;
    }


    //requirement = "> 28";
    //or " = 12 ";
    static class CityReaderNum {
        String city;
        int num;

        CityReaderNum(ResultSet resultSet) throws SQLException {
            city = resultSet.getString("city_r");
            num = resultSet.getInt("reader_number");
        }

        @Override
        public String toString() {
            return "CityReaderNum{" +
                    "city='" + city + '\'' +
                    ", num=" + num +
                    '}';
        }
    }

    static class AuthorBookNum {
        String author;
        int num;

        AuthorBookNum(ResultSet resultSet) throws SQLException {
            author = resultSet.getString("name_a");
            num = resultSet.getInt("book_number");
        }

        @Override
        public String toString() {
            return "AuthorBookNum{" +
                    "author='" + author + '\'' +
                    ", num=" + num +
                    '}';
        }
    }


    public void formReport(String filename) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();

        document.addHeader("Full Report", "Full Report for tables");
        document.add(new Paragraph("Full Report"));
        document.add(new Phrase(" "));

        document.add(new Paragraph("Readers"));
        document.add(new Phrase(" "));
        document.add(getReaderRepository().getTablePDF());

        document.add(new Paragraph("Books"));
        document.add(new Phrase(" "));
        document.add(getBookRepository().getTablePDF());

        document.add(new Paragraph("Authors"));
        document.add(new Phrase(" "));
        document.add(getAuthorRepository().getTablePDF());

        document.add(new Paragraph("Catalogs"));
        document.add(new Phrase(" "));
        document.add(getCatalogRepository().getTablePDF());

        document.add(new Paragraph("Instances"));
        document.add(new Phrase(" "));
        document.add(getBookInstanceRepository().getTablePDF());

        document.close();
    }

}
