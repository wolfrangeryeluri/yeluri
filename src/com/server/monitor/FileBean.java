 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.monitor;

/**
 *
 * @author prasad  
 */
public class FileBean {
    private String fileName;
    private Integer fileSize;
    public FileBean(){
          
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
}
