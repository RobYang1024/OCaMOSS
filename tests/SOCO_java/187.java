



import java.net.*;
import java.io.*;
import java.util.Date;

public class MyMail implements Serializable
{
	

	
	public static final int SMTPPort = 25;

	
	public static final char successPrefix = '2';

	
	public static final char morePrefix = '3';

	
	public static final char failurePrefix = '4';

	

	
	private static final String CRLF = "\r\n";

	
	private String mailFrom = "";

	
	private String mailTo = "";

	
	private String messageSubject = "";

	
	private String messageBody = "";

	
	private String mailServer = "";

	
	public MyMail ()
	{
		
		super();
	}

	
	public MyMail ( String serverName)
	{
		
		super();

		
		mailServer = serverName;
	}

	
	public String getFrom()
	{
		return mailFrom;
	}

	
	public String getTo()
	{
		return mailTo;
	}

	
	public String getSubject()
	{
		return messageSubject;
	}

	
	public String getMessage()
	{
		return messageBody;
	}

	
	public String getMailServer()
	{
		return mailServer;
	}

	
	public void setFrom( String from )
	{
		
		mailFrom = from;
	}

	
	public void setTo ( String To )
	{
		
		mailTo = To;
	}

	
	public void setSubject ( String subject )
	{
		
		messageSubject = subject;
	}

	
	public void setMessage ( String msg )
	{
		
		messageBody = msg;
	}

	
	public void setMailServer ( String server )
	{
		
		mailServer = server;
	}

	
	private boolean responseValid( String response )
	{
		

		
		if (response.indexOf(" ") == -1)
			
			return false;

		
		String cad = response.substring( 0, response.indexOf(" "));

		
		cad = cad.toUpperCase();

		
		if (( cad.charAt(0) == successPrefix ) ||
		    ( cad.charAt(0) == morePrefix )  )
			
				return true;
			else
				
				return false;
		}

	
	public void sendMail()
	{
		try {
		String response;

		
		Socket mailSock = new Socket (mailServer, SMTPPort);

		
		BufferedReader bf = new BufferedReader ( new InputStreamReader(mailSock.getInputStream()));
		PrintWriter pout = new PrintWriter ( new OutputStreamWriter(mailSock.getOutputStream()));

		
		System.out.println("1");
		response = bf.readLine();

		
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		
		try
		{
			InetAddress addr = InetAddress.getLocalHost();

			String localHostname = addr.getHostName();
			
			pout.print ("HELO " + localHostname + CRLF);
		}
		catch (UnknownHostException uhe)
		{
			
			pout.print ("HELO myhostname"  + CRLF);
		}

		
		pout.flush();

		
		System.out.println("2");
		response = bf.readLine();

		
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		
		pout.println ("MAIL From:<" + mailFrom + ">");

		
		pout.flush();

		
		System.out.println("3");
		response = bf.readLine();

		
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		
		pout.println ("RCPT :<" + mailTo + ">");

		
		pout.flush();

		
		System.out.println("4");
		response = bf.readLine();

		
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		
		pout.println ("DATA");

		
		pout.flush();

		
		System.out.println("5");
		response = bf.readLine();

		
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		
		
		pout.println ("From: " + mailFrom);
		pout.println (": " + mailTo);
		pout.println ("Subject: " + messageSubject);

		
		pout.println ();

		
		pout.println (messageBody);

		
		pout.println (".\n\r");

		
		pout.flush();

		
		System.out.println("6");
		response = bf.readLine();

		
		if ( !responseValid(response) )
			throw new IOException("ERR - " + response);

		
		pout.println ("QUIT");

		
		pout.flush();

		
		mailSock.close();
		}
		catch (IOException ioe)
		{
			System.out.println(ioe.getMessage());
		}
	}

}