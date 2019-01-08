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
    @FXML
    private AnchorPane rateWindow;
    @FXML
    private ImageView rate1;
    @FXML
    private ImageView rate2;
    @FXML
    private ImageView rate3;
    @FXML
    private ImageView rate4;
    @FXML
    private ImageView rate5;
    @FXML
    private ImageView rate6;
    @FXML
    private ImageView rate7;
    @FXML
    private ImageView rate8;
    @FXML
    private ImageView rate9;
    @FXML
    private ImageView rate10;
    @FXML
    private ImageView persenolRating;
    @FXML
    private ImageView ratingStar1;
    
    private boolean ratingWindowIsOpen = false;
    
    
   
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    //genreComBox.getItems().addAll(c)
    
        
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

    @FXML
    private void rate(MouseEvent event) {
        if (ratingWindowIsOpen){
            rateWindow.toBack();
            ratingWindowIsOpen = false;
        
        }  else {
            rateWindow.toFront();
            ratingWindowIsOpen = true;
        }
        
        
    }

    
 
  

}

