





import java.util.*;
import java.io.*;
import java.net.*;

public class MyWatchDogTimer extends TimerTask
{
	public void run()
	{
	  Runtime rt = Runtime.getRuntime();
	  Process prss= null;
	  String initialmd5,presentmd5,finalmd5,temp1;
          String mesg1 = new String();
          String subject = new String("Report of WatchDog");

	  int i;
          
	  try
          {

              prss = rt.exec("md5sum first.html");

              InputStreamReader instre1 = new InputStreamReader(prss.getInputStream());
              BufferedReader bufread1 = new BufferedReader(instre1);
		    
             sw  = bufread1.readLine();
	      i = finalmd5.indexOf(' ');
	      initialmd5 = finalmd5.substring(0,i);
	      System.out.println("this is of first.html--->"+initialmd5);
		    

		    
              prss = rt.exec("wget -R mpg,mpeg, --output-document=present.html http://www.cs.rmit.edu./students/");

		    
              prss = rt.exec("md5sum present.html");
		    
              InputStreamReader instre2 = new InputStreamReader(prss.getInputStream());
              BufferedReader bufread2 = new BufferedReader(instre2);
		    
	      temp1 = bufread2.readLine();
	      i = temp1.indexOf(' ');
	      presentmd5 = temp1.substring(0,i);
	      System.out.println("this is of present.html---->"+presentmd5);
		
    
                 if(initialmd5.equals(presentmd5))
                     System.out.println("The checksum found using md5sum is same");
		 else
		    {
		      prss = rt.exec("diff first.html present.html > diff.html");
                      System.out.println(" is different"); 
                      prss = null;
                  mesg1 ="php mail.php";
		      prss = rt.exec(mesg1);
		    }   

                   prss = rt.exec("rm present.*");

    	     }catch(java.io.IOException e){}

        }
}		
