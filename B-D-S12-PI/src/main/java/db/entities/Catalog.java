package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Catalog implements Entity{
    private Integer id;
    private String name;

    public static final String TYPE_PARAMS_PATTERN  = "10";


    public Catalog(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Catalog(String name) {
        this.name = name;
    }

    public Catalog() {
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
        return
//                "Catalog{" +
//                "id=" + id +
//                ", name='" +
                name;
//                + '\'' +
//                '}';
    }

    public <T> Catalog change(String innerVarName, T value)
    {
        switch (innerVarName)
        {
            case "id":
                //TODO IF ID ALREADY EXISTS
                setId((Integer) value);
                return this;
            case "name":
                setName((String) value);
                return this;

            default:
                System.out.println("Default");
                return this;
        }
    }
}
