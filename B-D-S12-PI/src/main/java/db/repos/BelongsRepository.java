package db.repos;

import db.entities.Belongs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
