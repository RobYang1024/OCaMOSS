
import java.io.*;
import java.util.*;
import java.text.*;
import java.net.*;



public class BruteForce 
{
                                    
    
    
    private  int consonantUpperBound = CrackingConstants.consonantUpperBound;
    private  int consonantLowerBound = CrackingConstants.consonantLowerBound;
    private  int vowelUpperBound = CrackingConstants.vowelUpperBound;
    private  int vowelLowerBound = CrackingConstants.vowelLowerBound;
    
    
    
    
    private int verbose = CrackingConstants.quietMode;
    private int scanType = CrackingConstants.casedScan;

    private int passwordsTried = 0;
 
	
	
	public static void main(String args[])
	{
		 int tStart;
		 int tFinish;
 		DateFormat longTimestamp = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	        
	    BruteForce  pwForcer = new BruteForce();

	    if(args.length > 0)
	    {
	        for(int i = 0; i < args.length; i++)
	        {
        		if((args[i].indexOf("-h") > -1) || (args[i].indexOf("-H") > -1))
        		    {
        			System.out.println("\n-s  -S\tonly tests lower  passwords.");
        			System.out.println("\n-v\tprints the patterns as    tried.");
        			System.out.println("-V\tprints out the patterns and the passwords as    generated. \n\tThis option slows the program considerably.\n");
        			return;
        		    }	
        		else if(args[i].indexOf("-v") > -1) 
        		    pwForcer.verbose = CrackingConstants.verboseMode1;
        		else if(args[i].indexOf("-V") > -1)
        		    pwForcer.verbose = CrackingConstants.verboseMode2;
        		else if((args[i].indexOf("-s") > -1) || (args[i].indexOf("-S") > -1))
        		    pwForcer.scanType = CrackingConstants.simpleScan;
		    }
	    }

        
	    System.out.println("\n\n********************************\n");
		System.out.println("Starting brute force run at " + 
		    longTimestamp.format(new Date()));
		if(args.length > 0)
		{
			String arguments = "";
			for( i =0; i < args.length; i++)
				arguments += args[i] + " ";
			System.out.println("\nOptions: " + arguments + "\n");
		}
		if(pwForcer.scanType == CrackingConstants.simpleScan)
    	    System.out.println("Only lower  passwords  tried.");
    	else
    	    System.out.println("Both lower and upper  passwords  tried.");
	    System.out.println("\n********************************\n");

	    tStart = System.currentTimeMillis();
	    pwForcer.run();
	    tFinish = System.currentTimeMillis();
	    
        if (pwForcer.scanType == CrackingConstants.casedScan)
        {
	        
    	    
	        
	        
	        System.out.println ("\n\n" + pwForcer.passwordsTried + " passwords were generated (out of a possible " + (26 * 26 * 26 * 8) + ")");
	        System.out.println ("That is " + pwForcer.passwordsTried/8  + " unique three letter combinations were tried (out of a possible " + (26 * 26 * 26) + ")");
        }
        else
        {
    	    System.out.println ("\n\n" + pwForcer.passwordsTried + " passwords were generated (out of a possible " + (26 * 26 * 26) + ")\n");
        }
        
	    
	    System.out.println("\n********************************\n");
		System.out.println("Finished brute force run at " + 
		    longTimestamp.format(new Date()));
		System.out.println("Time taken: " + ((tFinish - tStart)/1000) + " seconds");
	    System.out.println("\n********************************");
	}   

	
    public BruteForce()
    {
    }   

	
    private void run()
    {
        
        
         leftIndex = 0;
         midIndex = 0;
         rightIndex = 0;
        
        
		
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying stutters (AAA, aaa, etc.)");
        for( i = vowelLowerBound; i <= consonantUpperBound; i++)
        {
            leftIndex = i;
            midIndex = i;
            rightIndex = i;
            if(tryLogin(leftIndex, midIndex, rightIndex))
               return;
        }
            
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying consonant-vowel-consonant patterns.");
        for(leftIndex = consonantLowerBound; leftIndex <= consonantUpperBound; leftIndex++)
            for(midIndex = vowelLowerBound; midIndex <= vowelUpperBound; midIndex++)
                for (rightIndex = consonantLowerBound; rightIndex <= consonantUpperBound; rightIndex++)
                    if(tryLogin(leftIndex, midIndex, rightIndex))
                        return;
            
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying consonant-vowel-vowel patterns.");
        for(leftIndex = consonantLowerBound; leftIndex <= consonantUpperBound; leftIndex++)
            for(midIndex = vowelLowerBound; midIndex <= vowelUpperBound; midIndex++)
                for (rightIndex = vowelLowerBound; rightIndex <= vowelUpperBound; rightIndex++)
                    if(tryLogin(leftIndex, midIndex, rightIndex))
                        return;
            
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying vowel-consonant-vowel patterns.");
        for(leftIndex = vowelLowerBound; leftIndex <= vowelUpperBound; leftIndex++)
            for(midIndex = consonantLowerBound; midIndex <= consonantUpperBound; midIndex++)
                for (rightIndex = vowelLowerBound; rightIndex <= vowelUpperBound; rightIndex++)
                    if(tryLogin(leftIndex, midIndex, rightIndex))
                        return;
    
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying vowel-consonant-consonant patterns.");
        for(leftIndex = vowelLowerBound; leftIndex <= vowelUpperBound; leftIndex++)
            for(midIndex = consonantLowerBound; midIndex <= consonantUpperBound; midIndex++)
                for (rightIndex = consonantLowerBound; rightIndex <= consonantUpperBound; rightIndex++)
                    if(tryLogin(leftIndex, midIndex, rightIndex))
                        return;
    
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying vowel-vowel-consonant patterns.");
        for(leftIndex = vowelLowerBound; leftIndex <= vowelUpperBound; leftIndex++)
            for(midIndex = vowelLowerBound; midIndex <= vowelUpperBound; midIndex++)
                for (rightIndex = consonantLowerBound; rightIndex <= consonantUpperBound; rightIndex++)
                    if(tryLogin(leftIndex, midIndex, rightIndex))
                        return;
            
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying consonant-consonant-vowel patterns.");
        for(leftIndex = consonantLowerBound; leftIndex <= consonantUpperBound; leftIndex++)
            for(midIndex = consonantLowerBound; midIndex <= consonantUpperBound; midIndex++)
                for (rightIndex = vowelLowerBound; rightIndex <= vowelUpperBound; rightIndex++)
                    if(tryLogin(leftIndex, midIndex, rightIndex))
                        return;
            
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying  remaining vowel-vowel-vowel patterns.");
        for(leftIndex = vowelLowerBound; leftIndex <= vowelUpperBound; leftIndex++)
            for(midIndex = vowelLowerBound; midIndex <= vowelUpperBound; midIndex++)
                for (rightIndex = vowelLowerBound; rightIndex <= vowelUpperBound; rightIndex++)
                    if((leftIndex == midIndex) && (leftIndex == rightIndex))
                    {
                        
                    }
                    else
                    {
                        if(tryLogin(leftIndex, midIndex, rightIndex))
                            return;
                    }
            
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying  remaining consonant-consonant-consonant patterns.");
        for(leftIndex = consonantLowerBound; leftIndex <= consonantUpperBound; leftIndex++)
            for(midIndex = consonantLowerBound; midIndex <= consonantUpperBound; midIndex++)
                for (rightIndex = consonantLowerBound; rightIndex <= consonantUpperBound; rightIndex++)
                    if((leftIndex == midIndex) && (leftIndex == rightIndex))
                    {
                        
                    }
                    else
                    {
                        if(tryLogin(leftIndex, midIndex, rightIndex))
                            return;
                    }
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying monographs (A, a, etc.)");
        for ( i = 0; i <= consonantUpperBound; i++)
        {
            leftIndex = i;
            midIndex = -1;
            rightIndex = -1;
            if(tryLogin(leftIndex, midIndex, rightIndex))
               return;
        }
        
        
        
    	if(verbose > CrackingConstants.quietMode)
    	    System.out.println("Trying bigraphs (AA, aa, etc.)");
        for( i = 0; i <= consonantUpperBound; i++)
        {
        	for( j = 0; j <= consonantUpperBound; j++)
        	{
            	leftIndex = i;
            	midIndex = j;
        	    rightIndex = -1;
    	        if(tryLogin(leftIndex, midIndex, rightIndex))
	               return;
            }
        }
        
        return;
     }   

	
    private boolean tryLogin( int leftIndex, int midIndex, int rightIndex)
    {
        
        LoginAttempt login = new LoginAttempt();
        LoginAttemptResults results = new LoginAttemptResults();

        
        CasePasswords casedPasswords = new CasePasswords(verbose);

        
        
        String tail = "";

        results = login.tryPasswords(casedPasswords.createCasedPasswords(leftIndex, midIndex, rightIndex, tail, CrackingConstants.lowerChars, CrackingConstants.upperChars, scanType), passwordsTried);
        passwordsTried = results.getPasswordsTried();
        return results.getSuccess();
    }
         
} 
