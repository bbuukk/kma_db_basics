package db.repos;

import db.entities.Book;
import db.entities.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    Connection connection;

    public BookRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public Book getBook(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Book WHERE ISBN = " + id;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new Book(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            //throw new RuntimeException("Can`t select anything", e);
            return null;
        }
    }

    public List<Book> getBook(String name) {
        if (name == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Book WHERE name_b = '" + name + "'";
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            List<Book> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Book(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public ObservableList<Entity> getAllBooks() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Book")
        ) {
            ObservableList<Entity> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Book(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean update(Book book) {
        if (book.getISBN() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.Book SET name_b=?, publisher=?, pub_city=?," +
                        " pub_year=?, page_num=?, price=?" +
                        " WHERE ISBN=?")) {
            //statement.setInt(1, 1);
            statement.setString(1, book.getName());
            statement.setString(2, book.getPublisher());
            statement.setString(3, book.getPubCity());
            statement.setDate(4, Date.valueOf(book.getPubYear()));
            statement.setString(5, String.valueOf(book.getPageNum()));
            statement.setString(6, book.getPrice());
            statement.setInt(7, book.getISBN());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Book book) {
        if (book.getISBN() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.Book WHERE ISBN=?")) {
            //statement.setInt(1, 1);
            statement.setInt(1, book.getISBN());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(Book book) {
        if (book.getISBN() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.Book(name_b, publisher, pub_city, pub_year, page_num, price) " +
                        "values (?,?,?,?,?,?)")) {
            //statement.setInt(1, 1);
            statement.setString(1, book.getName());
            statement.setString(2, book.getPublisher());
            statement.setString(3, book.getPubCity());
            statement.setDate(4, Date.valueOf(book.getPubYear()));
            statement.setString(5, String.valueOf(book.getPageNum()));
            statement.setString(6, book.getPrice());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }


    //returns books, which reader has ever taken from library
    public ObservableList<Book> getAnyBooksOfReader(String login) {
        if (login == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from mydb.Book " +
                        "where ISBN in( " +
                        "    select ISBN from mydb.BookInstance " +
                        "    where id_i in( " +
                        "        select id_i from mydb.BookReader " +
                        "        where id_r in( " +
                        "            Select id_r from mydb.Reader " +
                        "            where login = ?" +
                        "            ) " +
                        "        ) " +
                        "    )")
        ) {
            statement.setString(1, login);
            ResultSet res = statement.executeQuery();
            ObservableList<Book> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Book(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    //returns books which reader hasnt returned yet
    public ObservableList<Book> getBooksWhichReaderHasntReturned(String login) {
        if (login == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from mydb.Book\n" +
                        "where ISBN in(\n" +
                        "    select ISBN from mydb.BookInstance\n" +
                        "    where id_i in(\n" +
                        "        select id_i from mydb.BookReader\n" +
                        "        where id_r in(\n" +
                        "            Select id_r from mydb.Reader\n" +
                        "            where login = ?\n" +
                        "            ) and date_return is null\n" +
                        "        )\n" +
                        "    )")
        ) {
            statement.setString(1, login);
            ResultSet res = statement.executeQuery();
            ObservableList<Book> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Book(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    //returns list of books with available instances
    public ObservableList<Book> getAvailableBooks() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(
                     "select * from mydb.Book " +
                             "where ISBN in( " +
                             "    select ISBN from mydb.BookInstance " +
                             "    where id_i in( " +
                             "        select id_i from mydb.BookReader " +
                             "        where date_return is not null " +
                             "        ) " +
                             "    )")
        ) {
            ObservableList<Book> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Book(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    public ObservableList<Book> getAllBooksOfAuthor(String name) {
        if (name == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from mydb.Book\n" +
                        "where ISBN in (\n" +
                        "    select ISBN\n" +
                        "    from mydb.Authorship\n" +
                        "    where id_a in (\n" +
                        "        select id_a\n" +
                        "        from mydb.Author\n" +
                        "        where name_a = ?\n" +
                        "    )\n" +
                        ")")
        ) {
            statement.setString(1, name);
            ResultSet res = statement.executeQuery();
            ObservableList<Book> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Book(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    public ObservableList<Book> getBooksOfCategory(String name) {
        if (name == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from mydb.Book\n" +
                        "where ISBN in (\n" +
                        "    select ISBN\n" +
                        "    from mydb.Belongs\n" +
                        "    where id_c in (\n" +
                        "        select id_c\n" +
                        "        from mydb.Catalog\n" +
                        "        where name_c = ?\n" +
                        "    )\n" +
                        ")")
        ) {
            statement.setString(1, name);
            ResultSet res = statement.executeQuery();
            ObservableList<Book> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Book(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

}
