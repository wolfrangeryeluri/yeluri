  package com.net;

import java.io.*;

  
public class NetResponseWriter
{
	private ObjectOutputStream oos;

	public NetResponseWriter(OutputStream os)
	{
		try{
			oos=new ObjectOutputStream(os);	
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	} 
	public void sendResponse(NetResponse response)
	{
		try{
                   // System.out.println("sending the response :"+response.getParameter("newname"));
			oos.writeObject(response);
                        
                        oos.flush();
		}catch(IOException e)
		{
			e.printStackTrace();
		}

	}

}
