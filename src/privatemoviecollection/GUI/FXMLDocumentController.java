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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
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
import javafx.stage.WindowEvent;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.BE.MovieImage;
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
    private Movie activeMovie;
    private final double COLLUMTHRESHOLD = 170;
    private int collumNum = 6;
    private int col;
    private int row;
    private GridPane moviegrid;
    private ColumnConstraints columnConstraints;
    private RowConstraints rowConstraints;
    private ObservableList<Movie> allMovies;
    private ObservableList<Movie> activeMovies;
    private ArrayList<ImageView> rateListe;
    private ObservableList<Movie> moviesToDelete1;
    private Movie movieClass;
    private Model model;
    private FilteredList<Movie> movieImage;
    private SortedList<Movie> sortedData;
    private ObservableList<Movie> genreMovies;

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
    private Label lblRating;
    @FXML
    private Label IMDbRating;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private MenuItem aboutTab;
    @FXML
    private AnchorPane anchorGrid;
    @FXML
    private Label runtime;
    @FXML
    private TextField searchBar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            model = new Model();
            genreComBox.getItems().setAll(model.getAllgenres());
            movies = model.getAllMovies();
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

        contexMenu = new ContextMenu();

        MenuItem delete = new MenuItem("Delete Movie");
        delete.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                model.deleteMovie(movieClass);

            }
        });

        /**
         * Right click function with a menu item with change info
         */
        MenuItem edit = new MenuItem("Change info");
        edit.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                try
                {
                    //sets which window to open
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/movieInfo.fxml"));

                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setAlwaysOnTop(true);
                    stage.setResizable(false);
                    stage.setTitle("Change info");
                    stage.setScene(new Scene(root));

                    stage.show();
                    MovieInfoController controller = loader.getController();
                    controller.setModel(model);
                    controller.setStage(stage);
                    controller.setMovie(activeMovie);
                } catch (IOException ex)
                {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        MenuItem play = new MenuItem("Play Movie");
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                play(null);
            }
        });

        MenuItem deleteGenre = new MenuItem("Delete Genre");
        deleteGenre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteCategoryAction();
            }
        });

        MenuItem addGenre = new MenuItem("Add Genre");
        addGenre.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //model.addGenre(;

            }
        });

        contexMenu.getItems().addAll(play, addGenre, edit, delete, deleteGenre);
        genreMovies = FXCollections.observableArrayList();
        movieImage = new FilteredList(genreMovies, p -> true);
        searchBarMovie();

        genreMovies.addAll(movies);


        //adds rate imeges to rateListe
        rateListe = new ArrayList<>();
        rateListe.add(rate1);
        rateListe.add(rate2);
        rateListe.add(rate3);
        rateListe.add(rate4);
        rateListe.add(rate5);
        rateListe.add(rate6);
        rateListe.add(rate7);
        rateListe.add(rate8);
        rateListe.add(rate9);
        rateListe.add(rate10);

        // create new constraints for columns and set their percentage
        columnConstraints = new ColumnConstraints();
        columnConstraints.setHgrow(Priority.NEVER);

        columnConstraints.setMaxWidth(140.0);
        columnConstraints.setMinWidth(140.0);
        columnConstraints.setPrefWidth(140.0);
        columnConstraints.setHalignment(HPos.RIGHT);

        // create new constraints for row and set their percentage
        rowConstraints = new RowConstraints();
        rowConstraints.setVgrow(Priority.NEVER);

        rowConstraints.setMaxHeight(210.0);
        rowConstraints.setPrefHeight(210.0);
        rowConstraints.setMinHeight(210.0);
        rowConstraints.setValignment(VPos.TOP);

        // don't set preferred size or anything on gridpane
        moviegrid = new GridPane();
        moviegrid.getRowConstraints().add(rowConstraints);
        moviegrid.getColumnConstraints().add(columnConstraints);
        moviegrid.setHgap(30);
        moviegrid.setVgap(50);
        Insets in = new Insets(10, 10, 10, 10);
        moviegrid.setPadding(in);

        // suppose your scroll pane id is scrollPane
        scrollpane.setFitToHeight(true);
        scrollpane.setFitToWidth(true);
        anchorGrid.getChildren().add(moviegrid);
        ImageView imageviewnull = new ImageView();
        imageviewnull.setImage(null);
        moviegrid.addRow(0, imageviewnull);

        col = 0;
        row = 0;
        ArrayList<Movie> moviesToDelete = new ArrayList<>();
        allMovies = FXCollections.observableArrayList();
        for (Movie movie : movies) {



            movie.getImageview().setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2) {
                            bringToFront(null);
                            title.setText(movie.getMovieTitle());
                            Year.setText("Year: " + movie.getYear());
                            ArrayList<String> genres = movie.getGenres();
                            String allGenres = "Genres: ";
                            for (int i = 0; i < genres.size(); i++) {
                                allGenres += genres.get(i) + ", ";
                                if (i >= genres.size() - 2) {
                                    allGenres += genres.get(i + 1);
                                    i++;
                                }
                            }
                            genre.setText(allGenres);
                            director.setText("Director: " + movie.getDirector());
                            actors.setText("Actors: " + movie.getActors());
                            summery.setText("Summary: " + movie.getPlot());
                            runtime.setText("runtime: " + movie.getRuntime());
                            lblRating.setText(String.valueOf(movie.getRating()));
                            IMDbRating.setText(movie.getImdb_rating());
                            imegePreview.setImage(new Image(movie.getPoster()));
                            imegePreview.setFitHeight(210);
                            imegePreview.setFitWidth(140);
                            activeMovie = movie;
                            rateMovie(movie.getRating());

                        }
                    }
                }

            });

            movie.getImageview().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {
                    contexMenu.hide();
                    contexMenu.show(movie.getImageview(), event.getScreenX(), event.getScreenY());
                    activeMovie = movie;
                }
            });
            //uses the is the isDoDateOver methot on all the movies if it is thrue adds the movie to the moviesToDelete list
            if (model.isDoDateOver(movie)) {
                moviesToDelete.add(movie);
            }
            allMovies.add(movie);

            //model.getlastView()
        }
        activeMovies = FXCollections.observableArrayList();
        activeMovies.addAll(allMovies);

        reloadGrid();
        //opens a new window if there is movies on the movisToDelerte list
        if (moviesToDelete.size() > 0) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/MoviesToDelete.fxml"));

                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setAlwaysOnTop(true);
                stage.setResizable(false);
                stage.setTitle("Movies to delete");
                stage.setScene(new Scene(root));
                //updates the movieGrid when the window closes
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        reloadGrid();
                    }
                });

                stage.show();
                MoviesToDeleteController controller = loader.getController();
                controller.setModel(model);
                controller.setStage(stage);
                controller.setMoviesTodelete(moviesToDelete);

            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        window.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resizeGrit(newValue.doubleValue());
            }
        });



    }

    private void reloadGrid() {
        anchorGrid.getChildren().clear();
        moviegrid = new GridPane();
        moviegrid.getRowConstraints().add(rowConstraints);
        moviegrid.getColumnConstraints().add(columnConstraints);
        moviegrid.setHgap(30);
        moviegrid.setVgap(50);
        Insets in = new Insets(10, 10, 10, 10);
        moviegrid.setPadding(in);
        anchorGrid.getChildren().add(moviegrid);

        col = 0;
        row = 0;
        for (Movie image : genreMovies) {

            moviegrid.add(image.getImageview(), col, row);
            col++;
            if (col > collumNum - 1) {
                col = 0;
                row++;
                ImageView imnull = new ImageView();
                imnull.setImage(null);
                moviegrid.addRow(row, imnull);
            }
        }

    }

    private void resizeGrit(double width) {
        if (width > COLLUMTHRESHOLD * (collumNum + 1) - 20) {
            collumNum++;
            reloadGrid();
            resizeGrit(width);
        }
        if (width < COLLUMTHRESHOLD * (collumNum) - 20) {
            collumNum--;
            reloadGrid();
            resizeGrit(width);
        }

    }
    // brings the movie info AnchorPane to the front of the viw en mose clik
    private void bringToFront(MouseEvent event) {
        stacPopUp.toFront();
        popUd.toFront();

    }

    @FXML
    private void play(MouseEvent event) {
        model.getMediaPlayer(activeMovie);
        model.lastePlayDate(activeMovie);

    }
    //a transparent AnchorPane that when you clic outsaid the movie info window sends the movie grid to the front
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
            stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.setTitle("Add Movie");
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
    private void addGenreAndDeleteGenre(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/AddnDeleteGenre.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.setTitle("Add or Delete Category");
            stage.setScene(new Scene(root));
            stage.show();
            AddnDeleteGenreController controller = loader.getController();
            controller.setModel(model);
            controller.setStage(stage);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    //opens ore closes the ratinf window 
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


    //sets the imege of the rate icon acrording to its rating
    private void rateMovie(int rating) {
        model.setRating(activeMovie, rating);
        activeMovie.setRating(rating);
        lblRating.setText(String.valueOf(rating));
        Image image1 = new Image("resus/icons8-christmas-star-96.png");
        Image image2 = new Image("resus/icons8-star-96.png");

        for (int i = 0; i < rateListe.size(); i++) {

            if (i <= rating - 1) {
                rateListe.get(i).setImage(image1);
            }
            if (i > rating - 1) {
                rateListe.get(i).setImage(image2);
            }

        }

    }
    // rates the movie 1 strar
    @FXML
    private void rate_1(MouseEvent event) {
        int rating = 1;

        rateMovie(1);

    }
    //// rates the movie 2 strar
    @FXML
    private void rate_2(MouseEvent event) {
        int rating = 2;
        model.setRating(activeMovie, rating);
        rateMovie(2);

    }

    @FXML
    private void rate_3(MouseEvent event) {
        int rating = 3;
        model.setRating(activeMovie, rating);
        rateMovie(3);

    }

    @FXML
    private void rate_4(MouseEvent event) {
        int rating = 4;
        model.setRating(activeMovie, rating);
        rateMovie(4);

    }

    @FXML
    private void rate_5(MouseEvent event) {
        int rating = 5;
        model.setRating(activeMovie, rating);
        rateMovie(5);

    }

    @FXML
    private void rate_6(MouseEvent event) {
        int rating = 6;
        model.setRating(activeMovie, rating);
        rateMovie(6);

    }

    @FXML
    private void rate_7(MouseEvent event) {
        int rating = 7;
        model.setRating(activeMovie, rating);
        rateMovie(7);

    }

    @FXML
    private void rate_8(MouseEvent event) {
        int rating = 8;
        model.setRating(activeMovie, rating);
        rateMovie(8);

    }

    @FXML
    private void rate_9(MouseEvent event) {
        int rating = 9;
        model.setRating(activeMovie, rating);
        rateMovie(9);

    }

    @FXML
    private void rate_10(MouseEvent event) {
        int rating = 10;
        model.setRating(activeMovie, rating);
        rateMovie(10);
    }

    @FXML
    private void aboutTab(ActionEvent event) {
        Alert aboutUs = new Alert(Alert.AlertType.INFORMATION);

        aboutUs.setTitle("About Us");
        aboutUs.setHeaderText("Disaster Respone Unit");
        aboutUs.setContentText("We made this program with love and care :) ");

        aboutUs.showAndWait();
    }

    @FXML
    private void sortByGenre(Event event) {
        String genre = genreComBox.getSelectionModel().getSelectedItem();
        ObservableList<Movie> movimg = FXCollections.observableArrayList();


        movimg = model.getMoviesByGenre(genre);
        genreMovies.setAll(movimg);

//        for (MovieImage movie : allMovies) {
//            if (movie.getMovie().getGenres().contains(genre)) {
//                movimg.add(movie);
//            }
//
//        }
//
//        activeMovies = movimg;



        reloadGrid();
      }

    private void searchBarMovie() {

        searchBar.textProperty().addListener((observable, oldValue, newValue)
                -> {
            movieImage.setPredicate(movie
                    -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                System.out.println("mojn");
                if (movie.getMovieTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (movie.getActors().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (movie.getDirector().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (movie.getYear().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                for (String genre1 : movie.getGenres()) {
                    if (genre1.toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });
        sortedData = new SortedList<>(movieImage); // Wrap the FilteredList in a SortedList.
        genreMovies.setAll(movieImage);
    }


    @FXML
    private void searchBarAction(KeyEvent event) {
        reloadGrid();
    }

    private void deleteCategoryAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/DeleteCategory.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete genre from movie");
            stage.setScene(new Scene(root));

            stage.show();

            DeleteCategoryController control = loader.getController();
            control.setModel(model);
            control.setStage(stage);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }










}
