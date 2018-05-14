
import java.util.*;
import java.io.*;

public class Result {
    
    private String strURL;
    
    private String strUsername;
    
    private String strPassword;
    
    private Date dtTimeStart;
    
    private Date dtTimeEnd;
    
    private int iAttempts;
    
    
    public Result(String url, String username, String password, Date startDate, Date endDate, int attempts) {
        strURL = url;
        strUsername = username;
        strPassword = password;
        dtTimeStart = startDate;
        dtTimeEnd = endDate;
        iAttempts = attempts;
    }
    
    
    public String toString() {
        String output;
        
        output = "******************************\n";
        output += "Password successfully cracked!\n\n";
        output += "URL: " + strURL + "\n";
        output += "Username: " + strUsername + "\n";
        output += "Password: " + strPassword + "\n";
        output += " Time: " + dtTimeStart.toString() + "\n";
        output += "End Time: " + dtTimeEnd.toString() + "\n";
        output += " Attempts: " + iAttempts + "\n";
        output += "******************************\n";
        
        return output;
    }
    
}
