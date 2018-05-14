
import java.io.*;
import java.net.Socket;
import java.util.*;

public class Email
{
     private String hello;
     private String mailFrom="";
     private String mailTo="";
     private String mailData="";
     
     private String subject="";
     private String content="";
     private String follows="";
     private String changeContent="";
     private String stop="";
     private String end="";
     private String line="";
     private InputStream is;
     private BufferedReader bf;
     private OutputStream os;
     private PrintWriter pw;
     public Email(Vector change)  throws Exception
     {
      hello= "HELO mail.rmit.edu.";
      mailFrom = "MAIL FROM: @cs.rmit.edu.";
      mailTo = "RCPT : @cs.rmit.edu.";
      mailData = "DATA";
      subject="Subject: Some changes occur";
      content=" is some changes  the : http://www.cs.rmit.edu./students/";
      follows="The changes  as follows:";
      for(int i=0;i<change.size();i++)
        changeContent+=change.elementAt(i).toString()+"\r\n";
      stop ="\r\n.";
      end="QUIT";
     }
     public void send() throws Exception
     {
      Socket sk = new Socket("mail.cs.rmit.edu.",25);
      is= sk.getInputStream();
      os = sk.getOutputStream();
      pw = new PrintWriter(new OutputStreamWriter(os));
      pw.println(hello);
      pw.println(mailFrom);
      pw.println(mailTo);
      pw.println(mailData);
      pw.println();
      pw.println(subject);
      pw.println(content);
      pw.println(follows);
      pw.println(changeContent);
      pw.println(stop);
      pw.println(end);
      pw.flush();
      pw.get();
      
     }
}