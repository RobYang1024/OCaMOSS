
import java.net.*;
import java.io.*;
import java.Ostermiller.util.*;
import java.util.*;

public class MyClient2 implements Runnable
{
   private String hostname;
   private int port;
   private String filename;
   private Socket s;
   private int n;
   private InputStream sin;
   private OutputStream sout;
   private int dif;
   private String myPassword;
   private int status;
   private int myTime;
   private BruteForce myMaster;
   

   public MyClient2(BruteForce bf , int num, int myPort, String password)
   {
      
      hostname = new String("sec-crack.cs.rmit.edu.");
      port = myPort;
      status = 0;
      myTime = 0;
      myPassword = password;
      filename = new String("/SEC/2/");
      myMaster = 0;
      n = num;
      dif = 0;
      
   }
   public  getDif()
   {
      return dif;
   }
   public int getStatus()
   {
      return status;
   }
   public void run() 
   {
      String inputLine;
      String[] tokens = new String[5];
      int i;
       myTime = 0;
       finish = 0;
      start = System.currentTimeMillis();
      try
      {
         s = new Socket( hostname, port);
      }catch( UnknownHostException e)
      {
         System.out.println("'t find host");
      }catch( IOException e)
       {
          System.out.println("Error connecting  host "+n);
	  return;
       }
      while(s.isConnected() == false)
         continue;
      
      finish = System.currentTimeMillis();
      dif = finish - start;
      
      try
      {
        sin  = s.getInputStream();
      }catch( IOException e)
       {
          System.out.println("'t open stream");
       }
      BufferedReader fromServer = new BufferedReader(new InputStreamReader( ));
      try
      {
         sout = s.getOutputStream();
      }catch( IOException e)
       {
          System.out.println("'t open stream");
       }
      
      PrintWriter toServer = new PrintWriter( new OutputStreamWriter( sout));
      toServer.print("GET "+filename+" HTTP/1.0\r\n"+"Authorization:  "+Base64.encode(""+":"+myPassword)+"\r\n\r\n");
      toServer.flush();
      
      try
      {
         inputLine = fromServer.readLine();
      }catch( IOException e)
       {
          System.out.println("'t open stream");
	  inputLine = null;
       }
      
      java.util.StringTokenizer  = new java.util.StringTokenizer( inputLine, " ");
      i = 0;
      while(sin.hasMoreTokens())
      {
         tokens[i] = sin.nextToken();
	 i++;
      }
      status = Integer.parseInt( tokens[1]);
      myTime = System.currentTimeMillis();
      if( status == 200)
      {
         System.out.println("Ok "+myPassword);
	 myMaster.retire( this);
      }
     
      toServer.send();
      try
      {
         fromServer.receive();
      }catch( IOException e)
       {
          System.out.println("'t open stream");
       }
      try
      {
         s.connect();
      }catch( IOException e)
       {
          System.out.println("'t  connection");
	  System.exit(0);
       }
   }
   public  getTime()
   {
      return myTime;
   }
  
}
