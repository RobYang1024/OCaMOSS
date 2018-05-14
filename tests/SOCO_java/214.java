

import java.io.*;
import java.net.*;
import java.util.*;
import java.*;


public class WatchDog
{

  public static void main (String args[])
  {
    StringWriter sw = new StringWriter();
    PrintWriter  pw = new PrintWriter();

    int flag=1;
    int loop=0;
     String ServerType = new String();
     String LastModified = null;
     String urlString = new String("http://www.cs.rmit.edu./students/");


     storeNewFile snf = new storeNewFile("History.txt");
     storeNewFile snf1 = new storeNewFile("Comparison.txt");
     String result = null;
     getImage myGI = new getImage();
     Process myProcess;
     String line = null;
 stime = System.currentTimeMillis();
   while(loop<5)
   {
     try {
        URL url = new URL (urlString);
        URLConnection uc = url.openConnection();
        InputStream content = (InputStream)uc.getContent();
        BufferedReader in   =
                      new BufferedReader (new InputStreamReader (content));
        String line2;
        while ((line2 = in.readLine()) != null) {

           pw.println (line2);
        }
        snf1.getStringW();
        if(LastModified !=null)
        {


          differenceFile df = new differenceFile();
          result = df.compareFile();
        }
        if(LastModified==null)
        {
          System.out.println("first check");
          LastModified=uc.getHeaderField(1);
          ServerType=uc.getHeaderField(2);
          snf.getStringW();
          myGI.tokenFile("History.txt");           
          myProcess = Runtime.getRuntime().exec("./compGIF.sh");
        }
        else if(result==null)
        {
           myGI.tokenFile("Comparison.txt");
           myProcess = Runtime.getRuntime().exec("./compGIF.sh");
           BufferedReader inputStream= new BufferedReader(new FileReader("pictResult.txt"));
           line=inputStream.readLine();
 
           if(line == null)
           {
             System.out.println(" changes   far..");
           }
           else
           {
             while(line!=null)
             {
               sendMail t = new sendMail();
               t.sendMail("yallara.cs.rmit.edu.", "@cs.rmit.edu.",line);
               line=inputStream.readLine();
             }
           }
           inputStream.close();
        }
        else
        {
           
           snf.translogFile(result);
           sendMail t = new sendMail();
           t.sendMail("yallara.cs.rmit.edu.", "@cs.rmit.edu.",result);
           System.out.println(" email is sent.. sent..sent..");
           snf.getStringW();
        }
        snf.closeStream();
        snf1.closeStream();

        try{
          synchronized(url){
             url.wait(15000);
          }
        }
       catch(InterruptedException e) {
          System.out.println("Error in wait() method");
        }
        catch(Exception e){
          e.printStackTrace();
        }
        loop++;
 endtime=System.currentTimeMillis();
System.out.println("process time is : " +(endtime-stime)/1000 +" seconds.");

     }catch (MalformedURLException e) {
        pw.println ("Invalid URL");
     }catch (IOException e) {
        pw.println ("Error  URL");
     }
   }

     System.out.println("ETag is "+ ServerType);
     System.out.println("LastModified is "+ LastModified);
  }
}