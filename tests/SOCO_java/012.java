import java.net.*; 
import java.io.*; 
import java.util.regex.*;
import java.util.Date;
import java.util.*;
import java.text.*; 




public class WatchDog { 
  public static BufferedReader in;
  

  public static int LIMITINMINUTES=60*24;
  public static int TIMELIMIT=LIMITINMINUTES*1000*60;
  public static void main(String[] args) throws Exception { 
    
    String watchedPage = "http://www.cs.rmit.edu./students/";
    String currentPage = "";  
    
    
    System.out.println(" stop the program, press \"Alt + C\"");
    
    boolean loggedout=false;
    while (!loggedout){
      
      currentPage="";
      
      
      Date date = new Date();
       startTime=date.getTime();
      
      
      URL cs = new URL(watchedPage); 
      HttpURLConnection connection;
      URLConnection csc = cs.openConnection();      
      try {
	BufferedReader in = new BufferedReader(new InputStreamReader(csc.getInputStream())); 
	String inputLine; 
	
	while ((inputLine = in.readLine()) != null) {
	  currentPage = currentPage+inputLine;
	}
	
      }
      catch (IOException s) {    
      }
      finally {
	while(in!=null)
          in.next();
      }
      
      String lastPage=readData();
      if (lastPage.trim().equals(currentPage.trim())) {
	System.out.println("Pages match, nothing  email.");
      }
      else {
	
	
	String checkCurrentPage = currentPage.trim();
	String checkLastPage = lastPage.trim();
	int iterations;
	
	boolean lastLongestString;
	if (checkCurrentPage.length()<checkLastPage.length()) {
          iterations = checkCurrentPage.length();
	  lastLongestString = true;
	}
	else {
          iterations = checkLastPage.length();
	  lastLongestString = false;
	  
	}
	String additions = "Here  the additions  the : \n";
	boolean add=false;
	String subtractions = "Here  the parts removed from the : \n";
	boolean sub=false;
	for (int count=0; count<iterations; count++) {
          
	  if (checkLastPage.length()>count && checkCurrentPage.length()>count){
	  
            if (checkLastPage.charAt(count)!=(checkCurrentPage.charAt(count))) {
	      
	      
	      if (count<20){
		additions = "Sorry changes   together  distinguish additions and subtractions  . Here is where  : "+ checkCurrentPage.substring(count, checkCurrentPage.length());
		count = iterations;
	      }
	      else {
		
		
		checkCurrentPage= checkCurrentPage.substring(count, checkCurrentPage.length());
		checkLastPage=checkLastPage.substring(count, checkLastPage.length());
		iterations=iterations-count;
		count=0;

		
		
		
		String regexAdd="";
		if (checkLastPage.length()<20){
		  regexAdd=checkLastPage.substring(count, checkLastPage.length());
		}
		else {	  
		  regexAdd=checkLastPage.substring(0,19);
		}
		String [] changes=checkCurrentPage.split(regexAdd, 2);
		int changeslength=changes.length;
		
		if (changeslength>1){
		  
		  add=true;
		  additions = additions + changes[0];	  
		  
		  
		  if (changeslength>1){
		    checkCurrentPage=regexAdd+changes[1];
		  }
		  else {
		    if (lastLongestString==true) 
	              count=iterations;
		  }  
		}
		else { 
	  		  
		  
		  
		  String regexSub="";
		  if (checkCurrentPage.length()<20){
		    regexSub=checkCurrentPage.substring(count, checkCurrentPage.length());
		  }
		  else {	  
		    regexSub=checkCurrentPage.substring(0,19);
		  }
		  String [] changesSub=checkLastPage.split(regexSub, 2);
		  int changeslengthSub=changesSub.length;
		  
		  if (changeslengthSub>1){
		    
		    sub=true;
		    subtractions = subtractions + changesSub[0];	  
		    
		    
		    if (changeslengthSub>1){
		      checkLastPage=regexSub+changesSub[1];
		    }
		    else {
		      if (lastLongestString==false) 
		      count=iterations;
		    }
		    
		    
		  }
		}
	      }

            } 
	  } 
	} 
	
	
	String emailBody="Changes   have been . \n"+additions+subtractions;

	
	sendEmail(emailBody);
      }

      
      writeData(currentPage);
      
      
      wait24(startTime);
    } 
  } 
  
  
  private static void wait24( int startTime) {
     boolean waiting=true;
     while(waiting){
       Date endDate = new Date();
        endTime=endDate.getTime();
       
       
       if (endTime>(TIMELIMIT+startTime)){
         
          waiting=false;
       }	
     }
  } 
     
  
  public static String readData() {
    String data;
    String lastPage="";
    try {
      BufferedReader in = new BufferedReader(new FileReader("LastVisitedPage.html"));
      while ((data = in.readLine())!=null) {
        lastPage= lastPage + data +"\n";
      }
      
    }
    catch (FileNotFoundException e1) {
      System.exit(0);
    }
    catch (IOException e2) {
      System.out.println("IO Exception, exiting");
      System.exit(0);
    }	    
    finally {
      try {
	if (null!=in) {
        in.next();
	}
    }
    catch (IOException e3) {}
    }
    return lastPage;
  }
  
  
  public static void writeData(String currentPage) {
    PrintWriter out;
      try {
	out = new PrintWriter (new BufferedWriter(new FileWriter("LastVisitedPage.html")));
	out.println(currentPage);
	
	
      }
      catch (IllegalArgumentException e1) {
	System.out.println ("Sorry, 't write  file. None of changes in this session have been saved");
	System.exit(0);
      }
      catch (IOException e2) {
	System.out.println ("Sorry, 't write  file. None of changes in this session have been saved");
	System.exit(0);
	}
      finally {}    
  }  

 
 
 
  public static void sendEmail(String emailBody){
    
    Socket smtpSocket =null;
    DataOutputStream os = null;
    InputStreamReader is = null ;

    Date dDate = new Date();
    DateFormat dFormat = DateFormat.getDateInstance(DateFormat.FULL,Locale.US);

    try{ 
      smtpSocket = new Socket(".rmit.edu.", 25);
      os = new DataOutputStream(smtpSocket.getOutputStream());
      is = new InputStreamReader(smtpSocket.getInputStream());
      BufferedReader  = new BufferedReader(is);

      if(smtpSocket != null && os != null && is != null){ 
      
	try {   
	  os.writeBytes("HELO .rmit.edu.\r\n");
	  
	  
	  os.writeBytes("MAIL From: <@.rmit.edu.>\r\n");

	  
	  os.writeBytes("RCPT : <@cs.rmit.edu.>\r\n");

	  
	  
	  os.writeBytes("DATA\r\n");

	  os.writeBytes("X-Mailer: Via Java\r\n");
	  os.writeBytes("DATE: " + dFormat.format(dDate) + "\r\n");
	  os.writeBytes("From:  <@cs.rmit.edu.>\r\n");
	  os.writeBytes(":   <@cs.rmit.edu.>\r\n");

	  os.writeBytes("Subject:  updated\r\n");
	  os.writeBytes(emailBody + "\r\n");
	  os.writeBytes("\r\n.\r\n");
	  os.writeBytes("QUIT\r\n");

	  
	  
	  String responseline;
	  while((responseline=is.readLine())!=null){  
            
            if(responseline.indexOf("Ok") != -1) {
            break;
            }
	  }
	}
	catch(Exception e){  
	  System.out.println("Cannot send email as  error occurred.");  
	}
      }
      else 
	System.out.println("smtpSocket  another variable is null!");
    } 
    catch(Exception e){ 
      System.out.println("Host unknown"); 
    }
  } 
   
} 


