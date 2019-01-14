/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL;
import privatemoviecollection.DAL.CheckMediaPlayer;
import java.io.IOException;


/**
 *
 * @author Nijas Hansen
 */
public class MediaPlayer {
    
    private CheckMediaPlayer mplayer;
    /**
     * Opens the standard mediaplayer and plays the given String name.
     * @param moviename
     * @throws IOException 
     */
    public void openMediaPlayer(String moviename) throws IOException {
        Runtime.getRuntime().exec(mplayer.CheckMediaPlayerPath()
                + "\"C:\\Users\\Nijas Hansen\\Documents\\GitHub\\MRS\\PrivateMovieCollection\\src\\resus\\Movies\\" + moviename + ".mp4\"");
    }
    
    
}
