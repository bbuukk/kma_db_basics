package vdb.dev.Controllers.addMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import vdb.dev.App;

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

    public void openAddMenu() throws IOException
    {
        Stage adminStage = new Stage();
        adminStage.setTitle("B-D-S12-PI Add Menu");
        Scene adminToolsScene = new Scene(App.loadFxml(PATH));
        adminStage.setScene(adminToolsScene);
        adminStage.show();
    }



}
