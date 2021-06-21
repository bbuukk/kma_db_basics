package db.repos;

import db.entities.BookReader;

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
                "INSERT INTO mydb.BookReader(id_r, id_i) " +
                        "values (?, ?)")) {

            statement.setInt(1, bookReader.getIdReader());
            statement.setInt(2, bookReader.getIdInstance());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(BookReader old, BookReader updated) {
        return delete(old) && insert(updated);
    }

    public List<BookReader> getAllBookReaders() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.BookReader")
        ) {
            List<BookReader> list = new ArrayList<>();
            while (res.next()) {
                list.add(new BookReader(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


}
