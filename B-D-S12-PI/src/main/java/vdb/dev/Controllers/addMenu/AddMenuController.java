package vdb.dev.Controllers.addMenu;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.tools.javac.Main;
import db.PasswordAuthentication;
import db.entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import vdb.dev.App;
import vdb.dev.Controllers.MainController;

public class AddMenuController
{

    MainController mainController;

    public AddMenuController(MainController mainController)
    {
        this.mainController = mainController;
    }

    public int iteratorOfClickAddMenu = 0;

    public void openRequestedAddMenu(String tableName)
    {
        try
        {
            if (iteratorOfClickAddMenu == 1)
            {
                add("Off");
                return;
            }

            add(tableName);

        } catch (NullPointerException | IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void add(String tableName) throws IOException
    {
        ++iteratorOfClickAddMenu;
        switch (tableName)
        {
            case "Reader":
                mainController.getMainTableView().setPrefWidth(572);
                mainController.getAddReaderMenuPane().setVisible(true);
                break;

            case "Authorship":
            case "BookReader":
            case "Author":
            case "Belongs":
                new Alert(Alert.AlertType.WARNING, "Not allowed!").showAndWait();
                break;

            case "Book":
                mainController.getMainTableView().setPrefWidth(572);
                mainController.addBookMenuPane.setVisible(true);
                configureNewBookSectionCatalogComboBox();
                configureNewBookSectionAuthorComboBox();

                break;
            case "BookInstance":
                mainController.getMainTableView().setPrefWidth(572);
                mainController.getAddNIBPaneMenu().setVisible(true);
                configureNewBookInstanceSectionBookTitlesComboBox();

                break;
            case "Catalog":
                mainController.getMainTableView().setPrefWidth(572);
                mainController.getAddCatalogMenuPane().setVisible(true);
                ++iteratorOfClickAddMenu;

                break;
            case "Off":
                iteratorOfClickAddMenu = 0;
                mainController.turnOffSection();

                break;
        }
    }

    public void createNewBook() throws IOException
    {
        //FIXME THIS GETS FROM MAINCONTROLLER
        String bookTitle = mainController.getBookTitleField().getText(),
                publisher = mainController.getPublisherField().getText(),
                publisherCity = mainController.getPublishersCityField().getText(),
                amountOfPages = mainController.getAmountOfPagesField().getText(),
                price = mainController.getPriceField().getText();

        LocalDate dateOfPublishing = mainController.getDateOfPublishingPicker().getValue();

//        String belonggToCatalog = mainController.getBelongsToLable().getText();
//        String[] belongsCatalogs = belonggToCatalog.split(",");
//        belongsCatalogs[0] = belongsCatalogs[0].substring(belonggToCatalog.indexOf("Belongs to:"));
//
//        String belongToAuthor = mainController.getBelongToAuthor().getText();
//        String[] belongsAuthors = belongToAuthor.split(",");
//        belongsAuthors[0] = belongsAuthors[0].substring(belonggToCatalog.indexOf("Authors:"));
//TODO CHECK FOR SUCH AUTHROS AND CATALOGS
//        var catalogs = App.sqlOps.getCatalogRepository().getAllCatalogs();
//        boolean newCatalog = false;
//        String cName;

//        for (String s : belongsCatalogs)
//        {
//            for (Entity e: catalogs)
//            {
//                Catalog catalog = (Catalog) e;
//                 cName = ((Catalog) e).getName();
//                if (cName.equals(s)){
//                    newCatalog = true;
//                    break;
//                }
//            }
//            if (newCatalog)
//                App.sqlOps.getCatalogRepository().insert(new Catalog(s));
//           var createdCatalog = App.sqlOps.getCatalogRepository().getCatalog(s);
//          int idCatalog = createdCatalog.get(0).getId();
//          App.sqlOps.getBelongsRepository().insert(new Belongs(idCatalog,))
//            newCatalog= false;
//        }
//
//        for (String s : belongsCatalogs)
//        {
//            for (Entity e: catalogs)
//            {
//                Catalog catalog = (Catalog) e;
//                cName = ((Catalog) e).getName();
//                if (cName.equals(s)){
//                    newCatalog = true;
//                    break;
//                }
//            }
//            if (newCatalog)
//                App.sqlOps.getCatalogRepository().insert(new Catalog(s));
//            newCatalog= false;
//        }


        if (!bookTitle.isEmpty() && !publisher.isEmpty() && !publisherCity.isEmpty() &&
                !amountOfPages.isEmpty() && !price.isEmpty() && dateOfPublishing != null)
        {
//            if(belongsCatalogs.length != 0)


            Book book = new Book(bookTitle, publisher, publisherCity,
                    dateOfPublishing, Integer.parseInt(amountOfPages), price);

            App.sqlOps.getBookRepository().insert(book);
            addBookMenuClean();
        } else
        {
            new Alert(Alert.AlertType.INFORMATION, "Not all data fields are entered!").showAndWait();
        }

    }

    private void addBookMenuClean()
    {
        String none = "";
        mainController.getBookTitleField().setText(none);
        mainController.getPublisherField().setText(none);
        mainController.getPublishersCityField().setText(none);
        mainController.getAmountOfPagesField().setText(none);
        mainController.getPriceField().setText(none);
    }


    public void createNewBookInstance() throws IOException
    {
        String shelf = mainController.getShelfField().getText(),
                amount = mainController.getAmountField().getText();

        String nameOfChosenBook = mainController.chooseBookTitles.getSelectionModel().getSelectedItem().toString();

        List<Book> books = App.sqlOps.getBookRepository().getBook(nameOfChosenBook);

        if (!shelf.equals("") && !amount.equals(""))
        {

            BookInstance bookInstance;
            for (int i = 0; i < Integer.parseInt(amount); i++)
            {
                bookInstance = new BookInstance(
                        Integer.parseInt(shelf),
                        books.get(0).getISBN());
                //TODO INSERTING BOOK WITH SAME NAMES !!!! we are inserting just first

                App.sqlOps.getBookInstanceRepository().insert(bookInstance);
                addNBIMenuClean();
                //TODO INSER LIST OF BOOKINSTANCES
            }
        } else
        {
            new Alert(Alert.AlertType.INFORMATION, "Not all data fields are entered!").showAndWait();
        }
    }

    private void addNBIMenuClean()
    {
        String none = "";
        mainController.getShelfField().setText(none);
        mainController.getAmountField().setText(none);
    }

    public void createNewCatalog() throws IOException
    {
        String catalogName = mainController.getCatalogNameField().getText();
        if (!catalogName.equals(""))
        {
            Catalog catalog = new Catalog(catalogName);
            App.sqlOps.getCatalogRepository().insert(catalog);
            addCatalogMenuClean();
        } else
        {
            new Alert(Alert.AlertType.INFORMATION, "Not all data fields are entered!").showAndWait();
        }
    }

    private void addCatalogMenuClean()
    {
        String none = "";
        mainController.getCatalogNameField().setText(none);
    }

    boolean addedUser = false;
    public void createNewReader() throws IOException
    {
        addedUser =false;
        String pib = mainController.getPibField().getText(), login = mainController.getLoginField().getText(),
                city = mainController.getCityField().getText(), build = mainController.getBuildField().getText(),
                apartament = mainController.getApartamentField().getText(), street = mainController.getStreetField().getText(),
                password = mainController.getPasswordField().getText(), confirmationPass = mainController.getConfirmPassField().getText();
        LocalDate dateOfBirth = mainController.getDateOfBirthDatePicker().getValue();
        LocalDate validDate = LocalDate.now().minusYears(18);

        if (!pib.isEmpty() && !login.isEmpty() && !city.isEmpty() && !build.isEmpty() &&
                !apartament.isEmpty() && !street.isEmpty() && !password.isEmpty() &&
                !confirmationPass.isEmpty() && dateOfBirth != null)
        {
            if (pib.matches("^.{1,50}$") && city.matches("^.{1,50}$") && build.matches("^\\d{1,15}$")
                    && apartament.matches("^\\d{1,15}$") && confirmationPass.equals(password) && dateOfBirth.isBefore(validDate))
            {
                password = new PasswordAuthentication().hash(password.toCharArray());

                Reader reader = new Reader(pib, password, login, 0,
                        city, street, build, apartament, null, dateOfBirth, null);

               addedUser = App.sqlOps.getReaderRepository().insert(reader);
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Not correct input!").showAndWait();
            }
            if (addedUser){
                addReaderMenuClean();
            }
        } else
        {
            new Alert(Alert.AlertType.INFORMATION, "Not all data fields are entered!").showAndWait();
        }
    }

    private void addReaderMenuClean(){
        String none = "";
        mainController.getPibField().setText(none);
        mainController.getLoginField().setText(none);
        mainController.getCityField().setText(none);
        mainController.getBuildField().setText(none);
        mainController.getApartamentField().setText(none);
        mainController.getStreetField().setText(none);
        mainController.getPasswordField().setText(none);
        mainController.getConfirmPassField().setText(none);
        mainController.getDateOfBirthDatePicker().setValue(null);
    }

    private void configureNewBookSectionCatalogComboBox()
    {
        ObservableList<String> entitiesNames = FXCollections.observableArrayList();
        var authors = App.sqlOps.getAuthorRepository().getAllAuthors();

        for (Entity ent : authors)
        {
            Author a = (Author) ent;
            entitiesNames.add(a.getName());
        }

        mainController.chooseAuthorComboBox.setItems(entitiesNames);
    }

    private void configureNewBookSectionAuthorComboBox()
    {
//        ObservableList<String> entitiesNames = FXCollections.observableArrayList();
//        var catalogs = App.sqlOps.getCatalogRepository().getAllCatalogs();
//
//        for (Entity ent : catalogs)
//        {
//            Catalog c = (Catalog) ent;
//            entitiesNames.add(c.getName());
//        }
//
//        mainController.chooseCatalogComboBox.setItems(entitiesNames);
    }

    private void configureNewBookInstanceSectionBookTitlesComboBox()
    {
//        ObservableList<String> entitiesNames = FXCollections.observableArrayList();
//        var books = App.sqlOps.getBookRepository().getAllBooks();
//
//        for (Entity ent : books)
//        {
//            Book b = (Book) ent;
//            entitiesNames.add(b.getName());
//        }
//
//        mainController.chooseBookTitles.setItems(entitiesNames);
    }

    int catalogNum = 0;

    public void configureAddToCatalogNewBookSectionLabelsLabel()
    {
//        try
//        {
//            if (catalogNum < 5)
//            {
//                String alreadyBelongs = mainController.belongsToLable.getText();
//                String chosenCatalog = mainController.chooseCatalogComboBox.getSelectionModel().getSelectedItem().toString();
//                alreadyBelongs += chosenCatalog + ",";
//                if (catalogNum == 2)
//                    alreadyBelongs += "\n";
//                mainController.belongsToLable.setText(alreadyBelongs);
//
//                ++catalogNum;
//            } else
//            {
//                new Alert(Alert.AlertType.WARNING, "Limit of catalogs for a book").showAndWait();
//            }
//        } catch (NullPointerException e)
//        {
//            new Alert(Alert.AlertType.WARNING, "Problem with data input").showAndWait();
//        }
    }

    int authorNum = 0;

    public void configureAddToAuthorNewBookSectionLabelsLabels()
    {
//        try
//        {
//            if (authorNum < 5)
//            {
//                String alreadyBelongs = mainController.belongToAuthor.getText();
//                String chosenAuthor = mainController.chooseAuthorComboBox.getSelectionModel().getSelectedItem().toString();
//                alreadyBelongs += chosenAuthor + ",";
//                if (authorNum == 2)
//                    alreadyBelongs += "\n";
//                mainController.belongToAuthor.setText(alreadyBelongs);
//                ++authorNum;
//            } else
//            {
//                new Alert(Alert.AlertType.WARNING, "Limit of authors for a book").showAndWait();
//            }
//        } catch (NullPointerException e)
//        {
//            new Alert(Alert.AlertType.WARNING, "Problem with data input").showAndWait();
//        }
    }

}
