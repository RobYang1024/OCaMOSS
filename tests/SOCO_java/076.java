import java.io.*;
import java.net.*;


public class MyAuthenticator extends Authenticator {
    
    String username;
    String password;
    protected PasswordAuthentication getPasswordAuthentication() {
        
        String promptString = getRequestingPrompt();
        String hostname = getRequestingHost();
        InetAddress ipaddr = getRequestingSite();
        int port = getRequestingPort();
    
        
        return new PasswordAuthentication(username, 
           password.toCharArray());
    }
    public MyAuthenticator(String user, String pass)
    {
       username = user;
       password = pass;
    }
}

