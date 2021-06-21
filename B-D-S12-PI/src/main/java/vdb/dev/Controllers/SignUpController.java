package vdb.dev.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class SignUpController {

    public static final String PATH = "Fxmls/Authorization/SignUp";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Text userGreetingText;

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
    private PasswordField confirmPasswrodField;

    @FXML
    private RadioButton showRadioButton;

    @FXML
    private Label showLabel;

    @FXML
    private PasswordField adminCodeField;

    @FXML
    private ToggleButton adminRightsTogButton;

    @FXML
    private TextField streetField;

    @FXML
    void initialize() {

    }
}


//    public void activeAdminPasswordField()
//    {
//        if (adminCheckBox.isSelected())
//            adminPasswordField.setVisible(true);
//        else
//            adminPasswordField.setVisible(false);
//
//    }
//


