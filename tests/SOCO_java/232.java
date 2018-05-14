import java.util.*;
import java.io.*;
import java.*;

public class WatchDog
{
   public static void main (String [] args) throws Exception
   {      
      executes("rm index.*");
      executes("wget http://www.cs.rmit.edu./students");

      while (true)
      {
         String addr= "wget http://www.cs.rmit.edu./students";
         executes(addr);
         String hash1 = md5sum("index.html");
         String hash2 = md5sum("index.html.1");
         System.out.println(hash1 +"|"+ hash2);
      
         if (hash1.equals(hash2))
         {    
         
         }
         else
         {
           executes(".~/Assign2/difference.sh");            
           executes(".~/Assign2/mail1.sh");
         }

         executes("rm index.html");
         executes("cp index.html.1 index.html");
         executes("rm index.html.1");
         executes("sleep 86400"); 
     }
   }

   public static void executes(String comm) throws Exception
   {
   Process p = Runtime.getRuntime().exec(new String[]{"/usr/local//bash","-c", comm });

         BufferedReader  = new BufferedReader(new InputStreamReader(p.getErrorStream()));

         String s;
         while(( s = bf.readLine()) != null)
         {
            System.out.println();
         }
	 p.waitFor();
   }

   public static String md5sum(String file) throws Exception
   {
      String s;
      String hash= "  ";      

      Process p = Runtime.getRuntime().exec(new String[]{"/usr/local//bash",
                                             "-c", "md5sum "+file });
      BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

      while((b = bf.readLine()) != null)
      {
         StringTokenizer word=new StringTokenizer();
         hash=word.nextToken();
         System.out.println(hash);
      }
      return hash;      

   }
}

