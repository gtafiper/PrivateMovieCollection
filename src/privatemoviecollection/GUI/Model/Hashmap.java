/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Model;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Nijas Hansen
 */
public class Hashmap {
    
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();

    public Hashmap() {
        hashMap.put(1, "Action");
        hashMap.put(2, "Romantic");
        hashMap.put(3, "Comedy");
        hashMap.put(4, "Adventure");
        hashMap.put(5, "Crime");
        hashMap.put(6, "Drama");
        hashMap.put(7, "Fantasy");
        hashMap.put(8, "Horror");
        hashMap.put(9, "Science Fiction");
        hashMap.put(10, "Thriller");
        hashMap.put(11, "Western");
        
        Iterator it = hashMap.keySet().iterator();
        
    }
    
    
    
    
}
