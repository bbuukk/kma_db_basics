package vdb.dev.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import vdb.dev.App;

public class LogInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton aboutButton;

    @FXML
    private JFXButton exitButton;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private JFXButton logInButton;

    @FXML
    private JFXTextField logInTextField;

    @FXML
    private JFXPasswordField passwordTextField;

    @FXML
    private Text userGreetingText;

    @FXML
    void initialize() {
    }

    public void logIn(MouseEvent event) throws IOException
    {
        String userName = logInTextField.getText();
        String password = passwordTextField.getText();
        if (!userName.equals("") && !password.equals(""))
        {
            App.setRoot("Main");
            MainController mainController = (MainController) App.controllerForTransferData;
            mainController.displayText(userName);
        }
    }

    //fuction that handels signUpButton event
    public void signUp(ActionEvent event) throws IOException
    {
        App.setRoot("SignUp");
    }

    public void askForExit(MouseEvent event)
    {
        Platform.exit();
    }
}

