package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookReader {
    private Integer idReader;
    private Integer idInstance;

    public BookReader(Integer idReader, Integer idInstance) {
        this.idReader = idReader;
        this.idInstance = idInstance;
    }

    public BookReader() {
    }

    public BookReader(ResultSet resultSet) throws SQLException {
        this.idReader = resultSet.getInt("id_r");
        this.idInstance = resultSet.getInt("id_i");
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
