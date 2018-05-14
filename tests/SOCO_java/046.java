import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;




public class WatchDog{
   
   
   
   
   
   public static void main (String[] args) throws InterruptedException, IOException{

    
    String urlString = "http://www.cs.rmit.edu./students/";
    
  
    String mesg = "";
    
    boolean flag = false;
    
    InputStream rtemp;
    
    if (args.length == 2) {
      	
      	System.err.println (
      		
      		"Usage : java BruteForce <Host> <Mailhost> <Sending E-mail>");
      	return;
      	
     }
    
    
    
    BufferedReader rnew;
    
    BufferedReader rold = ReadFile (urlString);
    
    SaveFile("weblog",urlString); 
   
    Date lasttime  =  CheckTime(urlString);
    
   
    Date newtime = new Date();
    
    int i = 0; 
    
    System.out.println("......"); 
    
    
    while (true) {
    	
    	   	
    	newtime =  CheckTime(urlString);
    	
    	System.out.println ("Checking "+ new Date());
    	
    	if (newtime.toString().equals(lasttime.toString())==false) {
    		
    	
    	    rnew = ReadFile (urlString);
    	    	
    		    		
    		mesg = CompareFile(rold,rnew);
    		
     		
     		SaveFile("weblog",urlString);
     		
     		
     		rold = OpenFile ("weblog");
     		
 		
    		lasttime=newtime;
    		
    	    System.out.println("Sending message");
    	    
    	    SendMail(trimtag(mesg),args[0],args[1],args[2]); 
    	    
    	    System.out.println(trimtag(mesg));
    	
    	
    	} 
    	
    	Thread.sleep (24*3600*1000);  
   	}
    
    
    
    }

  
  
  private static BufferedReader ReadFile  (String urlString) throws IOException{
  	
  	                          
 
        URL url = new URL (urlString);

        
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        
         
        InputStream in = (InputStream) uc.getInputStream();
         
        BufferedReader r = new BufferedReader (new InputStreamReader (in));
       
        
        
        return r;
      
        
   }

  

  private static BufferedReader OpenFile  (String FileName) throws IOException{
  	
        FileInputStream  in = new FileInputStream (FileName);
        
        InputStreamReader is= new InputStreamReader (in); 	                          
  
        BufferedReader r = new BufferedReader (is);
       
      
        return r;
        
        
      
        
   }


  
  
private static void SaveFile  (String FileName, String urlstring) throws IOException{
  	
  	    
  	String cmd = "wget -q "+urlstring+" -O "+ FileName ;
  	
  	
  	Runtime.getRuntime().exec(cmd); 
  	    
   }

  
  
  
   
  private static Date CheckTime (String urlString) throws IOException {
  	
  	    URL url = new URL (urlString);

        
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        
        uc.setRequestMethod ("HEAD");
        
        return (new Date (uc.getLastModified()));
        
                
       
   } 
  
  
  
  private static String CompareFile (BufferedReader inold, BufferedReader innew) throws IOException{
  	
  	
  	
  	    Vector newF= new Vector ();
        Vector oldF= new Vector ();


        int old_count=0;
	 	int new_count=0;

	 	String line="";

        StringBuffer mesg = new StringBuffer ("NEW CONTENT : \n");

	 	int j;
        
      
       
	    while ((line=inold.readLine())!= null){

	 		  if (line.trim().length()!=0){
	 		  oldF.addElement(line);
	 		 
	 	
	 		  
	 		 }

	 		 }

	 	while ((line=innew.readLine()) != null){
	 		  if (line.trim().length()!=0){
	 		  newF.addElement(line);
	         }

	 		 }

	 	for (int i=0; i<newF.size();i++){

	 		 j=0;

	 		 while (((String)newF.elementAt(i)).equals((String)oldF.elementAt(j))==false){

	 		 	j++;
                
                if (j==oldF.size()) 
                {   j--;
                	break;
                 }
	 		 	}

            
             
	 		 if (((String)newF.elementAt(i)).equals((String)oldF.elementAt(j))){

                 newF.removeElementAt(i);
	 	         i--;
	 	         oldF.removeElementAt(j);


	 		 	}

	 	 	}



	 	for (int i=0; i<newF.size();i++){

	 	  mesg.append((String)(newF.elementAt(i)));
	 	  mesg.append("\n");
          }


	    mesg.append("OLD CONTENT: \n");

         for (int i=0; i<oldF.size();i++){

          mesg.append((String)oldF.elementAt(i));
          mesg.append("\n");
       
	 	}

     


	    return mesg.toString();



	 


}



private static void SendMail (String mesg, 
           String host,String mailhost, String sending ) throws IOException {
	
    String send_cmd = "";

	try {
		
		Socket s = new Socket (host, 25);
		
	    PrintStream os = new PrintStream (s.getOutputStream());
	    
        send_cmd = "HELO " + mailhost;
        
        os.print(send_cmd + "\r\n");
        
        send_cmd = "MAIL From : website@cs.rmit.edu.";
        
        os.print(send_cmd + "\r\n");
        
        send_cmd = "RCPT  : " + sending;
        
        os.print(send_cmd + "\r\n");
        
        send_cmd = "DATA";
        
        os.print(send_cmd + "\r\n");
        
        send_cmd = ("Subject: Website Change Notice");
        
        os.print(send_cmd + "\r\n");
        
        os.print("\r\n");
        
        os.print(mesg+"\r\r\n");
        
        os.print(".\r\n");
        
        os.print("QUIT");
        
  	
	} catch (IOException e) {
		System.out.println(e);
	}
	

	
  }


private static String trimtag (String mesg){
	
	String[] taglist = {"<a", "<A", "<applet ", "<APPLET", "<img ", "<IMG "}; 
	
	String subst = "";
	
	StringBuffer tempst= new StringBuffer();
	int j = 0;
	
	int i = 0;
	
	int m = 0;
	
	
	while (mesg.length()!=0) {
	   
	   m=0;
	   
	   i = mesg.indexOf("<");
	   
	   
	   if (i!=-1) {
	     
	     tempst.append(mesg.substring(0,i));
	     
	     	
	   } 
	   else {  	
	    tempst.append(mesg.substring(0));
	    break;
       }
	   
	   
	   j = mesg.indexOf(">"); 
	
	   
	   subst=mesg.substring(i,j+1); 
	 
	   while (subst.startsWith(taglist[m])==false) {
	   	
	   	m++;
	   	
	   	if (m==taglist.length) 
	   	
	   	{   m--;
	   		break;
	    }
	   	
	   	}	
	   
	   if (subst.startsWith(taglist[m])) tempst.append (subst);
	   
	   
	   mesg = mesg.substring(j+1);
	   
	   
	   }
	
	  return tempst.toString();
	  
	}




} 