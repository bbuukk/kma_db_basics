package vdb.dev.Controllers.addMenu;

import java.net.URL;
import java.util.ResourceBundle;

import db.SqlOps;
import db.entities.Catalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AddNewBookController   {

    public static final String PATH = "Fxmls/Main/AddBook";
    ObservableList<String> langs;
    Catalog catalog;

    SqlOps sqlOps;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane authorsLabel;

    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextField publishersCityField;

    @FXML
    private TextField amountOfPagesField;

    @FXML
    private TextField priceField;

    @FXML
    private DatePicker dateOfPublishingPicker;

    @FXML
    private ComboBox<String> chooseCatalogComboBox;

    @FXML
    private Button addToCatalogButton;

    @FXML
    private ComboBox<?> chooseAuthorComboBox;

    @FXML
    private Button addAuthorButton;

    @FXML
    private Label belongsToLable;

    @FXML
    private Button createNewBookButton;

    @FXML
    void initialize() {

        langs = FXCollections.observableArrayList();
        catalog = new Catalog();
        sqlOps = new SqlOps();
        for (int i = 0; i<6; i++){
            langs.add(sqlOps.getCatalogRepository().getCatalog(i+1).getName().toString());
        }
        chooseCatalogComboBox.setItems(langs);
    }

    //todo method for creating a new book

    public void createNewBook(){

    }

    public void selectCatalog(ActionEvent actionEvent) {

        String a = chooseCatalogComboBox.getSelectionModel().getSelectedItem().toString();

    }

    public void selectAuthor(ActionEvent actionEvent) {

    }
}
