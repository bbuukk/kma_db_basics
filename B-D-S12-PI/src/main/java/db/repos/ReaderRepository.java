package db.repos;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import db.entities.BookReader;
import db.entities.Entity;
import db.entities.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ReaderRepository
{
    Connection connection;
    public static final double PENYA_PER_DAY = 5;

    public ReaderRepository(Connection connection)
    {
        this.connection = connection;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    public Reader getReader(Integer id)
    {
        if (id == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Reader WHERE id_r = " + id;
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        )
        {
            if (res.next())
            {
                return new Reader(res);
            }
            return null;
        } catch (SQLException e)
        {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public List<Reader> getReader(String login)
    {
        if (login == null) throw new IllegalArgumentException();
        String sql = "SELECT * FROM mydb.Reader WHERE login = '" + login + "'";
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery(sql)
        )
        {
            List<Reader> list = new ArrayList<>();
            while (res.next())
            {
                list.add(new Reader(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    //obviously won`t work
    public boolean doCredentialsMatch(String login, String password) {
        var query = "SELECT * FROM mydb.Reader WHERE login = ? AND password = ?";
        if (login == null || password == null) throw new IllegalArgumentException();
        try {
            var st = connection.prepareStatement(query);
            st.setString(1, login);
            st.setString(2, password);
            var result = st.executeQuery();

            return result.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isAdmin(String login)
    {
        var query = "SELECT type_rights FROM mydb.Reader WHERE login = ? AND password = ?";
        if (login == null) throw new IllegalArgumentException();
        try
        {
            var st = connection.prepareStatement(query);
            st.setString(1, login);
            var result = st.executeQuery();

            return result.next();
        } catch (SQLException e)
        {
            return false;
        }
    }

    public ObservableList<Entity> getAllReaders()
    {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Reader")
        )
        {
            ObservableList<Entity> list = FXCollections.observableArrayList();
            while (res.next())
            {
                list.add(new Reader(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public List<Reader> getReaders() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * FROM mydb.Reader")
        ) {
            List<Reader> list = new ArrayList<>();
            while (res.next()) {
                list.add(new Reader(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    public boolean update(Reader reader) {
        if (reader.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.Reader SET PIB=?, password=?, login=?," +
                        " type_rights=?, city_r=?, street_r=?, build_r=?," +
                        " apartment_r=?, workplace=?, birth_date_r=?, phone_num_r=?" +
                        " WHERE id_r=?")) {
            //statement.setInt(1, 1);
            statement.setString(1, reader.getPib());
            statement.setString(2, reader.getPassword());
            statement.setString(3, reader.getLogin());
            statement.setInt(4, reader.getTypeRights());
            statement.setString(5, reader.getCity());
            statement.setString(6, reader.getStreet());
            statement.setString(7, reader.getBuild());
            statement.setString(8, reader.getApartment());
            statement.setString(9, reader.getWorkplace());
            statement.setDate(10, Date.valueOf(reader.getBirthDate()));
            statement.setString(11, reader.getPhoneNum());
            statement.setInt(12, reader.getId());

            statement.executeUpdate();
            return true;

        } catch (SQLException e)
        {
            System.out.println("Не вірний SQL запит на update");
            e.printStackTrace();
            return false;
        }

    }

    public boolean delete(Reader reader)
    {
        if (reader.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM mydb.Reader WHERE id_r=?"))
        {
            //statement.setInt(1, 1);
            statement.setInt(1, reader.getId());

            statement.executeUpdate();
            return true;
        } catch (SQLException e)
        {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }

    public boolean insert(Reader reader)
    {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO mydb.Reader(PIB, password, login, city_r, street_r, build_r, apartment_r, workplace, birth_date_r, phone_num_r) " +
                        "values (?,?,?,?,?,?,?,?,?,?)"))
        {
            //statement.setInt(1, 1);
            statement.setString(1, reader.getPib());
            statement.setString(2, reader.getPassword());
            statement.setString(3, reader.getLogin());
            statement.setString(4, reader.getCity());
            statement.setString(5, reader.getStreet());
            statement.setString(6, reader.getBuild());
            statement.setString(7, reader.getApartment());
            statement.setString(8, reader.getWorkplace());
            statement.setDate(9, Date.valueOf(reader.getBirthDate()));
            statement.setString(10, reader.getPhoneNum());

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Such user already exists!").showAndWait();
            return false;
        }
    }


    public double penya(String login) {
        if (login == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * from mydb.BookReader\n" +
                        "where date_return is null and id_r in(\n" +
                        "    select id_r from mydb.Reader\n" +
                        "    where login = ? \n" +
                        "    )")
        ) {
            statement.setString(1, login);
            ResultSet res = statement.executeQuery();
            List<BookReader> list = new ArrayList<>();
            while (res.next()) {
                list.add(new BookReader(res));
            }
            double accum = 0;
            for (BookReader br : list) {
                LocalDate now = LocalDate.now();
                LocalDate exp = br.getDateExp();
                accum += PENYA_PER_DAY * ChronoUnit.DAYS.between(exp, now);
            }
            return accum;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    //returns Readers who are reading some books now(at least 1) EXCLUDING debtors
    public ObservableList<Reader> getActiveReaders() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * from mydb.Reader\n" +
                     "where id_r in (\n" +
                     "    SELECT BookReader.id_r from mydb.BookReader\n" +
                     "    where date_return is null and DATEDIFF(CURDATE(), date_exp)<0)")
        ) {
            ObservableList<Reader> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Reader(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

    //returns Readers who are reading some books now(at least 1) INCLUDING debtors
    public ObservableList<Reader> getAnyActiveReaders() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * from mydb.Reader\n" +
                     "where id_r in (\n" +
                     "    SELECT BookReader.id_r from mydb.BookReader\n" +
                     "    where date_return is null)")
        ) {
            ObservableList<Reader> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Reader(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    //returns readers with book debts
    public ObservableList<Entity> getDebtors() {
        try (Statement st = connection.createStatement();
             ResultSet res = st.executeQuery("SELECT * from mydb.Reader \n" +
                     "where id_r in (\n" +
                     "    SELECT BookReader.id_r from mydb.BookReader \n" +
                     "    where date_return is null and DATEDIFF(CURDATE(), date_exp)>0 \n" +
                     ")")
        ) {
            ObservableList<Entity> list = FXCollections.observableArrayList();
            while (res.next()) {
                list.add(new Reader(res));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }


    public int readerNumber() {
        String sql = "SELECT Count(*) as num FROM mydb.Reader";
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

        document.addHeader("Readers", "Readers");
        document.add(new Paragraph("Readers"));
        document.add(new Phrase(" "));
        PdfPTable table = new PdfPTable(9);
        addHeaders(table, Stream.of("id_reader", "PIB", "city", "street", "build", "apartment", "workplace", "birth_date", "phone_num"));
        addReaders(table, getReaders());
        table.setWidthPercentage(100);
        document.add(table);

        document.close();
        return document;
    }

    public PdfPTable getTablePDF() {

        PdfPTable table = new PdfPTable(9);
        addHeaders(table, Stream.of("id_reader  ", "PIB", "city", "street", "build", "apartment", "workplace", "birth_date", "phone_num"));
        addReaders(table, getReaders());
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

    private void addReaders(PdfPTable table, List<Reader> readers) {
        for (Reader reader : readers) {
            table.addCell(new Phrase(reader.getId().toString()));
            table.addCell(new Phrase(reader.getPib()));
            table.addCell(new Phrase(reader.getCity()));
            table.addCell(new Phrase(reader.getStreet()));
            table.addCell(new Phrase(reader.getBuild()));
            table.addCell(new Phrase(reader.getApartment()));
            table.addCell(new Phrase(reader.getWorkplace()));
            table.addCell(new Phrase(reader.getBirthDate().toString()));
            table.addCell(new Phrase(reader.getPhoneNum()));
        }
    }

}

