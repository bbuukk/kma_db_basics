package vdb.dev.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AddNewBookController {

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
    private ComboBox<?> chooseCatalogComboBox;

    @FXML
    private Button addToCatalogButton;

    @FXML
    private ComboBox<?> chooseAuthorComboBox;

    @FXML
    private Button addAuthorButton;

    @FXML
    private Label belongsToLable;

    @FXML
    void initialize() {

    }
}
