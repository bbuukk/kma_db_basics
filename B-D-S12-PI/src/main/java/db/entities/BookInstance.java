package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookInstance {
    private Integer id;
    private Integer shelf;
    private Integer ISBN;

    public BookInstance(Integer id, Integer shelf, Integer ISBN) {
        this.id = id;
        this.shelf = shelf;
        this.ISBN = ISBN;
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
