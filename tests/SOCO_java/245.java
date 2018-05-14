















import java.*;
import java.io.*;
import java.text.*;
import java.net.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class WatchDog {

   public static void main(String[] args) throws IOException {

      
      String host = "http://www.cs.rmit.edu./students/";

      String outFilename = null; 
      String diffFilename = null; 

      
      String email = "@cs.rmit.edu."; 
      String subject = null; 
      String message = "msg"; 
      String mutt = null; 

      
      List currentList = new ArrayList();
      List previousList = new ArrayList();
      List diffList = new ArrayList();
      String line = null;
      String previousLine = null;
      String diffLine = null;

      
      int sleepTime = 1000 * 60 * 60 * 24; 


      SimpleDateFormat format = new SimpleDateFormat("yyyyMMddkmm");
      Date currentDate = null;

      int c=0;

       {
         try {
            
            URL url = new URL(host);
            HttpURLConnection httpConnect = (HttpURLConnection) url.openConnection();
            InputStream webIn = httpConnect.getInputStream();
            BufferedReader webReader = new BufferedReader( new InputStreamReader( webIn ) );

            
            
            currentDate = null;
            currentDate = new java.util.Date();
            String dateString = format.format(currentDate);

            
            outFilename = "watch_" + dateString; 
            File outputFile = new File(outFilename);
            FileWriter out = new FileWriter(outputFile);

            
            diffFilename = "diff_" + dateString; 
            File diffOutputFile = new File(diffFilename);
            FileWriter diffOut = new FileWriter(diffOutputFile);

            line = ""; 

            
            while (( line = webReader.readLine()) != null ) {
                currentList.add(line + "\n"); 
            }

            webReader.close(); 

            Iterator iter = currentList.iterator();

            
            line = "";
            previousLine = "";
            diffLine = "";

            
            int l=0; 
            int d=0; 

            while (iter.hasNext()) {
              line = String.valueOf(iter.next());
              out.write(line); 

               
               if (!previousList.isEmpty()) {
                  try {
                     previousLine = String.valueOf(previousList.get(l));


                     if (line.compareTo(previousLine)==0) {
                     } else {
                        
                        diffLine = "Line " + (l+1) + " has changed :  " + line;
                        diffOut.write(diffLine);
                        d++; 
                     }

                  } catch (Exception e) {
                     
                     
                     diffLine = "Line " + (l+1) + " has been added:  " + line + " \n";
                     diffOut.write(diffLine);
                     d++; 
                  } 
               } 

              l++; 
            }

            
            out.close();
            diffOut.close();

            
            previousList.clear();
            previousList.addAll(currentList);
            currentList.clear();
            diffList.clear();

            if (d>0) {
               
               subject = "WatchDog_" + dateString; 
               mutt = "mutt -a " + diffFilename + " -s " + subject + " " + email;

               System.out.println("The webpage has changed.");
               Runtime rt = Runtime.getRuntime();
               rt.exec(mutt);
               
               

               System.out.println("Email sent  " + email + " at " + dateString);

            } else if (c>0) {
               
               System.out.println("Webpage checked at " + dateString + " and  changes were found");
            }

         } catch(MalformedURLException e) {
            System.out.println("Opps, the URL " + host + " is not valid.");
            System.out.println("Please check the URL and try again.");
            System.exit(0);

         } catch(IOException e) {
            System.out.println(", 't connect  " + host + ".");
            System.out.println("Please check the URL and try again.");
            System.out.println("Other possible causes include website is currently unavailable");
            System.out.println(" I have a problem   writing .");
            System.exit(0);

         } 

         
         try {
            Thread.sleep(sleepTime);
         } catch (Exception e) {
            
         } 

         c++;
      }  while(true); 
   }
}