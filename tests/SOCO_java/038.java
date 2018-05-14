
public class CrackingConstants 
{

    
    public static final int quietMode = 0;
    public static final  int verboseMode1 = 1;
    public static final int  verboseMode2 = 2;
    
    
    public static final int simpleScan = 0;
    public static final int casedScan = 1;
    
    
    public static final int notFound = -1;

   
    
    
    
    
    
    public static final String [] lowerChars = {"a", "e", "i", "o", "u", "y", 
                                                "b", "c","d", "f", "g", "h", 
                                                "j", "k", "l", "m", "n", "p", 
                                                "q", "r", "s", "t", "v", "w", 
                                                "x", "z", "0", "1", "2", "3",
                                                "4", "5", "6", "7", "8", "9",
                                                "\'", "`", ".", ",", "!", "\"",
                                                "&" };          
                                                        
    public static final String [] upperChars = {"A", "E", "I", "O", "U", "Y", 
                                                "B", "C", "D", "F", "G", "H", 
                                                "J", "K", "L", "M", "N", "P", 
                                                "Q", "R", "S", "T", "V", "W", 
                                                "X", "Z", "0", "1", "2", "3",
                                                "4", "5", "6", "7", "8", "9",                              
                                                "\'", "`", ".", ",", "!", "\"",
                                                "&" };          
                                                
	
    public static final int punctuationUpperBound = 42;
    public static final int punctuationLowerBound = 36;
    public static final int numbersUpperBound = 35;
    public static final int numbersLowerBound = 26;
    public static final int consonantUpperBound = 25;
    public static final int consonantLowerBound = 6;
    public static final int vowelUpperBound = 5;
    public static final int vowelLowerBound = 0;

	
    public static int findIndex(char letter, int lowerSearchBound, int upperSearchBound)
    {
        if(lowerChars.length < upperSearchBound)
           upperSearchBound = lowerChars.length;
            
        if(0 > lowerSearchBound)
           lowerSearchBound = 0;

        for(int i = lowerSearchBound; i < upperSearchBound; i++)
	    {
	        
	        
            if(lowerChars[i].indexOf(letter) > -1) 
                return i;
            if(upperChars[i].indexOf(letter) > -1) 
                return i;
        }
        return notFound;
    } 
    
} 