



import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class PasswordCombination
{
    private int      pwdCounter = 0;
    private   int   startTime;
    private String   str1,str2,str3;
    private String   url = "http://sec-crack.cs.rmit.edu./SEC/2/";
    private String   loginPwd;
    private String[] password;
    private HoldSharedData data;
    private char[] chars = {'A','B','C','D','E','F','G','H','I','J','K','L','M',
                            'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
                            'a','b','c','d','e','f','g','h','i','j','k','l','m',
                            'n','o','p','q','r','s','t','u','v','w','x','y','z'};

    public PasswordCombination()
    {
        System.out.println("Programmed by   for INTE1070 Assignment 2");

        String input = JOptionPane.showInputDialog( "Enter number of threads" );
        if(  input == null  )
           System.exit(0);

        int numOfConnections = Integer.parseInt( input );
        startTime = System.currentTimeMillis();
        int pwdCounter = 52*52*52 + 52*52 + 52;
        password = new String[pwdCounter];


        loadPasswords();
        System.out.println( "Total Number of Passwords: " + pwdCounter );
        createConnectionThread( numOfConnections );
    }

    private void doPwdCombination()
    {
        for( int i = 0; i < 52; i ++ )
        {
            str1 = "" + chars[i];
            password[pwdCounter++] = "" + chars[i];
            System.err.print( str1 + " | " );

            for( int j = 0; j < 52; j ++ )
            {
                str2 = str1 + chars[j];
                password[pwdCounter++] = str1 + chars[j];

                for( int k = 0; k < 52; k ++ )
                {
                    str3 = str2 + chars[k];
                    password[pwdCounter++] = str2 + chars[k];
                }
            }
        }
    }

    private void loadPasswords( )
    {
        FileReader     fRead;
        BufferedReader buf;
        String         line = null;
        String         fileName = "words";

        try
        {
            fRead = new FileReader( fileName );
            buf = new BufferedReader(fRead);

            while((line = buf.readLine( )) != null)
            {
                password[pwdCounter++] = line;
            }
        }
        catch(FileNotFoundException e)
        {
            System.err.println("File not found: " + fileName);
        }
        catch(IOException ioe)
        {
            System.err.println("IO Error " + ioe);
        }
    }

    private void createConnectionThread( int input )
    {
        data = new HoldSharedData( startTime, password, pwdCounter );

        int numOfThreads = input;
        int batch = pwdCounter/numOfThreads + 1;
        numOfThreads = pwdCounter/batch + 1;
        System.out.println("Number of Connection Threads Used=" + numOfThreads);
        ConnectionThread[] connThread = new ConnectionThread[numOfThreads];

        for( int index = 0; index < numOfThreads; index ++ )
        {
            connThread[index] = new ConnectionThread( url, index, batch, data );
            connThread[index].conn();
        }
    }
}  