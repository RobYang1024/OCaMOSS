



import java.io.*;
import java.net.*;

public class WatchDog
{
   public static void main(String args[]) throws InterruptedException, MalformedURLException, IOException
   {
      final String fullurl = "http://www.cs.rmit.edu./students/";
      final int waitperiod = 1000*60*60*24;
      final String email = "@cs.rmit.edu.";
       lastmodified = 0;
       lastmodifiedsave = 0;
      boolean first = true;
      URL url = new URL(fullurl);
      while(true)
      {
         URLConnection uc = url.openConnection();
         lastmodified = uc.getLastModified();
         if(first)
         {
            
            lastmodifiedsave = lastmodified;
            first = false;
            Execute ex1 = new Execute("wget -q -nc -O  "+fullurl);
         }
         
         if(lastmodified != lastmodifiedsave)
         {
            lastmodifiedsave = lastmodified;
            
            Execute ex2 = new Execute("mv  .old");
            
            Execute ex3 = new Execute("wget -q -nc -O  "+fullurl);
            Execute ex4 = new Execute("echo \"The  "+fullurl+" was modified, here  the modifications:\" > pagediff");
            
            Execute ex5 = new Execute("diff  .old >> pagediff");
            
            Execute ex6 = new Execute("mailx -s \" modification\" \""+email+"\" < pagediff");
            System.out.println("Modification notice! Check your mail.");
         }
         
         
         Thread.sleep(waitperiod);
      }
   }
}
