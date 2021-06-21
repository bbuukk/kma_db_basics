package vdb.dev.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LogInController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button aboutButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Button logInButton;

    @FXML
    private TextField logInTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Text userGreetingText;

    @FXML
    void initialize() {


    }
}


//    public void logIn(MouseEvent event) throws IOException
//    {
//        String userName = logInTextField.getText();
//        String password = passwordField.getText();
//        if (!userName.equals("") && !password.equals(""))
//        {
//            App.setRoot("Main");
//            MainController mainController = (MainController) App.controllerForTransferData;
//            mainController.displayText(userName);
//        }
//    }
//
//    //fuction that handels signUpButton event
//    public void signUp(ActionEvent event) throws IOException
//    {
//        App.setRoot("SignUp");
//    }
//
//    public void askForExit(MouseEvent event)
//    {
//        Platform.exit();
//    }



