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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
    private Button changeButton;

    @FXML
    private Button updateButton;

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
    private TableView<Entity> mainTableView;

    @FXML
    //id
    private TableColumn<Entity, String> cOne;

    @FXML
    //id
    private TableColumn<Entity, Integer> cOneInt;

    @FXML
    //pib
    private TableColumn<Entity, String > cTwo;

    @FXML
    //pib
    private TableColumn<Entity, Integer> cTwoInt;

    @FXML
    //password
    private TableColumn<Entity, String> cThree;

    @FXML
    //password
    private TableColumn<Entity, Integer> cThreeInt;

    @FXML
    //login
    private TableColumn<Entity, String> cFour;

    @FXML
    //password
    private TableColumn<Entity, Integer> cFourInt;

    @FXML
    //typeRights
    private TableColumn<Entity, String> cFive;

    @FXML
    //city
    private TableColumn<Entity, String> cSix;

    @FXML
    //street
    private TableColumn<Entity, String> cSeven;

    @FXML
    //build
    private TableColumn<Entity, String> cEight;

    @FXML
    //apartament
    private TableColumn<Entity, String> cNine;

    @FXML
    //birth Date
    private TableColumn<Entity, String> cTen;

    @FXML
    //phoneNum
    private TableColumn<Entity, String> cEleven;


    @FXML
    private TableColumn<Entity, String> cTwelve;

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
//                editableColumns();

    }

    private void editableColumns()
    {

        cTwo.setCellFactory(TextFieldTableCell.forTableColumn());
        cThree.setCellFactory(TextFieldTableCell.forTableColumn());
        cFour.setCellFactory(TextFieldTableCell.forTableColumn());
        cFive.setCellFactory(TextFieldTableCell.forTableColumn());
        cSix.setCellFactory(TextFieldTableCell.forTableColumn());
        cSeven.setCellFactory(TextFieldTableCell.forTableColumn());
        cEight.setCellFactory(TextFieldTableCell.forTableColumn());;
        cNine.setCellFactory(TextFieldTableCell.forTableColumn());
        cTen.setCellFactory(TextFieldTableCell.forTableColumn());
        cEleven.setCellFactory(TextFieldTableCell.forTableColumn());
        cTwelve.setCellFactory(TextFieldTableCell.forTableColumn());
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

        if(!isDigit(cc1))
        {
            cOne.setVisible(true);
            cOneInt.setVisible(false);
            cOne.setCellValueFactory(new PropertyValueFactory<>(cc1));
            cOne.setText(cc1);
        }
        else
        {
            cOne.setVisible(false);
            cOneInt.setVisible(true);
            cOneInt.setCellValueFactory(new PropertyValueFactory<>(cc1));
            cOneInt.setText(cc1);
        }
        if(!isDigit(cc2))
        {
            cTwo.setVisible(true);
            cTwoInt.setVisible(false);
            cTwo.setText(cc2);
            cTwo.setCellValueFactory(new PropertyValueFactory<>(cc2));
        }
        else
        {
            cTwo.setVisible(false);
            cTwoInt.setVisible(true);
            cTwoInt.setText(cc2);
            cTwoInt.setCellValueFactory(new PropertyValueFactory<>(cc2));
        }
        if(!isDigit(cc3))
        {
            cThree.setVisible(true);
            cThreeInt.setVisible(false);
            cThree.setText(cc3);
            cThree.setCellValueFactory(new PropertyValueFactory<>(cc3));
        }
        else
        {
            cThree.setVisible(false);
            cThreeInt.setVisible(true);
            cThreeInt.setText(cc3);
            cThreeInt.setCellValueFactory(new PropertyValueFactory<>(cc3));
        }

        if(!isDigit(cc4))
        {
            cFour.setVisible(true);
            cFourInt.setVisible(false);
            cFour.setText(cc4);
            cFour.setCellValueFactory(new PropertyValueFactory<>(cc4));
        }
        else{
            cFour.setVisible(false);
            cFourInt.setVisible(true);
            cFourInt.setText(cc4);
            cFourInt.setCellValueFactory(new PropertyValueFactory<>(cc4));
        }

        cFive.setCellValueFactory(new PropertyValueFactory<>(cc5));
        cSix.setCellValueFactory(new PropertyValueFactory<>(cc6));
        cSeven.setCellValueFactory(new PropertyValueFactory<>(cc7));
        cEight.setCellValueFactory(new PropertyValueFactory<>(cc8));
        cNine.setCellValueFactory(new PropertyValueFactory<>(cc9));
        cTen.setCellValueFactory(new PropertyValueFactory<>(cc10));
        cEleven.setCellValueFactory(new PropertyValueFactory<>(cc11));
        cTwelve.setCellValueFactory(new PropertyValueFactory<>(cc12));

        cFive.setText(cc5);cSix.setText(cc6);
        cSeven.setText(cc7);cEight.setText(cc8);cNine.setText(cc9);
        cTen.setText(cc10);cEleven.setText(cc11);cTwelve.setText(cc12);

    }


    private void displayReaderTable(String name) throws SQLException
    {
        switch (name){
            case "Reader":
//                cFive.setVisible(true);
                setCellValuesSettings("id","pib", "password","login","typeRights", "city","street",
                                        "build", "apartment","workplace", "birthDate", "phoneNum");
                var listOfReaders = App.sqlOps.getReaderRepository().getAllReaders();
                mainTableView.setItems(listOfReaders);
                break;
            case "Authorship":
//                cFive.setVisible(false);
                setCellValuesSettings("id","ISBN", "","","", "","",
                        "", "","", "", "");
                var listAuthorships = App.sqlOps.getAuthorshipRepository().getAllAuthorships();
                mainTableView.setItems(listAuthorships);
                break;
            case "Belongs":
//                cFive.setVisible(false);
                setCellValuesSettings("isbn","idCatalog", "","","", "","",
                        "", "","", "", "");
                var listBelongs = App.sqlOps.getBelongsRepository().getAllBelongs();
                mainTableView.setItems(listBelongs);
                break;
            case "Book":
//                cFive.setVisible(false);
                setCellValuesSettings("ISBN","name", "publisher","pubCity","pubYear", "pageNum","price",
                        "", "","", "", "");
                var listBooks = App.sqlOps.getBookRepository().getAllBooks();
                mainTableView.setItems(listBooks);
                break;
            case "BookInstance":
//                cFive.setVisible(false);
                setCellValuesSettings("id","shelf", "ISBN","","", "","",
                        "", "","", "", "");
                var listBooksInstances = App.sqlOps.getBookInstanceRepository().getAllBookInstances();
                mainTableView.setItems(listBooksInstances);
                break;
            case "BookReader":
//                cFive.setVisible(false);
                setCellValuesSettings("idReader","idInstance", "dateOut","dateExp","dateReturn", "","",
                        "", "","", "", "");
                var listBookReaders = App.sqlOps.getBookReaderRepository().getAllBookReaders();
                mainTableView.setItems(listBookReaders);
                break;
            case "Catalog":
//                cFive.setVisible(false);
                setCellValuesSettings("id","name", "","","", "","",
                        "", "","", "", "");
                var listCatalogs = App.sqlOps.getCatalogRepository().getAllCatalogs();
                mainTableView.setItems(listCatalogs);
                break;
            case "Author":
//                cFive.setVisible(false);
                setCellValuesSettings("id","name", "","","", "","",
                        "", "","", "", "");
                var listAuthors = App.sqlOps.getAuthorRepository().getAllAuthors();
                mainTableView.setItems(listAuthors);

                break;
        }
    }

    @FXML
    public void changeCurrentTable(javafx.scene.input.MouseEvent event)
    {

    }


    @FXML
    void updateTable(javafx.scene.input.MouseEvent event) throws SQLException
    {
        String nameOfSelectedTable = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();
        displayReaderTable(nameOfSelectedTable);
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

    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

