  package com.client;

import java.net.*;
import com.net.NetRequest;
import com.net.NetResponse;
import com.net.NetException;
import java.io.*;
 
public class ConnectionBean
{
	private String serverIP;
	private int serverPort;
        private java.io.ObjectOutputStream oos;
        private java.io.ObjectInputStream ois;
        
        public static void main(String args[])throws Exception{
            ConnectionBean bean = new ConnectionBean();
            System.out.println("connecting...");
            bean.setServerIP("localhost");
            bean.setServerPort(2018);
            bean.connect();
            System.out.println("connected..");
        }
	private ConnectionBean()
	{
	}
	
	public void setServerIP(String serverIP)
	{
		this.serverIP = serverIP;
	}

	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

	public String getServerIP()
	{
		return this.serverIP;
	}
        
	public int getServerPort()
	{
		return this.serverPort;
	}
        private Socket s =null;
        public boolean isConnect(){
            if(s!=null){
                return s.isConnected();
            }else{
                return false;
            }
        }
        public void disconnect()throws NetException{
            try{
                s.close();
            }catch(Exception e){
                throw new NetException();
            }
        }
        public void connect(String ip,int port)throws java.io.IOException,java.net.UnknownHostException, ClassNotFoundException
        {
                s = new Socket(ip,port);
               
                
                //		AlumniMessage msg=new AlumniMessage(1,"writter");
		oos=new java.io.ObjectOutputStream(s.getOutputStream());
                ois = new java.io.ObjectInputStream(s.getInputStream());
                
		com.net.NetRequest request = new com.net.NetRequest();
                request.setHandler("com.net.NetRequestHandler");
                request.setParameter("name","lakshmana");
                   System.out.println("Sending the request from bean...");
                     
                oos.writeObject(request);
                Object o = ois.readObject();
               // System.out.println("Got the response... object o :"+((com.net.NetResponse)o).getParameter("newname"));
 
	
        }
        public boolean isFree(){
            return free;
        }
        public void setFree(Boolean free){
            this.free = free;
        }
        private Boolean free;
        private static ConnectionBean bean=null;
        public static ConnectionBean getInstance(){
            if(bean==null){
                bean = new ConnectionBean();
            }
            return bean;
        }
	public void connect()throws java.io.IOException,java.net.UnknownHostException, ClassNotFoundException
	{
            connect(this.serverIP,this.serverPort);
        }
        public NetResponse getResponse(NetRequest request)throws NetException{
            NetResponse response =null;
            try{
                oos.writeObject(request);
                Object o = ois.readObject();
                if(o==null){
                    response = null;
                }
            }catch(Exception e){
                   throw new NetException();
               }
               return response;
        }
        public void sendFile(NetRequest request,File file)throws NetException{
            
        }
}