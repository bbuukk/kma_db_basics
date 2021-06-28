package db.repos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import db.entities.Author;
import db.entities.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AuthorRepository {
    Connection connection;

    public AuthorRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Author getAuthor(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Author WHERE id_a = " + id;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new Author(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public List<Author> getAuthor(String name) {
        if (name == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Author WHERE name_a = '" + name + "'";
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            List<Author> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Author(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean update(Author author) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.Author SET name_a = ?" +
                        " WHERE id_a=?")) {

            statement.setString(1, author.getName());
            statement.setInt(2, author.getId());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Author author) {
        if (author.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.Author WHERE id_a=?")) {

            statement.setInt(1, author.getId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(Author author) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.Author(name_a)" +
                        "values (?)")) {

            statement.setString(1, author.getName());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Entity> getAllAuthors() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Author")
        ) {
            ObservableList<Entity> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Author(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public List<Author> getAuthors() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Author")
        ) {
            List<Author> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Author(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public int authorNumber() {
        String sql = "SELECT Count(*) as num FROM mydb.Author";
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

        document.addHeader("Authors", "Authors");
        document.add(new Paragraph("Authors"));
        document.add(new Phrase(" "));
        PdfPTable table = new PdfPTable(2);
        addHeaders(table, Stream.of("id_author", "PIB"));
        addAuthors(table, getAuthors());
        table.setWidthPercentage(100);
        document.add(table);

        document.close();
        return document;
    }

    public PdfPTable getTablePDF() {
        PdfPTable table = new PdfPTable(2);
        addHeaders(table, Stream.of("id_author", "PIB"));
        addAuthors(table, getAuthors());
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

    private void addAuthors(PdfPTable table, List<Author> authors) {
        for (Author author : authors) {
            table.addCell(new Phrase(author.getId().toString()));
            table.addCell(new Phrase(author.getName()));
        }
    }


}
