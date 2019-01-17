/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.GUI.Model.Model;

/**
 * FXML Controller class
 *
 * @author Christian
 */
//a windiw that opens if there is movies to delete 
public class MoviesToDeleteController implements Initializable {
    //list of movies to delete 
    private ArrayList<Movie> moviestoDelete;
    private Model model;
    private Stage stage;
    @FXML
    private Button deleteAll;

    private ContextMenu contexMenu;
    //the liste view that contains the movies to delete 
    @FXML
    private ListView<Movie> listview;
    //sets the model
    public void setModel(Model model) {
        this.model = model;
    }
    // gets the list of movies to delete and adds them to the listview
    public void setMoviesTodelete(ArrayList<Movie> list) {
        this.moviestoDelete = list;
        listview.getItems().setAll(moviestoDelete);
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;

    }
    //goes through alle the overdo movies and delets them
    @FXML
    public void deleteAllOverDoMovies() {
        for (Movie m : moviestoDelete) {
            model.deleteMovie(m);
            listview.getItems().remove(m);

        }
        stage.close();
    }
    // sets all the movies lastPlayDate to the todays date 
    @FXML
    public void postePoneAllMovies() {
        for (Movie m : moviestoDelete) {
            model.lastePlayDate(m);
            listview.getItems().remove(m);
        }
        stage.close();
    }

    @FXML
    public void cancel() {
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //adds a contexMenu 
        contexMenu = new ContextMenu();
        //a Menu item that lets you delete a movie 
        MenuItem delete = new MenuItem("Delete Movie");
        delete.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Movie movie = listview.getSelectionModel().getSelectedItem();
                model.deleteMovie(movie);
                moviestoDelete.remove(movie);
                listview.getItems().remove(movie);

            }
        });
        // a menu item that sets the latPlayDate to today 
        MenuItem postpone = new MenuItem("reminde me later");
        postpone.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Movie movie = listview.getSelectionModel().getSelectedItem();
                model.lastePlayDate(movie);
                listview.getItems().remove(movie);
                moviestoDelete.remove(movie);

            }
        });
        //adds the menuItems to the contexboks 
        contexMenu.getItems().addAll(delete, postpone);

        listview.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {
                contexMenu.hide();
                contexMenu.show(listview, event.getScreenX(), event.getScreenY());
            }
        });
    }
    
    // set all movies lastPlayDate to today 
    private void postePoneAllMovies(MouseEvent event, Movie movie) {
        for (Movie m : moviestoDelete) {
            model.lastePlayDate(movie);
            stage.close();
        }
    }

    private void deleteAllOverDoMovies(ActionEvent event) {
        deleteAllOverDoMovies();
    }

    private void postePoneAllMovies(ActionEvent event) {
        postePoneAllMovies();
    }

}
