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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.GUI.Model.Model;







/**
 * FXML Controller class
 *
 * @author Christian
 */
public class MoviesToDeleteController implements Initializable {
    
    private ArrayList<Movie> moviestoDelete;
    private Model model;  
    private Stage stage;
    @FXML
    private Button deleteAll;
    @FXML
    private TableView<Movie> tableView;
    private ContextMenu contexMenu;
    
    
        
    
    
    public void setModel(Model model){
        this.model = model;
    }
    
    public void setMoviesTodelete(ArrayList<Movie> list){
       this.moviestoDelete = list;
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    public void deleteAllOverDoMovies(Movie movie){
        for(Movie m: moviestoDelete){
        model.deleteMovie(m);
    }
        stage.close();
    }
    
    public void postePoneAllMovies(Movie movie){
        for(Movie m: moviestoDelete){
            model.setPlayDatetToday(movie);
        }
    }

    
    
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       tableView.getItems().setAll(moviestoDelete);
       
        contexMenu = new ContextMenu();

        MenuItem delete = new MenuItem("Delete Movie");
        delete.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                 Movie movie = tableView.getSelectionModel().getSelectedItem();
                model.deleteMovie(movie);

            }
        });

        MenuItem play = new MenuItem("Play Movie");
        play.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                 Movie movie = tableView.getSelectionModel().getSelectedItem();
                model.setPlayDatetToday(movie);
            }
        });
        
        MenuItem postpone = new MenuItem("reminde me later");
        postpone.setOnAction(new EventHandler<ActionEvent>() {
            
           @Override
           public void handle(ActionEvent event) {
               Movie movie = tableView.getSelectionModel().getSelectedItem();
               model.setPlayDatetToday(movie);
           }
       });


        contexMenu.getItems().addAll(delete, play, postpone);
    }    

    
    
}
