import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class Mailsend
{
    static final String SMTP_SERVER = MailsendPropertyHelper.getProperty("smtpServer");
    static final String RECIPIENT_EMAIL = MailsendPropertyHelper.getProperty("recipient");
    static final String SENDER_EMAIL = MailsendPropertyHelper.getProperty("sender");
    static final String MESSAGE_HEADER = MailsendPropertyHelper.getProperty("messageHeader");


	

	public static void main(String args[])
	{
		try
		{
			
			String smtpServer = SMTP_SERVER;
			String recip = RECIPIENT_EMAIL;
			String from = SENDER_EMAIL;
			String subject = MESSAGE_HEADER;
			String body = "Testing";

			System.out.println("Started sending the message");
			Mailsend.send(smtpServer,recip , from, subject, body);
		}
		catch (Exception ex)
		{
			System.out.println(
				"Usage: java mailsend"
					+ " smtpServer toAddress fromAddress subjectText bodyText");
		}

		System.exit(0);
	}


	
	public static void send(String smtpServer, String receiver,	String from, String subject, String body)

	{
		try
		{
			Properties props = System.getProperties();

			

			props.put("mail.smtp.host", smtpServer);
			props.put("mail.smtp.timeout", "20000");
			props.put("mail.smtp.connectiontimeout", "20000");

			
			Session session = Session.getDefaultInstance(props, null);


			
			Message msg = new MimeMessage(session);

			
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.NORMAL,	InternetAddress.parse(receiver, false));



			
			msg.setSubject(subject);

			msg.setSentDate(new Date());

			msg.setText(body);

			
			Transport.send(msg);

			System.out.println("sent the email with the differences : "+ + "using the mail server: "+ smtpServer);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
