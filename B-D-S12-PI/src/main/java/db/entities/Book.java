package db.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Book implements Entity{
    private Integer ISBN;
    private String name;
    private String publisher;
    private String pubCity;
    private LocalDate pubYear;
    private Integer pageNum;
    private String price;

    public static final String TYPE_PARAMS_PATTERN  = "1000210";

    public Book(Integer ISBN, String name, String publisher, String pubCity, LocalDate pubYear, Integer pageNum, String price) {
        this.ISBN = ISBN;
        this.name = name;
        this.publisher = publisher;
        this.pubCity = pubCity;
        this.pubYear = pubYear;
        this.pageNum = pageNum;
        this.price = price;
    }

    public Book(String name, String publisher, String pubCity, LocalDate pubYear, Integer pageNum, String price) {
        this.name = name;
        this.publisher = publisher;
        this.pubCity = pubCity;
        this.pubYear = pubYear;
        this.pageNum = pageNum;
        this.price = price;
    }

    public Book(ResultSet resultSet) throws SQLException {
        this.ISBN = resultSet.getInt("ISBN");
        this.name = resultSet.getString("name_b");
        this.publisher = resultSet.getString("publisher");
        this.pubCity = resultSet.getString("pub_city");
        this.pubYear = resultSet.getDate("pub_year").toLocalDate();
        this.pageNum = resultSet.getInt("page_num");
        this.price = resultSet.getString("price");
    }

    public <T> Book change(String innerVarName, T value)
    {
        switch (innerVarName)
        {
            //TODO WHO CAN CHANGE LOGIN AND PASSWORD
            case "ISBN":
                //TODO IF ID ALREADY EXISTS
                setISBN((Integer) value);
                return this;
            case "name":
                setName((String) value);
                return this;
            case "publisher":
                setPublisher((String) value);
                return this;
            case "pubCity":
                setPubCity((String) value);
                return this;
            case "pubYear":
                setPubYear((LocalDate) value);
                return this;
            case "pageNum":
                setPageNum((Integer) value);
                return this;
            case "price":
                setPrice((String) value);
                return this;

            default:
                System.out.println("ReaderDefault");
                return this;
        }
    }

    public Book() {
    }

    public Integer getISBN() {
        return ISBN;
    }

    public void setISBN(Integer ISBN) {
        this.ISBN = ISBN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubCity() {
        return pubCity;
    }

    public void setPubCity(String pubCity) {
        this.pubCity = pubCity;
    }

    public LocalDate getPubYear() {
        return pubYear;
    }

    public void setPubYear(LocalDate pubYear) {
        this.pubYear = pubYear;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", name='" + name + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pubCity='" + pubCity + '\'' +
                ", pubYear=" + pubYear +
                ", pageNum=" + pageNum +
                ", price='" + price + '\'' +
                '}';
    }
}
