package vdb.dev.Controllers;

import db.PasswordAuthentication;
import db.entities.Reader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import vdb.dev.App;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignUpController
{

    public static final String PATH = "Fxmls/Authorization/SignUp";
    public static final String ADMIN_CODE = "Secret";
    public static final String LIBRARIAN_CODE = "LibSecret";

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
    void initialize()
    {
        passwordAuthentication = new PasswordAuthentication();
    }

    @FXML
    public void exit(javafx.scene.input.MouseEvent event)
    {
        Platform.exit();
    }

    @FXML
    public void goBack(javafx.scene.input.MouseEvent event) throws IOException
    {
        App.setRoot(LogInController.PATH);
    }

    boolean addedUser = false;
    public void signUp(javafx.scene.input.MouseEvent event) throws SQLException
    {
        try
        {
            addedUser =false;
            String pib = pibField.getText(), login = loginField.getText(),
                    city = cityField.getText(), build = buildField.getText(),
                    apartament = apartamentField.getText(), street = streetField.getText(),
                    password = passwordField.getText(), confirmationPass = confirmPasswrodField.getText();
            LocalDate dateOfBirth = dateOfBirthDatePicker.getValue();

            LocalDate validDate = LocalDate.now().minusYears(18);

            if (!pib.isEmpty() && !login.isEmpty() && !city.isEmpty() && !build.isEmpty() &&
                    !apartament.isEmpty() && !street.isEmpty() && !password.isEmpty() &&
                    !confirmationPass.isEmpty() && dateOfBirth != null )
            {
                if(pib.matches("^.{1,50}$") && city.matches("^.{1,50}$") && build.matches("^\\d{1,15}$")
                        && apartament.matches("^\\d{1,15}$") && confirmationPass.equals(password) && dateOfBirth.isBefore(validDate))
                {
                    password = new PasswordAuthentication().hash(password.toCharArray());
                    if (adminCodeField.getText().equals(ADMIN_CODE))
                    {
                        Reader reader = new Reader(pib, password, login, 1,
                                city, street, build, apartament, null, dateOfBirth, null);

                        addedUser = App.sqlOps.getReaderRepository().insert(reader);
                    } else if (adminCodeField.getText().equals(LIBRARIAN_CODE))
                    {
                        Reader reader = new Reader(pib, password, login, 2,
                                city, street, build, apartament, null, dateOfBirth, null);

                        addedUser = App.sqlOps.getReaderRepository().insert(reader);
                    }
                    {
                        Reader reader = new Reader(pib, password, login, 0,
                                city, street, build, apartament, null, dateOfBirth, null);

                        addedUser = App.sqlOps.getReaderRepository().insert(reader);
                    }
                }else {
                    new Alert(Alert.AlertType.INFORMATION, "Not correct input").showAndWait();
                }
                if (addedUser)
                {
                    App.setRoot(LogInController.PATH);
                }
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Not all data fields are entered!").showAndWait();
            }
        }catch (NullPointerException | IOException e){
            new Alert(Alert.AlertType.INFORMATION, "Not all data fields are entered!").showAndWait();
        }
    }
}




