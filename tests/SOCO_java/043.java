import java.util.*;
import java.io.*;
import javax.swing.text.html.*;


public class WatchDog {

  public WatchDog() {

  }
  public static void main (String args[]) {
    DataInputStream newin;

    try{
      System.out.println("ishti");

      System.out.println("Downloading first copy");
      Runtime.getRuntime().exec("wget http://www.cs.rmit.edu./students/ -O oldfile.html");
      String[] cmdDiff = {"//sh", "-c", "diff oldfile.html newfile.html > Diff.txt"};
      String[] cmdMail = {"//sh", "-c", "mailx -s \"Diffrence\" \"@cs.rmit.edu.\" < Diff.txt"};
      while(true){
            Thread.sleep(24*60*60*1000);
            System.out.println("Downloading new copy");
            Runtime.getRuntime().exec("wget http://www.cs.rmit.edu./students/ -O newfile.html");
            Thread.sleep(2000);
            Runtime.getRuntime().exec(cmdDiff);
            Thread.sleep(2000);
            newin = new DataInputStream( new FileInputStream( "Diff.txt"));
            if (newin.readLine() != null){
               System.out.println("Sending Mail");
               Runtime.getRuntime().exec(cmdMail);
               Runtime.getRuntime().exec("cp newfile.html oldfile.html");

            }
        }

    }
    catch(Exception e){
      e.printStackTrace();
    }

    }

}