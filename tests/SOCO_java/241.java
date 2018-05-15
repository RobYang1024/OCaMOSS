import java.io.*;
import java.net.*;
import java.util.*;
import java.*;


public class WatchDog {


public static final int interval = 79200000;

public static void main(String[] args) {
    WatchDog wd = new WatchDog();
    Thread thread = new Thread();
    URLConnection conn = null;
    DataInputStream data = null;
    DataInputStream in = null;
    String line;
    String lines;
    String buffer = new String();
    String buffers = new String();
    String url = new String("http://www.cs.rmit.edu./students/");
    boolean change;
	try{
	URL myurl = new URL(url);
        conn = myurl.openConnection();
        conn.connect();
        Object content = null;
        
        System.out.println("Connection opened......");
        System.out.println("Retrieving data from URL");
        data = new DataInputStream(new BufferedInputStream(conn.getInputStream()));
        System.out.println(" data from the URL......");
        content = myurl.getContent();
        BufferedReader reader = null;
	reader = new BufferedReader(new InputStreamReader((InputStream) content));

        
        while ((line = data.readLine()) != null)

        {
         System.out.println(line);
         FileWriter outnew = new FileWriter("watchdogresult.html");
         outnew.write(line);
        }
        System.out.println("Waiting for any change....");
        thread.sleep(79200000);
        conn = myurl.openConnection();
        conn.connect();
        in = new DataInputStream(new BufferedInputStream(conn.getInputStream()));
        while ((lines = in.readLine()) != null)
        {

	 FileWriter newf = new FileWriter("watchdogresult.tmp");
         newf.write(buffers);
        }
	change = true;
        if(change);
        else{
	change = false;
        
	wd.mail();
	}
}
 catch (InterruptedException e) {}
  catch (IOException e) {
    e.printStackTrace();
    String r = new String(e.getMessage());
    if ( r != null)
    {
     System.out.println("Message :" +r);
    }
     else
     System.out.println("Other problems");
    }
 }


public void mail(){

      try {

      String from = new String("Watchdog Reporter");
      String email = new String("@cs.rmit.edu.");
      String subject = new String(" is a change in ");

      
      URL u = new URL("mailto:" + email);
      URLConnection c = u.openConnection();
      c.setDoInput(false);
      c.setDoOutput(true);
      System.out.println("Connecting...");
      System.out.flush();
      c.connect();
      PrintWriter out =
        new PrintWriter(new OutputStreamWriter(c.getOutputStream()));

      
      out.println("From: \"" + from + "\" <" +
                  System.getProperty("user.name") + "@" +
                  InetAddress.getLocalHost().getHostName() + ">");
      out.println(": "  );
      out.println("Subject: " + subject);
      out.println();  

      
      String line = new String("Watchdog observe that  is a change in the web  .");
      out.close();
      System.out.println("Message sent.");
      System.out.flush();
    }
    catch (Exception e) {
      System.err.println(e);
      }

  }

}

