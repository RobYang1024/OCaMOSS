
import java.net.*;
import java.io.*;
import java.misc.*;
import java.io.BufferedInputStream;
import java.awt.*;
import java.awt.event.*;

public class WatchDog
{
   public static void main (String args[])
   {
      String url = "http://yallara.cs.rmit.edu./~";
      String file1 = "test1.txt";
      int flag = 0;

      
      WriteFile write = new WriteFile(url, file1, flag);

      
      
      new DoSleep().print();
   }
}

class DoSleep extends Thread
{
   public synchronized void run()
   {
      String url = "http://yallara.cs.rmit.edu./~";
      String file2 = "test2.txt";
      int flag = 1;
      int status = 0;
      String file1 = "test1.txt";

      System.out.println("Checking.........");
      try
      {
         
         
         
         sleep();

         
         
         WriteFile write = new WriteFile(url, file2, flag);

         status = write.getStatus();
         

          if(status != 0)
          {
             int flag2 = 0;
             
             
             WriteFile write2 = new WriteFile(url, file1, flag2);
          }
          
          new DoSleep().print();
      }
      catch (InterruptedException e) {}
   }
}


