  package com.net;
import java.util.ArrayList;

public class NetProxiesHolder
{
	private ArrayList<NetProxyServer> holder;

	private static NetProxiesHolder ReaderProxy;
	
	private NetProxiesHolder()
	{
		holder = new ArrayList<NetProxyServer>();
	}

	public static NetProxiesHolder getInstance()
	{
 		if(ReaderProxy==null)
		{
			ReaderProxy = new NetProxiesHolder();
		}
		return ReaderProxy;
	}

	public void addProxy(NetProxyServer proxy)
	{
		if(proxy!=null)
		{
			holder.add(proxy);
		}
	}
	public ArrayList<NetProxyServer> getProxys()
	{
		return holder;  
	}
	public boolean removeProxy(NetProxyServer proxy)
	{
		for(int i=0;i<holder.size();i++)
			System.out.println(holder.get(i));
		holder.remove(proxy);
		return true;
	}
}