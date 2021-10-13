  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net;

/**
 *  
 * @author prasad
 * This class provides default handling of request from the client. To override the default implementation, extend this class.
 */
public class NetRequestHandler {
    private NetProxyServer server;
    public NetRequestHandler(){
        
    }
    public void setProxyServer(NetProxyServer server){
        this.server = server;
    }
    public NetProxyServer getProxyServer(){
        return this.server;
    }
    public NetRequestHandler(NetProxyServer server){
        this.server = server;
    }
    
    public void handleRequest(NetRequest request,NetResponse response)throws Exception{
        // handler the request
        
        System.out.println("in handle request:  "+request.getParameter("name"));
        // prepare the response
        response.setParameter("newname", "Chanti");
        // default impl
    //    server.getWriter().sendResponse(response);
        
    }
}
