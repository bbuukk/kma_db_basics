package vdb.dev.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.javafx.scene.control.IntegerField;
import db.entities.*;
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
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
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
    void initialize()
    {
        tableNames = FXCollections.observableArrayList("Author", "Authorship", "Belongs", "Book",
                "BookInstance", "BookReader", "Catalog", "Reader");
        chooseTableComboBox.setItems(tableNames);

    }

    @FXML
    public void selectTabel(javafx.event.ActionEvent event) throws SQLException
    {
        String nameOfSelectedTable = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();
        displayReaderTable(nameOfSelectedTable);
    }

    private void createNewTableColumns(String[] columnsToCreate, String patterInt)
    {
        mainTableView.getColumns().clear();
        TableColumn column = null;
        char dataType;

        for (int i = 0; i < columnsToCreate.length; i++)
        {
            dataType = patterInt.charAt(i);
            switch (dataType)
            {
                case '0':
                    column = new TableColumn<Entity, String>();
                    column.setCellFactory(TextFieldTableCell.forTableColumn());
                    break;
                case '1':
                    column = new TableColumn<Entity, Integer>();
                    column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
                    break;
                case '2':
                    column = new TableColumn<Entity, Date>();
                    column.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
                    break;
            }
            column.setText(columnsToCreate[i]);
            column.setCellValueFactory(new PropertyValueFactory<>(columnsToCreate[i]));
            mainTableView.getColumns().add(column);
        }
    }

    private void displayReaderTable(String name) throws SQLException
    {
        switch (name)
        {
            case "Reader":
                String[] cellNamesReader = {"id", "pib", "password", "login", "typeRights", "city", "street",
                        "build", "apartment", "workplace", "birthDate", "phoneNum"};
                createNewTableColumns(cellNamesReader, Reader.TYPE_PARAMS_PATTERN);

                var listOfReaders = App.sqlOps.getReaderRepository().getAllReaders();
                mainTableView.setItems(listOfReaders);

                break;
            case "Authorship":
                String[] cellNamesAuthorship = {"id", "ISBN"};
                createNewTableColumns(cellNamesAuthorship, Authorship.TYPE_PARAMS_PATTERN);

                var listAuthorships = App.sqlOps.getAuthorshipRepository().getAllAuthorships();
                mainTableView.setItems(listAuthorships);

                break;
            case "Belongs":
                String[] cellNamesBelongs = {"isbn", "idCatalog"};
                createNewTableColumns(cellNamesBelongs, Belongs.TYPE_PARAMS_PATTERN);

                var listBelongs = App.sqlOps.getBelongsRepository().getAllBelongs();
                mainTableView.setItems(listBelongs);

                break;
            case "Book":
                String[] cellNamesBook = {"ISBN","name", "publisher", "pubCity", "pubYear", "pageNum", "price"};
                createNewTableColumns(cellNamesBook, Book.TYPE_PARAMS_PATTERN);

                var listBooks = App.sqlOps.getBookRepository().getAllBooks();
                mainTableView.setItems(listBooks);

                break;
            case "BookInstance":
                String[] cellNamesBookInstance = {"id", "shelf", "ISBN"};
                createNewTableColumns(cellNamesBookInstance, BookInstance.TYPE_PARAMS_PATTERN);

                var listBooksInstances = App.sqlOps.getBookInstanceRepository().getAllBookInstances();
                mainTableView.setItems(listBooksInstances);

                break;
            case "BookReader":
                String[] cellNamesBookReader = {"idReader", "idInstance", "dateOut", "dateExp", "dateReturn"};
                createNewTableColumns(cellNamesBookReader, BookReader.TYPE_PARAMS_PATTERN);

                var listBookReaders = App.sqlOps.getBookReaderRepository().getAllBookReaders();
                mainTableView.setItems(listBookReaders);

                break;
            case "Catalog":
                String[] cellNamesCatalog = {"id", "name"};
                createNewTableColumns(cellNamesCatalog, Catalog.TYPE_PARAMS_PATTERN);

                var listCatalogs = App.sqlOps.getCatalogRepository().getAllCatalogs();
                mainTableView.setItems(listCatalogs);

                break;
            case "Author":
                String[] cellNamesAuthor = {"id", "name"};
                createNewTableColumns(cellNamesAuthor, Author.TYPE_PARAMS_PATTERN);

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
    public void exit(javafx.scene.input.MouseEvent event)
    {
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

    private static boolean isDigit(String s) throws NumberFormatException
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e)
        {
            return false;
        }
    }
}

