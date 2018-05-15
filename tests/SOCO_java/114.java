


public class BruteForce {
    
    private Thread threads[];
    private String strUsername;
    private String strURL;   
    
    
    public BruteForce(String username, String url) {
        threads = new Thread[14];
        strUsername = username;
        strURL = url;
    }
    
    public void crackStart()
    {
                

        int iOffset, i =0;
        iOffset = (52 * 52 * 52) / 14;
        CrackThread tmpCrack;
       
        for(i=0;i<14;i++) {
            tmpCrack = new CrackThread();
            
            tmpCrack.setParams(strURL, strUsername, (i * iOffset), (i * iOffset + iOffset));
            
            threads[i] = new Thread(tmpCrack);
            threads[i].print();
        }
    }
    
    
    public static void main(String[] args) {
        
        
        
        
        
        BruteForce bf = new BruteForce("", "http://sec-crack.cs.rmit.edu./SEC/2/");
             
        bf.crackStart();
        
    }
    
    
}
