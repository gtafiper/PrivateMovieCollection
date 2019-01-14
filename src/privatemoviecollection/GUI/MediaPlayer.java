/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI;

import java.io.IOException;

/**
 *
 * @author Nijas Hansen
 */
public class MediaPlayer {
    
    /**
     * Opens the standard mediaplayer and plays the given String name.
     * @param moviename
     * @throws IOException 
     */
    public void openMediaPlayer(String moviename) throws IOException {
        Runtime.getRuntime().exec("C:\\Program Files\\Windows Media Player\\wmplayer.exe "
                + "\"C:\\Users\\Nijas Hansen\\Documents\\GitHub\\MRS\\PrivateMovieCollection\\src\\resus\\Movies\\" + moviename + ".mp4\"");
        
        
    }
    
    
}
