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
public class HistoryFileBean {
    private String fileName;
    private Date time;
    private String user;
    private String action;
    public HistoryFileBean(){
        
    }  
    
    public void setAction(String action){
        this.action = action;
    }
    public String getAction(){
        return this.action;
    }
    public void setUser(String user){
        this.user= user;
    }
    public String getUser(){
        return this.user;
    }
    public void setTime(Date time){
        this.time = time;
    }
    public Date getTime(){
        return this.time;
    }
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    public String getFileName(){
        return this.fileName;
    }
}
