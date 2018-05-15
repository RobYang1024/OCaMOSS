
import java.io.*;
import java.lang.Object;

public class WatchDog
{

   
   public static void main(String args[])throws Exception
   {
        
        for(;;)
        {
          
          String s[] = {"/usr/local//tcsh", "-c", "diff copy1 copy2 > diff.html"};

          String s1[] = {"/usr/local//tcsh", "-c", "mailx -s \"SEC Assignment2 part2\"  < diff.html"};
          String s2[] = {"/usr/local//tcsh", "-c", "mv www.cs.rmit.edu./images/*.* copy1"};
          String s3[] = {"/usr/local//tcsh", "-c", "mv www.cs.rmit.edu./students/*.* copy1"};
          String s4[] = {"/usr/local//tcsh", "-c", "mv www.cs.rmit.edu./images/*.* copy2"};
          String s5[] = {"/usr/local//tcsh", "-c", "mv www.cs.rmit.edu./students/*.* copy2"};

          Process p4;
          Process p5;

          Process c1 = Runtime.getRuntime().exec("mkdir copy1");
          c1.waitFor();
          Process c2 = Runtime.getRuntime().exec("mkdir copy2");
          c2.waitFor();

          
          Process p1 = Runtime.getRuntime().exec("wget -p --convert-links http://www.cs.rmit.edu./students/");
          p1.waitFor();

          Process a11 = Runtime.getRuntime().exec(s2);
          a11.waitFor();
          Process a12 = Runtime.getRuntime().exec(s3);
          a12.waitFor();

          
          Thread.sleep(86400000);

          Process p3 = Runtime.getRuntime().exec("wget -p --convert-links http://www.cs.rmit.edu./students/");
          p3.waitFor();

          Process a21 = Runtime.getRuntime().exec(s4);
          a21.waitFor();
          Process a22 = Runtime.getRuntime().exec(s5);
          a22.waitFor();

          try
          {
            String str;
            p4 = Runtime.getRuntime().exec(s);
            DataInputStream dis = new DataInputStream(p4.getInputStream());
            p4.waitFor();
            System.out.println("\t\t\tWATCHDOG PROGRAM");
            System.out.println("\t\t\t****************");

            
            System.out.println("If any change in the web  then the value   1");
            System.out.println("If  is  change then the value   0 ");
            System.out.println("The value :" + p4.exitValue());
            try
            {
                while ((str = dis.readLine()) != null)
                {
                    System.out.println(str);
                }
            }
            catch (IOException e)
            {
                System.exit(0);
            }

          }
          catch(FileNotFoundException e)
          {
              e.printStackTrace();
          }

          BufferedReader in = new BufferedReader(new FileReader("diff.html"));
          
          if (in.readLine() != null)
          {

          try
          {
            String str1;
            p5 = Runtime.getRuntime().exec(s1);
            DataInputStream dis1 = new DataInputStream(p5.getInputStream());
            p5.waitFor();
            System.out.println("u have received a mail");
            try
            {
                while ((str1 = dis1.readLine()) != null)
                {
                    System.out.println(str1);
                }
            }
            catch (IOException e1)
            {
                System.exit(0);
            }

          }
          catch(FileNotFoundException ie1)
          {
              ie1.printStackTrace();
          }

        }
    }
  }
}