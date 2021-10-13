 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net;

import java.util.Random;

/**
 *
 * @author Administrator  
 */
public class Testing
{
  public static void main(String []args)    
  {
      String key=generateKey();
      System.out.println(key);
  }
  static String generateKey()
    {
        String keyvalue="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklkmnopqrstuvwxyz";
        String mykey="";
        Random r=new Random();
        for(int i=1;i<=16;i++)
            // System.out.println("Positi is " + r.nextInt(keyvalue.length()));
             mykey+=keyvalue.charAt(r.nextInt(keyvalue.length()));
        return(mykey);
    }
}
