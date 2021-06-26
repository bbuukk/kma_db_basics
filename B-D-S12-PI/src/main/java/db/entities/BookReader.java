package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BookReader implements Entity {
    private Integer idReader;
    private Integer idInstance;
    private LocalDate dateOut;
    private LocalDate dateExp;
    private LocalDate dateReturn;

    public static final String TYPE_PARAMS_PATTERN = "11222";


    public BookReader(Integer idReader, Integer idInstance, LocalDate dateOut, LocalDate dateExp, LocalDate dateReturn) {
        this.idReader = idReader;
        this.idInstance = idInstance;
        this.dateOut = dateOut;
        this.dateExp = dateExp;
        this.dateReturn = dateReturn;
    }

    public <T> BookReader change(String innerVarName, T value)
    {
        switch (innerVarName)
        {
            case "idReader":
                //TODO IF ID ALREADY EXISTS
                setIdReader((Integer) value);
                return this;
            case "idInstance":
                setIdInstance((Integer) value);
                return this;
            case "dateOut":
                setDateOut((LocalDate) value);
                return this;
            case "dateExp":
                setDateExp((LocalDate) value);
                return this;
            case "dateReturn":
                setDateReturn((LocalDate) value);
                return this;

            default:
                System.out.println("Default");
                return this;

        }
    }

    public BookReader(ResultSet resultSet) throws SQLException {
        this.idReader = resultSet.getInt("id_r");
        this.idInstance = resultSet.getInt("id_i");
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
    }

    public BookReader() {
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

    public Integer getIdReader() {
        return idReader;
    }

    public void setIdReader(Integer idReader) {
        this.idReader = idReader;
    }

    public Integer getIdInstance() {
        return idInstance;
    }

    public void setIdInstance(Integer idInstance) {
        this.idInstance = idInstance;
    }

    @Override
    public String toString() {
        return "BookReader{" +
                "idReader=" + idReader +
                ", idInstance=" + idInstance +
                '}';
    }
}
