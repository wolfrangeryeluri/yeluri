  package com.net;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

   

public class NetRequestReader extends Thread
{
	private ObjectInputStream ois;
	private NetProxyServer parent;

	public NetRequestReader(NetProxyServer parent, InputStream in)
	{
		this.parent = parent;
		try{
			ois=new ObjectInputStream(in);
		}catch(IOException e)
		{
			e.printStackTrace();
		}

	}
	public void run()
	{
		while(true)
		{
            try {
                Object obj=null;
                try{
                        obj = ois.readObject();
                        
                }catch(Exception e)
                {
                        parent.closeSocket();
                        System.out.println("Server is closed by client123");
                        break;
                }
                System.out.println("got a request..."+obj);
                NetRequest request = (NetRequest)obj;
                String handlerClass = request.getHandler();
                
                Class s = Class.forName(handlerClass); 
                try {
                    System.out.println("class name :"+s.getName());
                    Object o = s.newInstance();
                    System.out.println("o :"+o);
                //    com.net.NetRequestHandler handler = (com.net.NetRequestHandler)s.newInstance();
                      com.net.NetRequestHandler handler = (com.net.NetRequestHandler)o;
                      
                      handler.setProxyServer(parent);
                    NetResponse response = new NetResponse();
                    try {
                        System.out.println("Handling the request from req reader...");
                        handler.handleRequest(request, response);
                    //    this.parent.getWriter().sendResponse(response);
                        System.out.println("Handled the request from req reader..."+this.parent.getWriter());
                        
                        this.parent.getWriter().sendResponse(response);
                           System.out.println("Response sended..."+response);
                     
       
                    } catch (Exception ex) {
                        Logger.getLogger(NetRequestReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (InstantiationException ex) {
                    Logger.getLogger(NetRequestReader.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(NetRequestReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NetRequestReader.class.getName()).log(Level.SEVERE, null, ex);
            }

		}
	}

}
