/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BLL.Exception;

/**
 *
 * @author Nijas Hansen
 */
public class MovieCollectionException extends Exception {
    
    /**
     * this is the exception for movie collection 
     * @param message 
     */
     public MovieCollectionException(String message)
    {
        super(message);
    }
    
    
}
