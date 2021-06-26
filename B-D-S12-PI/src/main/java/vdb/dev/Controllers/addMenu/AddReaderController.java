package vdb.dev.Controllers.addMenu;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import db.PasswordAuthentication;
import db.SqlOps;
import db.entities.Reader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import vdb.dev.App;

public class AddReaderController {

    public static final String PATH = "Fxmls/Main/AddReader";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private PasswordField confirmPassField;

    @FXML
    private RadioButton showRadioButton;

    @FXML
    private Label showLabel;

    @FXML
    private TextField streetField;

    @FXML
    private Button createReader;


    @FXML
    void initialize() {

    }
//    Reader reader = new Reader(
//            pibField.getText(),
//            confirmPassField.getText(),
//            loginField.getText(),
//            0,
//            cityField.getText(),
//            StreetField.getText(),
//            BuildField.getText(),
//            ApartamentField.getText(),
//            workplaceField.getText(),
//            dateOfBirthDatePicker.getValue(),
//            phoneField.getText()
//    );
    public void createNewReader(javafx.scene.input.MouseEvent event) throws IOException {

            String pib = pibField.getText(), login = loginField.getText(),
                    city = cityField.getText(), build = buildField.getText(),
                    apartament = apartamentField.getText(), street = streetField.getText(),
                    password = passwordField.getText(), confirmationPass = confirmPassField.getText();
            LocalDate dateOfBirth = dateOfBirthDatePicker.getValue();

            if (!pib.isEmpty() && !login.isEmpty() && !city.isEmpty() && !build.isEmpty() &&
                    !apartament.isEmpty() && !street.isEmpty() && !password.isEmpty() &&
                    !confirmationPass.isEmpty() && dateOfBirth != null)
            {
                    password = new PasswordAuthentication().hash(password.toCharArray());

                Reader reader = new Reader(pib, password, login, 0,
                        city, street, build, apartament,null, dateOfBirth, null);

                App.sqlOps.getReaderRepository().insert(reader);
            }else{
                new Alert(Alert.AlertType.INFORMATION, "Not all data fields are entered!").showAndWait();
        }
    }
}



