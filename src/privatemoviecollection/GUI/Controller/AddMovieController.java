/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import privatemoviecollection.BE.MovieImage;
import privatemoviecollection.GUI.Model.Model;

/**
 * FXML Controller class
 *
 * @author Nijas Hansen
 */
public class AddMovieController implements Initializable
{

    @FXML
    private TextField txtfldFileLocation;
    @FXML
    private TextField txtfldURL;
    @FXML
    private Button runbtn;

    private Model model;
    private String imdbid;
    private File chosenMovie;
    private Stage stage;
    private String moviePath;
    private MovieImage newMovie;
    @FXML
    private Button btnChangeFile;

    public void setModel(Model model)
    {
        this.model = model;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        chosenMovie = new File("");
        
    }

    /**
     * opens the filechooser
     * 
    **/
    @FXML
    private void OpenFileChooser(MouseEvent event)
    {
        try
        {
            FileChooser fc = new FileChooser();
            fc.setTitle("Open Movie Path");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp4 files", "*.mp4", "*.mpeg4"));
            chosenMovie = fc.showOpenDialog(null);
            String[] split = chosenMovie.getPath().split("src");
            moviePath = "src" + split[1];
            txtfldFileLocation.setText(moviePath);
        } catch (Exception e)
        {
            System.out.println("no file was chosen");
        }

    }

    /**
     * refactores the URL to get the id from IMDB
     */
    private void URL()
    {
        String txt = txtfldURL.getText();
        String string = txt.replace("https://www.imdb.com/title/", "");
        String[] split = string.split("/");
        imdbid = split[0];
    }

    @FXML
    private void btnToDAL(MouseEvent event) throws SQLException, IOException
    {

        try
        {
            URL();
            this.newMovie = model.CreateMovie(moviePath, imdbid);
        } catch (Exception e)
        {

        }
        stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST));
        
    }

    public void setStage(Stage stage)
    {
        this.stage = stage;
//        runbtn.setOnAction( event -> stage.fireEvent(new WindowEvent(stage,WindowEvent.WINDOW_CLOSE_REQUEST)));
    }

    public MovieImage getNewMovie()
    {
        return newMovie;
    }

    @FXML
    private void cancel(ActionEvent event)
    {
        stage.close();
    }

}
