package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Authorship implements Entity {
    private Integer id;
    private Integer ISBN;
    public static final String TYPE_PARAMS_PATTERN  = "11";

    public Authorship(Integer id, Integer ISBN) {
        this.id = id;
        this.ISBN = ISBN;
    }

    public Authorship() {
    }

    public <T> Authorship change(String innerVarName, T value)
    {
        switch (innerVarName)
        {
            case "id":
                //TODO IF ID ALREADY EXISTS
                setId((Integer) value);
                return this;
            case "ISBN":
                setISBN((Integer) value);
                return this;

            default:
                System.out.println("Default");
                return this;
        }
    }

    public Authorship(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id_a");
        this.ISBN = resultSet.getInt("ISBN");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return "Authorship{" +
                "id=" + id +
                ", ISBN=" + ISBN +
                '}';
    }
}
