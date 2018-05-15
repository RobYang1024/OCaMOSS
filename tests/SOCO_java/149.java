import	java.io.*;

class WatchDog {
    public static void main(String args[]) {
    
	    if (args.length<1)
	 {
       System.out.println("Correct Format Filename  email address <username@cs.rmit.edu.> of the recordkeeper"); 
        System.exit(1);	
	 }

	while (true)
		{
		
		
  FileInputStream stream=null;
  DataInputStream word=null;
  String input="   "; 


	try {


       String ls_str;
    
    	   
	  Process ls_proc = Runtime.getRuntime().exec("wget http://www.cs.rmit.edu./students");
   		try {
		Thread.sleep(2000);
		}catch (Exception e) {
    System.err.println("Caught ThreadException: " +e.getMessage());
	  }

		String[] cmd = {"//sh","-c", "diff Index2.html index.html >report.txt "};

	   ls_proc = Runtime.getRuntime().exec(cmd);
		          
			
			try {
		Thread.sleep(2000);
		}catch (Exception e) {
    System.err.println("Caught ThreadException: " +e.getMessage());
	  }
		
		
		
		if (ls_proc.exitValue()==2) 
		{
		  	   System.out.println("The file was checked for first time,  email sent");

           Process move = Runtime.getRuntime().exec("mv index.html Index2.html");
		   

		}
		else
		{

				stream = new FileInputStream ("report.txt"); 
				word =new DataInputStream(stream);


				if (word.available() !=0)
				{

					try
					{

					String[] cmd1 = {"//sh","-c","diff Index2.html index.html | mail "+args[0]};
					 Process proc = Runtime.getRuntime().exec(cmd1);
					 Thread.sleep(2000);
					Process move = Runtime.getRuntime().exec("mv index.html Index2.html");
					Thread.sleep(2000);
					System.out.println("Difference Found  , Email Sent");

					}
					catch (Exception e1) {
							System.err.println(e1);
							System.exit(1);
						
					  
						}
					 
	 
	 
				 }
				 else
					{
						   System.out.println(" Differnce Detected");


					}
		}
	}
	

	 catch (IOException e1) {
	    System.err.println(e1);
	    System.exit(1);
	
  
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
       	
try {
Thread.sleep(20000);  
    }
catch (Exception e) 
	{
System.err.println("Caught ThreadException: " +e.getMessage());
	}
		

        }   

	} 
	
   }