package vdb.dev.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vdb.dev.App;

public class MainController extends Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text userGreetingText;

    @FXML
    private Button addButton;

    @FXML
    private Button changeButton;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {

    }

    public void add(MouseEvent event) throws IOException
    {
        Stage adminStage = new Stage();
        adminStage.setTitle("AdminTools");
        Scene adminToolsScene = new Scene(App.loadFxml("AdminTools"), 666, 459);
        adminStage.setScene(adminToolsScene);
        adminStage.show();
    }

    public void displayText(String userName)
    {
        userGreetingText.setText("Hello!!! " +  userName);
    }
}
