package vdb.dev.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import db.PasswordAuthentication;
import db.SqlOps;
import db.entities.Reader;
import db.repos.ReaderRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import vdb.dev.App;


public class LogInController
{

    public static final String PATH = "Fxmls/Authorization/LogIn";

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
    void initialize()
    {

    }

    public void logIn(javafx.scene.input.MouseEvent event) throws IOException
    {
        String login = logInTextField.getText();
        String password = passwordTextField.getText();
        //todo authorization

        if (!login.equals("") && !password.equals(""))
        {

            try
            {
               List<Reader> readers =  App.sqlOps.getReaderRepository().getReader(login);

               Reader authorizedReader;
                for (Reader reader: readers)
                {
                   if (PasswordAuthentication.authenticate(password.toCharArray(), reader.getPassword())){
                       authorizedReader = reader;
                       MainController.setCurrentAuthorizedReader(authorizedReader);
                       App.setRoot(MainController.PATH);
                       break;
                   }
                }

            } catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        }
//    }
    }

    public void signUp(javafx.scene.input.MouseEvent event) throws IOException
    {
        App.setRoot(SignUpController.PATH);
    }

    @FXML
    void about(javafx.scene.input.MouseEvent event) {
         new Alert(Alert.AlertType.INFORMATION,
                "B-D-S12-PI is a unique program developed by several experts").showAndWait();
    }

    @FXML
    void exit(javafx.scene.input.MouseEvent event) {
        Platform.exit();
    }
}






