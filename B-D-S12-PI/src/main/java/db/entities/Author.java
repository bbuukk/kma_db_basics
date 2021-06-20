package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Author {

    private Integer id;
    private String name;

    public Author(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id_a");
        this.name = resultSet.getString("name_a");
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
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
