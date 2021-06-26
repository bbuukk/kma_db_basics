package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Belongs implements Entity {
    private Integer isbn;
    private Integer idCatalog;
    public static final String TYPE_PARAMS_PATTERN  = "11";

    public Belongs(Integer isbn, Integer idCatalog) {
        this.isbn = isbn;
        this.idCatalog = idCatalog;
    }

    public Belongs() {
    }

    public Belongs(ResultSet resultSet) throws SQLException {
        this.isbn = resultSet.getInt("ISBN");
        this.idCatalog = resultSet.getInt("id_c");
    }

    public <T> void change(String innerVarName, T value)
    {
        switch (innerVarName)
        {
            case "isbn":
                //TODO IF ID ALREADY EXISTS
                setIsbn((Integer) value);
                break;
            case "isCatalog":
                setIdCatalog((Integer) value);
                break;

            default:
                System.out.println("ReaderDefault");
        }
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public Integer getIdCatalog() {
        return idCatalog;
    }

    public void setIdCatalog(Integer idCatalog) {
        this.idCatalog = idCatalog;
    }

    @Override
    public String toString() {
        return "Belongs{" +
                "isbn=" + isbn +
                ", idCatalog=" + idCatalog +
                '}';
    }
}
