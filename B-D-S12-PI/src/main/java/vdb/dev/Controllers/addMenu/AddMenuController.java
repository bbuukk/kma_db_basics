package vdb.dev.Controllers.addMenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AddMenuController {

    public static final String PATH = "Fxmls/Main/AddMenu";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button createNewBookButton;

    @FXML
    private Button createNewReaderButton;

    @FXML
    private Button createNewInstanceBookButton;

    @FXML
    private Button createNewCatalog;

    @FXML
    void initialize() {
    }
}
