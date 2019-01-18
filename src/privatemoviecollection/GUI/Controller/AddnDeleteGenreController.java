/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import privatemoviecollection.GUI.Model.Model;

/**
 * FXML Controller class
 * yes
 * @author Nijas Hansen
 */
public class AddnDeleteGenreController implements Initializable {

    private static Model model;
    @FXML
    private ListView<String> lstviewGenres;
    @FXML
    private TextField txtfldAddGenre;
    private String highlight;

    private Stage stage;
    @FXML
    private Text label;

    public void setModel(Model model) {
        this.model = model;
        lstviewGenres.getItems().setAll(model.getAllGenres());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstviewGenres.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                label.setText("You Selected " + newValue);
            }
        });
    }

    @FXML
    private void addGenreBTN(MouseEvent event) {
        String genre = txtfldAddGenre.getText();
        model.createGenre(genre);
        lstviewGenres.getItems().setAll(model.getAllHashGenres());
    }

    @FXML
    private void deleteGenreBTN(MouseEvent event) {
        String genre = lstviewGenres.getSelectionModel().getSelectedItem();
        model.deleteGenre(genre);
        lstviewGenres.getItems().setAll(model.getAllHashGenres());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void cancelBTN(MouseEvent event) {
        stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    private void highlight(MouseEvent event) {
        highlight = lstviewGenres.getSelectionModel().getSelectedItem();
    }

}
