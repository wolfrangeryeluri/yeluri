 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.monitor;

/**
 *
 * @author prasad
 */   
public class BufferedFileMonitor extends Thread{
    private BufferedFileBean file;
    private long maxPeriodInMilliSecs;
  // responsible for monitor the buffered file
    // 1. it runs the time for the file
    // 2. if time exceeds the period of time then remove from the buffer
    public BufferedFileMonitor(BufferedFileBean file){
        this.file = file;
//        setMaxPeriodInMilliSecs(maxPeriodInMilliSecs);
    }
   
    public void run(){
        while(true){
            if(over){
                break;
            }
        try{
            Thread.sleep(1*1000);
        }catch(Exception e){
            System.out.println("Exception in monitoring the file.."+file.getFileName());
        }
        
        long durationInBuffer = new java.util.Date().getTime() - file.getLastUsedTime().getTime();
    //    System.out.println("from buffered file monitor :"+file.getFileName()+"  duration :"+durationInBuffer+ " max :"+Buffer.getInstance().getMaxFileTimeInBufferInMillis());
        if(durationInBuffer>Buffer.getInstance().getMaxFileTimeInBufferInMillis()){
            // remove the file from buufer
            Buffer.getInstance().removeBufferedBean(file);
            break;
        }
        }
    }
    private boolean over;
    public void setOver(boolean b) {
        this.over = true;
    }
}
