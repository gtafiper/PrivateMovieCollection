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
    private ObservableList<MovieImage> allMovieImages;
    private ObservableList<MovieImage> activeMovieImages;

    private ObservableList<Movie> moviesToDelete1;
    private Movie movieClass;
    private Model model;
    private FilteredList<MovieImage> movieImage;
    private SortedList<MovieImage> sortedData;

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
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            model = new Model();
            genreComBox.getItems().setAll(model.getAllgenres());
            movies = model.getAllMovies();
        } catch (IOException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!model.checkMediaPlayerPath())
        {
            new MovieCollectionException("Error", "Couldn't find Windows Media Player", "Please navigate to wmplayer.exe");
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Executables", "*.exe"));
            fc.setTitle("Open Windows Mediaplayer");
            File file = fc.showOpenDialog(null);
            model.setMediaPlayerPath(file);
        }

        contexMenu = new ContextMenu();

        MenuItem delete = new MenuItem("Delete Movie");
        delete.setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {

                model.deleteMovie(movieClass);

            }
        });

        MenuItem play = new MenuItem("Play Movie");
        play.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                throw new UnsupportedOperationException(""); //To change body of generated methods, choose Tools | Templates.
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
        addGenre.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                //model.addGenres(movieClass);

            }
        });

        contexMenu.getItems().addAll(play, addGenre, delete, deleteGenre);

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
        allMovieImages = FXCollections.observableArrayList();
        for (Movie movie : movies)
        {

            MovieImage image = new MovieImage(movie);

            image.getImageview().setOnMouseClicked(new EventHandler<MouseEvent>()
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
                            for (int i = 0; i < genres.size(); i++)
                            {
                                allGenres += genres.get(i) + ", ";
                                if (i >= genres.size() - 2)
                                {
                                    allGenres += genres.get(i + 1);
                                    i++;
                                }
                            }
                            genre.setText(allGenres);
                            director.setText("Director: " + movie.getDirector());
                            actors.setText("Actors: " + movie.getActors());
                            summery.setText("Summary: " + movie.getPlot());
                            runtime.setText("runtime: " + movie.getRuntime());
                            rating.setText(String.valueOf(movie.getRating()));
                            IMDbRating.setText(movie.getImdb_rating());
                            imegePreview.setImage(new Image(movie.getPoster()));
                            imegePreview.setFitHeight(210);
                            imegePreview.setFitWidth(140);
                            activeMovie = movie;

                        }
                    }
                }

            });

            image.getImageview().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>()
            {

                @Override
                public void handle(ContextMenuEvent event)
                {
                    contexMenu.hide();
                    contexMenu.show(image.getImageview(), event.getScreenX(), event.getScreenY());

                }
            });

            if (isDoDateOver(movie))
            {
                moviesToDelete.add(movie);
            }
            allMovieImages.add(image);

            //model.getlastView()
        }
        activeMovieImages = FXCollections.observableArrayList();
        activeMovieImages.addAll(allMovieImages);
        
        reloadGrid();

        if (moviesToDelete.size() > 0)
        {

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("privatemoviecollection/GUI/MoviesToDelete.fxml"));

                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Movies to delete");
                stage.setScene(new Scene(root));
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

       searchBarMovie(); 

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
        for (MovieImage image : activeMovieImages)
        {

            moviegrid.add(image.getImageview(), col, row);
            col++;
            if (col > collumNum - 1)
            {
                col = 0;
                row++;
                ImageView imnull = new ImageView();
                imnull.setImage(null);
                moviegrid.addRow(row, imnull);
            }
        }

    }

    private void resizeGrit(double width)
    {
        if (width > COLLUMTHRESHOLD * (collumNum + 1) - 20)
        {
            collumNum++;
            reloadGrid();
            resizeGrit(width);
        }
        if (width < COLLUMTHRESHOLD * (collumNum) - 20)
        {
            collumNum--;
            reloadGrid();
            resizeGrit(width);
        }

    }

    private void bringToFront(MouseEvent event)
    {
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
            stage.setTitle("Create Playlist");
            stage.setScene(new Scene(root));
            stage.show();
            AddMovieController controller = loader.getController();
            controller.setModel(model);
            controller.setStage(stage);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public boolean isDoDateOver(Movie movie)
    {

        if (movie.getLastView() != null)
        {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd MM yyyy");

            Date date = new Date();
            dateformat.format(date);
            Date lastPlayDate = new Date(); // catch in model
            try
            {
                lastPlayDate = dateformat.parse(movie.getLastView());
            } catch (ParseException ex)
            {

            }

            long doDate = date.getTime() - lastPlayDate.getTime();

            if (doDate >= 730)
            /*&& (movie.getRating() => 6)*/
            {
                moviesToDelete1.add(movie);
                return true;

            }
        }
        return false;

    }

    @FXML
    private void addGenre(ActionEvent event)
    {
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


    @FXML
    private void aboutTab(ActionEvent event)
    {
       Alert aboutUs = new Alert(Alert.AlertType.INFORMATION);
                
        aboutUs.setTitle("About Us");
        aboutUs.setHeaderText("Disaster Respone Unit");
        aboutUs.setContentText("We made this program with love and care :) ");
        
        aboutUs.showAndWait();
    }
    
    @FXML
    private void sortByGenre(Event event) {
        String genre = genreComBox.getSelectionModel().getSelectedItem();
        ObservableList<MovieImage> movimg = FXCollections.observableArrayList();
        

        for (MovieImage movie : allMovieImages) {
            if (movie.getMovie().getGenres().contains(genre)) {
                movimg.add(movie);
            }

        }

        activeMovieImages = movimg;
        
        searchBarMovie();
        
        reloadGrid();
      }

      private void searchBarMovie()
      {
          movieImage = new FilteredList(activeMovieImages, p -> true);
          searchBar.textProperty().addListener((observable, oldValue, newValue)
                  ->
          {
              movieImage.setPredicate(movie
                      ->
              {
                  if (newValue == null || newValue.isEmpty())
                  {
                      return true;
                  }
                  String lowerCaseFilter = newValue.toLowerCase();

                  if (movie.getMovie().getMovieTitle().toLowerCase().contains(lowerCaseFilter))
                  {
                      return true;
                  } else if (movie.getMovie().getActors().toLowerCase().contains(lowerCaseFilter))
                  {
                      return true;
                  } else if (movie.getMovie().getDirector().toLowerCase().contains(lowerCaseFilter))
                  {
                      return true;
                  } else if (movie.getMovie().getYear().toLowerCase().contains(lowerCaseFilter))
                  {
                      return true;
                  }

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
          activeMovieImages = movieImage;
    }


    @FXML
    private void searchBarAction(KeyEvent event)
    {
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
