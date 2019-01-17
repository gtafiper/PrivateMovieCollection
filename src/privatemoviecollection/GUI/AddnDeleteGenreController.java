/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import privatemoviecollection.GUI.Model.Model;



/**
 * FXML Controller class
 *
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

    public void setModel(Model model) {
        this.model = model;
        lstviewGenres.getItems().setAll(model.getAllCategory());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void addGenreBTN(MouseEvent event) {
        String genre = txtfldAddGenre.getText();
        model.createCategory(genre);
        lstviewGenres.getItems().setAll(model.getAllgenres());
    }

    @FXML
    private void deleteGenreBTN(MouseEvent event) {
        String genre = lstviewGenres.getSelectionModel().getSelectedItem();
        model.deleteCategory(genre);
        lstviewGenres.getItems().setAll(model.getAllgenres());
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void cancelBTN(MouseEvent event) {
        stage.close();
    }

    @FXML
    private void highlight(MouseEvent event) {
        highlight = lstviewGenres.getSelectionModel().getSelectedItem();
    }
    
    
    
}
