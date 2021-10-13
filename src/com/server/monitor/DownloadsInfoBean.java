 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.monitor;

import java.util.*;

/**
 *
 * @author prasad
 */  
public class DownloadsInfoBean {
     Map<String,Integer> files;
    
    private DownloadsInfoBean(){
        files = new HashMap<String,Integer>();
    }
      
    public void addFile(String fileName){
        int count = files.get(fileName);
        if(count>0){
            files.remove(fileName);
            files.put(fileName, count+1);
        }else{
            files.put(fileName, 1);
        }
    }
    public int getCountofFile(String fileName){
        return files.get(fileName);
    }
    public void removeFile(String fileName){
        int count = files.get(fileName);
        if(count==1){
            files.remove(fileName);
        }else{
            files.remove(fileName);
            files.put(fileName, count-1);
        }
    }
}
