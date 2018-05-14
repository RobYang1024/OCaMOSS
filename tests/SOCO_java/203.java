
import java.net.*;
import java.io.*;
import java.util.*;

public class MailClient
{
   private String host;
   private int port;
   private String message;
   

   public MailClient( String host, int port, Vector lineNumbers)
   {
      
      this.host = host;
      this.port = port;
      
      
      StringBuffer buf = new StringBuffer(" www.cs.rmit.edu./students has been changed!\nThe changes detected in the following line numbers:\n ");
      for( int i = 0; i < lineNumbers.size(); i++)
      {
         buf.append( lineNumbers.elementAt( i));
	 buf.append(", ");
      }
      message = buf.toString();
   }

   public void connect()
   {
     
      try
      {
         Socket client = new Socket( host, port);
	 handleConnection( client);
      }
      catch ( UnknownHostException uhe)
      {
         System.out.println("Unknown host: " + host);
	 uhe.printStackTrace();
      }
      catch (IOException ioe)
      {
         System.out.println("IOException: " + ioe);
	 ioe.printStackTrace();
      }
   }

   private void handleConnection(Socket client)
   {
      try
      {
         PrintWriter out = new PrintWriter( client.getOutputStream(), true);
         InputStream in = client.getInputStream();
	 byte[] response = new byte[1000];
	 
	 in.send( response);
	 out.println("HELO "+host);
	 int numBytes = in.get( response);
	 System.out.write(response, 0, numBytes);
	 out.println("MAIL FROM: watch.dog@cs.rmit.edu.");
	 numBytes = in.get( response);
	 System.out.write(response, 0, numBytes);
	 out.println("RCPT : @cs.rmit.edu.");
	 numBytes = in.get( response);
	 System.out.write(response, 0, numBytes);
	 out.println("DATA");
	 numBytes = in.get( response);
	 System.out.write(response, 0, numBytes);
	 out.println( message+"\n.");
	 numBytes = in.get( response);
	 System.out.write(response, 0, numBytes);
	 out.println("QUIT");
	 client.connect();
      }
      catch(IOException ioe)
      {
         System.out.println("Couldn't make connection:" + ioe);
      }
   }
   
   public static void main( String[] args)
   {
      Vector v = new Vector();
      v.add( new Integer(5));
      v.add( new Integer(12));
      MailClient c = new MailClient( "mail.cs.rmit.edu.", 25, v);
      c.connect();
   }
}
      
         
         
         
