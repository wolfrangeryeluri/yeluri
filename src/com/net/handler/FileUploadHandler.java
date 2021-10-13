  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.handler;

import com.net.NetRequestHandler;
import com.net.NetRequest;
import com.net.NetResponse;
import com.server.monitor.Buffer;
import com.server.history.*;

/**  
 *
 * @author Prasad
 */
public class FileUploadHandler extends NetRequestHandler{
      public void handleRequest(NetRequest request,NetResponse response)throws Exception{
        // handler the request
        
        System.out.println("in handle request:  "+request.getParameter("fileName"));
        // prepare the response
        
        String fileName = (String)request.getParameter("fileName");
        java.io.ByteArrayInputStream in = new java.io.ByteArrayInputStream((byte[])request.getParameter("filedata"));
        int size = in.available();
        java.io.FileOutputStream out = new java.io.FileOutputStream("data\\"+fileName);
        int i = in.read();
        System.out.println("reading the file....");
        while(i!=-1){
            out.write(i);
            i = in.read();
        }
        in.close();
        out.flush();
        out.close();
        com.server.monitor.FileBean bean = new com.server.monitor.FileBean();
        bean.setFileName(fileName);
        bean.setFileSize((int)Math.ceil(size/(double)1024));
        Buffer.getInstance().addFile(bean, (byte[])request.getParameter("filedata"));
        System.out.println("file reading completed...");
        
        
         HistoryFileBean fbean = new HistoryFileBean();
        fbean.setFileName(fileName);
        fbean.setTime(new java.util.Date());
        fbean.setUser(this.getProxyServer().getUser());
        fbean.setAction("Upload");
        HistoryManager.getInstance().addFile(fbean);
      
        // default impl
    //    server.getWriter().sendResponse(response);
        
    }
}
