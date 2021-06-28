package vdb.dev.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import db.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import vdb.dev.App;
import vdb.dev.Controllers.addMenu.AddMenuController;

public class MainController
{

    private static db.entities.Reader currentAuthorizedReader;
    private static boolean isAdmin;

    ObservableList<Entity> listEntitiesToChange;
    ObservableList<Entity> listEntitiesToDelete;

    public static final String PATH = "Fxmls/Main/Main";

    String lastTabledisplayed;

    FilteredList<Book> filteredData;
    ObservableList<Entity> searchRes = FXCollections.observableArrayList();
    SortedList<Entity> sortedList;
    public ComboBox comboboxDorDebtors;


    @FXML
    void initialize()
    {
        App.stage.setWidth(970);
        App.stage.setHeight(634);

        mainTableView.setPrefWidth(900);

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
    void createReader(javafx.scene.input.MouseEvent event) throws IOException
    {
        addMenuController.createNewReader();
    }

    @FXML
    void createCatalog(javafx.scene.input.MouseEvent event) throws IOException
    {
        addMenuController.createNewCatalog();
    }

    @FXML
    void createNIB(javafx.scene.input.MouseEvent event) throws IOException
    {
        addMenuController.createNewBookInstance();
    }

    @FXML
    void createBook(javafx.scene.input.MouseEvent event) throws IOException
    {
        addMenuController.createNewBook();
    }
    @FXML
    void addToCatalogNewBookSection(javafx.scene.input.MouseEvent event) throws IOException
    {
        addMenuController.configureAddToCatalogNewBookSectionLabelsLabel();
    }
    @FXML
    void addToAuthorNewBookSection(javafx.scene.input.MouseEvent event) throws IOException
    {
        addMenuController.configureAddToAuthorNewBookSectionLabelsLabels();
    }

    @FXML
    void updateTable(javafx.scene.input.MouseEvent event) throws SQLException
    {
        String nameOfSelectedTable = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();
        displayReaderTable(nameOfSelectedTable);
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

    AddMenuController addMenuController = new AddMenuController(this);
    @FXML
    public void addMenu(javafx.scene.input.MouseEvent event) throws IOException
    {
        try
        {
            String nameOfCurrentSelectedEntity = chooseTableComboBox.getSelectionModel().getSelectedItem().toString();
            addMenuController.openRequestedAddMenu(nameOfCurrentSelectedEntity);
        }catch (NullPointerException e){
            new Alert(Alert.AlertType.ERROR, "You didn't chose the table").showAndWait();
        }
    }

    public boolean turnOffSection(){
        try
        {
            addMenuController.iteratorOfClickAddMenu = 0;
            mainTableView.setPrefWidth(900);
            addReaderMenuPane.setVisible(false);
            addCatalogMenuPane.setVisible(false);
            addNIBPaneMenu.setVisible(false);
            addBookMenuPane.setVisible(false);
            return true;
        }
        catch (NullPointerException exception){
            System.out.println( exception.getMessage());
            return false;
        }
    }

    @FXML
    public void defaultQueriesMenu(javafx.scene.input.MouseEvent event) throws IOException
    {
        mainTableView.setPrefWidth(572);
    }

    @FXML
    void findQueries(javafx.scene.input.MouseEvent event)
    {
        mainTableView.setPrefWidth(572);
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

            EventHandler<KeyEvent> deleteHandler = new EventHandler<KeyEvent>()
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
        if (!name.equals(lastTabledisplayed)){
            turnOffSection();
        }

        switch (name)
        {
            case "Reader":

                lastTabledisplayed = name;
                String[] cellNamesReader = {"id", "pib", "password", "login",
                        "typeRights", "city", "street", "build",
                        "apartment", "workplace", "birthDate", "phoneNum"};

                createNewTableColumns(cellNamesReader, Reader.TYPE_PARAMS_PATTERN, 1);

                var listOfReaders = App.sqlOps.getReaderRepository().getAllReaders();
                mainTableView.setItems(listOfReaders);

                break;
            case "Authorship":

                lastTabledisplayed = name;
                String[] cellNamesAuthorship = {"id", "ISBN"};

                createNewTableColumns(cellNamesAuthorship, Authorship.TYPE_PARAMS_PATTERN, 0);

                var listAuthorships = App.sqlOps.getAuthorshipRepository().getAllAuthorships();
                mainTableView.setItems(listAuthorships);

                break;
            case "Belongs":

                lastTabledisplayed = name;
                String[] cellNamesBelongs = {"isbn", "idCatalog"};

                createNewTableColumns(cellNamesBelongs, Belongs.TYPE_PARAMS_PATTERN, 0);

                var listBelongs = App.sqlOps.getBelongsRepository().getAllBelongs();
                mainTableView.setItems(listBelongs);

                break;
            case "Book":

                lastTabledisplayed = name;
                String[] cellNamesBook = {"ISBN", "name", "publisher", "pubCity", "pubYear", "pageNum", "price"};

                createNewTableColumns(cellNamesBook, Book.TYPE_PARAMS_PATTERN, 1);

                var listBooks = App.sqlOps.getBookRepository().getAllBooks();
                mainTableView.setItems(listBooks);

                break;
            case "BookInstance":

                lastTabledisplayed = name;
                String[] cellNamesBookInstance = {"id", "shelf", "ISBN"};

                createNewTableColumns(cellNamesBookInstance, BookInstance.TYPE_PARAMS_PATTERN, 1);

                var listBooksInstances = App.sqlOps.getBookInstanceRepository().getAllBookInstances();
                mainTableView.setItems(listBooksInstances);

                break;
            case "BookReader":

                lastTabledisplayed = name;
                String[] cellNamesBookReader = {"idReader", "idInstance", "dateOut",
                        "dateExp", "dateReturn"};

                createNewTableColumns(cellNamesBookReader, BookReader.TYPE_PARAMS_PATTERN, 2);

                ObservableList<Entity> listBookReaders;

                if (isAdmin)
                {
                    listBookReaders = App.sqlOps.getBookReaderRepository().getAllBookReaders();
                } else
                {
                    int userId = currentAuthorizedReader.getId();
                    listBookReaders = App.sqlOps.getBookReaderRepository().getBookReadersByReader(userId);
                }

                mainTableView.setItems(listBookReaders);

                if (!isAdmin)
                    mainTableView.getColumns().get(0).setVisible(false);

                break;
            case "Catalog":

                lastTabledisplayed = name;
                String[] cellNamesCatalog = {"id", "name"};

                createNewTableColumns(cellNamesCatalog, Catalog.TYPE_PARAMS_PATTERN, 1);

                var listCatalogs = App.sqlOps.getCatalogRepository().getAllCatalogs();
                mainTableView.setItems(listCatalogs);

                break;
            case "Author":

                lastTabledisplayed = name;
                String[] cellNamesAuthor = {"id", "name"};

                createNewTableColumns(cellNamesAuthor, Author.TYPE_PARAMS_PATTERN, 1);

                var listAuthors = App.sqlOps.getAuthorRepository().getAllAuthors();
                mainTableView.setItems(listAuthors);

                break;
        }
    }


    @FXML
    private ComboBox<String> chooseTableComboBox;

    @FXML
    private TableView<Entity> mainTableView;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addMenuButton;

    @FXML
    private Button changeButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button DefaultMenuButton;

    @FXML
    private Button findButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button exitButton;

    @FXML
    private Pane addReaderMenuPane;

    @FXML
    private TextField pibField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField workplaceField;

    @FXML
    private TextField buildField;

    @FXML
    private TextField apartamentField;

    @FXML
    private TextField phoneField;

    @FXML
    private DatePicker dateOfBirthDatePicker;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPassField;

    @FXML
    private RadioButton showRadioButton;

    @FXML
    private Label showLabel;

    @FXML
    private TextField streetField;

    @FXML
    private Button createReader;


    public TextField getPibField()
    {
        return pibField;
    }

    public TextField getLoginField()
    {
        return loginField;
    }

    public TextField getCityField()
    {
        return cityField;
    }

    public TextField getWorkplaceField()
    {
        return workplaceField;
    }

    public TextField getBuildField()
    {
        return buildField;
    }

    public TextField getApartamentField()
    {
        return apartamentField;
    }

    public TextField getPhoneField()
    {
        return phoneField;
    }

    public DatePicker getDateOfBirthDatePicker()
    {
        return dateOfBirthDatePicker;
    }

    public PasswordField getPasswordField()
    {
        return passwordField;
    }

    public PasswordField getConfirmPassField()
    {
        return confirmPassField;
    }

    public RadioButton getShowRadioButton()
    {
        return showRadioButton;
    }

    public Label getShowLabel()
    {
        return showLabel;
    }

    public TextField getStreetField()
    {
        return streetField;
    }

    public Button getCreateReader()
    {
        return createReader;
    }

    @FXML
    private Pane addCatalogMenuPane;

    public TableView<Entity> getMainTableView()
    {
        return mainTableView;
    }

    public Pane getAddReaderMenuPane()
    {
        return addReaderMenuPane;
    }

    public Pane getAddCatalogMenuPane()
    {
        return addCatalogMenuPane;
    }

    public Pane getAddNIBPaneMenu()
    {
        return addNIBPaneMenu;
    }

    public Pane getAddBookMenuPane()
    {
        return addBookMenuPane;
    }

    @FXML
    private TextField catalogNameField;

    @FXML
    private Button createCatalog;

    @FXML
    private Pane addNIBPaneMenu;

    @FXML
    private TextField bookInstanceTitleField;

    @FXML
    private TextField ShelfField;

    @FXML
    private TextField amountField;

    @FXML
    private Button createNewBookInstanceButton;

    @FXML
    public Pane addBookMenuPane;

    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField publisherField;

    @FXML
    private TextField publishersCityField;

    @FXML
    private DatePicker dateOfPublishingPicker;

    @FXML
    private TextField priceField;

    @FXML
    private TextField amountOfPagesField;

    @FXML
    public ComboBox<String> chooseCatalogComboBox;

    @FXML
    public ComboBox<String> chooseBookTitles;

    @FXML
    private Button addToCatalogButton;

    @FXML
    public ComboBox<String> chooseAuthorComboBox;

    @FXML
    private Button addAuthorButton;

    @FXML
    public Label belongsToLable;

    @FXML
    public Label belongToAuthor;

    @FXML
    private Button createNewBookButton;

    public ObservableList<Entity> getListEntitiesToChange()
    {
        return listEntitiesToChange;
    }

    public ObservableList<Entity> getListEntitiesToDelete()
    {
        return listEntitiesToDelete;
    }

    public static boolean isIsAdmin()
    {
        return isAdmin;
    }

    public static String getPATH()
    {
        return PATH;
    }

    public AddMenuController getAddMenuController()
    {
        return addMenuController;
    }

    public ComboBox<String> getChooseTableComboBox()
    {
        return chooseTableComboBox;
    }

    public ResourceBundle getResources()
    {
        return resources;
    }

    public URL getLocation()
    {
        return location;
    }

    public Button getAddMenuButton()
    {
        return addMenuButton;
    }

    public Button getChangeButton()
    {
        return changeButton;
    }

    public Button getUpdateButton()
    {
        return updateButton;
    }

    public Button getDefaultMenuButton()
    {
        return DefaultMenuButton;
    }

    public Button getFindButton()
    {
        return findButton;
    }

    public Button getLogOutButton()
    {
        return logOutButton;
    }

    public Button getExitButton()
    {
        return exitButton;
    }

    public TextField getCatalogNameField()
    {
        return catalogNameField;
    }

    public Button getCreateCatalog()
    {
        return createCatalog;
    }

    public TextField getBookInstanceTitleField()
    {
        return bookInstanceTitleField;
    }

    public TextField getShelfField()
    {
        return ShelfField;
    }

    public TextField getAmountField()
    {
        return amountField;
    }

    public Button getCreateNewBookInstanceButton()
    {
        return createNewBookInstanceButton;
    }

    public TextField getBookTitleField()
    {
        return bookTitleField;
    }

    public TextField getPublisherField()
    {
        return publisherField;
    }

    public TextField getPublishersCityField()
    {
        return publishersCityField;
    }

    public DatePicker getDateOfPublishingPicker()
    {
        return dateOfPublishingPicker;
    }

    public TextField getPriceField()
    {
        return priceField;
    }

    public TextField getAmountOfPagesField()
    {
        return amountOfPagesField;
    }

    public Button getAddToCatalogButton()
    {
        return addToCatalogButton;
    }

    public ComboBox<String> getChooseAuthorComboBox()
    {
        return chooseAuthorComboBox;
    }

    public Button getAddAuthorButton()
    {
        return addAuthorButton;
    }

    public Label getBelongsToLable()
    {
        return belongsToLable;
    }

    public Label getBelongToAuthor()
    {
        return belongToAuthor;
    }

    public Button getCreateNewBookButton()
    {
        return createNewBookButton;
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

}

