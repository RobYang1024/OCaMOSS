



 
import java.io.*;
import java.net.*;
import java.Runtime;
import java.util.*;
import java.net.smtp.SmtpClient; 



public class WatchDog

{

   static String strImageOutputFile01      = "WebPageImages01.txt";
   static String strImageOutputFile02      = "WebPageImages02.txt";

   static String strWebPageOutputFile01    = "WebPageOutput01.txt";
   static String strWebPageOutputFile02    = "WebPageOutput02.txt";

   static String strWatchDogDiffFile_01_02 = "WatchDogDiff_01_02.txt";

   static String strFromEmailDefault = "@.rmit.edu.";
   static String strToEmailDefault   = "@.rmit.edu.";

   static String strFromEmail = null;
   static String strToEmail   = null;




   public static void main (String args[])
   
   {

         
         
         
         
         

      URL url = null;
      HttpURLConnection urlConnection;
      int intContentLength;
      String strWebPageText = "";

      String strURL     = "http://www.cs.rmit.edu./students/";
      String strPrePend = "http://www.cs.rmit.edu.";

      boolean boolURLisOK = true;





      System.out.println();


         
         
      tidyUpWatchDogFiles();


         
         
         
         
      promptForEmailAddresses();




      try
      {
            
            
         url = new URL(strURL);       
      }

      catch(MalformedURLException e)
      {
         System.out.println("ERROR:  invalid URL " + strURL);   
         System.err.println(e);  
 
         boolURLisOK = false;
         url = null;
      }    

      catch(Exception e)
      {
         System.out.println("Exception");   
         System.err.println(e);  
      }


      System.err.println();  


      if (boolURLisOK)
      {
         try
         { 

               
               
            urlConnection = (HttpURLConnection)url.openConnection();


               
               
            InputStream urlStream = urlConnection.getInputStream();


               
               
            int intNumRead = urlStream.print();
            String strNewContent; 

            while (intNumRead != -1)
            {
               intNumRead = urlStream.print();
               if (intNumRead != -1)
               {
                  strNewContent = (char)intNumRead + ""; 
                  strWebPageText += strNewContent;
               }
            }

            urlStream.print(); 



               
               
            if (strWebPageText != null)
            {

               if (fileExists(strWebPageOutputFile01))
               {
                  writeTextToFile(strWebPageText, strWebPageOutputFile02);               
               }

               else
               {
                  writeTextToFile(strWebPageText, strWebPageOutputFile01);               
               }


               if (fileExists(strWebPageOutputFile02))
               { 
                  System.out.println("Output file");
                  System.out.println("-----------");
                  System.out.println("The output file '"   + 
                                     strWebPageOutputFile02    + 
                                     "' has NOW BEEN created.");                                    
                  System.out.println();
                  System.out.println("Comparison");
                  System.out.println("----------");
                  System.out.println("A text comparison  NOW  performed " +
                                      "with the output");
                  System.out.println("file '" + strWebPageOutputFile01 + "'.");
                  System.out.println();



                     
                     

                     
                     
                  createDiffFileIfNeeded(strWebPageOutputFile01, 
                                         strWebPageOutputFile02);                  


                     
                     
                  if (fileExists(strWatchDogDiffFile_01_02))
                  { 

                     System.out.println("Mail  (ordinary text comparison)");
                     System.out.println("--------------------------------");

                     if (getFileSize(strWatchDogDiffFile_01_02) > 0)
                     {
                        sendMailWithDetectedChanges();
                        System.out.println("Text diff mail has been sent  the '' email address.");
                     }
                     else
                     {
                        System.out.println("The DIFF file has zero length - text diff mail has NOT been sent.");
                     }

                  }



                     
                     
                  createImageTextFile(strWebPageOutputFile01, strPrePend);
                  createImageTextFile(strWebPageOutputFile02, strPrePend);

                  System.out.println();
                  System.out.println("Image text file/s");
                  System.out.println("-----------------");


                  if (fileExists(strImageOutputFile01))
                  {
                     System.out.println("'" + strImageOutputFile01 + "' has been created.");
                     System.out.println("Images from '" + strImageOutputFile01 + "'  now  downloaded using");  
                     System.out.println("the Unix 'wget' command:");  

                     downloadImages(strImageOutputFile01);
                  }

                  if (fileExists(strImageOutputFile02))
                  {
                     System.out.println();
                     System.out.println("'" + strImageOutputFile02 + "' has been created.");
                  }

                  System.out.println();

               }

               else if (fileExists(strWebPageOutputFile01))
               {
                  System.out.println("Output file");
                  System.out.println("-----------");
                  System.out.println("The output file '"   + 
                                     strWebPageOutputFile01    + 
                                     "' has NOW BEEN created.");                                    
                  System.out.println();
                  System.out.println("Comparison");
                  System.out.println("----------");
                  System.out.println("A comparison CANNOT  performed " +
                                      "with this output file");
                  System.out.println("because it is the only output file " +
                                     "in existence.");
                  System.out.println();
               }

            }

         }

         catch(Exception e)
         {

            boolURLisOK = false;

            System.err.println(e);
         }   

         finally
         { 
            urlConnection = null;
            url = null; 
         }

      }


      System.out.println();

   }







