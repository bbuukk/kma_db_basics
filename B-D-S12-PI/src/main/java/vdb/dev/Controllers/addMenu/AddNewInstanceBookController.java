package vdb.dev.Controllers.addMenu;

import java.net.URL;
import java.util.ResourceBundle;

import db.SqlOps;
import db.entities.BookInstance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddNewInstanceBookController {

    public static final String PATH = "Fxmls/Main/AddBookInstance";

    SqlOps sqlOps;
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

        sqlOps = new SqlOps();
    }

    public void createNewBookInstance(javafx.scene.input.MouseEvent event) {
        if(
                !bookTitleField.getText().equals("") &&
                !ShelfField.getText().equals("") &&
                !amountField.getText().equals("")
        ){
            BookInstance bookInstance = new BookInstance(
                    Integer.parseInt(bookTitleField.getText()),
                    Integer.parseInt(ShelfField.getText())
            );
            sqlOps.getBookInstanceRepository().insert(bookInstance);
        }
    }
}
