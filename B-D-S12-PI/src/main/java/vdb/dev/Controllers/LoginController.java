package vdb.dev.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vdb.dev.App;

public class LoginController extends Controller
{


    @FXML
    private ResourceBundle resources;

    @FXML
    private AnchorPane scenePane;

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
    private Label tellUsText;

    @FXML
    private  Button exitButton;

    @FXML
    void initialize() {


    }

    public void logIn(MouseEvent event) throws IOException
    {
        String userName = logInTextField.getText();
        String password = passwordField.getText();
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
