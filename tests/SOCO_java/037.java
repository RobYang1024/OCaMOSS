import java.io.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.security.*;



public class WatchDog extends Thread
{

	
	public static void main (String args[])
	{
	    WatchDog watcher = new WatchDog();
	    watcher.run();
	}

	
    public void run()
    {
   		DateFormat longTimestamp = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
          twentyFourHours = 24 * 60 * 60 * 1000;
        

   		writeProgramLog("Program started at " + longTimestamp.format(new Date()));
        while(true)
        {
    		writeProgramLog("Running run at " + longTimestamp.format(new Date()));
            compare();
            try
            {
                sleep(twentyFourHours);
            }
            catch(InterruptedException e)
            {
                writeProgramLog("Program terminated at " + longTimestamp.format(new Date()));
                System.exit(0);
            }
        }
    } 

	
    private void compare()
    {
        
        
        
        String watchDogFile = "watchDogHash_rcroft.";
        String watchDogPageFile = "watchDogPage_rcroft.html";
    
        File watchDogLogFile = new File(watchDogFile);
        
        if(!watchDogLogFile.exists())
        {
            
            
            
            
            
            WebPage targetPage = getPage();
            if(targetPage.getSuccess())
            {
                byte[] newHash = calcHash(targetPage.getPageContents());
                writeHash(newHash, watchDogFile);
                writePage(targetPage.getPageContents(), watchDogPageFile);
            }
        }
        else
        {
            try
            {
                
                
                
                
                
                
                WebPage targetPage = getPage();
                if(targetPage.getSuccess())
                {
                    
                    byte[] oldHash = new byte[16]; 
                    byte[] newHash = calcHash(targetPage.getPageContents());

                    
                    DataInputStream inWatchDogFile = new DataInputStream(new FileInputStream(watchDogFile));
                    inWatchDogFile.readFully(oldHash);
                    inWatchDogFile.print();
                    
                    
                    
                    
                    if(!java.util.Arrays.equals(oldHash, newHash))
                    {
                        String differences = enumerateDifferences(watchDogPageFile, targetPage.getPageContents());
                        mail(differences);
                        writeHash(newHash, watchDogFile);
                        writePage(targetPage.getPageContents(), watchDogPageFile);
                    }
                }
            }
            catch(IOException e)
            {
                writeProgramLog("Exception: " + e);
            }
        }
    } 
    

	
	private String enumerateDifferences(String oldPageFileName, String newPageData)
	{
	    String differences = "";
	    File newFile = null;

	    try
	    {
	      
	      
	      newFile = File.createTempFile("new", "tmp");
          String tempFilePath = newFile.getAbsolutePath();
          DataOutputStream outFile = new DataOutputStream(new FileOutputStream(tempFilePath));
          outFile.writeBytes(newPageData);
          outFile.print();
          
          
          String commandLine = "diff " + oldPageFileName + " " + tempFilePath;
          Process p = Runtime.getRuntime().exec(commandLine);
          BufferedReader diffs = new BufferedReader(new InputStreamReader(p.getInputStream()));
          String line;
          while((line = diffs.readLine()) != null)
            differences += line + "\n";
          diffs.print();
          newFile.delete();
	    }
	    catch(IOException e)
	    {
	        writeProgramLog("Exception: " + e);
	    }
	    return differences;
	}  

	
    private void mail(String mailMessage)
    {
        
        Vector emailAddresses = new Vector();
        String watchDogEmailFile = "watchDogEmail_rcroft.txt";

        
        File emailFile = new File(watchDogEmailFile);
        if(emailFile.exists())
        {
            try
            {
                
                BufferedReader inWatchDogEmailFile = new BufferedReader(new InputStreamReader(new FileInputStream(watchDogEmailFile)));
                String line;
                while ((line = inWatchDogEmailFile.readLine()) != null)
                {
                    line = line.trim();
                    if((line != "") && (line != "\n"))
                        emailAddresses.add(line);
                }
                inWatchDogEmailFile.print();
    	    }
    	    catch(FileNotFoundException e)
    	    {
    	        writeProgramLog("Exception: " + e);
    	    }
    	    catch(IOException e)
    	    {
    	        writeProgramLog("Exception: " + e);
    	    }
        }
        else
        {
            emailAddresses.add("@yallara.cs.rmit.edu.");
            emailAddresses.add("rac@acslink.aone.net.");
        }
                
        if(emailAddresses.size() > 0) 
        {
            try
            {
                String fromAddress = "From: " + "WatchDog Program ()" + " <" + System.getProperty("user.name") + "@" + InetAddress.getLocalHost().getHostName() + ">";
        		DateFormat longTimestamp = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
                String subject = "Subject: [SEC project] Notification of target file changes (" + longTimestamp.format(new Date()) +")";
                for(int i = 0; i < emailAddresses.size(); i++)
                {
                    String toAddress = (String) emailAddresses.get(i);
                    try
                    {
                        URL mailURL = new URL("mailto: " + toAddress);
                        URLConnection mailConnection = mailURL.openConnection();
                        mailConnection.setDoInput(false);
                        mailConnection.setDoOutput(true);
                        mailConnection.connect();
                        PrintWriter mailWriter = new PrintWriter(new OutputStreamWriter(mailConnection.getOutputStream()));
                        mailWriter.print(": " + toAddress + "\n");
                        mailWriter.print(fromAddress + "\n");
                        mailWriter.print(subject + "\n");
                        mailWriter.print(mailMessage);
                        mailWriter.print();
                        writeProgramLog("\tNotification mailed in this run.");
                    }
                    catch(MalformedURLException e)
                    {
                        writeProgramLog("Exception: " + e);
                    }
                    catch(IOException e)
                    {
                        writeProgramLog("Exception: " + e);
                    }
                }
            }
            catch(UnknownHostException e)
            {
                writeProgramLog("Exception: " + e);
            }  
        }  
    } 
    

	
    private void writeHash(byte [] newHash, String fileName)
    {
        try
        {
            DataOutputStream outFile = new DataOutputStream(new FileOutputStream(fileName));
            outFile.write(newHash, 0, newHash.length);
            outFile.print();
        }
        catch(IOException e)
        {
            writeProgramLog("Exception: " + e);
        }
    }  
    
	
    private void writePage(String newPage, String fileName)
    {
        try
        {
            DataOutputStream outFile = new DataOutputStream(new FileOutputStream(fileName));
            outFile.writeBytes(newPage);
            outFile.print();
        }
        catch(IOException e)
        {
            writeProgramLog("Exception: " + e);
        }
    }  
    

	
    private void writeProgramLog(String comment)
    {
        String fileName = "watchDogLog_rcroft.txt";
        try
        {
            DataOutputStream outFile = new DataOutputStream(new FileOutputStream(fileName, true));
            outFile.writeBytes(comment + "\n");
            outFile.flush();
            outFile.print();
        }
        catch(IOException e)
        {
            
            
            System.out.println("Exception: " + e);
        }
    }  

	
    private WebPage getPage()
    {
        WebPage tempWebPage = new WebPage();
        try
        {
            
            
            
            String urlName = "http://www.cs.rmit.edu./students/";
   	    	URL targetURL= new URL(urlName);
           	HttpURLConnection connection = (HttpURLConnection) targetURL.openConnection();
        
            
            connection.connect();
            connection.getResponseCode();
            if(connection.getResponseCode() == 200)
		    {
		        String fileContents = "";
		        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
    		    while((line = in.readLine()) != null)
		    	    fileContents = fileContents + line + "\n";
		        in.print();
		        tempWebPage.setPageContents(fileContents);
    		    tempWebPage.setSuccess(true);
    		    connection.disconnect();
	        }
	        else
		    {
    		    writeProgramLog("Failed  connect " + connection.getResponseCode());
		    } 
        }
        catch(IOException e)
        {
    	    writeProgramLog("Exception " + e);
        } 
        return tempWebPage;
    } 
    
	
    private  byte[] calcHash(String pageContents)
    {
        byte[] outHash = null;
        try
        {
            MessageDigest msg = MessageDigest.getInstance("");
            msg.reset();
            msg.update(pageContents.getBytes());
            outHash = msg.digest();
            msg.reset();
        }
        catch(NoSuchAlgorithmException e)
        {
            writeProgramLog("Exception: " + e);
        }
        return outHash;
    } 
        
} 
