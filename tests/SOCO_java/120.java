


public class PasswordGen {
    
    private int iLastSeed = 0;
    private int iPasswordLength = 3;
    private String strPassword;
    
    
    public PasswordGen() {
        strPassword = "";
    }
    
    public boolean setSeed(int iSeedVal) {
        iLastSeed = iSeedVal;
        return true;
    }
    
    public String getPassword() {
        return strPassword;
    }
    
    public String getPassword(int iSeed) {
        int iRemainder, iAliquot, i;
        int arrChars[];
        boolean fDone;
        
        
        
        arrChars = new int[iPasswordLength];
        for(i = 0; i<iPasswordLength; i++)
                arrChars[i] = 0;

        fDone = false;
        iAliquot = iSeed;
        i=0;
        while(!fDone) {
            iRemainder = iAliquot % 52;
            if(iRemainder == 0) iRemainder=52;
            iAliquot = iAliquot / 52;
            
            arrChars[i] = iRemainder;
            if(i<iPasswordLength) i++;
            if(iAliquot == 0)
                fDone = true;
        }
        
        strPassword = convertPassword(arrChars);
        
        return strPassword;
    }
    
    public String getNextPassword() {
        iLastSeed++;
        strPassword = getPassword(iLastSeed);
        return strPassword;
    }
 
    private String convertPassword(int[] chars) {
        String strPwd;
        int i;
        
        strPwd = "";
        
        for(i=0; i<iPasswordLength; i++)
        {
            
            if(chars[i] != 0)
            {
                if (chars[i] < 27)
                {
                    strPwd += new Character((char)(chars[i]+64)).toString();
                }
                else
                {
                    strPwd += new Character((char)(chars[i]+70)).toString();
                }
            }
        }
        return strPwd;
    }
}
