






import java.io.*;
import java.lang.Object;

public class WatchDog
{

   
   public static void main(String args[])throws Exception
   {
        Process p1,p2,p3,p4,p5;
        
        for(;;)
        {
          

          String s1[] = {"/usr/local//tcsh", "-c", "mailx -s \"Part 2-Assignment2 \"  < change.html"};
          String s2[] = {"/usr/local//tcsh", "-c", "mv www.cs.rmit.edu./images/*.* predir"};
          String s3[] = {"/usr/local//tcsh", "-c", "mv www.cs.rmit.edu./students/*.* predir"};
          String s4[] = {"/usr/local//tcsh", "-c", "mv www.cs.rmit.edu./images/*.* postdir"};
          String s5[] = {"/usr/local//tcsh", "-c", "mv www.cs.rmit.edu./students/*.* postdir"};
          String s6[] = {"/usr/local//tcsh", "-c", "diff copy1 copy2 > diff.html"};


          Process p = Runtime.getRuntime().exec("mkdir predir");
          p.waitFor();
          Process p1 = Runtime.getRuntime().exec("mkdir postdir");
          p1.waitFor();

          
          p1 = Runtime.getRuntime().exec("wget -p --convert-links http://www.cs.rmit.edu./students/");
          p1.waitFor();

          Process q2 = Runtime.getRuntime().exec(s2);
          q2.waitFor();
          Process q3 = Runtime.getRuntime().exec(s3);
          q2.waitFor();

          
          Thread.sleep(86400000);

          p3 = Runtime.getRuntime().exec("wget -p --convert-links http://www.cs.rmit.edu./students/");
          p3.waitFor();

          Process q4 = Runtime.getRuntime().exec(s4);
          q4.waitFor();
          Process q5 = Runtime.getRuntime().exec(s5);
          q5.waitFor();

          try
          {
            String str;
            p4 = Runtime.getRuntime().exec(s6);
            DataInputStream inp1 = new DataInputStream(p4.getInputStream());
            p4.waitFor();
            
            System.out.println("The WatchDog - Returns 0 if  change  else 1");
            System.out.println("Value :" + p4.exitValue());
            try
            {
                while ((str = inp1.readLine()) != null)
                {
                    System.out.println(str);
                }
            }
            catch (IOException e)
            {
                System.exit(0);
            }

          }
          catch(FileNotFoundException e )
          {
              e.printStackTrace();
          }

          BufferedReader in = new BufferedReader(new FileReader("change.html"));
          
          if (in.readLine() != null)
          {

          try
          {
            String str1;
            p5 = Runtime.getRuntime().exec(s1);
            DataInputStream inp2 = new DataInputStream(p5.getInputStream());
            p5.waitFor();
          try
            {
                while ((str1 = inp2.readLine()) != null)
                {
                    System.out.println(str1);
                }
            }
            catch (IOException e1)
            {
                System.exit(0);
            }

          }
          catch(FileNotFoundException exp)
          {
              exp.printStackTrace();
          }

        }
    }
  }
}

