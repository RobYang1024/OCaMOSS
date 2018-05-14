
import java.util.*;
import java.io.*;
import java.net.*;

class WatchDog
{

 public static void main (String a[])
 {
   
   try{
	for(int i=0;i<4;i++)
    {
	 String filename = "new.txt";
	 if(i==0)
	 {
	  filename="original.txt";
	 }
   	 WatchDogThread myTh = new WatchDogThread(filename);
	 Thread th = new Thread( myTh );
	 th.start();
	 th.sleep(20000); 
     
	 if(i>2)
	 { 
      Runtime.getRuntime().exec("wget http://yallara.cs.rmit.edu.:28589/mymail.php");
	 }  
    }
	
	}
	catch(Exception ex1)
	{
	System.out.println(" error while running looop "+ex1);
	} 
	
   
}

}

class WatchDogThread implements Runnable
{
 
 String filename="incorrect.txt";
 public WatchDogThread(String filename)
 {
  this.filename = filename;
 }
 

 public void run()
 {
   
   URL u = null;
	try
	 {
	 u=new URL("http://yallara.cs.rmit.edu./~/.html");
	 
 	 BufferedReader in = new BufferedReader( new InputStreamReader(u.openStream( )) );
     

    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
    String s = in.readLine();
    while( s != null)
    {
	 out.println(s);
	 s=in.readLine();
	 System.out.println("printing : "+s);
	 
	}
	System.out.println("finished  ----- ");
	in.print();
	out.print();
    
	
	
	}
	catch(Exception ert)
	{
      System.out.println("exception in thread "+ert);
	}
  
 }

}