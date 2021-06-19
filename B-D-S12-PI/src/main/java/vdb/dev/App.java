package vdb.dev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vdb.dev.Controllers.Controller;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

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
        App.stage.getIcons().add(new Image("file:assets/pictures/png/iconOfProgramSelFDev.png"));
        stage.setResizable(false);

//        String stylesheet = App.class.getResource("vdb/dev/Css/LogIn.css").toExternalForm();
//        scene.getStylesheets().add(stylesheet);

        Parent root = loadFxml("LogIn");
//        root.getStylesheets().add(getClass().getResource("LogIn.css").toString());

        scene = new Scene(root, 816, 509);
//        scene.getStylesheets().add("LogIn.css");
        setRoot(root);

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

    static public void setRoot(Parent root) throws IOException
    {
        scene.setRoot(root);
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

    /**
     * Возвращает preference файла адресатов, то есть, последний открытый файл.
     * Этот preference считывается из реестра, специфичного для конкретной
     * операционной системы. Если preference не был найден, то возвращается null.
     *
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Задаёт путь текущему загруженному файлу. Этот путь сохраняется
     * в реестре, специфичном для конкретной операционной системы.
     *
     * @param file - файл или null, чтобы удалить путь
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(App.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Обновление заглавия сцены.
            stage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Обновление заглавия сцены.
            stage.setTitle("AddressApp");
        }
    }
}