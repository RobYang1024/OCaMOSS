







import java.net.*;
import java.io.*;
import java.util.*;


public class BruteForce
{
   public BruteForce()
   {
      try {
         URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2");
         MyAuthenticator m = new MyAuthenticator();
         Authenticator userPassword = m.getPasswordAuthentication(": ", "Z");
         URLConnection uc = url.openConnection();
         HttpURLConnection http = (HttpURLConnection) uc;
         uc.setRequestProperty  ("Authorization", " ");
         
         
         System.out.println("hello");
         
         http.setRequestMethod("POST");
       } catch (MalformedURLException e) {
         System.out.println("Invalid URL");
       } catch (IOException e) {
         System.out.println("Error  URL");
       }
    }

public static void main (String args[]) {
   BruteForce gogetit = new BruteForce();
   }

class MyAuthenticator {
   Authenticator getPasswordAuthentication(String login, char password) {
      {

         
         for(int i = 0; i < 3; i++)
         {
            password = (char)(57.0 * Math.random() + 65);
            if ((password < 97) && (password > 90))
            {
               i--;
               continue;
            }
            login = login + password;
         }
     
      }while(uc.getURL()!= 401);   
     
   }
}

}
