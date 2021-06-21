package vdb.dev.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddReaderController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField pibField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField workplaceField;

    @FXML
    private TextField BuildField;

    @FXML
    private TextField ApartamentField;

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
    private Button createNewReader;

    @FXML
    void initialize() {

    }
}
