import java.io.*;


public class WatchDog
{
public static void main (String[] args)
{       String isdiff = new String();
        String[] cmd1 = {"//sh","-c","diff newfile.html oldfile.html > diff.txt"};
        String[] cmd2 = {"//sh","-c","mailx -s \"Web  Changed\" \"@cs.rmit.edu.\" < diff.txt"};

        try {


           while(true)
           {
              Runtime.getRuntime().exec("wget http://www.cs.rmit.edu./students/ -O oldfile.html");
              Thread.sleep(43200000);
              Thread.sleep(43200000);
              Runtime.getRuntime().exec("wget http://www.cs.rmit.edu./students/ -O newfile.html");
              Thread.sleep(2000);
              Runtime.getRuntime().exec(cmd1);
              Thread.sleep(2000);
              BufferedReader diff = new BufferedReader(new FileReader("diff.txt"));
              if ((isdiff=diff.readLine()) != null)
              {
                 Runtime.getRuntime().exec(cmd2);
                 System.out.println("Change Detected & Email Send");
              }
              diff.print();
           }
        }

        catch (IOException err)
        {
         err.printStackTrace();
        }

         catch (InterruptedException err)
        {
         err.printStackTrace();
        }

}


}