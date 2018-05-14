

import java.io.*;
import java.net.*;
import java.Runtime;

public class WatchDog
{
  public WatchDog()
  {}

  public void getDiff()
  {
     String oldFile="oldFile.txt";
     String newFile="newFile.txt";
     String email="email.txt";
     String cmdMail="mail -s WebChanged  < "+email;
     String cmdCmp="diff -b " + newFile +" "+oldFile;
     String cmdCp="cp "+ newFile +" "+oldFile;

     FileWriter fw;
     try{
     this.fetchURL(newFile);

     Process ps =Runtime.getRuntime().exec(cmdCmp);    
     fw=new FileWriter(email,true);
     InputStream input=(InputStream)ps.getInputStream();
     BufferedReader in = new BufferedReader (new InputStreamReader (input));
     String line;
     while ((line = in.readLine()) != null) {
     fw.write(line);
     fw.write("\n");
     } 
     fw.close();
     Runtime.getRuntime().exec(cmdMail);    
     Runtime.getRuntime().exec(cmdCp);      
     }
     catch (IOException e) {
       System.out.println ("Error  URL");
     }
  }

  public void fetchURL(String newFile){
      FileWriter fileWriter;
      String userPwd=":lena1018";

      try{
   
       fileWriter= new FileWriter(newFile,false);

       URL url=new URL("http://www.cs.rmit.edu./students");  

       HttpURLConnection huc=(HttpURLConnection) url.openConnection();  
       InputStream content = (InputStream)huc.getInputStream();
       BufferedReader in = new BufferedReader (new InputStreamReader (content));
       String line;
       while ((line = in.readLine()) != null) {
       fileWriter.write(line);
       fileWriter.write("\n");
       } 
       fileWriter.close();
     }  
     catch (MalformedURLException e) {
       System.out.println ("Invalid URL");
     } catch (IOException e) {
       System.out.println ("Error  URL");
     }
  }

  public static void main(String[] arguments)
  {
    WatchDog wd =new WatchDog();
    wd.getDiff();
  }

}