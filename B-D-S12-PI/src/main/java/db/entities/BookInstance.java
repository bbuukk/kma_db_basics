package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookInstance {
    private Integer id;
    private Integer shelf;
    private LocalDate dateOut;
    private LocalDate dateExp;
    private LocalDate dateReturn;
    private Integer ISBN;

    public BookInstance(Integer id, Integer shelf, LocalDate dateOut, LocalDate dateExp, LocalDate dateReturn, Integer ISBN) {
        this.id = id;
        this.shelf = shelf;
        this.dateOut = dateOut;
        this.dateExp = dateExp;
        this.dateReturn = dateReturn;
        this.ISBN = ISBN;
    }

    public BookInstance(Integer shelf, LocalDate dateOut, LocalDate dateExp, LocalDate dateReturn, Integer ISBN) {
        this.shelf = shelf;
        this.dateOut = dateOut;
        this.dateExp = dateExp;
        this.dateReturn = dateReturn;
        this.ISBN = ISBN;
    }

    public BookInstance(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id_i");
        this.shelf = resultSet.getInt("shelf");
        try {
            this.dateOut = resultSet.getDate("date_out").toLocalDate();
        } catch (NullPointerException e) {
            this.dateOut = null;
        }
        try {
            this.dateExp = resultSet.getDate("date_exp").toLocalDate();
        } catch (NullPointerException e) {
            this.dateExp = null;
        }
        try {
            this.dateReturn = resultSet.getDate("date_return").toLocalDate();
        } catch (NullPointerException e) {
            this.dateReturn = null;
        }
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

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateOut) {
        this.dateOut = dateOut;
    }

    public LocalDate getDateExp() {
        return dateExp;
    }

    public void setDateExp(LocalDate dateExp) {
        this.dateExp = dateExp;
    }

    public LocalDate getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(LocalDate dateReturn) {
        this.dateReturn = dateReturn;
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
                ", dateOut=" + dateOut +
                ", dateExp=" + dateExp +
                ", dateReturn=" + dateReturn +
                ", ISBN=" + ISBN +
                '}';
    }
}
