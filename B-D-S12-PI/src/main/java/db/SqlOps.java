package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import db.repos.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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

}
