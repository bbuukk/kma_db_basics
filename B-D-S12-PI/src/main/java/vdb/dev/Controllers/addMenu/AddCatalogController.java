package vdb.dev.Controllers.addMenu;


import java.net.URL;
import java.util.ResourceBundle;

import db.SqlOps;
import db.entities.Catalog;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddCatalogController {

    public static final String PATH = "Fxmls/Main/AddCatalog";

    SqlOps sqlOps;
    Catalog catalog;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField catalogNameField;

    @FXML
    private Button createCatalog;

    @FXML
    void initialize() {

        sqlOps = new SqlOps();
        catalog = new Catalog();
    }


    public void createNewCatalog(MouseEvent event){
        if(!catalogNameField.equals(""))
            catalog = new Catalog(catalogNameField.getText());
        sqlOps.getCatalogRepository().insert(catalog);

    }

}
