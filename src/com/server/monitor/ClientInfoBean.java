 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.monitor;

import java.util.*;

/**
 *
 * @author prasad  
 */
public class ClientInfoBean {
    private String ip;
    private Integer port;
    private Date lastLoggedIn;
    public ClientInfoBean(String ip,Integer port,Date lastLoggedIn){
        setIp(ip);
        setPort(port);
        setLastLoggedIn(lastLoggedIn);
    }
//    public 
    public void setIp(String ip){
        this.ip = ip;
    } 
    public String getIp(){
        return this.ip;
    }
    public void setPort(Integer port)
    {
        this.port = port;
    }
    public Integer getPort(){
        return this.port;
    }
    public void setLastLoggedIn(Date lastLoggedIn){
        this.lastLoggedIn = lastLoggedIn;
    }
    public Date getLastLoggedIn(){
        return this.lastLoggedIn;
    }
}
