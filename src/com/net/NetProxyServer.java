  package com.net;



import java.net.*;
import java.io.*;
 
public class NetProxyServer
{
	private NetServer Server;
	private Socket Client;
	private String ip;
	private NetResponseWriter Writer;
	private NetRequestReader Reader;

	public NetProxyServer(NetServer Server,Socket Client)
	{
		this.Server = Server;
		this.Client = Client;
		try{
                        ip = Client.getLocalAddress().getHostAddress();
			Reader=new NetRequestReader(this,Client.getInputStream());
			Writer= new NetResponseWriter(Client.getOutputStream());
                }catch(IOException e)
		{
			e.printStackTrace();
		}
		Reader.start();
	}
 	public NetResponseWriter getWriter()
	{            	
		return this.Writer;
	}
//	public void writeMessage(AlumniMessage message)
//	{
//		Writer.writeMessage(message);	
//	}
        public String getUser(){
            return ip;
        }
	public void  removeProxy()
	{
		Server.removeProxy(this);		
	}
	public void closeSocket()
	{
		try
		{
                        Server.removeClient(Client.getInetAddress().toString(),Client.getPort());
			Client.close();	
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}