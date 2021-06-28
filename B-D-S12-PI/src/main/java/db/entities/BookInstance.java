package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class BookInstance implements Entity {
    private Integer id;
    private Integer shelf;
    private Integer ISBN;

    public static final String TYPE_PARAMS_PATTERN  = "111";

    public BookInstance(Integer id, Integer shelf, Integer ISBN) {
        this.id = id;
        this.shelf = shelf;

        this.ISBN = ISBN;
    }

    public <T> BookInstance change(String innerVarName, T value)
    {
        switch (innerVarName)
        {
            case "id":
                //TODO IF ID ALREADY EXISTS
                setId((Integer) value);
                return this;
            case "shelf":
                setShelf((Integer) value);
                return this;
            case "ISBN":
                setISBN((Integer) value);
                return this;

            default:
                System.out.println("Default");
                return this;
        }
    }

    public BookInstance() {
    }

    public BookInstance(Integer shelf, Integer ISBN) {
        this.shelf = shelf;
        this.ISBN = ISBN;
    }

    public BookInstance(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id_i");
        this.shelf = resultSet.getInt("shelf");
        this.ISBN = resultSet.getInt("ISBN");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShelf() {
        return shelf;
    }

    public void setShelf(Integer shelf) {
        this.shelf = shelf;
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return "BookInstance{" +
                "id=" + id +
                ", shelf=" + shelf +
                ", ISBN=" + ISBN +
                '}';
    }
}
