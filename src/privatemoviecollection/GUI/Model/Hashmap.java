/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.GUI.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Nijas Hansen
 */
public class Hashmap {
    
    HashMap<String, ArrayList> hashMap = new HashMap<String, ArrayList>();

    public Hashmap() {
        addValues("1", "Action");
        addValues("2", "Crime");
        addValues("3", "Comedy");
        addValues("4", "Romantic");
        addValues("5", "Horror");
        addValues("6", "Thriller");
        addValues("7", "Western");
        addValues("8", "Adventure");
        addValues("9", "Science Fiction");
        
        
        Iterator it = hashMap.keySet().iterator();
        ArrayList tempList = null;
        
        while (it.hasNext()) {
            int key = (int) it.next();
            tempList = hashMap.get(key);
            if(tempList != null) {
                for (Object value : tempList) {
                    System.out.println("Key : "+key+ " , Value : "+value);
                }
            } 
        }
        
    }
    
    private void addValues(String key, String value) {
        ArrayList tempList = null;
        if (hashMap.containsKey(key)) {
            tempList = hashMap.get(key);
            if (tempList == null) {
                tempList = new ArrayList();
                tempList.add(value);
            }
            else {
                tempList = new ArrayList();
                tempList.add(value);
            }
            hashMap.put(key, tempList);
        }
    }
    
    
    
    
}
