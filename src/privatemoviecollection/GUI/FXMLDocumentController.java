/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Christian
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private StackPane stacPane;
    @FXML
    private AnchorPane gridAnchor;
    @FXML
    private GridPane movieGrid;
    @FXML
    private ImageView FirstMovie;
    @FXML
    private ImageView FirstMovie1;
    @FXML
    private AnchorPane popUd;
    @FXML
    private AnchorPane closePopUp;
    @FXML
    private StackPane stacPopUp;
    @FXML
    private ComboBox<String> genreComBox;
    @FXML
    private Menu fileMenu;
    
    
   
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    //genreComBox.getItems().addAll(c)
    fileMenu.
        
    }    

    @FXML
    private void bringToFront(MouseEvent event) {
       stacPopUp.toFront();
       popUd.toFront();
    }     
    
    
    


  

    @FXML
    private void play(MouseEvent event) {
    }


    @FXML
    private void bringToBack(MouseEvent event) {
        stacPopUp.toBack();
    }

    @FXML
    private void addMovie(ActionEvent event) {
    }

    @FXML
    private void addGenre(ActionEvent event) {
    }

    @FXML
    private void rencentlyWatched(ActionEvent event) {
    }

    @FXML
    private void exit(ActionEvent event) {
    }

    @FXML
    private void deleteMovie(ActionEvent event) {
    }

    
 
  

}

