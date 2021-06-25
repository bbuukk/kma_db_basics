package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Reader implements Entity{
    private Integer id;
    private String pib;
    private String password;
    private String login;
    private Integer typeRights;
    private String city;
    private String street;
    private String build;
    private String apartment;
    private String workplace;
    private LocalDate birthDate;
    private String phoneNum;

    public static final String TYPE_PARAMS_PATTERN  = "100010000020";

    public Reader(Integer id, String pib, String password, String login, Integer typeRights, String city, String street, String build, String apartment, String workplace, LocalDate birthDate, String phoneNum) {
        this.id = id;
        this.pib = pib;
        this.password = password;
        this.login = login;
        this.typeRights = typeRights;
        this.city = city;
        this.street = street;
        this.build = build;
        this.apartment = apartment;
        this.workplace = workplace;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
    }

    public Reader(String pib, String password, String login, Integer typeRights, String city, String street, String build, String apartment, String workplace, LocalDate birthDate, String phoneNum) {
        this.pib = pib;
        this.password = password;
        this.login = login;
        this.typeRights = typeRights;
        this.city = city;
        this.street = street;
        this.build = build;
        this.apartment = apartment;
        this.workplace = workplace;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
    }

    public Reader(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id_r");
        this.pib = resultSet.getString("PIB");
        this.password = resultSet.getString("password");
        this.login = resultSet.getString("login");
        this.typeRights = resultSet.getInt("type_rights");
        this.city = resultSet.getString("city_r");
        this.street = resultSet.getString("street_r");
        this.build = resultSet.getString("build_r");
        this.apartment = resultSet.getString("apartment_r");
        this.workplace = resultSet.getString("workplace");
        this.birthDate = resultSet.getDate("birth_date_r").toLocalDate();
        this.phoneNum = resultSet.getString("phone_num_r");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getTypeRights() {
        return typeRights;
    }

    public void setTypeRights(Integer typeRights) {
        this.typeRights = typeRights;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "id=" + id +
                ", pib='" + pib + '\'' +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", typeRights=" + typeRights +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", build='" + build + '\'' +
                ", apartment='" + apartment + '\'' +
                ", workplace='" + workplace + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }


}
