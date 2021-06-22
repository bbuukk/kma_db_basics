package vdb.dev.Controllers.addMenu;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import vdb.dev.App;


import static vdb.dev.App.loadFxml;


public class AddMenuController {

    public static final String PATH = "Fxmls/Main/AddMenu";
    static Scene adminToolsScene;
    static Stage adminStage;

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
        adminStage = new Stage();
        adminStage.setTitle("B-D-S12-PI Add Menu");
        adminToolsScene = new Scene(App.loadFxml(PATH));
        adminStage.setScene(adminToolsScene);
        adminStage.show();
    }

    static public void setRoot(String fxml) throws IOException
    {
        adminToolsScene.setRoot(loadFxml(fxml));
    }

    static public Parent loadFxml(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
//        switch (fxml){
//            case AddReaderController.PATH:
//                fxmlLoader.setController(new AddReaderController());
//                break;
//            case AddNewBookController.PATH:
//                fxmlLoader.setController(new AddNewBookController());
//                break;
//            case AddNewInstanceBookController.PATH:
//                fxmlLoader.setController(new AddNewInstanceBookController());
//                break;
//            case AddCatalogController.PATH:
//                fxmlLoader.setController(new AddCatalogController());
//                break;
//            case AddMenuController.PATH:
//                fxmlLoader.setController(new AddMenuController());
//                break;
//        }

        return fxmlLoader.load();
    }

    public void createNewBook() throws IOException
    {
        setRoot(AddNewBookController.PATH);
    }
    public void createNewReader() throws IOException
    {
        setRoot(AddReaderController.PATH);
    }
    public void createNewBookInstance() throws IOException
    {
        setRoot(AddNewInstanceBookController.PATH);
    }
    public void createNewCatalog() throws IOException
    {
        setRoot(AddCatalogController.PATH);
    }



}
