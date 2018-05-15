
import java.util.*;
import java.net.*;
import java.io.*;
public class WatchDog
{
   private Vector init;
   public WatchDog()
   {
      try
      {
      Runtime run = Runtime.getRuntime();
      String command_line = "lynx http://www.cs.rmit.edu./students/ -dump";
      Process result = run.exec(command_line);
      BufferedReader in = new BufferedReader(new InputStreamReader(result.getInputStream()));
      String inputLine;
      init = new Vector();
      while ((inputLine = in.readLine()) != null)
      {
         init.addElement(inputLine);
      }
     
      }catch(Exception e)
      {
      }
   }
   public static void main(String args[])
   {
     WatchDog wd = new WatchDog();
     wd.nextRead();
   }

   public void nextRead()
   {
     while(true)
     {
      ScheduleTask sch = new ScheduleTask(init);
      if(sch.getFlag()!=0)
      {
        System.out.println("change happen");
        WatchDog wd = new WatchDog();
        wd.nextRead();
      }
     
     }
   }
}