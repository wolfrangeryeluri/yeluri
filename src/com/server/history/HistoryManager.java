 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.history;

import java.util.*;
/**
 *
 * @author Prasad
 */  
public class HistoryManager {
    private static HistoryManager manager;
    private Vector<HistoryFileBean> files;
    private com.server.ui.ServerMonitorViewer viewer;
    private HistoryManager(){
        
    } 
    public void setViewer(com.server.ui.ServerMonitorViewer viewer){
        this.viewer = viewer;
        reload();
    }
    public void reload(){
        files = new Vector<HistoryFileBean>();
        viewer.setHistoryModel(files);
    }
    public static HistoryManager getInstance(){
        if(manager==null){
            manager = new HistoryManager();
        }
        return manager;
    }
    public void addFile(HistoryFileBean file){
        files.add(file);
        viewer.setHistoryModel(files);   
    }
}
