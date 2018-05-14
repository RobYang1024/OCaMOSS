





import java.util.*;
import java.io.*;
import java.net.*;

public class Watchdog extends TimerTask
{
	public void run()
	{
		Runtime t = Runtime.getRuntime();
	  	Process pr= null;
	  	String Fmd5,Smd5,temp1;
	  	int index;
          
	 	try
          	{
		    
		    pr = t.exec("md5sum csfirst.html");

                    InputStreamReader stre = new InputStreamReader(pr.getInputStream());
                    BufferedReader bread = new BufferedReader(stre);
		    
		    s = bread.readLine();
		    index = s.indexOf(' ');
		    Fmd5 = s.substring(0,index);
		    System.out.println(Fmd5);
		    
		    pr = null;
		    
		    pr = t.exec("wget http://www.cs.rmit.edu./students/");
		    pr = null;
		    
		    pr = t.exec("md5sum index.html");
		    

		    InputStreamReader stre1 = new InputStreamReader(pr.getInputStream());
                    BufferedReader bread1 = new BufferedReader(stre1);
		    
		    temp1 = bread1.readLine();
		    index = temp1.indexOf(' ');
		    Smd5 = temp1.substring(0,index);
		    System.out.println(Smd5);
		
		    pr = null;
		
		    if(Fmd5 == Smd5)
		       System.out.println("  changes Detected");
		    else
		    {
		       pr = t.exec("diff csfirst.html index.html > report.html");
		       pr = null;
		       
		       try{
		       Thread.sleep(10000);
		       }catch(Exception e){}
		       
		       pr = t.exec(" Message.txt | mutt -s Chnages  Webpage -a report.html -x @yallara.cs.rmit.edu.");
		     
		       
		       
		    }   
		    
    	        }catch(java.io.IOException e){}
	}
}		
