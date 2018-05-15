import java.net.*;
import java.io.*;


public class EmailClient
{
	private String sender, recipient, hostName;

	public EmailClient(String nSender, String nRecipient, String nHost)
	{
		sender = nSender;
		recipient = nRecipient;
		hostName = nHost;
	}

	public void sendMail(String subject, String message)
	{
		try
		{
			Socket s1=null;
			InputStream	is = null;
			OutputStream os = null;

			DataOutputStream  = null;

			s1 = new Socket(hostName,25);
			is = s1.getInputStream();
			os = s1.getOutputStream();

			bd = new DataOutputStream(os);

			BufferedReader response = new BufferedReader(new InputStreamReader(is));

			bd.writeBytes("HELO "+ InetAddress.getLocalHost().getHostName() + "\r\n");

			waitForSuccessResponse(response);

			bd.writeBytes("MAIL FROM:"+sender+"\n");

			waitForSuccessResponse(response);

			bd.writeBytes("RCPT :"+recipient+"\n");

			waitForSuccessResponse(response);

			bd.writeBytes("data"+"\n");

			bd.writeBytes("Subject:"+subject+"\n");

			bd.writeBytes(message+"\n.\n");

			waitForSuccessResponse(response);
		}

		catch (UnknownHostException badUrl)
		{
			System.out.println("Host unknown.");
		}

		catch (EOFException eof)
		{
			System.out.println("<EOF>");
		}
		catch (Exception e)
		{
			System.out.println("got exception: "+e);
		}
	}

	private static void	waitForSuccessResponse(BufferedReader response) throws IOException
	{
		String rsp;
		boolean r250 = false;

		while( ! r250 )
		{
			rsp = response.readLine().trim();

			if(rsp.startsWith("250"))
				r250 = true;
		}

	}
}