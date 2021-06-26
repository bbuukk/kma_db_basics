package db.repos;

import db.entities.Catalog;
import db.entities.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogRepository {
    Connection connection;

    public CatalogRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Catalog getCatalog(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Catalog WHERE id_c = " + id;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new Catalog(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public List<Catalog> getCatalog(String name) {
        if (name == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Catalog WHERE name_c = '" + name + "'";
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            List<Catalog> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Catalog(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean update(Catalog catalog) {
        if (catalog.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.Catalog SET name_c = ?" +
                        " WHERE id_c=?")) {
            //statement.setInt(1, 1);
            statement.setString(1, catalog.getName());
            statement.setInt(2, catalog.getId());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Catalog catalog) {
        if (catalog.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.Catalog WHERE id_c=?")) {

            statement.setInt(1, catalog.getId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(Catalog catalog) {
        //if (catalog.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.Catalog(name_c)" +
                        "values (?)")) {

            statement.setString(1, catalog.getName());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Entity> getAllCatalogs() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Catalog")
        ) {
            ObservableList<Entity> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Catalog(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


}
