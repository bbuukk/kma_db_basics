package db.repos;

import db.entities.Authorship;
import db.entities.Entity;
import db.entities.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
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

    public boolean delete(Authorship authorship) {
        if (authorship.getId() == null || authorship.getISBN() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.Authorship WHERE id_a=? AND  ISBN = ?")) {

            statement.setInt(1, authorship.getId());
            statement.setInt(2, authorship.getISBN());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(Authorship authorship) {
        if (authorship.getId() == null || authorship.getISBN() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.Authorship(id_a, ISBN) " +
                        "values (?, ?)")) {

            statement.setInt(1, authorship.getId());
            statement.setInt(1, authorship.getISBN());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Authorship old, Authorship updated) {
        return delete(old) && insert(updated);
    }

    public ObservableList<Entity> getAllAuthorships() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Authorship")
        ) {
            ObservableList<Entity> list = FXCollections.observableArrayList();
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
