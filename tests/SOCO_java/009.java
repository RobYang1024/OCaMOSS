

import java.util.*;
import java.text.*;
import java.io.*;
import java.*;
import java.net.*;

public class WatchDog
{
   public static void main(String args[])
   {
      String s = null;
      String webpage = "http://www.cs.rmit.edu./students/";
      
      
      String file1 = "file1";
      String file2 = "file2";
      
      try
      {
         Process p = Runtime.getRuntime().exec("wget -O " + file1 + " " + webpage);
         
         BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

         BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            
         while ((s = stdInput.readLine()) != null) { 
            System.out.println(s);
         }
            
            
         while ((s = stdError.readLine()) != null) { 
            System.out.println(s);
         }
         
         try
         {
            p.waitFor(); 
         }
         catch (InterruptedException g)  
         {
         }  
      }
      catch (IOException e) {
         System.out.println("exception happened - here's what I know: ");
         e.printStackTrace();
         System.exit(-1);
      }
      
      while (true)  
      {
         try
         {
            Process p = Runtime.getRuntime().exec("sleep 86400"); 
             
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            
            while ((s = stdInput.readLine()) != null) { 
               System.out.println(s);
            }
            
            
            while ((s = stdError.readLine()) != null) { 
               System.out.println(s);
            }
         
            try
            {
               p.waitFor(); 
            }
            catch (InterruptedException g)  
            {
            }  
         }
         catch (IOException e) 
         {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
         } 
         try 
         {
            Process p = Runtime.getRuntime().exec("wget -O " + file2 + " " + webpage);
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            
            while ((s = stdInput.readLine()) != null) { 
               System.out.println(s);
            }
            
            
            while ((s = stdError.readLine()) != null) { 
               System.out.println(s);
            }
         
            try
            {
               p.waitFor(); 
            }
            catch (InterruptedException g)  
            {
            }  
         
         }
         catch (IOException e) 
         {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
         }
         try 
         {
            
            Process p = Runtime.getRuntime().exec("diff " + file1 + " " + file2);
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));    
            
             
            while ((s = stdError.readLine()) != null) { 
               System.out.println(s);
            }
         
            try
            {
               p.waitFor(); 
            }
            catch (InterruptedException g)  
            {
            }
            
            if ((p.exitValue()) == 1) 
            { 
            
               String mailServerURL = "yallara.cs.rmit.edu.";
               String host = "yallara.cs.rmit.edu.";
               String from = "@yallara.cs.rmit.edu.";
               
               String subject = "Change Detected In WatchDog.java";
     
               try
               {
               	
                  Socket csoc=new Socket(mailServerURL,25);
                  BufferedReader in=new BufferedReader(
                           new InputStreamReader(csoc.getInputStream()));
                  
                  PrintWriter out=new PrintWriter(csoc.getOutputStream(),true);
                  System.out.println("HELO "+host);
                  System.out.println(in.readLine());
                  out.println("MAIL FROM:"+from);
                  System.out.println(in.readLine());
                  System.out.println(in.readLine());
                  System.out.println("DATA");
                  System.out.println(in.readLine());
                  System.out.println("SUBJECT:"+subject);
                  System.out.println(in.readLine());
                  
                  
                  while ((s = stdInput.readLine()) != null){
                     System.out.println(s);
                  }
                  out.println(".");
                  System.out.println(in.readLine());
                  System.out.println("QUIT");
                  System.out.println(in.readLine());                
               }
               catch(Exception e)
               {
                  e.printStackTrace();
                  System.out.println("Some error occoured while communicating  server");
               }
            }      
         }
         catch (IOException e) 
         {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
         }
      }  
   }
}