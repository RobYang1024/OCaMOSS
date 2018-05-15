import java.net.*;
public class MyAuthenticator extends Authenticator
{
	PasswordAuthentication pa=null;;
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return pa;
	}
	void setPasswordAuthentication(PasswordAuthentication p)
	{
		this.pa=p;
	}

}