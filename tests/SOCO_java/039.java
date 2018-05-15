
public class CasePasswords
{

    
    static int verbose = CrackingConstants.quietMode;

    
	
	public void CasePasswords()
	{
    }

	
	public void CasePasswords(int inVerbose)
	{
	    verbose = inVerbose;
    }

	
    public String [] createCasedPasswords( int leftIndex, int  midIndex, int rightIndex, String tail, String [] lowerChars, String [] upperChars, int scanType)
    {
        String [] casedPasswords = null;
        
        
        
        if(scanType == CrackingConstants.casedScan)
            if(rightIndex > -1)
            {
                
                casedPasswords = new String[8];
            }
            else if(midIndex > -1)
            {
                
                casedPasswords = new String[4];
            }
            else
            {
                
                casedPasswords = new String[2];
            }
        else  
        {
            
            casedPasswords = new String[1];
        }	
                
        
        
        
        if(scanType == CrackingConstants.casedScan)
        {
            if(rightIndex > -1)
            {
                
                casedPasswords[0] = lowerChars[leftIndex] + lowerChars[midIndex] + lowerChars[rightIndex];
                casedPasswords[1] = upperChars[leftIndex] + upperChars[midIndex] + upperChars[rightIndex];
                casedPasswords[2] = lowerChars[leftIndex] + lowerChars[midIndex] + upperChars[rightIndex];
                casedPasswords[3] = lowerChars[leftIndex] + upperChars[midIndex] + lowerChars[rightIndex];
                casedPasswords[4] = upperChars[leftIndex] + lowerChars[midIndex] + lowerChars[rightIndex];
                casedPasswords[5] = upperChars[leftIndex] + upperChars[midIndex] + lowerChars[rightIndex];
                casedPasswords[6] = upperChars[leftIndex] + lowerChars[midIndex] + upperChars[rightIndex];
                casedPasswords[7] = lowerChars[leftIndex] + upperChars[midIndex] + upperChars[rightIndex];
            }
            else if(midIndex > -1)
            {
                
                casedPasswords[0] = lowerChars[leftIndex] + lowerChars[midIndex];
                casedPasswords[1] = upperChars[leftIndex] + upperChars[midIndex];
                casedPasswords[2] = lowerChars[leftIndex] + lowerChars[midIndex];
                casedPasswords[3] = lowerChars[leftIndex] + upperChars[midIndex];
            }
            else
            {
                
                casedPasswords[0] = lowerChars[leftIndex];
                casedPasswords[1] = upperChars[leftIndex];
            }
        }
        else	
        {
            if(rightIndex > -1)
            {
                
                casedPasswords[0] = lowerChars[leftIndex] + lowerChars[midIndex] + lowerChars[rightIndex];
            }
            else if(midIndex > -1)
            {
                
                casedPasswords[0] = lowerChars[leftIndex] + lowerChars[midIndex];
            }
            else
            {
                
                casedPasswords[0] = lowerChars[leftIndex];
            }
        }	
        
        
        
        
        
        if("" != tail)
        	for( i = 0; i < casedPasswords.length; i++)
        		casedPasswords[i] += tail;
                        
	    if(verbose  == CrackingConstants.verboseMode2)
	        printPasswords(casedPasswords);

        return casedPasswords;
    }  
    
	
    public String [] createCasedPasswords(String candidate, int  scanType)
    {
        
        int candLength = candidate.length();
        int arrayLength = 2 ^ candLength;
        arrayLength = 1;
        String [] shortCasedPasswords = new String[1];
        String [] casedPasswords = null;
        char[] password = new char [candidate.length()];
        
        
        if(scanType != CrackingConstants.simpleScan)
            candidate.getChars(0, candidate.length(), password, 0);
        
        
        
        
        
        
        
        
        
        
        if(scanType == CrackingConstants.simpleScan)
        {
            
            
            casedPasswords = new String[1];
            casedPasswords[0] = candidate;
        }
        else if(candidate.length() == 1)
        {
            casedPasswords = new String[2];
            casedPasswords[0] = Character.toString(Character.toLowerCase(password[0]));
            casedPasswords[1] = Character.toString(Character.toUpperCase(password[0]));
            
        }
        else if (candidate.length() == 2)
        {
            casedPasswords = new String[4];
            casedPasswords[0] = Character.toString(Character.toLowerCase(password[0])) + Character.toString(Character.toLowerCase(password[1]));
            casedPasswords[1] = Character.toString(Character.toUpperCase(password[0])) + Character.toString(Character.toUpperCase(password[1]));
            casedPasswords[2] = Character.toString(Character.toLowerCase(password[0])) + Character.toString(Character.toUpperCase(password[1]));
            casedPasswords[3] = Character.toString(Character.toUpperCase(password[0])) + Character.toString(Character.toLowerCase(password[1]));
            
        }
        else if (candidate.length() == 3)
        {
            casedPasswords = new String[8];
            casedPasswords[0] = Character.toLowerCase(password[0]) + Character.toString(Character.toLowerCase(password[1]))  + Character.toLowerCase(password[2]);
            casedPasswords[1] = Character.toUpperCase(password[0]) + Character.toString(Character.toUpperCase(password[1]))  + Character.toUpperCase(password[2]);
            casedPasswords[2] = Character.toLowerCase(password[0]) + Character.toString(Character.toLowerCase(password[1]))  + Character.toUpperCase(password[2]);
            casedPasswords[3] = Character.toLowerCase(password[0]) + Character.toString(Character.toUpperCase(password[1]))  + Character.toLowerCase(password[2]);
            casedPasswords[4] = Character.toUpperCase(password[0]) + Character.toString(Character.toLowerCase(password[1]))  + Character.toLowerCase(password[2]);
            casedPasswords[5] = Character.toUpperCase(password[0]) + Character.toString(Character.toUpperCase(password[1]))  + Character.toLowerCase(password[2]);
            casedPasswords[6] = Character.toUpperCase(password[0]) + Character.toString(Character.toLowerCase(password[1]))  + Character.toUpperCase(password[2]);
            casedPasswords[7] = Character.toLowerCase(password[0]) + Character.toString(Character.toUpperCase(password[1]))  + Character.toUpperCase(password[2]);
            
        }
        else if (candidate.length() > 3)
        {
            casedPasswords = new String[8];
            String tailCharacters = new String(password, 3, (password.length - 3));
            casedPasswords[0] = Character.toString(Character.toLowerCase(password[0])) + Character.toString(Character.toLowerCase(password[1]))  + Character.toString(Character.toLowerCase(password[2])) + tailCharacters;
            casedPasswords[1] = Character.toString(Character.toUpperCase(password[0])) + Character.toString(Character.toUpperCase(password[1]))  + Character.toString(Character.toUpperCase(password[2])) + tailCharacters;
            casedPasswords[2] = Character.toString(Character.toLowerCase(password[0])) + Character.toString(Character.toLowerCase(password[1]))  + Character.toString(Character.toUpperCase(password[2])) + tailCharacters;
            casedPasswords[3] = Character.toString(Character.toLowerCase(password[0])) + Character.toString(Character.toUpperCase(password[1]))  + Character.toString(Character.toLowerCase(password[2])) + tailCharacters;
            casedPasswords[4] = Character.toString(Character.toUpperCase(password[0])) + Character.toString(Character.toLowerCase(password[1]))  + Character.toString(Character.toLowerCase(password[2])) + tailCharacters;
            casedPasswords[5] = Character.toString(Character.toUpperCase(password[0])) + Character.toString(Character.toUpperCase(password[1]))  + Character.toString(Character.toLowerCase(password[2])) + tailCharacters;
            casedPasswords[6] = Character.toString(Character.toUpperCase(password[0])) + Character.toString(Character.toLowerCase(password[1]))  + Character.toString(Character.toUpperCase(password[2])) + tailCharacters;
            casedPasswords[7] = Character.toString(Character.toLowerCase(password[0])) + Character.toString(Character.toUpperCase(password[1]))  + Character.toString(Character.toUpperCase(password[2])) + tailCharacters;
            
        }
        
	    if(verbose == CrackingConstants.verboseMode2)
	        printPasswords(casedPasswords);

        return casedPasswords;
    }
    
   
	
    private void printPasswords(String [] passwords)
    {
        if(passwords.length > 0)
        {
           for( i = 0; i < passwords.length; i++)	
           {
                System.out.print(passwords[i] + "\t");
           }
           System.out.println("\n");
        }
    }  
    
} 
