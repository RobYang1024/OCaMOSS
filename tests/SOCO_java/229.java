

import java.io.*;
import java.*;
import java.net.*;
import java.util.*;

public class WatchDog {
 public static void  main (String[] args) throws IOException {
  BufferedReader stdin = new BufferedReader (new InputStreamReader(System.in));
   try{
     twentyfourhours = 86400000;
    Timer timer = new Timer();
    final Runtime rt = Runtime.getRuntime();

    try{
     Process wg1 = rt.exec("./.sh");
     wg1.waitFor();
    }
    catch(InterruptedException e ){
     System.err.println();
     e.printStackTrace();
    }

    class RepeatTask extends TimerTask{
     public void run(){
      try{
       Process wg2 = rt.exec("./task.sh");
       wg2.waitFor();
       FileReader fr = new FileReader("check.txt");
       BufferedReader bufr = new BufferedReader(fr);
       String check = bufr.readLine();
       if(check.equals(".txt: FAILED")) {
        Process difftosend = rt.exec("./diff.sh");
        difftosend.waitFor();
        Process reset = rt.exec("./.sh");
        reset.waitFor();
       }
       FileReader fr2 = new FileReader("imgdiffs.txt");
       BufferedReader bufr2 = new BufferedReader(fr2);
       String imdiff = bufr2.readLine();
       if(imdiff != null){
        Process imdifftosend = rt.exec("./img.sh");
        imdifftosend.waitFor();
        Process reset = rt.exec("./.sh");
        reset.waitFor();
       }
      }
   catch(InterruptedException e){System.err.println();e.printStackTrace();}
      catch(IOException e){
       System.err.println(e);
       e.printStackTrace();
      }
    }}

    timer.scheduleAtFixedRate(new RepeatTask(),twentyfourhours,twentyfourhours);
    
   }
   catch(IOException e){
    System.err.println(e);
    e.printStackTrace();
   }
   
}}
