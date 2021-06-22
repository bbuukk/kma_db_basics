package vdb.dev.Controllers;

import db.PasswordAuthentication;
import db.SqlOps;
import db.entities.Reader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {

    public static final String PATH = "Fxmls/Authorization/SignUp";
    public static final String ADMIN_CODE = "Secret";
    private SqlOps sqlOps;
    private PasswordAuthentication passwordAuthentication;

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
        sqlOps = new SqlOps();
        passwordAuthentication = new PasswordAuthentication();
    }

    public void exit(javafx.scene.input.MouseEvent event){
        Platform.exit();
    }

    public void sighUp(MouseEvent event) throws IOException {
        if (!pibField.getText().isEmpty()
                && !loginField.getText().isEmpty()
                && !cityField.getText().isEmpty()
                && !buildField.getText().isEmpty()
                && !apartamentField.getText().isEmpty()
                && !streetField.getText().isEmpty()
                && !passwordField.getText().isEmpty()
                && !confirmPasswrodField.getText().isEmpty()
                && dateOfBirthDatePicker.getValue() != null) {
            Reader reader;
            if (!adminCodeField.getText().equals(ADMIN_CODE)) {
                reader = new Reader(pibField.getText(),
                        passwordAuthentication.hash(passwordField.getText().toCharArray()),
                        loginField.getText(),
                        Integer.valueOf(0),
                        cityField.getText(),
                        streetField.getText(),
                        buildField.getText(),
                        apartamentField.getText(),
                        workplaceField.getText().isEmpty() ? null : workplaceField.getText(),
                        dateOfBirthDatePicker.getValue(),
                        phoneField.getText().isEmpty() ? null : phoneField.getText());
            } else {
                reader = new Reader(pibField.getText(),
                        passwordAuthentication.hash(passwordField.getText().toCharArray()),
                        loginField.getText(),
                        1,
                        cityField.getText(),
                        streetField.getText(),
                        buildField.getText(),
                        apartamentField.getText(),
                        workplaceField.getText().isEmpty() ? null : workplaceField.getText(),
                        dateOfBirthDatePicker.getValue(),
                        phoneField.getText().isEmpty() ? null : phoneField.getText());
            }
            sqlOps.getReaderRepository().insert(reader);
        }
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


