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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import privatemoviecollection.GUI.Model.Model;

/**
 * FXML Controller class
 *
 * @author Nijas Hansen
 */
public class DeleteCategoryController implements Initializable {

    
    private static Model model;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnDelnClose;
    @FXML
    private Button btnCancel;
    @FXML
    private ListView<String> lstviewGenres;
    
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    public void setModel(Model model) {
        this.model = model;
        lstviewGenres.getItems().setAll(model.getAllgenres());
    }

    @FXML
    private void deleteBTN(MouseEvent event) {
        String genre = lstviewGenres.getSelectionModel().getSelectedItem();
        model.deleteCategory(genre);
    }

    @FXML
    private void deleteNCloseBTN(MouseEvent event) {
        String genre = lstviewGenres.getSelectionModel().getSelectedItem();
        model.deleteCategory(genre);
        stage.close();
    }

    @FXML
    private void cancelBTN(MouseEvent event) {
        stage.close();
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
