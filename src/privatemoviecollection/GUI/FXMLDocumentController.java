/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.BLL.Exception.MovieCollectionException;
import privatemoviecollection.GUI.Model.Model;

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
    @FXML
    private AnchorPane window;
    private ContextMenu contexMenu;

    private boolean ratingWindowIsOpen = false;
    private List<Movie> movies;
    private GridPane movieGrid;
    private Movie activeMovie;
    private final double COLLUMTHRESHOLD = 180;
    private int collumNum = 7;
    private int col;
    private int row;
    private GridPane moviegrid;

    Movie movieClass;
    Model model;

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
    @FXML
    private MenuItem aboutTab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = new Model();
            genreComBox.getItems().setAll(model.getAllgenres());
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!model.checkMediaPlayerPath()) {
            new MovieCollectionException("Error", "Couldn't find Windows Media Player", "Please navigate to wmplayer.exe");
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Executables", "*.exe"));
            fc.setTitle("Open Windows Mediaplayer");
            File file = fc.showOpenDialog(null);
            model.setMediaPlayerPath(file);
        }
        movies = new ArrayList<>();
        contexMenu = new ContextMenu();

        MenuItem delete = new MenuItem("Delete Movie");
        delete.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                try {
                    model.deleteMovie(movieClass);
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        MenuItem play = new MenuItem("Play Movie");
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                throw new UnsupportedOperationException(""); //To change body of generated methods, choose Tools | Templates.
            }
        });

        MenuItem addGengre = new MenuItem("Add Genre");
        addGengre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    model.addGenres(movieClass);
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                throw new UnsupportedOperationException(""); //To change body of generated methods, choose Tools | Templates.
            }
        });

        contexMenu.getItems().addAll(delete, play, addGengre);

        // create new constraints for columns and set their percentage
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.NEVER);

        columnConstraints.setMaxWidth(140.0);
        columnConstraints.setMinWidth(140.0);
        columnConstraints.setPrefWidth(140.0);
        columnConstraints.setHalignment(HPos.RIGHT);

        // create new constraints for row and set their percentage
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.NEVER);

        rowConstraints.setMaxHeight(210.0);
        rowConstraints.setPrefHeight(210.0);
        rowConstraints.setMinHeight(210.0);
        rowConstraints.setValignment(VPos.TOP);

        // don't set preferred size or anything on gridpane
        GridPane moviegrid = new GridPane();
        moviegrid.getRowConstraints().add(rowConstraints);
        moviegrid.getColumnConstraints().add(columnConstraints);
        moviegrid.setHgap(30);
        moviegrid.setVgap(50);
        Insets in = new Insets(10, 10, 10, 10);
        moviegrid.setPadding(in);

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

        imegeviwe.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {
                contexMenu.hide();
                contexMenu.show(imegeviwe, event.getScreenX(), event.getScreenY());

            }
        });

        col = 0;
        row = 0;

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
                            rating.setText(String.valueOf(movie.getRating()));
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

            //model.getlastView()

        }

        window.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() > COLLUMTHRESHOLD * collumNum) {
                    collumNum++;
                    System.out.println("hej");
                }
            }
        });

    }

    private void setUpMovieAction(ImageView imageview, Movie movie) {
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
                        rating.setText(String.valueOf(movie.getRating()));
                        imegePreview.setImage(new Image(movie.getImageURL()));
                        activeMovie = movie;

                    }
                }
            }
        });
    }

    private void reloadGrid() {

        int collumcount = 0;
        int rowcount = 0;
        for (int i = 0; i < moviegrid.getChildren().size(); i++) {

            if (movies.size() >= i) {
                ImageView imageV = (ImageView) moviegrid.getChildren().get(i);
                imageV.setImage(new Image(movies.get(i).getImageURL()));
                setUpMovieAction(imageV, movies.get(i));

            }

            if (movies.size() < i) {
                moviegrid.getChildren().get(i);
                ImageView imageV = (ImageView) moviegrid.getChildren().get(i);
                imageV.setImage(null);
                imageV.setOnMouseClicked(null);

            }

            collumcount++;
            if (collumcount == collumNum) {
                rowcount++;
                collumcount = 0;
            }

        }
    }

    private void resizeGrit(double width) {
        if (width > COLLUMTHRESHOLD * collumNum) {
            movieGrid.addColumn(collumNum, new ImageView());
            collumNum++;
            reloadGrid();
            resizeGrit(width);
        }
        if (width < COLLUMTHRESHOLD * collumNum - 1) {
            movieGrid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == collumNum);
            collumNum--;
            reloadGrid();
            resizeGrit(width);
        }

    }
//
//    private void resizeGrit(double width)
//    {
//        if (width > COLLUMTHRESHOLD * collumNum)
//        {
//            movieGrid.addColumn(collumNum, null);
//            collumNum++;
//            movieGrid.addColumn(collumNum, null);
//            for (int i = 0; i < row; i++)
//            {
//                getNodeFromGridPane(movieGrid, 0, i);
//            }
//            resizeGrit(width);
//        }
//        if (width < COLLUMTHRESHOLD * collumNum - 1)
//        {
//            collumNum--;
//            //fjern en collum og tilpas film
//            resizeGrit(width);
//        }
//
//    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    private void bringToFront(MouseEvent event) {
        stacPopUp.toFront();
        popUd.toFront();

    }

    @FXML
    private void play(MouseEvent event) throws IOException, SQLException
    {
        model.getMediaPlayer(activeMovie);
        model.lastePlayDate(activeMovie);
    }
    @FXML
    private void bringToBack(MouseEvent event) {
        stacPopUp.toBack();
    }

    @FXML
    private void addMovie(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/AddMovie.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Create Playlist");
            stage.setScene(new Scene(root));
            stage.show();
            AddMovieController controller = loader.getController();
            controller.setModel(model);
            controller.setStage(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void addGenre(ActionEvent event) {
    }

    @FXML
    private void rencentlyWatched(ActionEvent event) {
    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
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

    @FXML
    private void addGenre(MouseEvent event) {
    }

    @FXML
    private void aboutTab(ActionEvent event) {

    }

}
