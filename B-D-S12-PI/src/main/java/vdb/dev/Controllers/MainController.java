package vdb.dev.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class MainController {

    public static final String PATH = "Fxmls/Main/Main";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addMenuButton;

    @FXML
    private Button showMenuButton;

    @FXML
    private Button ChangeMenuButton;

    @FXML
    private Button DefaultMenuButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button logOutButton;

    @FXML
    private ComboBox<?> chooseTableComboBox;

    @FXML
    private TableView<?> mainTableView;

    @FXML
    void initialize() {

    }
}


//    public void add(MouseEvent event) throws IOException
//    {
//        Stage adminStage = new Stage();
//        adminStage.setTitle("B-D-S12-PI Admin Tools");
//        Scene adminToolsScene = new Scene(App.loadFxml("AdminTools"), 666, 459);
//        adminStage.setScene(adminToolsScene);
//        adminStage.show();
//    }
//
//    public void displayText(String userName)
//    {
//        userGreetingText.setText("Hello!!! " +  userName);
//    }

