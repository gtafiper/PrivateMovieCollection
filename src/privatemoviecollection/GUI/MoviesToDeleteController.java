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
public class MoviesToDeleteController implements Initializable {

    private ArrayList<Movie> moviestoDelete;
    private Model model;
    private Stage stage;
    @FXML
    private Button deleteAll;

    private ContextMenu contexMenu;
    @FXML
    private ListView<Movie> listview;

    public void setModel(Model model) {
        this.model = model;
    }

    public void setMoviesTodelete(ArrayList<Movie> list) {
        this.moviestoDelete = list;
        listview.getItems().setAll(moviestoDelete);
    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    @FXML
    public void deleteAllOverDoMovies() {
        for (Movie m : moviestoDelete) {
            model.deleteMovie(m);
            moviestoDelete.clear();

        }
        stage.close();
    }

    @FXML
    public void postePoneAllMovies() {
        for (Movie m : moviestoDelete) {
            model.lastePlayDate(m);
            moviestoDelete.clear();
        }
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

        contexMenu = new ContextMenu();

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

        contexMenu.getItems().addAll(delete, postpone);

        listview.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {
                contexMenu.hide();
                contexMenu.show(listview, event.getScreenX(), event.getScreenY());
            }
        });
    }

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
