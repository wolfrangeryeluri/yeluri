  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.handler;
  

import com.net.NetRequestHandler;
import com.net.NetRequest;
import com.net.NetResponse;
import com.server.history.*;
import com.server.monitor.Buffer;
import java.util.Random;

/**
 *
 * @author PrasadKetan
 */
public class FileDownloadHandler extends NetRequestHandler
{
    ConfusionMatrix cm=null;
    String pt="";
    String cipher="";
    String mykey="";      
    
    private String generateKey() //key generated here dynamically in random fashion
    {
        String keyvalue="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklkmnopqrstuvwxyz";
        Random r=new Random();
        for(int i=1;i<=16;i++)
             mykey+=keyvalue.charAt(r.nextInt(keyvalue.length()));
        return(mykey);
    }
      public void handleRequest(NetRequest request,NetResponse response)throws Exception{
        // handler the request
        
        System.out.println("in handle request:  "+request.getParameter("fileName"));
        System.out.println("in handle request:  Secure With Key"+request.getParameter("secure"));
        // prepare the response
        
        String fileName = (String)request.getParameter("fileName");
        boolean flag=(Boolean)request.getParameter("secure");
       System.out.println("   "+this.getProxyServer().getUser() +  "----Secure is " + flag);
        byte[] data;
        
        if(Buffer.getInstance().containsFile(fileName))
        {
             // file from buffer
           data = readFileAsByteArray(Buffer.getInstance().readFile(fileName));
           if(flag) //apply securit to it so it will be in encrypted fashion
           {
               for(byte bit:data)
                    pt+=(char)bit;
              cm=new ConfusionMatrix(mykey=generateKey(),pt,1);
              // cm=new ConfusionMatrix("hellohowareuhell",pt,1);
              cipher=cm.encrypt();
              
              data=new byte[cipher.split(",").length];
              
              //mask the cipher text back to data format and send to client
              for(int i=0;i<cipher.split(",").length;i++)
                    data[i]=(byte)Integer.parseInt(cipher.split(",")[i]);
              
              System.out.println("Successfully Encryted the File " + data.length + " Bytes");
              for(byte bit : data)
                   System.out.print(bit +   "    ");
              System.out.println();
           }
        }
        else
        {
            //file from actual location
           data = readFileAsByteArray(new java.io.File("data\\"+fileName));
           com.server.monitor.FileBean bean = new com.server.monitor.FileBean();
           bean.setFileName(fileName);
           bean.setFileSize((int)Math.ceil(data.length/(double)1024));
           Buffer.getInstance().addFile(bean, data);//original data
           if(flag) //with security 
           {
               for(byte bit:data)
                    pt+=(char)bit;
              cm=new ConfusionMatrix(generateKey(),pt,1);
              //   cm=new ConfusionMatrix("hellohowareuhell",pt,1);
              cipher=cm.encrypt();
              data=new byte[cipher.split(",").length];
              //mask the cipher text back to data format and send to client
              for(int i=0;i<cipher.split(",").length;i++)
                    data[i]=(byte)Integer.parseInt(cipher.split(",")[i]);
              
               System.out.println("Successfully Encryted the File " + data.length + " Bytes");
              for(byte bit : data)
                   System.out.print(bit +   "    ");
              System.out.println();
           }
        }
        
        if(flag)
             response.setParameter("key",mykey);
        
        response.setParameter("fileName",fileName);
        response.setParameter("fileData", data);
        
            
        System.out.println("file reading completed...");
     
        HistoryFileBean fbean = new HistoryFileBean();
        fbean.setFileName(fileName);
        fbean.setTime(new java.util.Date());
        fbean.setUser(this.getProxyServer().getUser());
        if(flag)
        fbean.setAction("Download With Key");
        else
        fbean.setAction("Download ");            
        HistoryManager.getInstance().addFile(fbean);
        // default impl
    //    server.getWriter().sendResponse(response);
        
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
}

