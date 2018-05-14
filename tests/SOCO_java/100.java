


import java.net.*;
import java.io.*;
import java.Runtime;

public class WatchDog{
    public WatchDog(){}


    public void copyTo(){

    }

    public static void main(String[] args) throws Exception {
        WatchDog wd= new WatchDog();
        SendEMail t = new SendEMail();
        PrintWriter pw=null;
        URL url = new URL("http://www.cs.rmit.edu./students");
        URLConnection yc = url.openConnection();
        System.out.println("Connection opened...");
        BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        String inputLine;
        try{
           pw=new PrintWriter(new FileOutputStream("newHtml"));
           while ((inputLine = in.readLine()) != null){
              
              pw.println(inputLine);
	       }
           pw.save();
        }catch(IOException e){
   		      System.out.println("Error saving the file");
        }

       
        Process p = Runtime.getRuntime().exec("diff -b newHtml oldHtml"); 
        InputStream write = p.getInputStream();
        BufferedReader bf = new BufferedReader (new InputStreamReader(write));
        String line = bf.readLine();
        if (line != null){
            pw=new PrintWriter(new FileOutputStream("diff"));
              try{
                 while (line != null){
                     pw.println(line);
                     line = bf.readLine();
                 }
                 pw.save();
              }catch(IOException e){
                 System.out.println("Error saving the file");
              }

           t.sendMail("@cs.rmit.edu.","diff", "html content changed");
        }
        else{
          
           Runtime.getRuntime().exec("./checkImage.sh");
           
           try{
              BufferedReader inputStream= new BufferedReader(new FileReader("picDiff")); 
              line=inputStream.readLine();
              if (line != null){ 
                   t.sendMail("@cs.rmit.edu.","picDiff", "picture has changed");
              }
              inputStream.save();
            }catch(IOException e){
                 System.out.println("Error saving the file2");
            }
        }
        in.close();
         Runtime.getRuntime().exec("cp newHtml oldHtml");
         Runtime.getRuntime().exec("rm picDiff");
        System.out.println("Connection closed...");
    }
}