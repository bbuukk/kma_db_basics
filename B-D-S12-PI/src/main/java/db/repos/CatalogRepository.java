package db.repos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import db.entities.Catalog;
import db.entities.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CatalogRepository {
    Connection connection;

    public CatalogRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Catalog getCatalog(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Catalog WHERE id_c = " + id;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new Catalog(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public List<Catalog> getCatalog(String name) {
        if (name == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Catalog WHERE name_c = '" + name + "'";
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            List<Catalog> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Catalog(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean update(Catalog catalog) {
        if (catalog.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.Catalog SET name_c = ?" +
                        " WHERE id_c=?")) {
            //statement.setInt(1, 1);
            statement.setString(1, catalog.getName());
            statement.setInt(2, catalog.getId());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Catalog catalog) {
        if (catalog.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.Catalog WHERE id_c=?")) {

            statement.setInt(1, catalog.getId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(Catalog catalog) {
        //if (catalog.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.Catalog(name_c)" +
                        "values (?)")) {

            statement.setString(1, catalog.getName());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Entity> getAllCatalogs() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Catalog")
        ) {
            ObservableList<Entity> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Catalog(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public List<Catalog> getCatalogs() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Catalog")
        ) {
            List<Catalog> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Catalog(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    public int catalogNumber() {
        String sql = "SELECT Count(*) as num FROM mydb.Catalog";
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return res.getInt("num");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    public Document formReport(String filename) throws DocumentException, FileNotFoundException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();

        document.addHeader("Catalogs", "Catalogs");
        document.add(new Paragraph("Catalogs"));
        document.add(new Phrase(" "));
        PdfPTable table = new PdfPTable(2);
        addHeaders(table, Stream.of("id_catalog", "Name"));
        addAuthors(table, getCatalogs());
        table.setWidthPercentage(100);
        document.add(table);

        document.close();
        return document;
    }

    public PdfPTable getTablePDF() {
        PdfPTable table = new PdfPTable(2);
        addHeaders(table, Stream.of("id_catalog", "Name"));
        addAuthors(table, getCatalogs());
        table.setWidthPercentage(100);
        return table;
    }


    private void addHeaders(PdfPTable table, Stream<String> headers) {
        headers.forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void addAuthors(PdfPTable table, List<Catalog> catalogs) {
        for (Catalog catalog : catalogs) {
            table.addCell(new Phrase(catalog.getId().toString()));
            table.addCell(new Phrase(catalog.getName()));
        }
    }

}