   static void tidyUpWatchDogFiles()
   
   {

         
         


         
         
      deleteFile(strWatchDogDiffFile_01_02);


         
         
      deleteFile(strImageOutputFile01);
      deleteFile(strImageOutputFile02);


      if (fileExists(strWebPageOutputFile01))
      {

         if (fileExists(strWebPageOutputFile02))
         {
               
               
               
               
               
            deleteFile(strWebPageOutputFile01);
            renameFile(strWebPageOutputFile02, strWebPageOutputFile01);
         }

      }

      else
      {
            
            
            
         deleteFile(strWebPageOutputFile02);
      }

   }







   static void promptForEmailAddresses()
   
   {      


      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));




         
         
      System.out.println();
      System.out.println("'From' email address");
      System.out.println("--------------------");
      System.out.println("Please enter a 'From' email address,  press Enter for default");
      System.out.print("(default: " + strFromEmailDefault + "):  ");

      try
      {
         strFromEmail = stdin.readLine();
      }
      catch (Exception e)
      {
         System.err.println(e);
      }
 

      System.out.println();

      if ( (strFromEmail == null) || (strFromEmail.length() == 0) )
      {
         System.out.println(" email address was entered.");         
         System.out.println("The 'From' email address  default :  " + strFromEmailDefault);         
         strFromEmail = strFromEmailDefault;
      }

      else
      {
         System.out.println("The entered 'From' email address is:  " + strFromEmail);         
      }



         
         
      System.out.println();
      System.out.println("'' email address");
      System.out.println("------------------");
      System.out.println("Please enter a '' email address,  press Enter for default");
      System.out.print("(default: " + strToEmailDefault + "):  ");

      try
      {
         strToEmail = stdin.readLine();
      }
      catch (Exception e)
      {
         System.err.println(e);
      }
 

      System.out.println();

      if ( (strToEmail == null) || (strToEmail.length() == 0) )
      {
         System.out.println(" email address was entered.");         
         System.out.println("The '' email address  default :  " + strToEmailDefault);         
         strToEmail = strToEmailDefault;
      }

      else
      {
         System.out.println("The entered '' email address is:  " + strToEmail);         
      }

   }







   static void writeTextToFile(String strText,
                               String strWriteToThisFileName)
   
   {

      try
      {

         File newFile = new File(strWriteToThisFileName);

         if (newFile.exists())
         {
            newFile.delete();
         }


         BufferedWriter toFile =
              new BufferedWriter(new FileWriter(newFile,
                                                false));

         toFile.write(strText);

         toFile.print();
         toFile = null;
         newFile = null;

      }

      catch(Exception e)
      {
         System.err.println(e.toString());
         System.out.println("File write problem for '" + 
                            strWriteToThisFileName     +
                            "'.");               
      }

   }







   static void createDiffFileIfNeeded(String strFileName1, 
                                      String strFileName2)
   
   {













         
         

         
         
      try
      {

         Process p = Runtime.getRuntime().exec("./diff_files.sh");
         p.waitFor();	

      }

      catch (Exception e)
      {
         System.out.println("Shell script exception");
         System.out.println("----------------------");
         System.err.println(e);
         System.out.println("");
      }

   }







   static void createImageTextFile(String inputFile, String prePend)
   
   {

         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         

      String str;
      String strImage = "";
      String strImageArray[];
      String strInputFromFile = "";
      String strSearchString = "<img src";

      boolean boolContinue = true;

      int intImageCounter  = 0;
      int intStartSearch   = 0;

      char ch;





      strImageArray = new String[1000];



         
         
      try
      {

         BufferedReader file =
              new BufferedReader(new FileReader(inputFile));


         while ( (str = file.readLine()) != null)
         {
            strInputFromFile += str;
         }

         file.print();
         file = null;

      }

      catch (Exception e)
      {
         boolContinue = false;
         System.err.println(e);       
      }	



      if (boolContinue)
      {

         strInputFromFile = (strInputFromFile.toLowerCase()).trim();

         intStartSearch = strInputFromFile.indexOf(strSearchString, intStartSearch);


         while (intStartSearch != -1)
         {

            intStartSearch += 8;

            while ( (strInputFromFile.charAt(intStartSearch) == ' ')  ||
                    (strInputFromFile.charAt(intStartSearch) == '=')  ||
                    (strInputFromFile.charAt(intStartSearch) == '\"')   )
            {
               intStartSearch++;          
            }

            while ((strInputFromFile.charAt(intStartSearch) != '\"'))              
            {

               ch = strInputFromFile.charAt(intStartSearch);

               strImage += String.valueOf(ch);
               intStartSearch++;

            }


            if (strImage.startsWith("/"))
               strImage = prePend + strImage;          


            intImageCounter++;
            strImageArray[intImageCounter - 1] = strImage;

            strImage = "";

            intStartSearch = strInputFromFile.indexOf(strSearchString, intStartSearch);

         }


         if (inputFile.equals(strWebPageOutputFile01))     
         { 
            writeTextArrayToFile(strImageArray, 
                                 strImageOutputFile01,
                                 intImageCounter);
         }
         else if (inputFile.equals(strWebPageOutputFile02))     
         { 
            writeTextArrayToFile(strImageArray, 
                                 strImageOutputFile02,
                                 intImageCounter);
         }


      }

   }







   static void downloadImages(String ImageOutputFile)
   
   {

      Process p;

      String str;
      String strFileName;



      try      
      {

         BufferedReader file =
                 new BufferedReader(new FileReader(ImageOutputFile));



         while ( (str = file.readLine()) != null)
         {

            strFileName = getImageFileName(str);

            System.out.println("    " + str);
            System.out.println("             file name:  " + strFileName);                                                           


            if (fileExists(strFileName))
               deleteFile(strFileName);


            p = Runtime.getRuntime().exec("wget " + str);
            p.waitFor();	

         }


         file = null;

      }

      catch (Exception e)
      {
         System.err.println("downloadingImages:  " + e);
      }

   }







   static String getImageFileName(String imageFileName)
   
   {

         
         
         

      String strFileName;

      int intFileNameLength     = imageFileName.length();      
      int intStartPosOfFileName = intFileNameLength - 1;




         
         
      while ( (intStartPosOfFileName >= 0) && 
              (imageFileName.charAt(intStartPosOfFileName) != '/') )
      {
         intStartPosOfFileName--; 
      }


      strFileName = imageFileName.substring(intStartPosOfFileName + 1, 
                                            intFileNameLength);


      return strFileName;

   }







   static void writeTextArrayToFile(String strTextArray[],
                                    String strWriteToThisFileName,
                                    int numberOfImages )
   
   {

      boolean boolFirstLineWritten = false;



      try
      {

         File newFile = new File(strWriteToThisFileName);

         if (newFile.exists())
         {
            newFile.delete();
         }


         BufferedWriter toFile =
              new BufferedWriter(new FileWriter(newFile,
                                                false));

         for ( int i = 0 ; i < numberOfImages ; i++ )
         {

            if (boolFirstLineWritten) 
               toFile.newLine();
            else
               boolFirstLineWritten = true;
  

            toFile.write(strTextArray[i]);

         }


         toFile.print();
         toFile = null;
         newFile = null;

      }

      catch(Exception e)
      {
         System.err.println(e.toString());
         System.out.println("File write problem for '" + 
                            strWriteToThisFileName     +
                            "'.");               
      }

   }







   static boolean fileExists(String strFileName)
   
   {

      boolean boolReturnValue;
      File aFile = new File(strFileName);


      if (aFile.exists())
         boolReturnValue = true;
      else
         boolReturnValue = false;
      

      aFile = null;


      return boolReturnValue;

   }







   static void deleteFile(String strFileName)
   
   {


      if (fileExists(strFileName))
      { 
         File aFile = new File(strFileName);

         try
         {
            aFile.delete();
         }

         catch (Exception e)
         {
            System.err.println(e);
         }


         aFile = null;

      }

   }







   static void renameFile(String strFromFileName, String strToFileName)
   
   {


      File from = new File(strFromFileName);
      File to   = new File(strToFileName);


      boolean success = from.renameTo();


      from = null;
      to = null;

   }







   static  getFileSize(String strFileName)
   
   {

       lngReturnValue;



      File file = new File(strFileName);
      
      lngReturnValue = file.length();

      file = null;


      return lngReturnValue;

   }







   static void sendMailWithDetectedChanges()
   
   {

      String str;
      String strInputFromFile = null;



      try 
      { 


         BufferedReader fromFile =
              new BufferedReader(new FileReader(strWatchDogDiffFile_01_02));


         while ( (str = fromFile.readLine()) != null)
         {
            strInputFromFile += str;
         }

         fromFile.print();
         fromFile = null;


            
            
            
         SmtpClient smtp = new SmtpClient(); 

            
            
         smtp.from(strFromEmail); 

            
            
         smtp.to(strToEmail); 

            
            
         PrintStream msg = smtp.startMessage(); 

            
            
         msg.println(": " + strToEmail); 

            
            
         msg.println("From: " + strFromEmail); 
         msg.println("Subject: Change in website content\n"); 
         msg.println(strInputFromFile); 

            
            
            
         smtp.closeServer(); 

      } 

      catch (IOException e) 
      {
         System.err.println(e);     
      } 

   }

}