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
public class BufferedFileBean implements Comparable{
    private String fileName;
    private Integer fileSize;
    private Date lastUsedTime;
//    private Integer timeInBufferInMillis;
 
    public BufferedFileBean(){
        new BufferedFileMonitor(this).start();
    }
    public void setFileName(String fileName){
        this.fileName = fileName;
    }
    public String getFileName(){
        return this.fileName;
    }
    // size in KB
    public void setFileSize(Integer fileSize){
        this.fileSize = fileSize;
    }
    public Integer getFileSize(){
        return this.fileSize;
    }
    public void setLastUsedTime(Date lastUsedTime){
        this.lastUsedTime = lastUsedTime;
    }
    public Date getLastUsedTime(){
        return this.lastUsedTime;
    }
   private  BufferedFileMonitor monitor;
    public void startMonitorMe(){
         monitor =  new BufferedFileMonitor(this);
         monitor.start();
    }

    public void removeMonitor() {
        monitor.setOver(true);
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int compareTo(Object o) {
    //    throw new UnsupportedOperationException("Not supported yet.");
        int result = 0;
        BufferedFileBean bean = (BufferedFileBean)o;
        if(this.lastUsedTime.before(bean.getLastUsedTime())){
            result = 1;
        }else if(this.lastUsedTime.after(bean.getLastUsedTime())){
            result = -1;
        }
        return result;
    }

  
}
