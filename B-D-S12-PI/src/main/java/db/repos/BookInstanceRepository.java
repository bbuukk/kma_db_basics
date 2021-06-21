package db.repos;

import db.entities.BookInstance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookInstanceRepository {
    Connection connection;

    public BookInstanceRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public BookInstance getBookInstance(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.BookInstance WHERE id_i = " + id;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new BookInstance(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean update(BookInstance bookInstance) {
        if (bookInstance.getId() == null || bookInstance.getISBN() == null) throw new IllegalArgumentException();

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.BookInstance SET shelf = ?, ISBN = ?" +
                        " WHERE id_i=?")) {

            statement.setInt(1, bookInstance.getShelf());
            statement.setInt(2, bookInstance.getISBN());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(BookInstance bookInstance) {
        if (bookInstance.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.BookInstance WHERE id_i=?")) {

            statement.setInt(1, bookInstance.getId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(BookInstance bookInstance) {
        if (bookInstance.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.BookInstance(shelf, ISBN) " +
                        "values (?,?)")) {

            statement.setInt(1, bookInstance.getShelf());
            statement.setInt(2, bookInstance.getISBN());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }


    public List<BookInstance> getAllBookInstances() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.BookInstance")
        ) {
            List<BookInstance> list = new ArrayList<>();
            while (res.next()) {
                list.add(new BookInstance(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

}
