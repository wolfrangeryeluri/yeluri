 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.handler;

/**
 *
 * @author Prasad 
 */  
public class ConfusionMatrix
{
 String plain,key,ciphertext;
   int []keyAscii;
   int [][]keyMatrix;
   int[][] keytrans = new int[4][4];
   int[] permutate = { 2, 4, 6, 8, 10, 3, 5, 7, 9, 11, 15, 21, 27, 33, 16, 12 };
   
  public ConfusionMatrix()
  {
      plain=key=null;
  }
  public ConfusionMatrix(String key,String plain,int mode)
  {
       this.key=key;
       
       if(mode==1)
       this.plain=plain;
       else
           this.ciphertext=plain;
       
       constructKeyMatrix();
       keyTranspose();
  }
  private void keyTranspose()
  {
            for (int row = 0; row < 4; row++)
                for (int col = 0; col < 4; col++)
                    keytrans[row][col] = keyMatrix[col][row];
    
  } //end of keyTranspose
  
  private void constructKeyMatrix()
  {
       keyAscii=new int[16];
          for (int i = 0; i < key.length(); i++)
                keyAscii[i] = (int)key.charAt(i);
          
          keyMatrix=new int[4][4];
          int x = 0;
            for (int j = 0; j < 4; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    keyMatrix[j][ k] = keyAscii[x];
                    x++;
                }
            }
    } //end of keymatrix method
  
  public String encrypt()
  {
        try
        {
            int[][] ptmatrix = new int[4][4];
            int[][] m1 = new int[4][4];
            int[][] m2 = new int[4][ 4];
            int[][]m3 = new int[4][4];
            int[][]m4 = new int[4][4];
            int[][]m5 = new int[4][4];
            int[][]m6 = new int[4][4];
            int[][]m7 = new int[4][4];
            String cipher = "";
            int y;
            int start = 0; int end = 16;
            int cnt = plain.length() / 16;
            int rem = plain.length() % 16;
            if (rem != 0)
            {
                //pad remaining characters with blankspaces.
                for(int st=1;st<=(16-rem);st++)
                     plain+=' ';
                cnt++;
            }
            int iteration = 1;
            int p = 0;
            String pt="";
            while (iteration <= cnt)
            {
                if (start + end > plain.length())
                {
                    pt=plain.substring(start);
                    if (pt.length() < 16)
                        for (int pi = pt.length(); pi < 16; pi++)
                            pt += ' ';
                }
                else
                    pt=plain.substring(start, (start+end));
                
                p = 0;
                for (int i = 0; i < 4; i++)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        ptmatrix[i][j] = (int)pt.charAt(p);
                        p++;
                    }
                }
                
                //m1=key^pt^keytrans
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                       m1[i][j] = keyMatrix[i][j] ^ ptmatrix[i][j] ^ keytrans[i][ j];
                
                
                //m2 permutate
                y = 0;
                int temp;
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                    {
                        temp = (int)((char)permutate[y]);
                        m2[i][j] = m1[i][j] ^ temp;
                        y++;
                    }
                
                //m3 transpose
                for (int row = 0; row < 4; row++)
                    for (int col = 0; col < 4; col++)
                        m3[row][col] = m2[col][row];

                //m4 row mixing
                int z = 1;
                for (int i = 0; i < 4; i++)
                    m4[0][i] = m3[0][i];
                for (int i = 0; i < 4; i++)
                {
                    if (z < 4)
                    {
                        m4[1][i] = m3[1][z];
                        z++;
                    }
                    m4[1][3] = m3[1][0];
                }
                z = 2;
                for (int i = 0; i < 4; i++)
                {
                    if (z < 4)
                    {
                        m4[2][i] = m3[2][z];
                        z++;
                    }
                    m4[2][2] = m3[2][0];
                    m4[2][3] = m3[2][1];
                }
                m4[3][0] = m3[3][3];
                m4[3][1] = m3[3][0];
                m4[3][2] = m3[3][1];
                m4[3][3] = m3[3][ 2];
                
                //Column mixing m6 m5^key
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                        m6[i][j] = keyMatrix[i][j] ^ m4[i][j];
                

                //m7 permutate m6
                y = 0;
                //int temp;
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                    {
                        temp = (int)((char)permutate[y]);
                        m7[i][j] = m6[i][j] ^ temp;
                        y++;
                    }
                
                //ciphertext
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                        cipher += m7[i][j] + ",";

             start = (start + end) ;
                end = 16;
        //        System.out.println("Iteration " + iteration + " is over ");
                iteration++;
                
            }//end of while for iteration
     //       System.out.println("Over and Out");
            return(cipher);
       }//end of try block
        catch(Exception ex)
        {
            return("Error Found ");
        }
  }
  public String decrypt()
  {
      try
      {
      String[] cipherarr = ciphertext.split(",");
         // String[] cipherarr=null;
      /*    if(ciphertext.length()%8==0)
          cipherarr=new String[ciphertext.length()/8];
          else
              cipherarr=new String[(ciphertext.length()/8)+1];
         */         
          
          int idx=0;
          /*for(int i=0;i<ciphertext.length();i+=8)
          {
              if(i+8>ciphertext.length())
                  cipherarr[idx]=(Integer.valueOf(ciphertext.substring(i),2))+"";
              else
                cipherarr[idx]=(Integer.valueOf(ciphertext.substring(i,(i+8)),2))+"";
                idx++;
          }
          */
             String plaintxt="";
             int[][] cipherblk = new int[4][4];
             int[][] dm1 = new int[4][4];
             int[][] dm2 = new int[4][4];
             int[][] dm3 = new int[4][4];
             int[][] dm4 = new int[4][4];
             int[][] dm5 = new int[4][4];
             int[][] dm6 = new int[4][4];
             int[][] dm7 = new int[4][4];
            int z = 0;
            int start = 0;
            int increment = 1;
            int y = 0;
            int temp;
            int cnt = (cipherarr.length)/16;
            
            while(increment<=cnt)
              {
                  y = 0;
                  //cipher block
                  for (int j = 0; j < 4; j++)
                          for (int k = 0; k < 4; k++)
                          {
                                                          cipherblk[j][k] =Integer.parseInt(cipherarr[z]);
                                                          z++;
                          }
                  
                  //m1 decrypt
                  for (int i = 0; i < 4; i++)
                      {
                          for (int j = 0; j < 4; j++)
                          {
                              temp = (int)((char)permutate[y]);
                             dm1 [i][j] =cipherblk[i][j] ^ temp;
                              y++;
                          }
                      }
                  
                  //m2 decrypt m1^key
                  for (int i = 0; i < 4; i++)
                          for (int j = 0; j < 4; j++)
                              dm2[i][j] = keyMatrix[i][j] ^ dm1[i][j];
                      
                  //m3 Inverse rowmix
                       for (int j = 0; j < 4; j++)
                              dm3[0][j] = dm2[0][j];
                     
                      dm3[1][0] = dm2[1][3];
                      dm3[1][1]=dm2[1][0];
                      dm3[1][2]=dm2[1][1];
                      dm3[1][3]=dm2[1][2];
                      
                    dm3[2][0]=dm2[2][2];
                    dm3[2][1]=dm2[2][3];
                    dm3[2][2]=dm2[2][0];
                    dm3[2][3] = dm2[2][1];
                                                       
                      dm3[3][0] = dm2[3][1];
                      dm3[3][1] = dm2[3][2];
                      dm3[3][2] = dm2[3][3];
                      dm3[3][3] = dm2[3][0];

                      // m4 transpose m3
                      for (int row = 0; row < 4; row++)
                          for (int col = 0; col < 4; col++)
                              dm4[row][col] = dm3[col][row];
                      

                      //m5 reverse permutate m4
                      y = 0;
                      for (int i = 0; i < 4; i++)
                      {
                          for (int j = 0; j < 4; j++)
                          {
                              temp = (int)((char)permutate[y]);
                              dm5[i][j] = dm4[i][j] ^ temp;
                              y++;
                          }
                      }
                      
                      //m6 key^m5^keytrans
                 for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                        dm6[i][j] = keyMatrix[i][j] ^ dm5[i][j] ^ keytrans[i][j];

                 //Plaintext
                 for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                        //plaintxt += (char)dm6[i][j]+"";
                        plaintxt+=dm6[i][j]+",";
          //       System.out.println("Iteration " + increment + " Is over");
                 increment++;
              }//end of while loop
       //     System.out.println("Over and out");
            return(plaintxt);
      }//end of try
      catch(Exception ex)
      {
          System.out.println("Not successfully decrypted ");
          return("Not successfully decrypted " + ex.getMessage());
      }
  } //end of decrypt    
}
