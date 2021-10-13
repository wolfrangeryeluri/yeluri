  /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net;

import java.io.Serializable;
import java.util.*;

/**
 * 
 
 */
public class NetRequest implements Serializable{
    private String handler;
    private Map<String,Object> parameters;
    
    public NetRequest(){
        handler = null;
        parameters = new HashMap<String,Object>();
    }
    public void setHandler(String handler){
        this.handler = handler;
    }
    public String getHandler(){
        return this.handler;
    }
    public void setParameter(String name,Object value){
        if(parameters.containsKey(name)){
            parameters.remove(name);
        }
        parameters.put(name, value);
    }
    public Object getParameter(String name){
        Object param = null;
        if(parameters.containsKey(name)){
            param = parameters.get(name);
        }
        return param;
    }
}
