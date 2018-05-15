

import java.net.*;
import java.io.*;

public class sendMail {

public void sendMail(String mailServer, String recipient, String result) {
   try {
      Socket s = new Socket(mailServer, 25);
      BufferedReader in = new BufferedReader
          (new InputStreamReader(s.getInputStream(), "8859_1"));
      BufferedWriter out = new BufferedWriter
          (new OutputStreamWriter(s.getOutputStream(), "8859_1"));

      send(in, out, "HELO client");

      send(in, out, "MAIL FROM: <WatchDog@SecureECommerce.>");
      send(in, out, "RCPT : " + recipient);
      send(in, out, "DATA");
      send(out, "Subject: ");
      send(out, "From: Admin <WatchDog@SecureECommerce.>");
      send (out, "\n");
      
      send(out, result);
      send(out, "\n.\n");
      send(in, out, "QUIT");

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