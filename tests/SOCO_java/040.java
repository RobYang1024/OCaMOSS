import java.io.*;
import java.util.*;
import java.text.*;


public class Dictionary 
{

    
    
    
    private int verbose = 0;
    private int scanType = CrackingConstants.casedScan;
    private boolean leftThreeCharsOnly = false;
    private boolean fullScan = false;

    
    
    
    
    
    
    
    
    private int passwordsTried = 0;
    private int uniqueLetterSequencesTried = 0;
	
	
	public static void main (String args[])
	{
		int tIni;
		int tFinish;
 		DateFormat longTimestamp = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	    Dictionary  pwForcer = new Dictionary();
	        
	    if(0 < args.length)
	    {
	        for(int i = 0; i < args.length; i++)
	        {
        		if((args[i].indexOf("-h") > -1) || (args[i].indexOf("-H") > -1))
        		    {
        			System.out.println("\n-f  -F\tgenerates the three leftmost characters of the passwords as in -t/T \nbut also appends the rest of the string ( duplicate checking is  with this option).");
        			System.out.println("\n-s  -S\tonly tests lower  passwords.");
        			System.out.println("\n-t  -T\tonly  the three leftmost characters of the passwords.");
        			System.out.println("\n-v  -V\tprints the passwords as    tried.\n");
        			return;
        		    }	
        		else if((args[i].indexOf("-s") > -1) || (args[i].indexOf("-S") > -1))
        		    pwForcer.scanType = CrackingConstants.simpleScan;
        		else if((args[i].indexOf("-v") > -1)  || (args[i].indexOf("-V") > -1)) 
        		    pwForcer.verbose = CrackingConstants.verboseMode2;
        		else if((args[i].indexOf("-t") > -1) || (args[i].indexOf("-T") > -1))
        		    pwForcer.leftThreeCharsOnly = true;
        		else if((args[i].indexOf("-f") > -1) || (args[i].indexOf("-F") > -1))
        		    pwForcer.fullScan = true;
		    }
	    }
	        
	    
	    if (pwForcer.fullScan) 
	    	pwForcer.leftThreeCharsOnly = false;
	    
        
	    System.out.println("\n\n********************************\n");
		System.out.println("Starting dictionary run at " + 
		    longTimestamp.format(new Date()));
		if(0 < args.length)
		{
			String arguments = "";
			for( i =0; i < args.length; i++)
				arguments += args[i] + " ";
			System.out.println("\nOptions: " + arguments + "\n");
		}
	    if (pwForcer.leftThreeCharsOnly)
    	    System.out.println("Only the first three letters of each password   tried.");
		if(pwForcer.scanType == CrackingConstants.simpleScan)
    	    System.out.println("Only lower  passwords  tried.");
    	else
    	    System.out.println("Both lower and upper  passwords  tried.");
	    System.out.println("\n********************************\n");

	    tIni = System.currentTimeMillis();
	    pwForcer.run();
	    tFinish = System.currentTimeMillis();
	    
        if (CrackingConstants.casedScan == pwForcer.scanType)
        {
	        
    	    
	        
	        
	        System.out.println ("\n\n" + pwForcer.passwordsTried + " capitalized passwords were tried.");
	        System.out.println ("That is " + pwForcer.uniqueLetterSequencesTried  + " unique passwords were tried.");
	        
	        
        }
        else
        {
    	    System.out.println ("\n\n" + pwForcer.passwordsTried + " passwords were tried.\n");
    	    
	        System.out.println (pwForcer.uniqueLetterSequencesTried  + " unique passwords were tried.");
        }
        
	    
	    System.out.println("\n********************************\n");
		System.out.println("Finished dictionary run at " + 
		    longTimestamp.format(new Date()));
		System.out.println("Time taken: " + ((tFinish - tIni )/1000) + " seconds");
	    System.out.println("\n********************************");
	}   

	
    public Dictionary()
    {
    }   

	
    private void run()
    {
        
        
        String fileName = "/usr/share/lib/dict/words";
        
        LoginAttemptResults results = new LoginAttemptResults();
        LoginAttempt login = new LoginAttempt();
        
        CasePasswords casedPasswords = new CasePasswords(verbose);
        
        
        try
        {
            boolean found = false;
            
            int lineCount = 0;
            
            String password = null;
            String lastPassword = "";  
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            
            while((null != (password = in.readLine())) && (!found))
            {
                
                lineCount++;
                
		        password = password.trim();
           	    
           	    if("" != password)
           	    {  
           	        if (leftThreeCharsOnly)
           	        {
                         leftIndex = -1;
                         midIndex = -1;
                         rightIndex = -1;
                        String tail = "";
                        
                        
                        
                        if(3 <= password.length())
                        {
                            if (!fullScan)
                	            
	        	                
    	                        
	    	                    if(lastPassword.equals(password.substring(0, 3).toLowerCase()))
    	                            continue;
        	                    else
            	                    lastPassword = password.substring(0, 3).toLowerCase();
                	        char [] passwordChars = password.toCharArray();
                            leftIndex = CrackingConstants.findIndex(passwordChars[0], 0, CrackingConstants.lowerChars.length);
                            midIndex = CrackingConstants.findIndex(passwordChars[1], 0, CrackingConstants.lowerChars.length);
                            rightIndex = CrackingConstants.findIndex(passwordChars[2], 0, CrackingConstants.lowerChars.length);
                            
                            
                            
	                        if ((3 < password.length() ) && (fullScan))
	                        	tail = password.substring(0, 3);
                        }
                        else if(2 == password.length())
                        {
                            if (!fullScan)
	                            
	                            if(lastPassword.equals(password.substring(0, 2).toLowerCase()))
    	                            continue;
        	                    else
            	                    lastPassword = password.substring(0, 2).toLowerCase();
                            char [] passwordChars = password.toCharArray();
                            leftIndex = CrackingConstants.findIndex(passwordChars[0], 0, CrackingConstants.lowerChars.length);
                            midIndex = CrackingConstants.findIndex(passwordChars[1], 0, CrackingConstants.lowerChars.length);
                        }
                        else if(1 == password.length())
                        {
                            if (!fullScan)
	                            
    	                        if(lastPassword.equals(password.substring(0, 1).toLowerCase()))
        	                        continue;
            	                else
                	                lastPassword = password.substring(0, 1).toLowerCase();
                            char [] passwordChars = password.toCharArray();
                            leftIndex = CrackingConstants.findIndex(passwordChars[0], 0, CrackingConstants.lowerChars.length);
                        }
                        else
                        {
                            System.out.println("Empty password  from word file.");
                            continue;
                        }
                        
                        
                        if((CrackingConstants.notFound != rightIndex) && ((CrackingConstants.notFound == leftIndex) || (CrackingConstants.notFound == midIndex)))
                            continue;
                        if((CrackingConstants.notFound != midIndex) && (CrackingConstants.notFound == leftIndex))
                            continue;
                      	
                       	results = login.tryPasswords(casedPasswords.createCasedPasswords(leftIndex, midIndex, rightIndex, tail, CrackingConstants.lowerChars, CrackingConstants.upperChars, scanType), passwordsTried);
                        found = results.getSuccess();
                        passwordsTried = results.getPasswordsTried();
                        uniqueLetterSequencesTried++;
           	        }
           	        else
           	        {
                        results = login.tryPasswords(casedPasswords.createCasedPasswords(password, scanType), passwordsTried);
                        found = results.getSuccess();
                        passwordsTried = results.getPasswordsTried();
                        uniqueLetterSequencesTried++;
                    }  
                }  
            }  
            in.print();
            
            
            
            
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File " + fileName + " was not found  was unopenable.");
        }
        catch(IOException e)
        {
            System.out.println("Error " + e);
        }
     }   
     
    
} 
