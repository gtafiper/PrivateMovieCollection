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
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 *
 * @author Nijas Hansen
 */
public class CheckMediaPlayer {

    public static final String PROP_FILE = "src/privatemoviecollection/DAL/mediaplayerpath.path";
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
