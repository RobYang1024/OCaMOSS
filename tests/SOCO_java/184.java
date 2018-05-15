

import java.util.Timer;
import java.util.TimerTask;
import java.net.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class WatchDog {
    Timer timer;

    
    public WatchDog() {
        timer = new Timer();
        timer.schedule(new WatchTask(),
	               0,        
	               1*1000);  
    }

    class WatchTask extends TimerTask {

        public void run() {
			try {
				URL rmitUrl = new URL("http://www.cs.rmit.edu./students/");
				URLConnection rmitConnection = rmitUrl.openConnection();

        		BufferedReader in = new BufferedReader(
        		                        new InputStreamReader(
        		                        rmitConnection.getInputStream()));
        		String inputLine;

        		BufferedReader bufr = new BufferedReader(
					                      new FileReader ("dest.txt"));
        		String outputLine;

        		while (((inputLine = in.readLine()) != null) && ((outputLine = bufr.readLine()) != null))
        		{
					if (inputLine.compareTo(outputLine) != 0)
					{
						System.out.println(inputLine);
						System.out.println(outputLine);
						System.out.println("Sending Email Part...");
					
						postMail("@cs.rmit.edu.", "Notice", inputLine, "@cs.rmit.edu.");
					}

				}
    
        		in.close();
        		bufr.close();
			}
			catch(Exception e){}

		}
    }

    public static void main(String args[]) throws Exception{
		System.out.println("About  schedule task.");
			URL phpUrl = new URL("http://www.cs.rmit.edu./students/");
			URLConnection urlConnection = phpUrl.openConnection();

        	BufferedReader out = new BufferedReader(
        	                        new InputStreamReader(
        	                        urlConnection.getInputStream()));
        	String record;

        	PrintWriter pw = new PrintWriter (new
        	                     BufferedWriter (new FileWriter ("dest.txt")));

        	record = out.readLine();
        	while (record != null)
        	{
				pw.println(record);
				record = out.readLine();
			}
			pw.close();

		for (int i = 0; i < 3600; i ++)
		{
			System.out.println("WatchDog running...");
			new WatchDog();
		}
		System.out.println("Task scheduled.");
    }


    public void postMail( String recipients, String subject, String message , String from)
    			throws Exception
	{
	    boolean debug = false;

	     
	     Properties props = new Properties();
	     props.put("mail.smtp.host", "pwd..rmit.edu.");

	    
	    Session session = Session.getDefaultInstance(props, null);
	    session.setDebug(debug);

	    
	    Message msg = new MimeMessage(session);

	    
	    InternetAddress addressFrom = new InternetAddress(from);
	    msg.setFrom(addressFrom);

	    InternetAddress addressTo = new InternetAddress();
        addressTo = new InternetAddress(recipients);
	    msg.setRecipients(Message.RecipientType.NORMAL, addressTo);


	    msg.addHeader("MyHeaderName", "WebPage Changed Notice");

	    
	    msg.setSubject(subject);
	    msg.setContent(message, "text/plain");
	    Transport.send(msg);
	}

}

