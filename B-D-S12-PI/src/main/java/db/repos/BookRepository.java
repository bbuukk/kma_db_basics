package db.repos;

import db.entities.Book;

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

    public List<Book> getAllBooks() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Book")
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


}
