package vdb.dev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vdb.dev.Controllers.Controller;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;
    public static Controller controllerForTransferData;

    public static Stage getStage()
    {
        return stage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        stage.setTitle("B-D-S12-PI");
        scene = new Scene(loadFxml("LogIn"), 816, 509);
        setRoot("LogIn");
        App.stage.setScene(scene);
        App.stage.show();
    }

    static public void exit()
    {
        stage = (Stage) scene.getWindow();
        stage.close();
    }

    static public void setRoot(String fxml) throws IOException
    {
        scene.setRoot(loadFxml(fxml));
    }

    static public Parent loadFxml(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();

        controllerForTransferData = fxmlLoader.getController();
        return root;
    }

    public static void main(String[] args) {
        launch();
    }

}