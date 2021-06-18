package vdb.dev.Controllers;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController extends Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField logInTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField logInTextField1;

    @FXML
    private CheckBox adminCheckBox;

    @FXML
    private PasswordField adminPasswordField;

    @FXML
    void initialize() {

    }

    public void activeAdminPasswordField()
    {
        if (adminCheckBox.isSelected())
            adminPasswordField.setVisible(true);
        else
            adminPasswordField.setVisible(false);

    }
//
}

