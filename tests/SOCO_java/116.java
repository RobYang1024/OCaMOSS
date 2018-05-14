
import java.io.*;

public class PasswordFile {
    
    private String strFilepath;
    private String strCurrWord;
    private File fWordFile;
    private BufferedReader in;
    
    
    public PasswordFile(String filepath) {
        strFilepath = filepath;
        try {
            fWordFile = new File(strFilepath);
            in = new BufferedReader(new FileReader(fWordFile));
        }
        catch(Exception e)
        {
            System.out.println("Could not open file " + strFilepath);
        }
    }
    
    String getPassword() {
        return strCurrWord;
    }
    
    String getNextPassword() {
        try {
            strCurrWord = in.readLine();
            
            
            
        }
        catch (Exception e)
        {
            
            return null;
        }
                
        return strCurrWord;
    }
    
}
