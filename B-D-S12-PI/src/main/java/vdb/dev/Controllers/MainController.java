package vdb.dev.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import db.entities.Authorship;
import db.entities.Entity;
import db.entities.Reader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import vdb.dev.App;
import vdb.dev.Controllers.addMenu.AddMenuController;

public class MainController
{
    ObservableList<String> tableNames;
    private static db.entities.Reader currentAuthorizedReader;

    Entity entity;

    public static final String PATH = "Fxmls/Main/Main";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addMenuButton;

    @FXML
    private Button showMenuButton;

    @FXML
    private Button ChangeMenuButton;

    @FXML
    private Button DefaultMenuButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button logOutButton;

    @FXML
    private ComboBox<String> chooseTableComboBox;

    @FXML
    private TableView<?> mainTableView;

    @FXML
    //id
    private TableColumn<Reader, String> cOne;

    @FXML
    //pib
    private TableColumn<Reader, String > cTwo;

    @FXML
    //password
    private TableColumn<Reader, String> cThree;

    @FXML
    //login
    private TableColumn<Reader, String> cFour;

    @FXML
    //typeRights
    private TableColumn<Reader, String> cFive;

    @FXML
    //city
    private TableColumn<Reader, String> cSix;

    @FXML
    //street
    private TableColumn<Reader, String> cSeven;

    @FXML
    //build
    private TableColumn<Reader, String> cEight;

    @FXML
    //apartament
    private TableColumn<Reader, String> cNine;

    @FXML
    //birth Date
    private TableColumn<Reader, String> cTen;

    @FXML
    //phoneNum
    private TableColumn<Reader, String> cEleven;


    @FXML
    private TableColumn<?, ?> cTwelve;

//    c1 = new TableColumn<Reader, Integer>();c2 = new TableColumn<Reader, String>();c3 = new TableColumn<Reader, String>();
//    с4 = new TableColumn<Reader, Integer>();с5 = new TableColumn<Reader, String>();с6 = new TableColumn<Reader, String>();
//    с7 = new TableColumn<Reader, String>();с8 = new TableColumn<Reader, String>();с9 = new TableColumn<Reader, String>();
//    с10 = new TableColumn<Reader, String>();с11 = new TableColumn<Reader, Date>(); c2 =


    @FXML
    void initialize()
    {
        tableNames = FXCollections.observableArrayList("Author", "Authorship","Belongs", "Book",
                                                   "BookInstance", "BookReader","Catalog", "Reader");
        chooseTableComboBox.setItems(tableNames);

    }

    @FXML
    public void selectTabel(javafx.event.ActionEvent event) throws SQLException
    {
        String nameOfSelectedTable = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();
        displayReaderTable(nameOfSelectedTable);

    }

    private void setCellValuesSettings(String cc1, String cc2,String cc3,String cc4,String cc5,
                                       String cc6,String cc7,String cc8,String cc9,String cc10,
                                       String cc11, String cc12){

        cOne.setCellValueFactory(new PropertyValueFactory<>(cc1));
        cTwo.setCellValueFactory(new PropertyValueFactory<>(cc2));
        cThree.setCellValueFactory(new PropertyValueFactory<>(cc3));
        cFour.setCellValueFactory(new PropertyValueFactory<>(cc4));
        cFive.setCellValueFactory(new PropertyValueFactory<>(cc5));
        cSix.setCellValueFactory(new PropertyValueFactory<>(cc6));
        cSeven.setCellValueFactory(new PropertyValueFactory<>(cc7));
        cEight.setCellValueFactory(new PropertyValueFactory<>(cc8));
        cNine.setCellValueFactory(new PropertyValueFactory<>(cc9));
        cTen.setCellValueFactory(new PropertyValueFactory<>(cc10));
        cEleven.setCellValueFactory(new PropertyValueFactory<>(cc11));
        cTwelve.setCellValueFactory(new PropertyValueFactory<>(cc12));

        cOne.setText(cc1);cTwo.setText(cc2);cThree.setText(cc3);
        cFour.setText(cc4);cFive.setText(cc5);cSix.setText(cc6);
        cSeven.setText(cc7);cEight.setText(cc8);cNine.setText(cc9);
        cTen.setText(cc10);cEleven.setText(cc11);cTwelve.setText(cc12);
    }


