package vdb.dev.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import db.entities.Reader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import vdb.dev.App;
import vdb.dev.Controllers.addMenu.AddMenuController;

public class MainController
{

    private static db.entities.Reader currentAuthorizedReader;

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
    void initialize()
    {

        ObservableList<String> tableNames = FXCollections.observableArrayList("Author", "Authorship","Belongs", "Book","BookInstance",
                "BookReader","Catalog", "Reader");
        chooseTableComboBox.setItems(tableNames);


        //todo get names of all tables

    }

    public void logOut(MouseEvent event) throws IOException
    {
        App.setRoot(LogInController.PATH);
    }

    public void addMenu(MouseEvent event) throws IOException
    {
        AddMenuController addMenuController = new AddMenuController();
        addMenuController.openAddMenu();
    }

    public void exit(MouseEvent event){
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

    @FXML
    private TableColumn<?, ?> col_1;

    @FXML
    private TableColumn<?, ?> col_2;

    @FXML
    private TableColumn<?, ?> col_3;

    @FXML
    private TableColumn<?, ?> col_4;

    @FXML
    private TableColumn<?, ?> col_5;

    @FXML
    private TableColumn<?, ?> col_6;

    @FXML
    public void chooseTable()
    {
        String s = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();

    }

}

