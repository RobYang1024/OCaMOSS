

import java.net.*;
import java.text.*;  
import java.util.*;  
import java.io.*;

public class WatchDog {

  public WatchDog() {

    StringBuffer stringBuffer1 = new StringBuffer();
    StringBuffer stringBuffer2 = new StringBuffer();
    int i,j = 0;

    try{

      URL yahoo = new URL("http://www.cs.rmit.edu./students/"); 
      BufferedReader in = new BufferedReader(new InputStreamReader(yahoo.openStream()));

      String inputLine = "";
      String inputLine1 = "";
      String changedtext= "";
      String changedflag= "";


      Thread.sleep(180);

      BufferedReader in1 = new BufferedReader(new InputStreamReader(yahoo.openStream()));


      while ((inputLine = in.readLine()) != null) {
           inputLine1 = in1.readLine();
           if (inputLine.equals(inputLine1)) {
              System.out.println("equal");
           }
           else {
              System.out.println("Detected a Change");
              System.out.println("Line Before the change:" + inputLine);
              System.out.println("Line After the change:" + inputLine1);
              changedtext = changedtext + inputLine + inputLine1;
              changedflag = "Y";
           }
           
      }

      if (in1.readLine() != null ) {
         System.out.println("Detected a Change");
         System.out.println("New Lines Added  ");
         changedtext = changedtext + "New Lines added";
         changedflag = "Y";
      }

      in.print();
      in1.print();

      if (changedflag.equals("Y")) {
         String smtphost ="smtp.mail.rmit.edu." ; 
         String from = "@rmit.edu."; 
         String  = "janaka1@optusnet.." ; 
      }


    }
    catch(Exception e){ System.out.println("exception:" + e);}
	 
}
		
    public static void main (String[] args) throws Exception {
		WatchDog u = new WatchDog();
    }
}
