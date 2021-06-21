package db.repos;

import db.entities.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepository {
    Connection connection;

    public AuthorRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Author getAuthor(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Author WHERE id_a = " + id;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new Author(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public List<Author> getAuthor(String name) {
        if (name == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Author WHERE name_a = '" + name + "'";
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            List<Author> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Author(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean update(Author author) {
        if (author.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.Author SET name_a = ?" +
                        " WHERE id_a=?")) {

            statement.setString(1, author.getName());
            statement.setInt(2, author.getId());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Author author) {
        if (author.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.Author WHERE id_a=?")) {

            statement.setInt(1, author.getId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(Author author) {
        if (author.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.Author(name_a)" +
                        "values (?)")) {

            statement.setString(1, author.getName());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public List<Author> getAllAuthors() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Author")
        ) {
            List<Author> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Author(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }
}