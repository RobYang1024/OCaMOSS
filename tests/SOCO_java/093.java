
import java.io.*;
import java.text.*;

public class TestWatchDog{
       
       class NegativeValueException extends Exception{
             public NegativeValueException() { super("It is not  Integer." ); }
             public NegativeValueException(int n) { super(n  + "- Negative Number Input.");}
             public NegativeValueException(double n) {super(n +"Not  Integer.");}
             public NegativeValueException(String s){super("Invalid Data Type Input");}
       }
       
       public int getNumber()throws NegativeValueException, NumberFormatException, IOException{
		      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		      int number;
		      try{
			      number = Integer.parseInt(stdin.readLine());
		      }catch (NumberFormatException e){throw e;}
		      if (number< 0) throw new NegativeValueException(number);
		      return number;
	   }
	   
	   public String getStringInfo()throws Exception{
		      BufferedReader stdin =new BufferedReader(new InputStreamReader(System.in));
		      String strInfo = stdin.readLine();
		      if ((strInfo.trim().equals(""))||(strInfo.equals("\r")))
		         throw new Exception( strInfo +"  Invalid input!");
	          return strInfo;
	   }
	   
	   public String getAnyKey()throws Exception{
		      BufferedReader stdin =new BufferedReader(new InputStreamReader(System.in));
		      String key= stdin.readLine();
	          return key;
	   }
	   
	   public static void main (String[]args){

               String spec = "";
               String smtpserver= "";
               String mfrom = "";
               String mto = "";

               int port = 0;
               int option =0;
               int duration = 0;
               int interval = 0;
               boolean valid = false;
               TestWatchDog twd = new TestWatchDog();
               WatchDog wd = new WatchDog();

               System.out.println("\n ***********************************************************");
               System.out.println(" *                                                         *");
               System.out.println(" *      WatchDog Program  ------ SEC  Assignment Two       *");
               System.out.println(" *   --------------------------------------------------    *");
               System.out.println(" *           Author:     ID              *");
               System.out.println(" *   --------------------------------------------------    *");
               System.out.println(" *                                                         *");
               System.out.println(" * < Options >                                             *");
               System.out.println(" *  1). Uses the default configuration  monitor Web Site *");
               System.out.println(" *               ----- http://www.cs.rmit.edu./students/ *");
               System.out.println(" *  2). Monitor the Web Site that user specified           *");
               System.out.println(" *                                                         *");
               System.out.println(" ***********************************************************");
               while (!valid){
                    System.out.print("\nPlease Choose  option: ");
			   	    try{
					    option = twd.getNumber();
					    valid = true;
				    }catch(Exception e){System.out.println("Warning! Invalid Input!"
                                       + e.getMessage()+" Try again!");}
			        if ((option ==1)||(option ==2))
			             valid = true;
			        else{
			             System.out.println("Invalid Input. Try it again!");
			             valid = false;
			        }
               }
               
               if(option == 1){
                  
                  System.out.println("\n    Default Configuration:\n");
                  System.out.println("--> Monitor WebSite: " + wd.getMonitorURL());
                  System.out.println("--> Monitor Duration: " + 30*24 + " hours (30 days)");
                  System.out.println("--> Check Webpage Intervals: " + wd.getInterval()/(1000*60) + " minutes (24 hrs)");
                  System.out.println("--> SMTP server: " +wd.getSMTPServer() + " Port: " + wd.getPortnumber());
                  System.out.println("--> MailFrom: " + wd.getMailFrom());
                  System.out.println("--> MailTo: " + wd.getMailTo());
                  valid = false;
                  
                  while (!valid){
                        System.out.print("\n    Press EnterKey  :");
                         try{
                            String key = twd.getAnyKey();
                            valid = true;
                         }catch(Exception e){System.out.println(e.getMessage());}
                  }
                  
                  wd.FirstRead();
                  wd.print();

               }
               valid = false;
               
               if(option == 2){
                  System.out.println("\n  Modified the Configuration:\n");
                  
                  while (!valid){
                        System.out.println("> Input the URL of the web  <Usage: http://yallara.cs.rmit.edu./~/>: ");
                         try{
                             spec = twd.getStringInfo();
                             valid = true;
                         }catch(Exception e){System.out.println(e.getMessage());}
                  }
                  valid = false;
                  
                  while (!valid){
                        System.out.println("> Input the Monitor Duration <Usage: 24 - (format: hours)>:");
                         try{
                             duration = twd.getNumber();
                             valid = true;
                         }catch(Exception e){System.out.println(e.getMessage());}
                  }
                  valid = false;
                  
                  while (!valid){
                        System.out.println("> Input the Interval for Checking Webpage <Usage: 30 - (format: minutes)>:");
                         try{
                             interval  = twd.getNumber();
                             valid = true;
                         }catch(Exception e){System.out.println(e.getMessage());}
                  }
                  valid = false;
                  
                  while (!valid){
                        System.out.println("> Set the Mail Server <Usage: mail.rmit.edu.>:");
                         try{
                             smtpserver  = twd.getStringInfo();
                             valid = true;
                         }catch(Exception e){System.out.println(e.getMessage());}
                  }
                  valid = false;
                  
                  while (!valid){
                        System.out.println("> Set the Port Number <Usage: 25 - (default port 25 for smtp server)>:");
                         try{
                             port  = twd.getNumber();
                             valid = true;
                         }catch(Exception e){System.out.println(e.getMessage());}
                  }
                  valid = false;
                  
                  while (!valid){
                        System.out.println("> Set the MailFrom <Usage: @cs.rmit.edu.>:");
                         try{
                             mfrom = twd.getStringInfo();
                             valid = true;
                         }catch(Exception e){System.out.println(e.getMessage());}
                  }
                  valid = false;
                  
                  while (!valid){
                        System.out.println("> Set the MailTo <Usage: @.rmit.edu.>:");
                         try{
                             mto = twd.getStringInfo();
                             valid = true;
                         }catch(Exception e){System.out.println(e.getMessage());}
                  }
                  
                  System.out.println("\n    new  Configuration:\n");
                  System.out.println("-> Monitor WebSite: " + spec);
                  System.out.println("-> Monitor Duration: " + duration + " hours");
                  System.out.println("-> Check Webpage Intervals: " + interval + " minutes");
                  System.out.println("-> SMTP server: " +smtpserver + " Port: " + port);
                  System.out.println("-> MailFrom: " + mfrom);
                  System.out.println("-> MailTo: " + mto);
                  valid = false;
                  
                  while (!valid){
                        System.out.print("\n    Press EnterKey  :");
                         try{
                            String key = twd.getAnyKey();
                            valid = true;
                         }catch(Exception e){System.out.println(e.getMessage());}
                  }
                  
                  wd.setMonitorURL(spec);
                  wd.setMonitorDuration(duration);
                  wd.setMonitorInterval(interval);
                  wd.setSMTPServer(smtpserver);
                  wd.setSMTPPort(port);
                  wd.setMailFrom(mfrom);
                  wd.setMailTo(mto);
                  
                  wd.FirstRead();
                  wd.print();
               }
       }
}