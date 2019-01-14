/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Nijas Hansen
 */
public class AddMovieController implements Initializable {

    @FXML
    private Label lblFileLocation;
    @FXML
    private Button btnChooseFile;
    @FXML
    private TextField txtfldURL;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    private void btn() {
        
    }

//    @FXML
//    private void OpenFileChooser(MouseEvent event) {
//        FileChooser fc = new FileChooser();
//        fc.setTitle("Open Movie Path");
//        File chosenMovie = fc.showOpenDialog(null);
//        String fileAsString = chosenMovie.toString();
//        lblFileLocation.setText(fileAsString);
//    }
    
}
