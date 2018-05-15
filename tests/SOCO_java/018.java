

import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;



public class ParsingImgLink
{
    String url, imgLink, line;

    public ParsingImgLink( String baseURL, String str )
    {
        url = baseURL;
        line = str;
        parsingLine();
    }

    public void parsingLine()
    {
        int  end;
        String imgURLStr = null;
        if( ( url = line.indexOf("src=\"") ) != -1 )
        {
            String subStr = line.substring(+5);
            end   = subStr.indexOf("\"");
            imgURLStr = subStr.substring( 0, end );
            System.out.println(imgURLStr);
        }
        else if( ( end = line.indexOf("SRC=\"")) != -1 )
        {
            String subStr = line.substring(+5);
            end   = subStr.indexOf("\"");
            imgURLStr = subStr.substring( 0, end );
            System.out.println(imgURLStr);
        }

        if( imgURLStr.indexOf("://") == -1 )
        {
            try
            {
                URL    baseURL = new URL( url );
                URL imgURL  = new URL( baseURL, imgURLStr );
                imgLink = imgURL.toString();
            }
            catch( MalformedURLException mue )
            {
                String msg = "Unable  parse URL !";
                System.err.println( msg );
            }
        }
    }

    public String getImgLink()
    {
        return imgLink;
    }
} 