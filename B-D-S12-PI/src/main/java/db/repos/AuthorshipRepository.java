package db.repos;

import db.entities.Authorship;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorshipRepository {

    Connection connection;

    public AuthorshipRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Authorship getAuthorship(Integer idAuthor, Integer ISBN) {
        if (idAuthor == null || ISBN == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Authorship WHERE id_a = " + idAuthor + " AND ISBN = " + ISBN;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new Authorship(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public List<Authorship> getAllAuthorships() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Authorship")
        ) {
            List<Authorship> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Authorship(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


}
