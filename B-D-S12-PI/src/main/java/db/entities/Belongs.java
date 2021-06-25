package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Belongs implements Entity {
    private Integer isbn;
    private Integer idCatalog;

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
