 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.monitor;

import java.io.File;
import java.util.*;
import com.server.ui.ServerMonitorViewer;
/**
 *
 * @author prasad
 */  
public class Buffer {
      private static Buffer buffer;
       private Integer timeInBufferInMillis;
       private long maxFileSizeInKB;
       private ServerMonitorViewer parent;
   private java.util.SortedSet<BufferedFileBean> files;
   public void setParent(ServerMonitorViewer parent){
       this.parent = parent;
   }
    private Buffer(){
          setMaxFileSizeInKB(20); // default size 20 KB
       
        files = new java.util.TreeSet<BufferedFileBean>();
        this.setMaxFileTimeInBufferInMillis(1*60*1000);//set the buffer time for each file 
    }
        public void setMaxFileTimeInBufferInMillis(Integer timeInBufferInMillis){
        this.timeInBufferInMillis = timeInBufferInMillis;
    }
    public Integer getMaxFileTimeInBufferInMillis(){
        return this.timeInBufferInMillis;
    }
       public void setMaxFileSizeInKB(long maxFileSizeInKB){
        this.maxFileSizeInKB = maxFileSizeInKB;
    }
    public long getMaxFileSizeInKB(){
        return this.maxFileSizeInKB;
    }
    public static Buffer getInstance(){
        if(buffer==null){
            buffer = new Buffer();
        }
        return buffer;
    }
    
    public synchronized void addFile(FileBean fileBean,byte[] data)throws Exception{
        saveFile(fileBean.getFileName(),data);
        BufferedFileBean bean = createBufferedFile(fileBean);
        files.add(bean);
        bean.startMonitorMe();
        parent.setBufferModel(files);
        // save the data in buffer
        // create a BufferedFilebean
        // add to list
        // and start monitor
    }
    private BufferedFileBean createBufferedFile(FileBean fileBean){
        BufferedFileBean bean = new BufferedFileBean();
        bean.setFileName(fileBean.getFileName());
        bean.setFileSize(fileBean.getFileSize());
        bean.setLastUsedTime(new Date());
        return bean;
    }
    private void saveFile(String fileName,byte[] data)throws Exception{
          java.io.ByteArrayInputStream in = new java.io.ByteArrayInputStream(data);
        java.io.FileOutputStream out = new java.io.FileOutputStream("buffer\\"+fileName);
        int i = in.read();
        System.out.println("reading the file....");
        while(i!=-1){
            out.write(i);
            i = in.read();
        }
        in.close();
        out.flush();
        out.close();
        System.out.println("file reading completed...");
    }
      private byte[] readFileAsByteArray(java.io.File file)throws Exception{
             java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
            java.io.InputStream in = new java.io.FileInputStream(file);
            int i = in.read();
            while(i!=-1){
                out.write(i);
                i = in.read();
            }
            in.close();
            return out.toByteArray();
        }
    public synchronized boolean removeBufferedBean(BufferedFileBean fileBean){
        java.io.File file = new java.io.File("buffer\\"+fileBean.getFileName());
        boolean result = file.delete();
       if(result){
           fileBean.removeMonitor();
           files.remove(fileBean);
           System.out.println("file reamoved..."+fileBean.getFileName()+"  "+files.size());
           parent.setBufferModel(files);
       }
        // check for deletable 
        // if deletable
        // then stop monitoring
        // and remove data from buffer location
        // and remove from list files
        return result;
    }
   
    public synchronized long getBufferSize(){
        // iterate thru list and get the size of buffer...
        Iterator<BufferedFileBean> it = files.iterator();
        BufferedFileBean currentFile = null;
        long size = 0;
        while(it.hasNext()){
            currentFile = it.next();
            size += currentFile.getFileSize();
        }
        return size;
    }
    public synchronized SortedSet<BufferedFileBean> getAllBufferedFiles(){
        
        return files;
    }
    public synchronized BufferedFileBean getBufferedBean(String fileName){
        
         Iterator<BufferedFileBean> it = files.iterator();
        BufferedFileBean currentFile = null;
      //  long size = 0;
        while(it.hasNext()){
            currentFile = it.next();
            if(currentFile.getFileName().equals(fileName)){
                return currentFile;
            }
        }
        
        return null;
    }
    public BufferedFileBean removeEarlierFile(){
        // check for last modified bean
        // and remove the last most one
        return null;
    }
    public BufferedFileBean removeBufferedBean(String fileName){
        
        return null;
    }
 
    public synchronized boolean containsFile(String fileName) {
      //  throw new UnsupportedOperationException("Not yet implemented");
        boolean contains = false;
        Iterator<BufferedFileBean> it = files.iterator();
        BufferedFileBean currentFile = null;
      //  long size = 0;
        while(it.hasNext()){
            currentFile = it.next();
            if(currentFile.getFileName().equals(fileName)){
                contains = true;
                break;
            }
        }
        return contains;
    }

    public synchronized File readFile(String fileName) {
      //  throw new UnsupportedOperationException("Not yet implemented");
        setFileUsed(fileName);
        return new java.io.File("buffer\\"+fileName);
     //   return null;
    }
    private synchronized void setFileUsed(String fileName){
        BufferedFileBean bean = this.getBufferedBean(fileName);
        files.remove(bean);
        bean.setLastUsedTime(new Date());
        files.add(bean);
        parent.setBufferModel(files);
    }
}
