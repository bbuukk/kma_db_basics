package db.repos;

import db.entities.Entity;
import db.entities.Reader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderRepository
{
    Connection connection;

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
        } catch (SQLException e)
        {
            System.out.println("Не вірний SQL запит на вибірку даних");
            e.printStackTrace();
            throw new RuntimeException("Can`t select anything", e);
        }
    }

//   public boolean updateTable(ObservableList<Entity> entity)
//    {
//        if (reader.getId() == null) throw new IllegalArgumentException();
//        try (PreparedStatement statement = connection.prepareStatement(
//                "UPDATE mydb.Reader SET PIB=?, password=?, login=?," +
//                        " type_rights=?, city_r=?, street_r=?, build_r=?," +
//                        " apartment_r=?, workplace=?, birth_date_r=?, phone_num_r=?" +
//                        " WHERE id_r=?"))
//        {
//            //statement.setInt(1, 1);
//            statement.setString(1, reader.getPib());
//            statement.setString(2, reader.getPassword());
//            statement.setString(3, reader.getLogin());
//            statement.setInt(4, reader.getTypeRights());
//            statement.setString(5, reader.getCity());
//            statement.setString(6, reader.getStreet());
//            statement.setString(7, reader.getBuild());
//            statement.setString(8, reader.getApartment());
//            statement.setString(9, reader.getWorkplace());
//            statement.setDate(10, Date.valueOf(reader.getBirthDate()));
//            statement.setString(11, reader.getPhoneNum());
//            statement.setInt(12, reader.getId());
//
//            statement.executeUpdate();
//            return true;
//
//        } catch (SQLException e)
//        {
//            System.out.println("Не вірний SQL запит на update");
//            e.printStackTrace();
//            return false;
//        }
//
//    }


    public boolean update(Reader reader)
    {
        if (reader.getId() == null) throw new IllegalArgumentException();
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE mydb.Reader SET PIB=?, password=?, login=?," +
                        " type_rights=?, city_r=?, street_r=?, build_r=?," +
                        " apartment_r=?, workplace=?, birth_date_r=?, phone_num_r=?" +
                        " WHERE id_r=?"))
        {
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
        } catch (SQLException e)
        {
            System.out.println("Не вірний SQL запит на delete");
            e.printStackTrace();
            return false;
        }
    }


}
