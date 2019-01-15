/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.GUI.Model.Model;





/**
 * FXML Controller class
 *
 * @author Christian
 */
public class MoviesToDeleteController implements Initializable {
    
    private ArrayList<Movie> MoviestoDelete;
    private Model model;  
    private Stage stage;
        
    
    
    public void setModel(Model model){
        this.model = model;
    }
    
    public void setMoviesTodelete(ArrayList<Movie> list){
       this.MoviestoDelete = list;
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }

    
    
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
