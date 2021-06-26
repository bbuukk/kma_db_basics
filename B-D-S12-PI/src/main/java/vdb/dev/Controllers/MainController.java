package vdb.dev.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import db.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import vdb.dev.App;
import vdb.dev.Controllers.addMenu.AddMenuController;

public class MainController
{

    private static db.entities.Reader currentAuthorizedReader;
    //TODO IF ID OF CREATED ENTITY == NUll, JUST INSERT SUCH ENTITY
    ObservableList<Entity> listEntitiesToChange;
    ObservableList<Entity> listEntitiesToDelete;

    EventHandler<KeyEvent> deleteHandler;

    private static boolean isAdmin;

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
        isAdmin = isAdminRightsSet();
        setRightsConfigurations(isAdmin);
    }

    @FXML
    public void selectTabel(javafx.event.ActionEvent event) throws SQLException
    {
        String nameOfSelectedTable = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();
        displayReaderTable(nameOfSelectedTable);
    }

    private void createNewTableColumns(String[] columnsToCreate, String patterInt, int amoutColumnsEditBlock)
    {
        TableColumn column = null;
        mainTableView.getColumns().clear();

        char dataTypeCurrent;
        String COLUMN_NAME;

        for (int i = 0; i < columnsToCreate.length; i++)
        {
            COLUMN_NAME = columnsToCreate[i];
            dataTypeCurrent = patterInt.charAt(i);

            column = getValidColumn(dataTypeCurrent);
            column.setText(COLUMN_NAME);
            column.setCellValueFactory(new PropertyValueFactory<>(COLUMN_NAME));


            if (isAdmin)
            {
                setEditableColumn(column);

                if (amoutColumnsEditBlock != 0)
                {
                    column.setEditable(false);
                    --amoutColumnsEditBlock;
                }
            }
            mainTableView.getColumns().add(column);
        }
    }

    @FXML
    public void changeCurrentTable(javafx.scene.input.MouseEvent event)
    {
        String nameOfCurrentSelectedEntity = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();

        switch (nameOfCurrentSelectedEntity)
        {
            case "Reader":

                for (Entity entity : listEntitiesToDelete)
                {
                    App.sqlOps.getReaderRepository().delete((Reader) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getReaderRepository().update((Reader) entity);
                }

                break;
            case "Authorship":

                for (Entity entity : listEntitiesToDelete)
                {
                    App.sqlOps.getAuthorshipRepository().delete((Authorship) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getAuthorshipRepository().insert((Authorship) entity);
                }

                break;
            case "Belongs":

                for (Entity entity : listEntitiesToDelete)
                {
                    App.sqlOps.getBelongsRepository().delete((Belongs) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getBelongsRepository().insert((Belongs) entity);
                }

                break;
            case "Book":
                for (Entity entity : listEntitiesToDelete)
                {
                    App.sqlOps.getBookRepository().delete((Book) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getBookRepository().update((Book) entity);
                }

                break;
            case "BookInstance":

                for (Entity entity : listEntitiesToDelete)
                {
                    App.sqlOps.getBookInstanceRepository().delete((BookInstance) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getBookInstanceRepository().update((BookInstance) entity);
                }

                break;
            case "BookReader":

                for (Entity entity : listEntitiesToDelete)
                {
                    App.sqlOps.getBookReaderRepository().delete((BookReader) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getBookReaderRepository().update((BookReader) entity);
                }

                break;
            case "Catalog":

                for (Entity entity : listEntitiesToDelete)
                {
                    App.sqlOps.getCatalogRepository().delete((Catalog) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getCatalogRepository().update((Catalog) entity);
                }

                break;
            case "Author":

                for (Entity entity : listEntitiesToDelete)
                {
                    App.sqlOps.getAuthorRepository().delete((Author) entity);
                }
                for (Entity entity : listEntitiesToChange)
                {
                    App.sqlOps.getAuthorRepository().update((Author) entity);
                }

                break;
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
    public void defaultQueriesMenu(javafx.scene.input.MouseEvent event) throws IOException
    {

    }

    @FXML
    public void logOut(javafx.scene.input.MouseEvent event) throws IOException
    {
        App.scene.setOnKeyPressed(null);
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


    private boolean isAdminRightsSet()
    {
        if (currentAuthorizedReader.getTypeRights() == 0)
            return false;
        else
            return true;
    }

    private void setRightsConfigurations(boolean admin)
    {
        if (admin)
        {
            var tableNames = FXCollections.observableArrayList(
                    "Author", "Authorship", "Belongs", "Book",
                    "BookInstance", "BookReader", "Catalog", "Reader");

            chooseTableComboBox.setItems(tableNames);

            listEntitiesToChange = FXCollections.observableArrayList();
            listEntitiesToDelete = FXCollections.observableArrayList();

            deleteHandler = new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent keyEvent)
                {
                    try
                    {
                        if (keyEvent.getCode() == KeyCode.DELETE)
                        {
                            var allData = mainTableView.getItems();
                            var selectedData = mainTableView.getSelectionModel().getSelectedItems();
                            listEntitiesToDelete.addAll(selectedData);

                            selectedData.forEach(allData::remove);
                        }
                    } catch (Exception e)
                    {
                    }
                }
            };

            App.scene.setOnKeyPressed(deleteHandler);


        } else
        {
            var tableNames = FXCollections.observableArrayList(
                    "Author", "Authorship", "Belongs",
                    "Book", "BookReader", "Catalog");

            chooseTableComboBox.setItems(tableNames);

            addMenuButton.setDisable(true);
            changeButton.setDisable(true);

        }

    }

    //returns column with appropriate for us data type and makes it editable
    private TableColumn getValidColumn(char dataTypeCurrent)
    {
        TableColumn column = null;
        switch (dataTypeCurrent)
        {
            case '0':
                column = new TableColumn<Entity, String>();
                column.setCellFactory(TextFieldTableCell.forTableColumn());
                return column;
            case '1':
                column = new TableColumn<Entity, Integer>();
                column.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
                return column;
            case '2':
                column = new TableColumn<Entity, Date>();
                column.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
                return column;
            default:
                return null;
        }
    }

    private void setEditableColumn(TableColumn userColumn)
    {

        listEntitiesToChange.clear();
        listEntitiesToDelete.clear();

        String fCOLUMN_NAME = userColumn.getText();

        userColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent event)
            {
                String nameOfCurrentSelectedEntity = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();

                switch (nameOfCurrentSelectedEntity)
                {
                    case "Reader":
                        Reader readerToChange = (Reader) event.getRowValue();
                        listEntitiesToChange.add(readerToChange.change(fCOLUMN_NAME, event.getNewValue()));
                        break;
                    case "Authorship":
                        Authorship authorship = (Authorship) event.getRowValue();
                        listEntitiesToDelete.add(authorship);
                        listEntitiesToChange.add(authorship.change(fCOLUMN_NAME, event.getNewValue()));
                        break;
                    case "Belongs":
                        Belongs belongs = (Belongs) event.getRowValue();
                        listEntitiesToDelete.add(belongs);
                        listEntitiesToChange.add(belongs.change(fCOLUMN_NAME, event.getNewValue()));
                        break;
                    case "Book":
                        Book book = (Book) event.getRowValue();
                        listEntitiesToChange.add(book.change(fCOLUMN_NAME, event.getNewValue()));
                        break;
                    case "BookInstance":
                        BookInstance bookInstance = (BookInstance) event.getRowValue();
                        listEntitiesToChange.add(bookInstance.change(fCOLUMN_NAME, event.getNewValue()));
                        break;
                    case "BookReader":
                        BookReader bookReader = (BookReader) event.getRowValue();
                        listEntitiesToDelete.add(bookReader);
                        listEntitiesToChange.add(bookReader.change(fCOLUMN_NAME, event.getNewValue()));
                        break;
                    case "Catalog":
                        Catalog catalog = (Catalog) event.getRowValue();
                        listEntitiesToChange.add(catalog.change(fCOLUMN_NAME, event.getNewValue()));
                        break;
                    case "Author":
                        Author author = (Author) event.getRowValue();
                        listEntitiesToChange.add(author.change(fCOLUMN_NAME, event.getNewValue()));
                        break;

                    default:

                }
            }
        });
    }

    private void displayReaderTable(String name) throws SQLException
    {
        switch (name)
        {
            case "Reader":

                String[] cellNamesReader = {"id", "pib", "password", "login",
                        "typeRights", "city", "street", "build",
                        "apartment", "workplace", "birthDate", "phoneNum"};

                createNewTableColumns(cellNamesReader, Reader.TYPE_PARAMS_PATTERN, 1);

                var listOfReaders = App.sqlOps.getReaderRepository().getAllReaders();
                mainTableView.setItems(listOfReaders);

                break;
            case "Authorship":

                String[] cellNamesAuthorship = {"id", "ISBN"};

                createNewTableColumns(cellNamesAuthorship, Authorship.TYPE_PARAMS_PATTERN, 0);

                var listAuthorships = App.sqlOps.getAuthorshipRepository().getAllAuthorships();
                mainTableView.setItems(listAuthorships);

                break;
            case "Belongs":

                String[] cellNamesBelongs = {"isbn", "idCatalog"};

                createNewTableColumns(cellNamesBelongs, Belongs.TYPE_PARAMS_PATTERN, 0);

                var listBelongs = App.sqlOps.getBelongsRepository().getAllBelongs();
                mainTableView.setItems(listBelongs);

                break;
            case "Book":

                String[] cellNamesBook = {"ISBN", "name", "publisher", "pubCity", "pubYear", "pageNum", "price"};

                createNewTableColumns(cellNamesBook, Book.TYPE_PARAMS_PATTERN, 1);

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

                String[] cellNamesBookReader = {"idReader", "idInstance", "dateOut",
                        "dateExp", "dateReturn"};

                createNewTableColumns(cellNamesBookReader, BookReader.TYPE_PARAMS_PATTERN, 2);

                ObservableList<Entity> listBookReaders;

                if (isAdmin)
                {
                    listBookReaders = App.sqlOps.getBookReaderRepository().getAllBookReaders();
                }
                else
                {
                    int userId = currentAuthorizedReader.getId();
                    listBookReaders = App.sqlOps.getBookReaderRepository().getBookReadersByReader(userId);
                }

                mainTableView.setItems(listBookReaders);

                if (!isAdmin)
                    mainTableView.getColumns().get(0).setVisible(false);

                break;
            case "Catalog":

                String[] cellNamesCatalog = {"id", "name"};

                createNewTableColumns(cellNamesCatalog, Catalog.TYPE_PARAMS_PATTERN, 1);

                var listCatalogs = App.sqlOps.getCatalogRepository().getAllCatalogs();
                mainTableView.setItems(listCatalogs);

                break;
            case "Author":

                String[] cellNamesAuthor = {"id", "name"};

                createNewTableColumns(cellNamesAuthor, Author.TYPE_PARAMS_PATTERN, 1);

                var listAuthors = App.sqlOps.getAuthorRepository().getAllAuthors();
                mainTableView.setItems(listAuthors);

                break;
        }
    }

}

