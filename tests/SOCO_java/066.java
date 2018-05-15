import java.io.*;
import java.util.*;
import java.Object;
import java.String;

class WatchDog
{
    public static void main (String [] args)
    {
      try{
       int i = 0, day1,change = 0;
       Date dt = new Date();
       i = dt.getDate();
       System.out.println();
       Runtime.getRuntime().exec("wget http://www.cs.rmit.edu./students");
       System.out.println("THE PROGRAMMING IS RUNNING,IF  WANT  EXIT,PRESS CTRL + Z\n");
       System.out.println("Today is: " + dt );
           for(int i= 1 ; i > 0; i++)
           {
              Date dt1 = new Date();
              day1 = dt1.getDate();
              if( i != day1)
              {
              System.out.println("this  is: " + i );
               i = day1;
              change = check_web();
              }
               if (change == 1)
                break;
           }
         }
     catch(IOException ex)
      {
           System.out.println("hello,try");
      }
   }

    public static int check_web()
    {
    try{
    int ch=0;
    String line="";
    String file = "index.html";
    String file1 = "index.html.1";
    Runtime.getRuntime().exec("wget http://www.cs.rmit.edu./students");
    Runtime.getRuntime().exec("diff index.html index.html.l > diff.");
    File f = new File("diff.");
    if (f.length()!=0)
    {
     ch = 1;
     Runtime.getRuntime().exec("mail @cs.rmit.edu. < diff."); 
    }
    else
     System.out.println("the webpage was not changed!!!");
     File f1 = new File(file);
     File f2 = new File(file1);
     if(ch == 1)
     {
      f1.delete(); 
     }
     else
      f2.delete(); 

      return ch;
    }
    catch(IOException e)
    {
      System.out.println("hello,try again!!!");
      return 1;
    }
  }

}
