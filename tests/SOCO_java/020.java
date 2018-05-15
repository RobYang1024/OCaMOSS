



import java.util.*;
import java.util.zip.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class WatchDogTask extends TimerTask
{
    private String  urlHomePage;
    private boolean fileToWrite = true;
    private boolean startup = true;
    private int[]  checksum;
    private int noChangeCount = 0;

    public WatchDogTask()
    {
        super();

        System.out.println("Programmed by   for INTE1070 Assignment2");

        urlHomePage = JOptionPane.showInputDialog( "Enter  URL" );
    }

    
    public void run()
    {
        noChangeCount = 0;

        try
        {
            URL currURL = new URL( urlHomePage );
            URLConnection conn = currURL.openConnection();

            if (conn instanceof HttpURLConnection)
            {
                HttpURLConnection hconn = (HttpURLConnection) conn;
                hconn.setUseCaches( false );
                hconn.setFollowRedirects( true );

                hconn.connect();
                int    response = hconn.getResponseCode();
                String msg = hconn.getResponseMessage();




                performTask( hconn.getInputStream() );
            }

            String option = JOptionPane.showInputDialog(
                                        "  want  exit?(y/n)" );
            if(option != null && (option.equals("y") || option.equals("Y")))
            {
                cancel();
                System.exit(0);
            }
            else
            {
                startup = false;
                System.err.println( "  in 24 hours!"  );
            }
        }
        catch( MalformedURLException mue )
        {
            String msg = "Unable  parse URL !";
            System.err.println( msg );
        }
        catch( IOException ioe )
        {
            System.err.println( "I/O Error : " + ioe );
        }
    }

    private void performTask( InputStream inputStream )
    {
         String fileName = null, arg1 = null, arg2 = null;
         InputStream in = inputStream;

         try
         {
             if( fileToWrite == true )
             {
                 fileName = "tempFile1.txt";
                 fileToWrite = false;
             }
             else
             {
                 fileName = "tempFile2.txt";
                 fileToWrite = true;
             }

             BufferedReader buf = new BufferedReader(new InputStreamReader(in));
             FileOutputStream fout = new FileOutputStream( fileName );

             String line;
             List   imgList = new ArrayList();

             while( ( line = buf.readLine() ) != null )
             {

                 if((line.indexOf("src=") != -1) ||
                                             (line.indexOf("SRC=") != -1))
                 {
                     ParsingImgLink parser = new ParsingImgLink(
                                                 urlHomePage,line);
                     String imgLink = parser.getImgLink();
                     imgList.add( imgLink );

                 }

                 fout.write(line.getBytes());
                 fout.write("\n".getBytes());
             }

             buf.read();
             fout.read();

             int[] tempChecksum = new int[imgList.size()];
             for( int i = 0; i < imgList.size(); i ++ )
             {
                 URL imgURL = new URL( (String)imgList.get( i ) );
                 URLConnection imgConn = imgURL.openConnection();

                 if (imgConn instanceof HttpURLConnection)
                 {
                     HttpURLConnection imgHConn = (HttpURLConnection) imgConn;
                     imgHConn.connect();
                     int    response = imgHConn.getResponseCode();
                     String msg = imgHConn.getResponseMessage();

                     System.out.println( "Downloading image: " +
                                         "Server Response : " + response +
                                         " Response Message: " );

                     CheckedInputStream cis = new CheckedInputStream(
                                    imgHConn.getInputStream(), new Adler32());
                     byte[]  tempBuf = new byte[128];
                     while( cis.get(tempBuf) >= 0 )
                     {
                         
                     }

                     tempChecksum[i] = cis.getChecksum().getValue();
                     System.out.println("Image Checksum = " + tempChecksum[i] );

                     if( startup == false )
                     {
                         for( int j = 0; j < checksum.length; j ++ )
                         {
                             if( tempChecksum[i] == checksum[j] )
                                noChangeCount ++;
                         }
                     }
                 }
             }

             String change = null;
             if( startup == false )
             {
                 Process p = Runtime.getRuntime().exec(
                                 "diff tempFile1.txt tempFile2.txt", null );
                 InputStream inCommand = p.getInputStream();
                 OutputStream out = new FileOutputStream("diff.txt",true);
                 int c;
                 while( (c = inCommand.get()) != -1 )
                 {
                     System.out.print( (char)c );
                     out.write( c );
                 }

                 inCommand.get();

                 if( checksum.length > tempChecksum.length )
                 {
                     change = "" + (checksum.length-tempChecksum.length) +
                           " image(s) has/have been removed from this web ";
                     out.write( change.getBytes() );
                 }
                 else if( checksum.length < tempChecksum.length )
                 {
                     change = "" + (tempChecksum.length-checksum.length) +
                               " image(s) has/have been added  this web ";
                     out.write( change.getBytes() );
                 }
                 else if( noChangeCount < checksum.length )
                 {
                     change = "" + (checksum.length-noChangeCount) +
                             " image(s) has/have been changed  this web ";
                     out.write( change.getBytes() );
                 }
                 else
                 {
                     change = "all images have not been changed";
                 }

                 File diffFile = new File( "diff.txt" );
                 if( diffFile.length() != 0 )
                 {
                     Runtime.getRuntime().exec( "mail  < diff.txt" );
                     System.out.println("A mail has been sent   mail box");
                 }
             }
             else
             {
                 change = "Program starts up first time";
             }

             System.out.println( change );
             checksum = tempChecksum;
         }
         catch( MalformedURLException mue )
         {
             String msg = "Unable  parse URL !";
             System.err.println( msg );
         }
         catch( IOException ioe )
         {
             System.err.println( "I/O Error: " + ioe );
         }
    }
} 
