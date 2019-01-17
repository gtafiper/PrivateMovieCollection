/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
public class FXMLDocumentController implements Initializable
{

    private ContextMenu contexMenu;
    private boolean ratingWindowIsOpen = false;
    private MovieImage activeMovie;
    private final double COLLUMTHRESHOLD = 170;
    private int collumNum = 6;
    private int col;
    private int row;
    private GridPane moviegrid;
    private ColumnConstraints columnConstraints;
    private RowConstraints rowConstraints;
    private ArrayList<ImageView> rateListe;
    private Model model;
    private FilteredList<MovieImage> movieImage;
    private SortedList<MovieImage> sortedData;
    private ObservableList<MovieImage> genreMovies;
    private ObservableList<MovieImage> allMovies;
    private ObservableList<MovieImage> moviesToDelete;
    private List<Movie> movies;
    private Menu deleteGenre;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        model = new Model();
        movies = model.getAllMovies();

        ValidateMediaPlayer();

        createContextMenu();

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

        setupGridpane();

        // start Row and Collum Index
        col = 0;
        row = 0;

        moviesToDelete = FXCollections.observableArrayList();
        allMovies = FXCollections.observableArrayList();

        for (Movie movie : movies)
        {
            MovieImage movieImage = new MovieImage(movie);
            setUpMouseEvents(movieImage, movie);
            //uses the is the isDoDateOver methot on all the movies if it is thrue adds the movie to the moviesToDelete list
            if (model.isDoDateOver(movie))
            {
                moviesToDelete.add(movieImage);
            }
            allMovies.add(movieImage);

            //model.getlastView()
        }

        model.createGenreMoviePairs(allMovies);
        genreComBox.getItems().setAll(model.getAllHashGenres());
        genreMovies = FXCollections.observableArrayList();
        genreMovies.addAll(allMovies);
        movieImage = new FilteredList(genreMovies, p -> true);
        searchBarMovie();

