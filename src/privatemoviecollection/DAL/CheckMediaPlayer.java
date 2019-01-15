/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.DAL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
<<<<<<< HEAD
=======
import java.util.logging.Level;
import java.util.logging.Logger;
import privatemoviecollection.BE.Movie;
import privatemoviecollection.BLL.Exception.MovieCollectionException;
import privatemoviecollection.GUI.FXMLDocumentController;
>>>>>>> parent of 7db8491... Merge branch 'master' of https://github.com/gtafiper/PrivateMovieCollection

/**
 *
 * @author Nijas Hansen
 */
public class CheckMediaPlayer {

<<<<<<< HEAD
    public static final String PROP_FILE = "src/privatemoviecollection/DAL/mediaplayerpath.path";
=======
    private static final String PROP_FILE = "src/privatemoviecollection/DAL/mediaplayerpath.path";
>>>>>>> parent of 7db8491... Merge branch 'master' of https://github.com/gtafiper/PrivateMovieCollection
    public static final String TEMP_PROPFILE = "src/privatemoviecollection/DAL/temp_mediaplayerpath.path";

    public static Boolean CheckMediaPlayerPath() throws IOException {
        Properties mediaplayerProperties = new Properties();
        FileInputStream is = new FileInputStream(PROP_FILE);
        mediaplayerProperties.load(is);

        String path = mediaplayerProperties.getProperty("Path");
        File file = new File(path);
        is.close();
        if (!file.exists()) {
            return false;

        }
        return true;
    }

    public static void setMediaPlayerPath(File path) throws IOException {
        File tempPropfile = new File(TEMP_PROPFILE);
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempPropfile));

        bw.write("Path=" + path.getAbsolutePath().replaceAll("\\\\", "/"));
        bw.close();
        Files.copy(tempPropfile.toPath(), new File(PROP_FILE).toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tempPropfile.toPath());

    }

}
