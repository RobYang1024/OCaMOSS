


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Watchdog {
    static final String LOGON_SITE = WatchdogPropertyHelper.getProperty("logonSite");
    static final String PREVIOUS_FILE_NAME = WatchdogPropertyHelper.getProperty("previousFileName");
    static final String CURRENT_FILE_NAME = WatchdogPropertyHelper.getProperty("currentFileName");
    static final String TIME_IN_MILLS = WatchdogPropertyHelper.getProperty("timeInMilliseconds");    


    static final String SMTP_SERVER = MailsendPropertyHelper.getProperty("smtpServer");
    static final String RECIPIENT_EMAIL = MailsendPropertyHelper.getProperty("recipient");
    static final String SENDER_EMAIL = MailsendPropertyHelper.getProperty("sender");
    static final String MESSAGE_HEADER = MailsendPropertyHelper.getProperty("messageHeader");    
    
	private static final String COMMAND = WatchdogPropertyHelper.getProperty("unixCommand");    
	
	
	public static void main(String[] args) {
		System.out.println(" Monitoring the Web : "+ LOGON_SITE);
		System.out.println("The output of this is written  the file: "+ PREVIOUS_FILE_NAME);
		System.out.println("The webpage   monitored every: "+ TIME_IN_MILLS+ " milliseconds");		
		
		Watchdog watchdog = new Watchdog();
		watchdog.accessWebPage(LOGON_SITE);
	}
	
	public void accessWebPage( String urlString ) {
		BufferedReader reader = null;
		BufferedReader readerNew = null;		
		String data = null;
		

        FileOutputStream out = null; 
        FileOutputStream outNew = null; 
        PrintStream p = null; 
        PrintStream pNew = null; 

		URL url = null;

		
		
		while (true) {
			try {
	
	            out = new FileOutputStream(PREVIOUS_FILE_NAME);
	
	            
	            p = new PrintStream( out );
			
				url = new URL(urlString);            		            
	
				reader = new BufferedReader (new InputStreamReader(url.openStream()));
				
	
				
				while (( data = reader.readLine()) != null ) {
					p.println(data);										
					
				}
				System.out.println("Completed writing the output  the file: "+ PREVIOUS_FILE_NAME);				
			
			}
			catch (MalformedURLException mfue) {
				mfue.printStackTrace();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			finally {
	           p.close();
			}		
			
			
			
			try {
				
				
				

				System.out.println("Sleeping for : "+ TIME_IN_MILLS+ " milliseconds");				
				Thread.sleep(Integer.valueOf(TIME_IN_MILLS).intValue());
	
	            outNew = new FileOutputStream(CURRENT_FILE_NAME);
	
	            
	            pNew = new PrintStream(outNew);
			
	            
				readerNew = new BufferedReader (new InputStreamReader(url.openStream()));
				
				while (( data = readerNew.readLine()) != null ) {
					pNew.println(data);
					
				}
			
				System.out.println("Completed writing the output  the file: "+ PREVIOUS_FILE_NAME);								
			}
			catch (MalformedURLException mfue) {
				mfue.printStackTrace();
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
			
			catch (InterruptedException e) {
				e.printStackTrace();
			}		
			
			finally {
	           pNew.close();
			}		
			
			
			InputStream inputStream = null;
			int count = 0; 
						
			System.out.println(" comparing the the current content with the previous content ");					
			
			System.out.println("Executing the command: "  + COMMAND);			
			
			try {			
				
				
				
				Process process = Runtime.getRuntime().exec(COMMAND);		

				process.waitFor();
								
				inputStream = process.getInputStream();
		
			} catch (Exception e) {
				
				System.out.println("Error Executing the command: "  + COMMAND+ "\n "  + e.toString());
			}
	
	
	
		
			try {
				
				
				
				count = inputStream.available();
									
								if (count > 0) {
					byte[] buffer = new byte[count];
					inputStream.read(buffer);
									
					String stdout = new String(buffer);
					
					System.out.println("The following changes has occured in the web ");					
					System.out.println(stdout);
					
					
					Mailsend.send(SMTP_SERVER, RECIPIENT_EMAIL, SENDER_EMAIL, MESSAGE_HEADER, stdout);				
				}
				
				else {
					System.out.println(" has been  change  the site");
				}
				
			} catch (Exception g) {
									
				System.out.println("Exception: "  + g.toString());
			}
		}		
						
	}		
		
		
	public Watchdog() {

	}


}


