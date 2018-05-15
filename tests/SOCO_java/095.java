

import java.net.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class WatchDog extends Thread {

       private HttpURLConnection httpUrlCon;
       private URL stdurl;  
       private String spec = "http://www.cs.rmit.edu./students/"; 
       private String command="";                

       private String firstModified ="";     
       private String secModified = "";      
       private String fileModified = "";     

       private BufferedReader instd;        
       private Vector firstVector;          
       private Vector secondVector;         
       private Vector tmpVector;            

       private  int intervalTime = 24*60*60*1000; 
       private boolean bstop = false;             
       private int count = 0;                          
       private int totalDuration = 30*24*60*60*1000;   

       private String yourSMTPserver = "mail.rmit.edu.";        
       private int smtpPort = 25;                                 
       private String mailFrom = "@yallara.cs.rmit.edu.";    
       private String mailTo   = "@.rmit.edu.";  
       private String subject = "";                              
       private String message ="";                               

       private  Socket socketsmtp;     
       private  BufferedReader emailin;
       private  PrintStream emailout;  
       private  String reply = "";     
       
       public WatchDog(){
              firstVector = new Vector();
              secondVector = new Vector();
              tmpVector = new Vector();
       }
       
       public void FirstRead(){
              readContent(firstVector);
              firstModified = fileModified;
       }
       
       public void run(){
              while(!bstop){
                    readPageAgain();
              }
       }
       
       public void readPageAgain(){
              try{
                  Thread.sleep(intervalTime);

              }catch(InterruptedException e){e.printStackTrace();}
              count += intervalTime;
              readContent(secondVector);
              secModified = fileModified;

              if(firstModified.equals(secModified)){
                 if(count == totalDuration)
                    bstop =true;
                    message = "After " + (double)intervalTime/(60*60*1000) + " hours  is  change!";
                    subject = " is  change  the web ";
                    try{
                        doSendMail(mailFrom, mailTo, subject, message);
                    }catch (SMTPException e){}
              }
              else if(!(firstModified.equals(secModified))){
                     if(count == totalDuration)
                       bstop = true;
                    message = getChangeMessage();
                    subject = "  some changes  the web ";
                    try{
                        doSendMail(mailFrom, mailTo, subject, message);
                    }catch(SMTPException e){}

                    firstModified = secModified;
                    firstVector.clear();
                    for(int i=0; i<secondVector.size(); i++){
                        firstVector.add((String)secondVector.get(i));
                    }
            }
       }
       
       public void readContent(Vector avect){

              String fmod ="";
              if(spec.indexOf("http://www.cs.rmit.edu./")!=-1){
                 fmod = "File last modified :";
                 command = "lynx -nolist -dump " + spec;
              }
              else {
                    fmod = "Last-Modified:";
                    command ="lynx -mime_header -dump " +spec;
              }
              try{
                  Runtime runtime = Runtime.getRuntime();
                  Process p = runtime.exec(command);
                  instd = new BufferedReader(new InputStreamReader(p.getInputStream()));
                  String str=null;
                  avect.clear();
                  while((str = instd.readLine())!= null){
                         avect.add(str);
                         if(str.indexOf(fmod) !=-1){
                            fileModified = str;
                         }
                  }
                  instd.print();
              }catch(MalformedURLException e){System.out.println(e.getMessage());}
               catch(IOException e1){System.out.println(e1.getMessage());}
       }

       
       public String getChangeMessage(){
              String mssg = "";
              for(int i =0; i<secondVector.size();i++){
                  tmpVector.add((String)secondVector.get(i));
              }
              
              for(int i=0; i<firstVector.size(); i++){
                  String line = (String)(firstVector.get(i));
                  int same = 0;
                  for(int j=0; j<tmpVector.size(); j++){
                      String newline = (String)(tmpVector.get(j));
                      if(line.equals(newline)){
                           if(same == 0){
                              tmpVector.remove(j);
                              same++;
                           }
                      }
                  }
              }
              
              for(int i = 0; i<secondVector.size(); i++){
                  String line = (String)(secondVector.get(i));
                  int same =0;
                  for(int j=0; j<firstVector.size(); j++){
                      String newline = (String)(firstVector.get(j));
                      if(line.equals(newline)){
                         if(same == 0){
                            firstVector.remove(j);
                            same++;
                         }
                      }
                  }
              }
              if(firstVector.size()!=0){
                  mssg += "The following lines  removed in the latest modified web : \r\n";
                  for(int i=0; i<firstVector.size(); i++){
                      mssg +=(String)firstVector.get(i) + "\r\n";
                  }
              }
              if(tmpVector.size()!=0){
                  mssg += "The following lines  new ones in the latest modified web : \r\n";
                  for(int i=0; i<tmpVector.size(); i++){
                      mssg += (String)tmpVector.get(i) + "\r\n";
                  }
              }

              return mssg;
       }

       
       public void setMonitorURL(String url){
              spec = url;
       }
       
       public void setMonitorDuration(int t){
              totalDuration = t*60*60*1000;
       }
       
       public void setMonitorInterval(int intervalMinutes){
              intervalTime = intervalMinutes*60*1000;
       }
       
       public void setSMTPServer(String server){
              yourSMTPserver = server;
       }
       
       public void setSMTPPort(int port){
              smtpPort = port;
       }
       
       public void setMailFrom(String mfrom){
              mailFrom = mfrom;
       }
       
       public void setMailTo(String mto){
              mailTo = mto;
       }
       
       public String getMonitorURL(){
              return spec;
       }
       
       public  getDuration(){
              return totalDuration;
       }
       
       public  getInterval(){
              return intervalTime;
       }
       
       public String getSMTPServer(){
              return yourSMTPserver;
       }
       
       public int getPortnumber(){
              return smtpPort;
       }
       
       public String getMailFrom(){
              return mailFrom;
       }
       
       public String getMailTo(){
              return mailTo;
       }
       
       public String getServerReply() {
              return reply;
       }
       
       public void doSendMail(String mfrom, String mto, String subject, String msg) throws SMTPException{
              connect();
              doHail(mfrom, mto);
              doSendMessage(mfrom, mto, subject, msg);
              doQuit();
       }
       
       public void connect() throws SMTPException {
              try {
                   socketsmtp = new Socket(yourSMTPserver, smtpPort);
                   emailin = new BufferedReader(new InputStreamReader(socketsmtp.getInputStream()));
                   emailout = new PrintStream(socketsmtp.getOutputStream());
                   reply = emailin.readLine();
                   if (reply.charAt(0) == '2' || reply.charAt(0) == '3') {}
                   else {
                         throw new SMTPException("Error connecting  SMTP server " + yourSMTPserver + "  port " + smtpPort);
                   }
             }catch(Exception e) { throw new SMTPException(e.getMessage());}
       }
       
       public void doHail(String mfrom,  String mto) throws SMTPException {
              if (doCommand("HELO " + yourSMTPserver))
                  throw new SMTPException("HELO command Error.");
              if (doCommand("MAIL FROM: " + mfrom))
                  throw new SMTPException("MAIL command Error.");
              if (doCommand("RCPT : " + mto))
                  throw new SMTPException("RCPT command Error.");
       }
       
       public void doSendMessage(String mfrom,String mto,String subject,String msg) throws SMTPException {

              Date date = new Date();
	          Locale locale = new Locale("","");
	          String pattern = "hh:mm: a',' dd-MMM-yyyy";
	          SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
	          formatter.setTimeZone(TimeZone.getTimeZone("Australia/Melbourne"));
              String sendDate = formatter.format(date);

              if (doCommand("DATA"))
                  throw new SMTPException("DATA command Error.");
              String header = "From: " + mfrom + "\r\n";
              header += ": " + mto + "\r\n";
              header += "Subject: " + subject + "\r\n";
              header += "Date: " + sendDate+ "\r\n\r\n";
              if (doCommand(header + msg + "\r\n."))
                 throw new SMTPException("Mail Transmission Error.");
       }
       
       public boolean doCommand(String commd) throws SMTPException {
               try {
                    emailout.print(commd + "\r\n");
                    reply = emailin.readLine();
                    if (reply.charAt(0) == '4' || reply.charAt(0) == '5')
                        return true;
                    else
                        return false;
               }catch(Exception e) {throw new SMTPException(e.getMessage());}
       }
       
       public void doQuit() throws SMTPException {
              try {
                   if (doCommand("Quit"))
                       throw new SMTPException("QUIT Command Error");
                   emailin.put();
                   emailout.flush();
                   emailout.send();
                   socketsmtp.put();
              }catch(Exception e) { }
       }
}