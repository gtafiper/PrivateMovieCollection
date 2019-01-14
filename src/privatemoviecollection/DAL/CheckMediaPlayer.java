/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author Nijas Hansen
 */
public class CheckMediaPlayer {
    
    private static final String PROP_FILE = "src/privatemoviecollection/DAL/mediaplayerpath.path";
    
    public String CheckMediaPlayerPath() throws IOException {
        Properties mediaplayerProperties = new Properties();
        mediaplayerProperties.load(new FileInputStream(PROP_FILE));
        
        String path = mediaplayerProperties.getProperty(PROP_FILE);
        
        return path;
    }
    
    
    public void setFilePath() throws IOException {
        Path source = Paths.get(PROP_FILE);
        Path desctination = Paths.get(PROP_FILE);
        
        
        
        
    }
    
}
