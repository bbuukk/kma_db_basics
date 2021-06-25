package db.repos;

import db.entities.BookReader;
import db.entities.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookReaderRepository {
    Connection connection;

    public BookReaderRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public BookReader getBookReader(Integer idReader, Integer idInstace) {
        if (idReader == null || idInstace == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.BookReader WHERE id_i = " + idInstace + " AND id_r = " + idReader;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new BookReader(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean delete(BookReader bookReader) {
        if (bookReader.getIdReader() == null || bookReader.getIdInstance() == null)
            throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.BookReader WHERE id_r=? AND  id_i = ?")) {

            statement.setInt(1, bookReader.getIdReader());
            statement.setInt(2, bookReader.getIdInstance());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(BookReader bookReader) {
        if (bookReader.getIdReader() == null || bookReader.getIdInstance() == null)
            throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.BookReader(id_r, id_i, date_out, date_exp, dare_return) " +
                        "values (?, ?, ?, ?, ?)"))  {

            statement.setInt(1, bookReader.getIdReader());
            statement.setInt(2, bookReader.getIdInstance());
            statement.setDate(3, Date.valueOf(bookReader.getDateOut()));
            statement.setDate(4, Date.valueOf(bookReader.getDateExp()));
            statement.setDate(5, Date.valueOf(bookReader.getDateReturn()));

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(BookReader bookReader) {
        if (bookReader.getIdReader() == null || bookReader.getIdInstance() == null)
            throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.BookReader SET date_out = ?, date_exp=?, dare_return=?" +
                        " WHERE id_i=? and id_r=?")) {
            //statement.setInt(1, 1);
            statement.setDate(1, Date.valueOf(bookReader.getDateOut()));
            statement.setDate(2, Date.valueOf(bookReader.getDateExp()));
            statement.setDate(3, Date.valueOf(bookReader.getDateReturn()));
            statement.setInt(4, bookReader.getIdInstance());
            statement.setInt(5, bookReader.getIdReader());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Entity> getAllBookReaders() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.BookReader")
        ) {
            ObservableList<Entity> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new BookReader(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
//            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


}
