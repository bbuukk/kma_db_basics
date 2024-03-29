package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Author implements Entity {

    private Integer id;
    private String name;

    public static final String TYPE_PARAMS_PATTERN  = "10";

    public Author(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public <T> Author change(String innerVarName, T value)
    {
        switch (innerVarName)
        {
            case "id":
                //TODO IF ID ALREADY EXISTS
                setId((Integer) value);
                return this;
            case "ISBN":
                setName((String) value);
                return this;
            default:
                System.out.println("Default");
                return this;
        }
    }

    public Author() {

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

    public void change(String value, String nameOfInnerVariable ){
//        switch ()
        //make properties file
    }
}
