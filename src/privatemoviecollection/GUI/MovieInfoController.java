/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.GUI.Model.Model;

/**
 * FXML Controller class
 *
 * @author Bruger
 */
public class MovieInfoController implements Initializable
{
    @FXML
    private TextField txtMovieTitle;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnChangeFile;
    @FXML
    private TextField txtImdb;
    @FXML
    private TextField txtYear;
    @FXML
    private TextField txtActor;
    @FXML
    private TextField txtDirector;
    @FXML
    private TextField txtFile;
    @FXML
    private TextArea txtPlot;
    @FXML
    private TextField txtRuntime;
    
    private Movie movieInfo;
    private Stage stage;
    private Model pmcModel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }    

    /**
     * get the new info on the movie and replace with the old 
     * @param event 
     */
    @FXML
    private void btnChangestuffOk(ActionEvent event)
    {
        movieInfo.setMovieTitle(txtMovieTitle.getText());
        movieInfo.setActors(txtActor.getText());
        movieInfo.setDirector(txtDirector.getText());
        movieInfo.setFilePath(txtFile.getText());
        movieInfo.setImdb_rating(txtImdb.getText());
        movieInfo.setPlot(txtPlot.getText());
        movieInfo.setRuntime(txtRuntime.getText());
        movieInfo.setYear(txtYear.getText());
        
        pmcModel.updateMovie(movieInfo);
        
        stage.close();
    }

    /**
     * close the window without any changes
     * @param event 
     */
    @FXML
    private void btnChangeCancel(ActionEvent event)
    {
        stage.close();
    }

    /**
     * change the destination of where the file is
     * @param event 
     */
    @FXML
    private void btnChangeFileDest(ActionEvent event)
    {
        FileChooser efc = new FileChooser();
        efc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mpeg4 files", "*.mp4","*.mpeg4"));
        File addedFile = efc.showOpenDialog(null);
        
        txtFile.setText(addedFile.getAbsolutePath());
    }

    /**
     * set the information of the movie into the textviews
     * @param movie 
     */
    public void setMovie(Movie movie)
    {
        this.movieInfo = movie;
        
        txtMovieTitle.setText(movieInfo.getMovieTitle());
        txtActor.setText(movieInfo.getActors());
        txtDirector.setText(movieInfo.getDirector());
        txtFile.setText(movieInfo.getFilePath());
        txtImdb.setText(movieInfo.getImdb_rating());
        txtPlot.setText(movieInfo.getPlot());
        txtRuntime.setText(movieInfo.getRuntime());
        txtYear.setText(movieInfo.getYear());
    }
    
    public void setModel(Model model)
    {
        this.pmcModel = model;
    }
    
    public void setStage(Stage stage)
    {
        this.stage = stage;
    }
    
}
