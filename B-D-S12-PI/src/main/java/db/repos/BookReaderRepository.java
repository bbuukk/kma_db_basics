package db.repos;

import db.entities.BookReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
