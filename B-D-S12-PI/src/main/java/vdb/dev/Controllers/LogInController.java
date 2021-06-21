package vdb.dev.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import db.PasswordAuthentication;
import db.SqlOps;
import db.entities.Reader;
import db.repos.ReaderRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import vdb.dev.App;


public class LogInController
{
 private SqlOps sqlOps;

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
        sqlOps = new SqlOps();
    }

    public void logIn(MouseEvent event) throws IOException
    {
        String login = logInTextField.getText();
        String password = passwordTextField.getText();
        //todo authorization

        if (!login.equals("") && !password.equals(""))
        {

            try
            {
               List<Reader> readers =  sqlOps.getReaderRepository().getReader(login);

               Reader authorizedReader;
                for (Reader reader: readers)
                {
                   if (PasswordAuthentication.authenticate(password.toCharArray(), reader.getPassword())){
                       authorizedReader = reader;
                       App.setRoot("Main");
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
}


//
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



