  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.handler;
  
import com.net.NetRequestHandler;
import com.net.NetRequest;
import com.net.NetResponse;

/**
 *
 * @author Prasad
 */
public class FileSearchHandler extends NetRequestHandler{
      public void handleRequest(NetRequest request,NetResponse response)throws Exception{
        // handler the request
        
        System.out.println("in handle request:  "+request.getParameter("fileNameString"));
        // prepare the response
        response.setParameter("newname", "Prasad");
        String fileNameString = (String)request.getParameter("fileNameString");
           System.out.println("   "+this.getProxyServer().getUser());
       
        System.out.println("filename string :"+fileNameString);
        java.io.File dataFile = new java.io.File("data");
        String[] fileNames = dataFile.list();
        java.util.ArrayList<String> results = new java.util.ArrayList<String>();
          System.out.println("file count :"+fileNames.length);
      
        for(int i=0;i<fileNames.length;i++){
            if(fileNames[i].contains(fileNameString)){
                results.add(fileNames[i]);
            }
        }
        
        response.setParameter("search_results", results);
        System.out.println("search results count :"+results.size());
      
        
    }
}