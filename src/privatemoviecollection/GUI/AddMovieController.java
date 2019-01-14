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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import privatemoviecollection.GUI.Model.Model;

/**
 * FXML Controller class
 *
 * @author Nijas Hansen
 */
public class AddMovieController implements Initializable {

    @FXML
    private Label lblFileLocation;
    @FXML
    private TextField txtfldURL;
    @FXML
    private Button btnChooseFile;
    @FXML
    private Button runbtn;
    
    private Model model;
    private String imdbid;
    private File chosenMovie;
    private Stage stage;
    
    public void setModel(Model model) {
        this.model = model;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void OpenFileChooser(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Movie Path");
        chosenMovie = fc.showOpenDialog(null);
        lblFileLocation.setText(chosenMovie.getPath());
    }
    
    private void URL() {
        String txt = txtfldURL.getText();
        String string = txt.replace("https://www.imdb.com/title/", ""); 
        String[] split = string.split("/");
        imdbid = split[0];
    }

    @FXML
    private void btnToDAL(MouseEvent event) throws SQLException {
        //model.CreateMovie(chosenMovie.getPath(), imdbid);
        Stage stage = (Stage) runbtn.getScene().getWindow();
        
        stage.close();
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
