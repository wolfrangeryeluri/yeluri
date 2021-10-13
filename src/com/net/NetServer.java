 package com.net;

import com.net.NetProxyServer;
import java.net.*;
import java.io.*;
import java.util.*;
import com.server.monitor.*;
  
public class NetServer extends Thread
{
        private com.server.ui.ServerMonitorViewer monitor;
	private ServerSocket alumniServer;
	private Socket Client;

	private NetProxyServer proxyServer;

	private Vector<NetProxyServer> proxyList;

        public void setMonitor(com.server.ui.ServerMonitorViewer viewer){
            this.monitor = viewer;
        }
        public com.server.ui.ServerMonitorViewer getMonitor(){
            return this.monitor;
        }
	public NetServer(com.server.ui.ServerMonitorViewer viewer)
	{
                monitor = viewer;
		System.out.println("Connect and Binding server..");
		try{
			alumniServer = new ServerSocket(2018);
                        monitor.setServerInfo(java.net.InetAddress.getLocalHost().toString(), 2018); 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		proxyList = new Vector<NetProxyServer>();
		start();
	}
	public void run()
	{
		for(;;)
		{
			try{
				System.out.println("Listening for request...");
				Client =alumniServer.accept();
			}catch(Exception e)
			{
				e.printStackTrace();
				break;
			}
                        System.out.println(Client);
                        System.out.println("  "+Client.getInetAddress()+"  "+Client.getPort());
                        monitor.addNewClient(new ClientInfoBean(Client.getInetAddress().toString(),Client.getPort(),new Date()));
			proxyServer = new NetProxyServer(this,Client);
			proxyList.add(proxyServer);
			System.out.println(proxyServer);
		}
	}
        public void removeClient(String clientIP,Integer port){
            monitor.removeClient(clientIP,port);
        }
	public Vector<NetProxyServer> getServerList()
	{
		return this.proxyList;
	}
	public void  removeProxy(NetProxyServer proxy)
	{
		for(int i=0;i<proxyList.size();i++)
			System.out.println(proxyList.get(i));
		
		if(proxyList.remove(proxy))
		{
			 proxy.closeSocket();
			System.out.println(proxy+"is Disconnectd");
			
		}
		else System.out.println("problum occurs in disconnecting");
		
	}
        private com.server.ui.ServerMonitorViewer viewer = null;
	public static void main(String args[])
	{
          //  new NetServer();
            com.server.ui.ServerMonitorViewer viewer = null;
            viewer = new com.server.ui.ServerMonitorViewer();
            viewer.setVisible(true);
            new NetServer(viewer);
	}
}