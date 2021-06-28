package db.repos;

import db.entities.Belongs;
import db.entities.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Set;

public class BelongsRepository {
    Connection connection;

    public BelongsRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public Belongs getBelongs(Integer idCatalog, Integer ISBN) {
        if (idCatalog == null || ISBN == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Belongs WHERE id_c = " + idCatalog + " AND ISBN = " + ISBN;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new Belongs(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean delete(Belongs belongs) {
        if (belongs.getIdCatalog() == null || belongs.getIsbn() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.Belongs WHERE id_c=? AND  ISBN = ?")) {

            statement.setInt(1, belongs.getIdCatalog());
            statement.setInt(2, belongs.getIsbn());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }



    public boolean insert(Belongs belongs) {
        if (belongs.getIdCatalog() == null || belongs.getIsbn() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.Belongs(id_c, ISBN) " +
                        "values (?, ?)")) {

            statement.setInt(1, belongs.getIdCatalog());
            statement.setInt(2, belongs.getIsbn());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Belongs old, Belongs updated) {
        return delete(old) && insert(updated);
    }

    public ObservableList<Entity> getAllBelongs() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Belongs")
        ) {
            ObservableList<Entity> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Belongs(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public int belongsNumber() {
        String sql = "SELECT Count(*) as num FROM mydb.Belongs";
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return res.getInt("num");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

}
