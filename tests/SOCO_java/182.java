import java.net.*;
import java.io.*;


public class Dictionary extends Authenticator {

    
    private String username;
    
    private char [] thisPassword;
    
    private URL url;
    
    private BufferedReader bf;

    
    public static void main(String [] args) {
        if(args.length!=3) {
            System.err.println(
                    "usage: Dictionary <url> <username> <dictionary-file>");
            System.exit(1);
        }
        Dictionary d = null;
        try {
            d = new Dictionary(args[0], args[1], args[2]);
        } catch (MalformedURLException me) {
            me.printStackTrace();
            System.exit(1);
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
            System.exit(1);
        }
        d.work();
    }

    
    public Dictionary(String url, String username, String passwordFilename) 
            throws MalformedURLException, FileNotFoundException {
        this.url = new URL(url);
        this.username = username;
        thisPassword = new char [] {'a'};
        File f = new File(passwordFilename);
        FileReader fr = new FileReader(f);
        bf  = new BufferedReader(fr);
    }

    
    public void work() {
        Authenticator.setDefault(this);
        HttpURLConnection uc = null;
        try {                                                 
            uc = (HttpURLConnection) url.openConnection();    
            uc.connect();                                     
            while(uc.getResponseCode()==HttpURLConnection.HTTP_UNAUTHORIZED &&
                thisPassword !=null) {
                try {                                         
                    InputStream is = uc.getInputStream();     
                    uc.connect();                             
                } catch (ProtocolException pe) {              
                    uc = (HttpURLConnection) url.openConnection();          
                } catch (NullPointerException npe) {          
                    npe.printStackTrace();                    
                    System.exit(1);                           
                }                                             
            }                                                 
        } catch (java.io.IOException e ) {                    
            e.printStackTrace();                             
            System.exit(1);                                   
        }                                                     
        System.out.println("password=" + new String(thisPassword));
    }

    
    public PasswordAuthentication getPasswordAuthentication() {
        String s=null;
        try {
            for(s = bf.readLine(); s!=null; s = bf.readLine()) {
                if(s.length()==3) {
                    break;
                }
            } 
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if(s.length()!=3) {
            thisPassword = null;
        } else {
            thisPassword = s.toCharArray();
        }
        return new PasswordAuthentication(username, thisPassword);
    }
}
