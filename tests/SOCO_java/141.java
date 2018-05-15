



import java.net.*;
import java.io.*;
import java.util.*;

public class WatchDog
{

  public WatchDog()
  {
  }

  public static void main(String[] args)
  {
      try
      {
          if( args.length != 2 )
          {
            System.out.println("USAGE: java WatchDog <URL> <mailing UserName>");
            System.exit(0);
          }

                Runtime.getRuntime().exec("rm LastWatch.html");
                Runtime.getRuntime().exec("rm WatchDog.ini");

                Thread.sleep(1000);

            while (true)
            {
                WatchDog myWatchDog = new WatchDog();
                myWatchDog.readHTML(args[0], args[1]);

                Runtime.getRuntime().exec("rm Report.txt");
                Runtime.getRuntime().exec("rm diffReport.txt");
                Runtime.getRuntime().exec("rm NewWatch.txt");

                System.out.println(" check after 2 ... press Ctrl-Z  suspend WatchDog...");

                Thread.sleep(2*60*1000); 


            }
      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
  }

  void readHTML (String strHTML, String userName)
  {

    Properties myProp = loadLastMD5 ();

    try
    {

      System.out.println("Running WatchDog  \"" + strHTML + "\" ...... Please Wait....");

      URL url = new URL (strHTML);

      String strHost = url.getHost().toLowerCase();

      Runtime r = Runtime.getRuntime();



  

    
      

      InputStream in = url.openStream();

      DataInputStream bf = new  DataInputStream (in);

      FileOutputStream fOut = new FileOutputStream ("Watch.html");
      DataOutputStream dOut = new DataOutputStream (fOut);

      Vector vtrImages = new Vector ();

      while ( bf!= null)
      {

          String str = bf.readLine();

          if (str == null)
              break;


         if ( str.toLowerCase().indexOf("img") > 0 )
         {
            int indexImg = str.toLowerCase().indexOf("img");
            int indexImgUrl = str.toLowerCase().indexOf("\"", indexImg);
            int indexImgUrlEnd = str.toLowerCase().indexOf("\"", indexImgUrl+1);

            String strImage = str.toLowerCase().substring(indexImgUrl+1, indexImgUrlEnd);

            if (strImage.toLowerCase().indexOf(strHost) > 0)
            {
                int index = strImage.toLowerCase().indexOf(strHost) + strHost.length();
                strImage = strImage.toLowerCase().substring(index);
            }

            if (!vtrImages.contains(strImage.toLowerCase()))
                vtrImages.add (strImage.toLowerCase());
         }

          dOut.writeBytes(str+"\n");
      }

      dOut.print();
      fOut.print();
 
    

      for (int i=0 ; i < vtrImages.size() ; i ++)
      {

          
          r.exec("wget " + strHost + vtrImages.get(i).toString().trim());
      }

      Thread.sleep(2000);

      String [] command = {"//sh", "-c","md5sum *.* > NewWatch.txt"};

      Runtime.getRuntime().exec(command);

      Thread.sleep(1000);

      FileInputStream fIn = new FileInputStream ("NewWatch.txt");
      DataInputStream  = new  DataInputStream (fIn);

      Properties prop = new Properties ();

      while ( bf  != null)
      {

          String str = bf.readLine();

          if (str == null)
              break;

          int index = str.indexOf(" ");


          if (fileDownloaded (str.substring(index + 1), vtrImages) || str.substring(index + 1).trim().equalsIgnoreCase("Watch.html") )
              prop.setProperty(str.substring(index + 1).trim().toLowerCase(), str.substring(0, index).trim().toLowerCase());
      }

      
      fIn.close();

      int isAnyChange = GenerateChangeFile (strHTML, myProp, prop);

      if (isAnyChange > 0)
      {

        if (isAnyChange == 2)
        {
            File f = new File ("LastWatch.html");

            if (! f.exists())
            {
                f.createNewFile();
                Thread.sleep(1000);
            }

            String [] diffCommand = {"//sh", "-c","diff Watch.html LastWatch.html > diffReport.txt"};

            Runtime.getRuntime().exec(diffCommand);

            Thread.sleep(2000);

            FileInputStream feIn = new FileInputStream ("diffReport.txt");
            DataInputStream deIn = new DataInputStream (feIn);

            FileOutputStream feOut = new FileOutputStream ("Report.txt", true);
            DataOutputStream deOut = new DataOutputStream (feOut);

            deOut.writeBytes("\n\n\nDifferences in  Target  :\n\n");

            while (deIn != null)
            {
                String str = deIn.readLine();

                if (str == null)
                  break;

                deOut.writeBytes(str + "\n");
            }

            deOut.print();
            feOut.print();

            deIn.close();
            feIn.close();
        }

        String [] mailCommand = {"//sh", "-c","less Report.txt | mail " + userName};

        Runtime.getRuntime().exec(mailCommand);

        System.out.println("Mailing difference");
      }
      else
          System.out.println(" difference detected");


      Runtime.getRuntime().exec("mv Watch.html LastWatch.html");

    }
    catch (Exception e)
    {
        e.printStackTrace();
    }

  }

  private Properties loadLastMD5 ()
  {
      Properties myProp = new Properties ();

      try
      {
          myProp.load(new FileInputStream ("WatchDog.ini"));
      }
      catch (Exception e)
      {
      }

      return myProp;
  }

  private boolean fileDownloaded (String strFile, Vector vtrImages)
  {
      for ( int i = 0 ; i < vtrImages.size() ; i ++ )
      {
          String strImage = vtrImages.get(i).toString().trim();

          if ( strImage.toLowerCase().indexOf(strFile.toLowerCase().trim()) > -1 )
              return true;
      }

      return false;
  }

  private int GenerateChangeFile (String strUrl, Properties myProp, Properties prop)
  {
      int change = 0;
      boolean boolMainChange = false;

      try
      {
          FileOutputStream myOut = new FileOutputStream ("WatchDog.ini");
          DataOutputStream myIniOut = new DataOutputStream (myOut);

          FileOutputStream fOut = new FileOutputStream ("Report.txt");
          DataOutputStream dOut = new DataOutputStream (fOut);

          dOut.writeBytes("Report of changes for \"" + strUrl + "\":\n\n\n\n\n");

          Enumeration e = prop.keys();

          while (e.hasMoreElements())
          {
              String file = e.nextElement().toString().toLowerCase().trim();

              Runtime.getRuntime().exec("rm " + file);

              myIniOut.writeBytes(file.toLowerCase() + "=" + prop.getProperty(file) + "\n");

              if (myProp.containsKey(file))
              {
                  String OldValue = myProp.getProperty(file);
                  String newValue = prop.getProperty(file);

                  if (OldValue != null && newValue != null)
                  {
                      if (!OldValue.trim().equals(newValue.trim()))
                      {
                          if (file.toLowerCase().trim().equalsIgnoreCase("Watch.html"))
                          {
                            dOut.writeBytes("Traget html  has been changed\n");
                            boolMainChange = true;
                          }
                          else
                            dOut.writeBytes("File \"" + file + "\" has been changed\n");

                          change = 1;
                      }
                  }
              }
              else
              {
                  if (file.toLowerCase().trim().equalsIgnoreCase("Watch.html"))
                  {
                       dOut.writeBytes("Target html is checked for first time\n");
                       boolMainChange = true;
                  }
                  else
                      dOut.writeBytes("File \"" + file + "\" is checked for first time and is new\n");

                   change = 1;
              }
          }

          dOut.print();
          fOut.print();

          myIniOut.close();
          myOut.close();
      }
      catch (Exception ex)
      {
          ex.printStackTrace ();
      }

      if (boolMainChange)
          return 2;

      return change;
  }
}