package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Catalog implements Entity{
    private Integer id;
    private String name;

    public Catalog(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Catalog(String name) {
        this.name = name;
    }

    public Catalog(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id_c");
        this.name = resultSet.getString("name_c");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
