

import java.io.*;
import java.net.*;
import java.*;
import java.String;
import java.util.*;

 public class WatchDogThread extends Thread {
  private  int dayTime;
  private Vector webPageLines;
  private Vector originalWebPageLines;
  private Vector addLines;
  private Vector deductLines;
  private Vector v;
  private Runtime run;
  private String str;
  private String responseLine;
  private String host;
  private int lineNo;
  private Process process;
  private Socket socket;
  private BufferedReader in;
  private BufferedReader pageLine;
  private PrintWriter out;
  private boolean checked = false;  
  private boolean changed = false;  
  private static String commandLine="curl http://www.cs.rmit.edu./students/ -i";
  private String[] sendString = {
    "HELO mail.nowhere.",
    "MAIL FROM: @cs.rmit.edu.",
    "RCPT : @cs.rmit.edu.",
    "DATA",
    "Subject: webpage changed!",
    ".",
    "QUIT"};

  public WatchDogThread(String str)
  {
   super(str);
   dayTime = 24*60*60*1000;
   webPageLines = new Vector();
   originalWebPageLines = new Vector();
   addLines = new Vector();
   deductLines = new Vector();
    v = new Vector();
  }

  public void run()
  {
   run = Runtime.getRuntime();
   host = "mail.cs.rmit.edu.";
   while(true) {
   try{
     if(!loadPage())    
        break;

     if(checked)  {
       compare();

     if(addLines.size() != 0 || deductLines.size() != 0)
     {
       changed = true;
     }
     else if(v.size() != 0)
     {
       changed = true;
       addLines.removeAllElements();
       deductLines.removeAllElements();
     }
     else
     {
      changed = false;
      addLines.removeAllElements();
      deductLines.removeAllElements();
     }
     if(changed) {
      socket = new Socket(host,25);
      in = new
      BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
      out = new PrintWriter(new
                         OutputStreamWriter(
                         socket.getOutputStream()));
      
      out.println(sendString[0]);
      out.flush();

      while((responseLine = in.readLine())!=null){
       if(responseLine.indexOf("pleased  meet ") != -1)  break;
      }

      if(responseLine.indexOf("pleased  meet ") != -1) {

       out.println(sendString[1]);
       out.flush();
       responseLine = in.readLine();

      if(responseLine.substring(responseLine.length()-9,responseLine.length()).equals("Sender ok")) {
       out.println(sendString[2]);
       out.flush();
       responseLine = in.readLine();

      if(responseLine.substring(responseLine.length()-12,responseLine.length()).equals("Recipient ok")) {
       out.println(sendString[3]);
       out.flush();
       responseLine = in.readLine();

      if(responseLine.substring(0,14).equals("354 Enter mail")) {
      
       out.println(sendString[4]);
       out.flush();

     
      lineNo = 0;
      if(addLines.size()!=0)
      {
        out.println("The following lines  the added new lines:");
        out.flush();
        while(addLines.size()>lineNo)
        {
         out.println(String.valueOf(lineNo+1)+": "+(String)(addLines.elementAt(lineNo++)));
         out.flush();
        }
        out.println("\n---  ---  ---  ---  ---  --- ---\n");
        out.flush();
      }
      lineNo = 0;
      if(deductLines.size()!=0)
      {
        out.println("The following lines  the deducted lines:");
        out.flush();
        while(deductLines.size()>lineNo)
        {
         out.println(String.valueOf(lineNo+1)+": "+ (String)(deductLines.elementAt(lineNo++)));
         out.flush();
        }
        out.println("\n---  ---  ---  ---  ---  --- ---\n");
        out.flush();
      }
      lineNo = 0;
      if(v.size() != 0)
      {
        out.println("The following lines  the added lines that  the same");
        out.flush();
        out.println(" as some lines in the original webpage:");
        out.flush();
        while(v.size()>lineNo)
        {
         out.println(String.valueOf(lineNo+1)+": "+ (String)(v.elementAt(lineNo++)));
         out.flush();
        }
        out.println("\n---  ---  ---  ---  ---  --- ---\n");
        out.flush();
      }
      

      
      out.println(sendString[5]);
      out.flush();
      responseLine = in.readLine();

     if(responseLine.substring(responseLine.length()-29,responseLine.length()).equals("Message accepted for delivery")) {
      
      out.println(sendString[6]);
      out.flush();
     }
     }
     }
     }
     }
     socket.close();
    }
    update(changed);
    }
    else {
     checked = true;
     update(changed);
    }
    }
    catch (Exception e) {
     System.out.println(e.getMessage());
     break;
    }

    try {
     sleep(dayTime);
    }
    catch (InterruptedException e ) {
     System.out.println(e.getMessage());
     break;
    }
   }
  }


  public void compare()
  {
   int n = 0;
   int j = 0;
   try {
    v.removeAllElements();
    while(n < webPageLines.size()) {
     for(j = 0; j < originalWebPageLines.size(); j++)
     {
      if(((String)(webPageLines.elementAt(n)))
         .equals((String)(originalWebPageLines.elementAt(j))))
      {
       v.addElement((String)(webPageLines.elementAt(n)));
       break;
      }
     }
     if(j == originalWebPageLines.size())
     {
       addLines.addElement((String)(webPageLines.elementAt(n)));
     }
     n++;
    }

    n = 0;
    while(n < originalWebPageLines.size()) {
     for(j = 0; j < webPageLines.size(); j++)
     {
      if(((String)(originalWebPageLines.elementAt(n)))
          .equals((String)(webPageLines.elementAt(j))))
      {
        v.remove((String)(originalWebPageLines.elementAt(n)));
        break;
      }
     }
     if(j == webPageLines.size())
     {
       deductLines.addElement((String)(originalWebPageLines.elementAt(n)));
     }
     n++;
    }
   }
   catch (Exception e) {
     System.out.println(e.getMessage());
   }
}


  public void update(boolean updatePageLines)
  {
    try {
     addLines.removeAllElements();
     deductLines.removeAllElements();
     v.removeAllElements();
     if(updatePageLines)
     {
        originalWebPageLines.removeAllElements();
        for(int i=0; i< webPageLines.size(); i++)
        {
           originalWebPageLines.addElement((String)(webPageLines.elementAt(i)));
        }
        webPageLines.removeAllElements();
     }
     else   webPageLines.removeAllElements();
     changed = false;
    }
   catch (Exception e) {
     System.out.println(e.getMessage());
   }
}


  public boolean loadPage()
  {
   try {
     process = run.exec(commandLine);      
     pageLine = new
     BufferedReader(new InputStreamReader(process.getInputStream()));
     str = pageLine.readLine();
     if(str.equals("HTTP/1.1 200 OK")) {
        {
        str = pageLine.readLine();
        if(str.indexOf("<") != -1) break;
       }while(true);
       if(!checked)  originalWebPageLines.addElement(str);
       else          webPageLines.addElement(str);
        {
        str = pageLine.readLine();
        if(str != null) {
          if(str.length() != 0 && !(str.equals(""))) {
             if(!checked)  originalWebPageLines.addElement(str);
             else          webPageLines.addElement(str);
          }
        }
        else      break;
       } while(true);
       return true;
     }
     else return false;
   }
   catch (Exception e) {
     System.out.println(e.getMessage());
   }
   return true;
  }
}

