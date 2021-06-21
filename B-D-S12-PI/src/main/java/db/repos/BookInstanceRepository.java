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
        String dateReturn = bookInstance.getDateReturn() == null ? "" : ", date_return=?";
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.BookInstance SET shelf = ?, ISBN = ?, date_out=?, date_exp=? "
                        + dateReturn +
                        " WHERE id_a=?")) {

            statement.setInt(1, bookInstance.getShelf());
            statement.setInt(2, bookInstance.getISBN());
            statement.setDate(3, Date.valueOf(bookInstance.getDateOut()));
            statement.setDate(4, Date.valueOf(bookInstance.getDateExp()));
            if (!dateReturn.isEmpty()) {
                statement.setDate(5, Date.valueOf(bookInstance.getDateReturn()));
                statement.setInt(6, bookInstance.getId());
            } else {
                statement.setInt(5, bookInstance.getId());
            }

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
                "INSERT INTO mydb.BookInstance(shelf, date_out, date_exp, date_return, ISBN) " +
                        "values (?,?,?,?,?)")) {

            statement.setInt(1, bookInstance.getShelf());
            statement.setDate(2, Date.valueOf(bookInstance.getDateOut()));
            statement.setDate(3, Date.valueOf(bookInstance.getDateExp()));
            statement.setDate(4, Date.valueOf(bookInstance.getDateReturn()));
            statement.setInt(5, bookInstance.getISBN());

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