        reloadGrid();
        //opens a new window if there is movies on the movisToDelerte list
        if (moviesToDelete.size() > 0)
        {

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/MoviesToDelete.fxml"));

                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setAlwaysOnTop(true);
                stage.setResizable(false);
                stage.setTitle("Movies to delete");
                stage.setScene(new Scene(root));
                //updates the movieGrid when the window closes
                stage.setOnCloseRequest(new EventHandler<WindowEvent>()
                {
                    @Override
                    public void handle(WindowEvent event)
                    {
                        reloadGrid();
                    }
                });

                stage.show();
                MoviesToDeleteController controller = loader.getController();
                controller.setModel(model);
                controller.setStage(stage);
                controller.setMoviesTodelete(moviesToDelete);

            } catch (IOException ex)
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        window.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                resizeGrit(newValue.doubleValue());
            }
        });

    }

    private void setupGridpane()
    {
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
    }

    private void createContextMenu()
    {
        contexMenu = new ContextMenu();

        MenuItem delete = new MenuItem("Delete Movie");
        delete.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {

                model.deleteMovie(activeMovie);
                allMovies.remove(activeMovie.getMovie());
                genreMovies.remove(activeMovie);
                reloadGrid();

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
                    controller.setMovie(activeMovie.getMovie());
                } catch (IOException ex)
                {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        MenuItem play = new MenuItem("Play Movie");
        play.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                play(null);
            }
        });

        Menu addGenre = new Menu("Add to Genre:  ");

        for (String genre : model.getAllGenres())
        {

            MenuItem genreItem = new MenuItem(genre);
            genreItem.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {

                    model.addMovieToNewCategory(activeMovie, genre);
                    genreComBox.getItems().setAll(model.getAllHashGenres());
                }
            });
            addGenre.getItems().add(genreItem);

        }

        deleteGenre = new Menu("Delete Genre:  ");

        contexMenu.getItems().addAll(play, addGenre, edit, delete, deleteGenre);
    }

    private void ValidateMediaPlayer()
    {
        if (!model.checkMediaPlayerPath())
        {
            new MovieCollectionException("Error", "Couldn't find Windows Media Player", "Please navigate to wmplayer.exe");
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Executables", "*.exe"));
            fc.setTitle("Open Windows Mediaplayer");
            File file = fc.showOpenDialog(null);
            model.setMediaPlayerPath(file);
        }
    }

    private void setUpMouseEvents(MovieImage movieImage1, Movie movie)
    {
        movieImage1.getImageview().setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY))
                {
                    if (mouseEvent.getClickCount() == 2)
                    {
                        bringToFront(null);
                        title.setText(movie.getMovieTitle());
                        Year.setText("Year: " + movie.getYear());
                        ArrayList<String> genres = movie.getGenres();
                        String allGenres = "Genres: ";

                        if (genres.size() == 1)
                        {
                            allGenres += genres.get(0);
                        } else if (genres.size() > 1)
                        {
                            for (int i = 0; i < genres.size(); i++)
                            {
                                allGenres += genres.get(i) + ", ";
                                if (i >= genres.size() - 2)
                                {
                                    allGenres += genres.get(i + 1);
                                    i++;
                                }
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
                        activeMovie = movieImage1;
                        rateMovie(movie.getRating());

                    }
                }
            }

        });
        movieImage1.getImageview().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>()
        {

            @Override
            public void handle(ContextMenuEvent event)
            {
                contexMenu.hide();
                contexMenu.show(movieImage1.getImageview(), event.getScreenX(), event.getScreenY());
                activeMovie = movieImage1;
                deleteGenre.getItems().clear();
                for (String genre : activeMovie.getMovie().getGenres())
                {

                    MenuItem genreItem = new MenuItem(genre);
                    genreItem.setOnAction(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            model.deleteCategoryFromMovie(activeMovie, genre);
                            sort(null);
                            genreComBox.getItems().setAll(model.getAllHashGenres());

                        }
                    });
                    deleteGenre.getItems().add(genreItem);

                }
            }
        });
    }

    private void reloadGrid()
    {
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
        for (MovieImage movie : movieImage)
        {

            moviegrid.add(movie.getImageview(), col, row);
            col++;
            if (col > collumNum - 1)
            {
                col = 0;
                row++;
            }
        }

    }

    private void resizeGrit(double width)
    {
        if (width > COLLUMTHRESHOLD * (collumNum + 1) - 12)
        {
            collumNum++;
            reloadGrid();
            resizeGrit(width);
        }
        if (width < COLLUMTHRESHOLD * (collumNum) - 12)
        {
            collumNum--;
            reloadGrid();
            resizeGrit(width);
        }

    }

    // brings the movie info AnchorPane to the front of the viw en mose clik
    private void bringToFront(MouseEvent event)
    {
        stacPopUp.toFront();
        popUd.toFront();

    }

    @FXML
    private void play(MouseEvent event)
    {
        model.getMediaPlayer(activeMovie.getMovie());
        model.lastePlayDate(activeMovie.getMovie());

    }

    //a transparent AnchorPane that when you clic outsaid the movie info window sends the movie grid to the front
    @FXML
    private void bringToBack(MouseEvent event)
    {
        stacPopUp.toBack();
    }

    @FXML
    private void addMovie(ActionEvent event)
    {
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/AddMovie.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();
            AddMovieController controller = loader.getController();
            stage.setResizable(false);
            stage.setTitle("Add Movie");
            stage.setScene(new Scene(root));
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent event)
                {

                    try
                    {
                        MovieImage mi = controller.getNewMovie();
                        setUpMouseEvents(mi, mi.getMovie());

                    } catch (Exception e)
                    {
                        System.out.println("no new movie");
                    }
                    sort(null);
                    reloadGrid();
                }
            });

            controller.setModel(model);
            controller.setStage(stage);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @FXML
    private void addGenreAndDeleteGenre(ActionEvent event)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/AddnDeleteGenre.fxml"));

            Parent root = loader.load();
            Stage stage = new Stage();

            stage.setResizable(false);
            stage.setTitle("Add or Delete Category");
            stage.setScene(new Scene(root));

            AddnDeleteGenreController controller = loader.getController();

            controller.setModel(model);
            controller.setStage(stage);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>()
            {
                @Override
                public void handle(WindowEvent event)
                {
                    System.out.println("hi");
                    createContextMenu();
                }
            });

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @FXML
    private void rencentlyWatched(ActionEvent event)
    {

    }

    @FXML
    private void exit(ActionEvent event)
    {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void deleteMovie(ActionEvent event)
    {
    }

    //opens ore closes the ratinf window
    @FXML
    private void rate(MouseEvent event)
    {
        if (ratingWindowIsOpen)
        {
            rateWindow.toBack();
            ratingWindowIsOpen = false;

        } else
        {
            rateWindow.toFront();
            ratingWindowIsOpen = true;
        }

    }

    //sets the imege of the rate icon acrording to its rating
    private void rateMovie(int rating)
    {
        model.setRating(activeMovie.getMovie(), rating);
        activeMovie.getMovie().setRating(rating);
        lblRating.setText(String.valueOf(rating));
        Image image1 = new Image("resus/icons8-christmas-star-96.png");
        Image image2 = new Image("resus/icons8-star-96.png");

        for (int i = 0; i < rateListe.size(); i++)
        {

            if (i <= rating - 1)
            {
                rateListe.get(i).setImage(image1);
            }
            if (i > rating - 1)
            {
                rateListe.get(i).setImage(image2);
            }

        }

    }

    // rates the movie 1 strar
    @FXML
    private void rate_1(MouseEvent event)
    {
        int rating = 1;

        rateMovie(1);

    }

    //// rates the movie 2 strar
    @FXML
    private void rate_2(MouseEvent event) {
        rateMovie(2);

    }

    @FXML
    private void rate_3(MouseEvent event) {
        rateMovie(3);

    }

    @FXML
    private void rate_4(MouseEvent event) {
        rateMovie(4);

    }

    @FXML
    private void rate_5(MouseEvent event) {
        rateMovie(5);

    }

    @FXML
    private void rate_6(MouseEvent event) {
        rateMovie(6);

    }

    @FXML
    private void rate_7(MouseEvent event) {
        rateMovie(7);

    }

    @FXML
    private void rate_8(MouseEvent event) {
        rateMovie(8);

    }

    @FXML
    private void rate_9(MouseEvent event) {
      rateMovie(9);

    }

    @FXML
    private void rate_10(MouseEvent event) {
        rateMovie(10);
    }

    @FXML
    private void aboutTab(ActionEvent event)
    {
        Alert aboutUs = new Alert(Alert.AlertType.INFORMATION);

        aboutUs.setTitle("About Us");
        aboutUs.setHeaderText("Disaster Respone Unit");
        aboutUs.setContentText("We made this program with love and care :) ");

        aboutUs.show();
    }

    @FXML
    private void sort(Event event)
    {
        ObservableList<MovieImage> movimg = null;

        String genre = genreComBox.getSelectionModel().getSelectedItem();
        sortByGenre(genre);
    }

    private void sortByGenre(String genre1)
    {
        ObservableList<MovieImage> movimg;
        if (genre1 != null)
        {
            movimg = model.getMoviesByGenre(genre1);
        } else
        {
            movimg = model.getMoviesByGenre(Model.HASH_ALLMOVIES);
        }
        genreMovies.setAll(movimg);
        searchBarMovie();
        reloadGrid();
    }

    /**
     *  a searchbar to search for movie title, actors, director, genre and the year it been make
     */
    private void searchBarMovie() {
        //making a Filteredlist named movieImage and connect the searchbar with the movieImage
        searchBar.textProperty().addListener((observable, oldValue, newValue)
                ->
        {
            movieImage.setPredicate(movie
                    ->
            {
                //if the searchbar is empty show all movies
                if (newValue == null || newValue.isEmpty())
                {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (movie.getMovie().getMovieTitle().toLowerCase().contains(lowerCaseFilter))//Filter the list with movie titles
                {
                    return true;
                } else if (movie.getMovie().getActors().toLowerCase().contains(lowerCaseFilter))//Filter the list with the actors
                {
                    return true;
                } else if (movie.getMovie().getDirector().toLowerCase().contains(lowerCaseFilter)) //Filter the list to the director
                {
                    return true;
                } else if (movie.getMovie().getYear().toLowerCase().contains(lowerCaseFilter)) //filter the list when the movie are made
                {
                    return true;
                }
                //checking the list with genre though
                for (String genre1 : movie.getMovie().getGenres())
                {
                    if (genre1.toLowerCase().contains(lowerCaseFilter))
                    {
                        return true;
                    }
                }
                return false;
            });
        });
        sortedData = new SortedList<>(movieImage); // Wrap the FilteredList in a SortedList.
        genreMovies.setAll(sortedData);
    }

    private void deleteCategoryAction()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/DeleteCategory.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete genre from movie");
            stage.setScene(new Scene(root));

            stage.show();

            DeleteCategoryController control = loader.getController();
            control.setModel(model);
            control.setStage(stage);
        } catch (IOException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void searchBarAction(KeyEvent event)
    {
        sort(null);
        reloadGrid();
    }

}