    private void displayReaderTable(String name) throws SQLException
    {
        switch (name){
            case "Reader":
                setCellValuesSettings("id","pib", "password","login","typeRights", "city","street",
                                        "build", "apartment","workplace", "birthDate", "phoneNum");
                var listOfReaders = App.sqlOps.getReaderRepository().getAllReaders();
                TableView<Reader> readerTableView = new TableView<>();
//                readerTableView.getColumns().addAll(cOne, cTwo, cThree,cFour,cFive,cSix,cSeven,cEight,cNine,cTen,cEleven,cTwelve);
                readerTableView.setItems(listOfReaders);
                break;
            case "Authorship":
                setCellValuesSettings("id","ISBN", "","","", "","",
                        "", "","", "", "");
                var listOfAuthorships = App.sqlOps.getAuthorshipRepository().getAllAuthorships();
//                mainTableView.setItems(listOfAuthorships);
                break;
            case "Belongs":
                setCellValuesSettings("isbn","idCatalog", "","","", "","",
                        "", "","", "", "");
                break;
            case "Book":
                setCellValuesSettings("ISBN","name", "publisher","pubCity","pubYear", "pageNum","price",
                        "", "","", "", "");
                break;
            case "BookInstance":
                setCellValuesSettings("id","shelf", "isb","","", "","",
                        "", "","", "", "");
                break;
            case "BookReader":
                setCellValuesSettings("idReader","idInstance", "dateOut","dateExp","dateReturn", "","",
                        "", "","", "", "");
                break;
            case "Catalog":
                setCellValuesSettings("id","name", "","","", "","",
                        "", "","", "", "");
                break;
            case "Author":
                setCellValuesSettings("id","name", "","","", "","",
                        "", "","", "", "");

                break;
        }


//        ObservableList<Reader> listOfReaders = FXCollections.observableArrayList();

//        c1 = new TableColumn<Reader, Integer>();c2 = new TableColumn<Reader, String>();c3 = new TableColumn<Reader, String>();
//        с4 = new TableColumn<Reader, Integer>();с5 = new TableColumn<Reader, String>();с6 = new TableColumn<Reader, String>();
//        с7 = new TableColumn<Reader, String>();с8 = new TableColumn<Reader, String>();с9 = new TableColumn<Reader, String>();
//        с10 = new TableColumn<Reader, String>();с11 = new TableColumn<Reader, Date>();

        LocalDate localDateBirth;

//        for (Reader r: readers)
//        {
//            var id = r.getId(); var pib = r.getPib();
//            var password = r.getPassword(); var login = r.getLogin();
//            var typeRights = r.getTypeRights(); var city = r.getCity();
//            var street = r.getStreet(); var build = r.getBuild();
//            var apartament   = r.getApartment(); var workplace = r.getWorkplace();
//            Date dateOfBirth = Date.valueOf(r.getBirthDate()); var phone = r.getPhoneNum();
//
//            localDateBirth = dateOfBirth.toLocalDate();
//            listOfReaders.add(new Reader(id,pib,password,login,typeRights,city,street,
//                    build,apartament,workplace,localDateBirth,phone));
//        }
    }

    @FXML
    public void addMenu(javafx.scene.input.MouseEvent event) throws IOException
    {
        AddMenuController addMenuController = new AddMenuController();
        addMenuController.openAddMenu();
    }

    @FXML
    public void showMenu(javafx.scene.input.MouseEvent event) throws IOException
    {
    }

    @FXML
    public void changeMenu(javafx.scene.input.MouseEvent event) throws IOException
    {
    }

    @FXML
    public void defaultQueriesMenu(javafx.scene.input.MouseEvent event) throws IOException
    {

    }

    @FXML
    public void logOut(javafx.scene.input.MouseEvent event) throws IOException
    {
        MainController.currentAuthorizedReader = null;
        App.setRoot(LogInController.PATH);
    }

    @FXML
    public void exit(javafx.scene.input.MouseEvent event){
        Platform.exit();
    }

    public static Reader getCurrentAuthorizedReader()
    {
        return currentAuthorizedReader;
    }

    public static void setCurrentAuthorizedReader(Reader currentAuthorizedReader)
    {
        MainController.currentAuthorizedReader = currentAuthorizedReader;
    }
}

