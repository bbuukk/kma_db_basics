package db.repos;

import db.entities.Belongs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Belongs> getAllBelongs() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Belongs")
        ) {
            List<Belongs> list = new ArrayList<>();
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

}
