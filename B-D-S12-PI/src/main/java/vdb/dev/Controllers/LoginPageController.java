package vdb.dev.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logInButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField logInTextField;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        assert logInButton != null : "fx:id=\"logInButton\" was not injected: check your FXML file 'logIn.fxml'.";
        assert passwordField != null : "fx:id=\"passwordField\" was not injected: check your FXML file 'logIn.fxml'.";
        assert logInTextField != null : "fx:id=\"logInTextField\" was not injected: check your FXML file 'logIn.fxml'.";
        assert signUpButton != null : "fx:id=\"signUpButton\" was not injected: check your FXML file 'logIn.fxml'.";

        logInButton.setOnAction(actionEvent -> {
            System.out.println("Pressed LOGIN");
        });

    }
}
