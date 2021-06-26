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
import javafx.event.EventHandler;
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
    //TODO IF ID OF CREATED ENTITY == NUll, JUST INSERT SUCH ENTITY
    ObservableList<Entity> listEntitiesToChange;
    ObservableList<Entity>listEntitiesToDelete;

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
        listEntitiesToChange = FXCollections.observableArrayList();
        listEntitiesToDelete = FXCollections.observableArrayList();
    }

    @FXML
    public void selectTabel(javafx.event.ActionEvent event) throws SQLException
    {
        String nameOfSelectedTable = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();
        displayReaderTable(nameOfSelectedTable);
    }

    private void createNewTableColumns(String[] columnsToCreate, String patterInt, int amoutColumnsEditBlock)
    {
        mainTableView.getColumns().clear();
        listEntitiesToChange.clear();
        TableColumn column = null;
        char dataTypeCurrent;
        String COLUMN_NAME;

        for (int i = 0; i < columnsToCreate.length; i++)
        {
            COLUMN_NAME = columnsToCreate[i];
            dataTypeCurrent = patterInt.charAt(i);
            switch (dataTypeCurrent)
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
            column.setText(COLUMN_NAME);
            column.setCellValueFactory(new PropertyValueFactory<>(COLUMN_NAME));

            if (amoutColumnsEditBlock != 0){
                column.setEditable(false);
                --amoutColumnsEditBlock;
            }
            String fCOLUMN_NAME = COLUMN_NAME;
            column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>()
            {
                @Override
                public void handle(TableColumn.CellEditEvent event)
                {
                    String nameOfCurrentSelectedEntity = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();

                    switch (nameOfCurrentSelectedEntity)
                    {
                        case "Reader":
                            Reader readerToChange = (Reader) event.getRowValue();
//
                            listEntitiesToChange.add(readerToChange.change(fCOLUMN_NAME, event.getNewValue()));
                            break;
                        case "Authorship":
                            Authorship authorship = (Authorship) event.getRowValue();
//
                            listEntitiesToDelete.add(authorship);
                            listEntitiesToChange.add(authorship.change(fCOLUMN_NAME, event.getNewValue()));
                            break;
                        case "Belongs":
                            Belongs belongs = (Belongs) event.getRowValue();
//
                            listEntitiesToDelete.add(belongs);
                            listEntitiesToChange.add(belongs.change(fCOLUMN_NAME, event.getNewValue()));
                            break;
                        case "Book":
                            Book book = (Book) event.getRowValue();
//                            App.sqlOps.getBookRepository().update(book);
                            listEntitiesToChange.add(book.change(fCOLUMN_NAME, event.getNewValue()));
                            break;
                        case "BookInstance":
                            BookInstance bookInstance = (BookInstance) event.getRowValue();
//                            App.sqlOps.getBookInstanceRepository().update(bookInstance);
                            listEntitiesToChange.add(bookInstance.change(fCOLUMN_NAME, event.getNewValue()));
                            break;
                        case "BookReader":
                            BookReader bookReader = (BookReader) event.getRowValue();
//                            App.sqlOps.getBookReaderRepository().update(bookReader);
                            listEntitiesToDelete.add(bookReader);
                            listEntitiesToChange.add(bookReader.change(fCOLUMN_NAME, event.getNewValue()));
                            break;
                        case "Catalog":
                            Catalog catalog = (Catalog) event.getRowValue();
//                            App.sqlOps.getBookReaderRepository().update(catalog);
                            listEntitiesToChange.add(catalog.change(fCOLUMN_NAME, event.getNewValue()));
                            break;
                        case "Author":
                            Author author = (Author) event.getRowValue();
//                            App.sqlOps.getBookReaderRepository().update(catalog);
                            listEntitiesToChange.add(author.change(fCOLUMN_NAME, event.getNewValue()));
                            break;

                        default:

                    }
                }
            });

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
                createNewTableColumns(cellNamesReader, Reader.TYPE_PARAMS_PATTERN, 1);

                var listOfReaders = App.sqlOps.getReaderRepository().getAllReaders();
                mainTableView.setItems(listOfReaders);

                break;
            case "Authorship":
                String[] cellNamesAuthorship = {"id", "ISBN"};
                createNewTableColumns(cellNamesAuthorship, Authorship.TYPE_PARAMS_PATTERN,0);

                var listAuthorships = App.sqlOps.getAuthorshipRepository().getAllAuthorships();
                mainTableView.setItems(listAuthorships);

                break;
            case "Belongs":
                String[] cellNamesBelongs = {"isbn", "idCatalog"};
                createNewTableColumns(cellNamesBelongs, Belongs.TYPE_PARAMS_PATTERN,0);

                var listBelongs = App.sqlOps.getBelongsRepository().getAllBelongs();
                mainTableView.setItems(listBelongs);

                break;
            case "Book":
                String[] cellNamesBook = {"ISBN", "name", "publisher", "pubCity", "pubYear", "pageNum", "price"};
                createNewTableColumns(cellNamesBook, Book.TYPE_PARAMS_PATTERN,1);

                var listBooks = App.sqlOps.getBookRepository().getAllBooks();
                mainTableView.setItems(listBooks);

                break;
            case "BookInstance":
                String[] cellNamesBookInstance = {"id", "shelf", "ISBN"};
                createNewTableColumns(cellNamesBookInstance, BookInstance.TYPE_PARAMS_PATTERN, 1);

                var listBooksInstances = App.sqlOps.getBookInstanceRepository().getAllBookInstances();
                mainTableView.setItems(listBooksInstances);

                break;
            case "BookReader":
                String[] cellNamesBookReader = {"idReader", "idInstance", "dateOut", "dateExp", "dateReturn"};
                createNewTableColumns(cellNamesBookReader, BookReader.TYPE_PARAMS_PATTERN,2);

                var listBookReaders = App.sqlOps.getBookReaderRepository().getAllBookReaders();
                mainTableView.setItems(listBookReaders);

                break;
            case "Catalog":
                String[] cellNamesCatalog = {"id", "name"};
                createNewTableColumns(cellNamesCatalog, Catalog.TYPE_PARAMS_PATTERN,1);

                var listCatalogs = App.sqlOps.getCatalogRepository().getAllCatalogs();
                mainTableView.setItems(listCatalogs);

                break;
            case "Author":
                String[] cellNamesAuthor = {"id", "name"};
                createNewTableColumns(cellNamesAuthor, Author.TYPE_PARAMS_PATTERN,1);

                var listAuthors = App.sqlOps.getAuthorRepository().getAllAuthors();
                mainTableView.setItems(listAuthors);

                break;
        }
    }

    @FXML
    public void changeCurrentTable(javafx.scene.input.MouseEvent event)
    {
        String nameOfCurrentSelectedEntity = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();

        switch (nameOfCurrentSelectedEntity)
        {
            case "Reader":
                for (Entity entity : listEntitiesToChange)
                {
                    Reader reader = (Reader) entity;
                    App.sqlOps.getReaderRepository().update(reader);
                }
                break;

            case "Authorship":
                for (Entity entity :listEntitiesToDelete)
                {
                    App.sqlOps.getAuthorshipRepository().delete((Authorship) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getAuthorshipRepository().insert((Authorship) entity);
                }
                break;

            case "Belongs":
                for (Entity entity :listEntitiesToDelete)
                {
                    App.sqlOps.getBelongsRepository().delete((Belongs) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getBelongsRepository().insert((Belongs) entity);
                }
                break;

            case "Book":
                for (Entity entity : listEntitiesToChange)
                {
                    Book book = (Book) entity;
                    App.sqlOps.getBookRepository().update(book);
                }
                break;

            case "BookInstance":
                for (Entity entity : listEntitiesToChange)
                {
                    BookInstance bookInstance = (BookInstance) entity;
                    App.sqlOps.getBookInstanceRepository().update(bookInstance);
                }
                break;

            case "BookReader":
                for (Entity entity : listEntitiesToChange)
                {
                    BookReader bookReader = (BookReader) entity;
                    App.sqlOps.getBookReaderRepository().update(bookReader);
                }
                break;

            case "Catalog":
                for (Entity entity : listEntitiesToChange)
                {
                    Catalog catalog = (Catalog) entity;
                    App.sqlOps.getCatalogRepository().update(catalog);
                }
                break;

            case "Author":
                for (Entity entity : listEntitiesToChange)
                {
                    Author author = (Author) entity;
                    App.sqlOps.getAuthorRepository().update(author);
                }
                break;

            default:

        }
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
}

