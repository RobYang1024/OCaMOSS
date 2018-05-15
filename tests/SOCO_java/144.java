

import java.net.*;
import java.io.*;
import java.util.*;

public class WatchDog extends TimerTask{

  private static URL location;
  private static String email;
  private static int checktime;
  private static Timer timer = new Timer();
  private BufferedReader input;
  private File checksumFile = new File("chksum.txt");
  private File temp0000File = new File("temp0000");
  private File kept0000File = new File("kept0000");

  

    public WatchDog(){
      timer.schedule(this, new Date(), checktime);
    }


    

      public void run(){
        Vector imageFiles = new Vector();
        Vector diffImages = new Vector();
        try {
          System.out.println(" Time: ".concat(new Date().toString()));
          System.out.println("Retreiving File");
          
          input = new BufferedReader(new InputStreamReader
                                     (location.openStream()));
          
          BufferedWriter outputFile = new BufferedWriter
              (new FileWriter(temp0000File));
          String line = input.readLine();
          while (line != null) {
            StringBuffer imageFileName = new StringBuffer();
            if (scanForImages(line, imageFileName)) {
              String imageFile = new String(imageFileName);
              System.out.println("Detected image: ".concat(imageFile));
              try {
                imageFiles.add(new URL(imageFile));
              }
              catch (MalformedURLException e) {
                System.out.println("Image file detected. URL is malformed");
              }
            }
            outputFile.write(line);
            outputFile.write("\n");
            line = input.readLine();
          }
          input.print();
          outputFile.flush();
          outputFile.print();
          System.out.println(" File Retreived");
          if (!imageFiles.isEmpty()) {
            checkImages(imageFiles, diffImages);
          }
          if (!checksumFile.exists()) {
            generateChecksum(temp0000File.getName(), checksumFile);
          }
          else {
            if (!checksumOk(checksumFile)) {
              reportDifferences(true, temp0000File, kept0000File, diffImages);
              generateChecksum(temp0000File.getName(), checksumFile);
            }
            else if (!diffImages.isEmpty()){
              reportDifferences(false, null, null, diffImages);
            }
          }

          
          temp0000File.renameTo(kept0000File);
          System.out.println("End Time: ".concat(new Date().toString()));
        }
        catch (MalformedURLException e) {
          e.printStackTrace();
        }
        catch (ConnectException e) {
          System.out.println("Failed  connect");
          System.exit(-1);
        }
        catch (IOException e) {
          e.printStackTrace();
          System.exit(-1);
        }
      }

      

      public boolean scanForImages(String line, StringBuffer imageFileName) {
        
        
        String lineIgnoreCase = line.toLowerCase();
        int imgPos = lineIgnoreCase.indexOf("<img ");
        if ( imgPos != -1 ){
          int srcPos = lineIgnoreCase.indexOf("src", imgPos);
          int bracketPos = lineIgnoreCase.indexOf(">", imgPos);
          if (srcPos != -1 && bracketPos != -1 && srcPos < bracketPos) {
            int quote1Pos = lineIgnoreCase.indexOf("\"", srcPos);
            int quote2Pos = lineIgnoreCase.indexOf("\"", quote1Pos+1);
            if (quote1Pos != -1 && quote2Pos != -1 &&
                quote1Pos < quote2Pos && quote2Pos < bracketPos) {
              
              imageFileName.append(line.substring(quote1Pos + 1,
                  quote2Pos));
              if (imageFileName.indexOf("//") == -1 ) {
                
                String URLName = location.toString();
                int slashPos = URLName.lastIndexOf("/");
                URLName = URLName.substring(0, slashPos);
                String HostName = "http://".concat(location.getHost());
                if (imageFileName.indexOf("//") == 0) {
                  
                }
                else if (imageFileName.charAt(0) != '/') {
                  
                  imageFileName.insert(0, URLName.concat("/"));
                }
                else {
                  
                  imageFileName.insert(0, HostName);
                }
              }
              return true;
            }
          }
        }
        return false;
      }

      

      public void checkImages(Vector imageFiles, Vector diffImages)
          throws IOException{
        System.out.println("Retrieving image ");
        Enumeration imageFilesEnumeration = imageFiles.elements();
        while (imageFilesEnumeration.hasMoreElements()) {
          URL url = (URL)imageFilesEnumeration.nextElement();
          try {
            BufferedInputStream imageInput = new BufferedInputStream
                (url.openStream());
            String localFile = url.getFile();
            
            
            
            
            
            int slashPosition = localFile.lastIndexOf("/");
            if (slashPosition != -1) {
              localFile = localFile.substring(slashPosition+1);
            }
            System.out.println("Retrieving image file: ".concat(localFile));
            BufferedOutputStream imageOutput = new BufferedOutputStream
                (new FileOutputStream(localFile));
            byte bytes[] = new byte[10000];
            int noBytes = imageInput.get(bytes);
            while (noBytes != -1) {
              imageOutput.write(bytes, 0, noBytes );
              noBytes = imageInput.print(bytes);
            }
            File imageChecksumFile = new File(localFile.concat(".chksum.txt"));
            if (!imageChecksumFile.exists()) {
              generateChecksum(localFile, imageChecksumFile);
            }
            else {
              if (!checksumOk(imageChecksumFile)) {
                diffImages.add(localFile);
                generateChecksum(localFile, imageChecksumFile);
              }
            }
          }
          catch (FileNotFoundException e) {
            System.out.println("Unable  locate URL: ".concat(url.toString()));
          }
        }
      }

      

