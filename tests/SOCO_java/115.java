
import java.net.*;
import java.util.*;
import java.io.*;

public class PasswordTest {
    
    private String strURL;
    private String strUsername;
    private String strPassword;
    
    
    public PasswordTest(String url, String username, String password) {
        strURL = url;
        strUsername = username;
        strPassword = password;        
    }
 
    boolean func() {
        boolean result = false;

        Authenticator.setDefault (new MyAuthenticator ());
        try {
            URL url = new URL(strURL);
            HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
            urlConn.connect();
            if(urlConn.getResponseMessage().equalsIgnoreCase("OK")) {
               result = true;
            }
        } 
        catch (IOException e) {}
        
        return result;
    }
    
    
     class MyAuthenticator extends Authenticator {
       protected PasswordAuthentication getPasswordAuthentication() {
            String username = strUsername;
            String password = strPassword;
            return new PasswordAuthentication(username, password.toCharArray());
        }
    }

}
