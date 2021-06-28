package db.repos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import db.entities.BookInstance;
import db.entities.Entity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class BookInstanceRepository {
    Connection connection;

    public BookInstanceRepository(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public BookInstance getBookInstance(Integer id) {
        if (id == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.BookInstance WHERE id_i = " + id;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        ) {
            if (res.next()) {
                return new BookInstance(res);
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean update(BookInstance bookInstance) {
        if (bookInstance.getId() == null || bookInstance.getISBN() == null) throw new IllegalArgumentException();

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.BookInstance SET shelf = ?, ISBN = ?" +
                        " WHERE id_i=?")) {

            statement.setInt(1, bookInstance.getShelf());
            statement.setInt(2, bookInstance.getISBN());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(BookInstance bookInstance) {
        //if (bookInstance.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.BookInstance WHERE id_i=?")) {

            statement.setInt(1, bookInstance.getId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(BookInstance bookInstance) {
       // if (bookInstance.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.BookInstance(shelf, ISBN) " +
                        "values (?,?)")) {

            statement.setInt(1, bookInstance.getShelf());
            statement.setInt(2, bookInstance.getISBN());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }
    }


    public ObservableList<Entity> getAllBookInstances() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.BookInstance")
        ) {
            ObservableList<Entity> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new BookInstance(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    public List<BookInstance> getBookInstances() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.BookInstance")
        ) {
            List<BookInstance> list = new ArrayList<>();
            while (res.next()) {
                list.add(new BookInstance(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    public int bookInstanceNumber() {
        String sql = "SELECT Count(*) as num FROM mydb.BookInstance";
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


        document.addHeader("Book instances", "Book instances");
        document.add(new Paragraph("Book instances"));
        document.add(new Phrase(" "));
        PdfPTable table = new PdfPTable(4);
        addHeaders(table, Stream.of("id_instance", "ISBN", "name", "shelf"));
        addBooks(table, getBookInstances());
        table.setWidthPercentage(100);
        document.add(table);
        document.close();
        return document;
    }

    private HashMap<Integer, String> getBookNames() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT ISBN, name_b FROM mydb.Book")
        ) {
            HashMap<Integer, String> map = new HashMap<>();
            while (res.next()) {
                map.put(res.getInt("ISBN"), res.getString("name_b"));
            }
            return map;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public PdfPTable getTablePDF() {

        PdfPTable table = new PdfPTable(4);
        addHeaders(table, Stream.of("id_instance", "ISBN", "name", "shelf"));
        addBooks(table, getBookInstances());
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

    private void addBooks(PdfPTable table, List<BookInstance> books) {
        HashMap<Integer, String> map = getBookNames();
        for (BookInstance book : books) {
            table.addCell(new Phrase(book.getId().toString()));
            table.addCell(new Phrase(book.getISBN().toString()));
            table.addCell(new Phrase(map.get(book.getISBN())));
            table.addCell(new Phrase(book.getShelf().toString()));
        }
    }
}
