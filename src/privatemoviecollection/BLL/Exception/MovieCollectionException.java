/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BLL.Exception;

import javafx.scene.control.Alert;

/**
 *
 * @author Nijas Hansen
 */
public class MovieCollectionException extends Exception
{

    /**
     * this is the exception for movie collection
     *
     * @param message
     */
    public MovieCollectionException(String title, String header, String contentText)
    {
        super(contentText);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);
        super.printStackTrace();
        alert.showAndWait();
    }

}
