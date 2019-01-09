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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import privatemoviecollection.BE.Movie;

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
    private List<Movie> movies;
    private GridPane movieGrid;
    private Movie activeMovie;

    @FXML
    private ImageView imegePreview;
    @FXML
    private Label title;
    @FXML
    private Label Year;
    @FXML
    private Label genre;
    @FXML
    private Label director;
    @FXML
    private Label actors;
    @FXML
    private Label summery;
    @FXML
    private Label rating;
    @FXML
    private Label IMDbRating;
    @FXML
    private ScrollPane scrollpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        movies = new ArrayList<>();

        // create new constraints for columns and set their percentage
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.NEVER);
        
        columnConstraints.setMaxWidth(180.0);
        columnConstraints.setMinWidth(180.0);
        columnConstraints.setPrefWidth(180.0);
        columnConstraints.setHalignment(HPos.LEFT);
        
        
        // create new constraints for row and set their percentage
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.NEVER);
        
        rowConstraints.setMaxHeight(270.0);
        rowConstraints.setPrefHeight(270.0);
        rowConstraints.setMinHeight(270.0);
        rowConstraints.setValignment(VPos.TOP);
        

        // don't set preferred size or anything on gridpane
        GridPane moviegrid = new GridPane();
        moviegrid.getRowConstraints().add(rowConstraints);
        moviegrid.getColumnConstraints().add(columnConstraints);
        

        // suppose your scroll pane id is scrollPane
        scrollpane.setContent(moviegrid);
        scrollpane.setFitToHeight(true);
        scrollpane.setFitToWidth(true);
        ImageView imegeviwe = new ImageView(new Image("resus/test.jpg"));
        imegeviwe.setFitHeight(210);
        imegeviwe.setFitWidth(140);
        ImageView imegeviwe2 = new ImageView(new Image("resus/test.jpg"));
        imegeviwe2.setFitHeight(210);
        imegeviwe2.setFitWidth(140);
        moviegrid.add(imegeviwe, 0, 0);
        moviegrid.add(imegeviwe2, 1, 0);
        
        int col = 0;
        int row = 0;

        for (Movie movie : movies) {
            Image image = new Image(movie.getImageURL());
            ImageView imageview = new ImageView(image);
            imageview.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2) {
                            bringToFront(null);
                            title.setText(movie.getTitle());
                            Year.setText(movie.getYear());
                            genre.setText(movie.getGerne);
                            director.setText(movie.getDirector());
                            actors.setText(movie.getActors());
                            summery.setText(movie.getSummry());
                            rating.setText(Double.toString(movie.getRating()));
                            imegePreview.setImage(new Image(movie.getImageURL()));
                            activeMovie = movie;

                        }
                    }
                }
            });

            movieGrid.add(imageview, col, row);
            col++;

            if (col > 7) {
                col = 0;

                row++;
                movieGrid.addRow(row, (Node) null);
            }

        }

        //genreComBox.getItems().addAll(c)
    }

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
        if (ratingWindowIsOpen) {
            rateWindow.toBack();
            ratingWindowIsOpen = false;

        } else {
            rateWindow.toFront();
            ratingWindowIsOpen = true;
        }

    }

}