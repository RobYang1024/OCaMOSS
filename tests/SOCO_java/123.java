

import java.net.*;
import java.io.*;
	

class MyAuthenticator  extends Authenticator {

   String password;

   public MyAuthenticator(String pwdin) {
       password = pwdin;
   }
      
   protected PasswordAuthentication getPasswordAuthentication(){
	String  pwd = password;
	return new PasswordAuthentication("",pwd.toCharArray());
   }
}
