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
public class ServeFileHistoryBean {
    private String fileName;
    private Date actionTakenTime;
    private String action;
    private String actionTakenBy;
    
    public void setFileName(String fileName){
        this.fileName = fileName;
    }  
    public String getFileName(){
        return this.fileName;
    }
    public void setActionTakenTime(Date actionTakenTime){
        this.actionTakenTime = actionTakenTime;
    }
    public Date getActionTakenTime(){
        return this.actionTakenTime;
    }
    public void setAction(String action){
        this.action = action;
    }
    public String getAction(){
        return this.action;
    }
    public void setActionTakenBy(String actionTakenBy){
        this.actionTakenBy = actionTakenBy;
    }
    public String getActionTakenBy(){
        return this.actionTakenBy;
    }
}
