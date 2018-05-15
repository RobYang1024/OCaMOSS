



import java.util.*;
import java.net.*;
import java.io.*;
import misc.BASE64Encoder;
import javax.swing.*;

public class ConnectionThread extends Thread
{
    private String         url;
    private URL            currURL;
    private URLConnection  conn;
    private HoldSharedData sharedData;
    private int            noOfThread;
    private int            batch;

    public ConnectionThread( String pageURL, int wThread,
                             int newBatch, HoldSharedData data )
    {
        super();
        url = pageURL;
        noOfThread = wThread ;
        batch = newBatch;
        sharedData = data;
    }

    
    public void run()
    {
        try
        {
            currURL = new URL( url );

            for( int i = noOfThread*batch; (i < (noOfThread + 1)*batch) &&
                                    (i < sharedData.getPwdCount()); i ++ )
            {
                String pwd = sharedData.getPasswordAt( i );

                conn = currURL.openConnection();

                if (conn instanceof HttpURLConnection)
                {
	            HttpURLConnection hconn = (HttpURLConnection) conn;
                    hconn.setFollowRedirects(false);
                    String cad = " " + based64Encoder( ":" + pwd );
                    hconn.setRequestProperty( "Authorization", cad );

                    hconn.connect();
	            int response = hconn.getResponseCode();
                    sharedData.setNumOfConnections();

                    if( response == 200 )
                    {
                         totalTime = System.currentTimeMillis() -
                                           sharedData.getStartTime();
                        int numOfConnections = sharedData.getNumOfConnections();

                        System.out.println( "Password is " + pwd );
                        System.out.println( "Total Time(seconds)=" +
                                           (double)totalTime/1000 );
                        System.out.println( "Number Of Connections: " +
                                                    numOfConnections );
                        System.exit(0);
                    }
                    else
                    {
                        hconn.disconnect();
                    }
                }
            }
        }
        catch( MalformedURLException mue )
        {
            String msg = "Unable  parse URL: " + url;
            System.err.println( msg );
        }
        catch( IOException ioe )
        {
            System.err.println( "I/O Error : " + ioe );
        }
    }

    private String based64Encoder( String pwd )
    {
        
        String str = pwd;
        byte[] buf = str.getBytes();
        String encodedStr = new misc.BASE64Encoder().encode(buf);


        return encodedStr;
    }
}  