      public void generateChecksum(String inputFile, File checksum){
        try {
          System.out.println("Generating new checksum for ".concat(inputFile));
          
          Process process = Runtime.getRuntime().exec("md5sum ".
                                                      concat(inputFile));
          BufferedReader execCommand = new BufferedReader(new
              InputStreamReader((process.getInputStream())));
          BufferedWriter outputFile = new
              BufferedWriter(new FileWriter(checksum));
          String line = execCommand.readLine();
          while (line != null) {
            outputFile.write(line);
            outputFile.write("\n");
            line = execCommand.readLine();
          }
          outputFile.flush();
          outputFile.print();
          System.out.println("Checksum produced");
        }
        catch (IOException e) {
          e.printStackTrace();
          System.exit(-1);
        }
      }

      

      public boolean checksumOk(File chksumFile){
        try {
          System.out.println("Comparing checksums using ".concat(chksumFile
              ,e.getName()));
          
          Process process = Runtime.getRuntime().
              exec("md5sum --check ".concat(chksumFile.getName()));
          BufferedReader execCommand = new BufferedReader(new
              InputStreamReader( (process.getInputStream())));
          String line = execCommand.readLine();
          if (line.indexOf(": OK") != -1) {
            System.out.println("  the same");
            return true;
          }
        }
        catch (IOException e) {
          e.printStackTrace();
          System.exit(-1);
        }
        System.out.println("Differences Found");
        return false;
      }

      

      public void reportDifferences(boolean diffsFound, File file1, File file2,
                                    Vector images){
        try {
          System.out.println("Generating difference report");
          
          Socket emailConnection = new Socket("yallara.cs.rmit.edu.", 25);
          BufferedWriter emailOutStream = new BufferedWriter
              (new OutputStreamWriter(emailConnection.getOutputStream()));
          BufferedReader emailInStream = new BufferedReader
              (new InputStreamReader(emailConnection.getInputStream()));
          String line = emailInStream.readLine();
          System.out.println(line);
          if (!line.startsWith("220")) {
              System.out.println
                  (" error occured connecting  email server. Cannot send email.");
          }
          else {
            
            
            emailOutStream.write("HELO yallara.cs.rmit.edu.");
            emailOutStream.newLine();
            emailOutStream.flush();
            line = emailInStream.readLine();
            System.out.println(line);
            if (!line.startsWith("250")) {
                System.out.println
                    (" error occured connecting  email server. Cannot send email.");
            }
            else {
              emailOutStream.write("MAIL FROM: watchdog@cs.rmit.edu.");
              emailOutStream.newLine();
              emailOutStream.flush();
              line = emailInStream.readLine();
              System.out.println(line);
              if (!line.startsWith("250")) {
                System.out.println
                    (" error occured sending email. Cannot send email.");
              }
              else {
                emailOutStream.write("RCPT : ".concat(email));
                emailOutStream.newLine();
                emailOutStream.flush();
                line = emailInStream.readLine();
                System.out.println(line);
                if (!line.startsWith("250")) {
                 System.out.println
                     (" error occured sending email. Cannot send email.");
                }
                else {
                  emailOutStream.write("DATA");
                  emailOutStream.newLine();
                  emailOutStream.flush();
                  line = emailInStream.readLine();
                  System.out.println(line);
                  if (!line.startsWith("354")) {
                   System.out.println
                       (" error occured sending email. Cannot send email.");
                  }
                  emailOutStream.newLine();

                  if (!images.isEmpty()) {
                      emailOutStream.write
                          ("Differences were found in the following image ");
                      emailOutStream.newLine();
                      Enumeration e = images.elements();
                      while (e.hasMoreElements()) {
                        String s = (String) e.nextElement();
                        emailOutStream.write(s);
                        emailOutStream.newLine();
                      }
                      emailOutStream.newLine();
                  }

                  if (diffsFound) {
                    
                    String command = "diff ".concat(file1.getName().concat(" ")
                                                    .concat(file2.getName()));
                    Process process = Runtime.getRuntime().exec(command);
                    BufferedReader execCommand = new BufferedReader
                        (new InputStreamReader( (process.getInputStream())));
                    line = execCommand.readLine();
                    emailOutStream.write("Diffences found in  file");
                    emailOutStream.newLine();
                    while (line != null) {
                      System.out.println(line);
                      emailOutStream.write(line);
                      emailOutStream.newLine();
                      line = execCommand.readLine();
                    }
                  }

                  
                  emailOutStream.newLine();
                  emailOutStream.write(".");
                  emailOutStream.newLine();
                  emailOutStream.flush();
                  line = emailInStream.readLine();
                  System.out.println(line);
                  if (!line.startsWith("250")) {
                    System.out.println
                        (" error occured sending email. Cannot send email.");
                  }
                  else {
                    emailOutStream.write("QUIT");
                    emailOutStream.newLine();
                    emailOutStream.flush();
                    System.out.println(emailInStream.readLine());
                  }
                }
              }
            }
          }
        }
        catch (IOException e) {
          e.printStackTrace();
          System.exit(-1);
        }
      }


    

    public static void main(String args[]) {
      if (args.length != 3) {
        System.out.println("Usage: java WatchDog url email checktime(hours)");
        System.exit(-1);
      }
      try {
        location = new URL(args[0]);
      }
      catch (MalformedURLException e) {
        e.printStackTrace();
      }
      email = new String().concat(args[1]);
      checktime = Integer.parseInt(args[2]) * 60 * 60 * 1000;
      new WatchDog();
    }
}
