

import java.*;
import java.io.*;
import java.util.*;
import java.net.*;

public class WatchDog
{
 public static void main(String[] args) throws IOException
 {
   sleepTime = 24*1000*60*60;
  WatchDog wd=new WatchDog();
  wd.doWget();
  wd.doThreads(sleepTime);
 }
 public void doThreads(int sleepTime)
 {
   new MyThread("thread",sleepTime).start();
 }

 
 public void doWget()
 {
  Process p;
  Runtime rt=Runtime.getRuntime();
  try
  {
	 p=rt.exec("wget -O test0.html http://www.cs.rmit.edu./students/");
   p.destroy();
  }catch(IOException ioe)
  {System.out.println(ioe);}
  catch(NullPointerException npe)
  {System.out.println(npe);}
 }
}

class MyThread extends Thread
{
 private int sleepTime;
 private int times;
 private Process p;

 public MyThread (String str, int s)
 {
  super(str);
  times=1;
  sleepTime=s;
 }

 public void run()
 {
  Runtime rt=Runtime.getRuntime();
  char flags='1';
  String[] differents=new String[2000];
  int k,lineNum,diffNum;
  boolean isDiff=false;
  String result="result\n";


  for(int i=0;i<times;i++)
  {
   try{
    
    sleep(sleepTime);
   }
   catch(InterruptedException e)
   {}

	 try{
    p=rt.exec("wget -O "+flags+".html http://www.cs.rmit.edu./students/");
    
    p.destroy();
    flags++;
   }
   catch(IOException ioe)
   {System.out.println(ioe);}
   catch(NullPointerException npe)
   {System.out.println(npe);}

  try
  {
   File f = new File (""+(char)((int)flags-1)+".html");
   File f1 = new File (""+(char)((int)flags-2)+".html");
   FileReader fin = new FileReader (f);
   BufferedReader buf = new BufferedReader(fin);
   FileReader fin1 = new FileReader (f1);
   BufferedReader buf1 = new BufferedReader(fin1);
   String line="000";
   String line1="000";
   k=0;lineNum=1;diffNum=0;

   
   
   {
	 if(line.compareTo(line1)!=0)
	 {
	  differents[k]="line #"+lineNum+": "+line+" <---> "+line1;
	  k++;
	  isDiff=true;
     }
     line=buf.readLine();
     line1=buf1.readLine();
     lineNum++;
     diffNum=k;
     if(diffNum==2000)
     {
      System.out.println(" many differents  store!");
      System.exit(1);
     }
   }while(line!=null&&line1!=null);

   
   if(isDiff)
   {
    try
    {
     File diffFile=new File("differents"+(char)((int)flags-2)+".txt");
     FileWriter fw = new FileWriter (diffFile);
     for(int j=0;j<diffNum;j++)
      result+=differents[j]+'\n';
     fw.write (result, 0, result.length());
     fw.close ();
    } catch (FileNotFoundException exc) {
     System.out.println ("File Not Found2"+exc);
    } catch (IOException exc) {
     System.out.println ("IOException 1");
    } catch (NullPointerException exc) {
	   System.out.println ("NullPointerException");
    }

    

		try
		{
			
			Socket sk=new Socket("yallara.cs.rmit.edu.",25);

			
			InputStreamReader input=new InputStreamReader(sk.getInputStream());
			System.out.println(new BufferedReader(input).readLine());
			
			PrintStream output=new PrintStream(sk.getOutputStream());

			output.print("HELO mail.nowhere.\r\n");
			System.out.println(new BufferedReader(input).readLine());
			output.print("MAIL FROM:@cs.rmit.edu.\r\n");
			System.out.println(new BufferedReader(input).readLine());
			output.print("RCPT :@cs.rmit.edu.\r\n");
			System.out.println(new BufferedReader(input).readLine());
			output.print("DATA\r\n");
			System.out.println(new BufferedReader(input).readLine());
			output.print(result+"\r\n");
			output.print(".\r\n");

			sk.close();


		} catch (IOException exc) {
     System.out.println ("IOException 3"+exc);
    } catch (NullPointerException exc) {
	   System.out.println ("NullPointerException");
    }

   }

  } catch (FileNotFoundException exc) {
     System.out.println ("File Not Found3"+exc);
  } catch (IOException exc) {
     System.out.println ("IOException 1");
  } catch (NullPointerException exc) {
	  System.out.println ("NullPointerException");
  }


  }
 }
}

