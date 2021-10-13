 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.monitor;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author prasad
 */    
public class DownloadsBean {
    private List<String> files;
    private static DownloadsBean downloads;
    private DownloadsBean(){
        files = new ArrayList<String>();
    }
    public static DownloadsBean getInstance(){
        if(downloads==null){
            downloads = new DownloadsBean();
        }
        return downloads;
    }
    public void addDownload(String fileName){
        files.add(fileName);
    }
    public List<String> getDownloads(){
        return files;
    }
    public void removeDownload(String fileName){
        if(files.contains(fileName)){
            files.remove(fileName);
        }
    }
}
