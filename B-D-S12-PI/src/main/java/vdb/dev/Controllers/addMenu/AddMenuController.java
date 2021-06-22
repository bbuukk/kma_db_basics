package vdb.dev.Controllers.addMenu;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import vdb.dev.App;

import static vdb.dev.App.loadFxml;
import static vdb.dev.App.scene;

public class AddMenuController {

    public static final String PATH = "Fxmls/Main/AddMenu";
    static Scene adminToolsScene;

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
        adminToolsScene = new Scene(loadFxml(PATH));
        adminStage.setScene(adminToolsScene);
        adminStage.show();
    }

    static public void setRoot(String fxml) throws IOException
    {
        adminToolsScene.setRoot(App.loadFxml(fxml));
    }

    public void CreateNewBook(MouseEvent event) throws IOException
    {
        setRoot(AddNewBookController.PATH);
    }
    public void CreateNewReader(MouseEvent event) throws IOException
    {
        setRoot(AddReaderController.PATH);
    }
    public void CreateNewCatalog(MouseEvent event) throws IOException
    {
        setRoot(AddCatalogController.PATH);
    }
    public void CreateNewBookInstance(MouseEvent event) throws IOException
    {
        setRoot(AddNewInstanceBookController.PATH);
    }





}
