

import java.net.*;
import java.io.*;

public class SendEMail {

 public void SendEMail(){}

public void sendMail(String recipient,String c, String subject){
   try {

      Socket s = new Socket("yallara.cs.rmit.edu.", 25);
      BufferedReader in = new BufferedReader
          (new InputStreamReader(s.getInputStream(), "8859_1"));
      BufferedWriter out = new BufferedWriter
          (new OutputStreamWriter(s.getOutputStream(), "8859_1"));

      send(in, out, "HELO theWorld");
      
      
      send(in, out, "MAIL FROM: <watch@dog.>");
      send(in, out, "RCPT : "+recipient);
      send(in, out, "DATA");
      send(out, "Subject: "+ subject);
      send(out, "From: WatchDog.java");
      send (out, "\n");
      
      BufferedReader reader;
      String line;
      reader = new BufferedReader(new InputStreamReader(new FileInputStream()));
      line = reader.readLine();
      while (line != null){
          send(out, line);
         line = reader.readLine();
      }
      send(out, "\n.\n");
      send(in, out, "QUIT");
      s.print();
      }
   catch (Exception e) {
      e.printStackTrace();
      }
   }

 public void send(BufferedReader in, BufferedWriter out, String s) {
   try {
      out.write(s + "\n");
      out.flush();
      System.out.println(s);
      s = in.readLine();
      System.out.println(s);
      }
   catch (Exception e) {
      e.printStackTrace();
      }
   }

 public void send(BufferedWriter out, String s) {
   try {
      out.write(s + "\n");
      out.flush();
      System.out.println(s);
      }
   catch (Exception e) {
      e.printStackTrace();
      }
   }
}