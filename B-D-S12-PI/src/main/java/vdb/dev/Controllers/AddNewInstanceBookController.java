package vdb.dev.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddNewInstanceBookController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField bookTitleField;

    @FXML
    private TextField ShelfField;

    @FXML
    private TextField amountField;

    @FXML
    private Button createNewBookInstanceButton;

    @FXML
    void initialize() {

    }
}
