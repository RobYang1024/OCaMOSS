import java.io.*;
import java.util.StringTokenizer;
import java.net.smtp.SmtpClient;
import java.util.Timer;
import java.util.TimerTask;


public class WatchDog {
public static void main(String[] args) {
try {
Process y = Runtime.getRuntime().exec("./init");
}
catch (Exception e) {System.err.println(e);}


WatchDog poodle=new WatchDog();
 {
poodle.startWatch();
} while(1==1);
}

public void startWatch() {
String error_mes=new String();
String mesg=new String();
String url="wget -p http://www.cs.rmit.edu./students";

try {
Process a = Runtime.getRuntime().exec(url);
}
catch (Exception e) {System.err.println(e);}

try {
Process b = Runtime.getRuntime().exec("diff org/images/ www.cs.rmit.edu./images/");
 BufferedReader stdInputimages = new BufferedReader(new InputStreamReader(b.getInputStream()));
            while ((error_mes = stdInputimages.readLine()) != null) {

              mesg=mesg.concat(error_mes);
              
              
              }
}
catch (Exception e) {System.err.println(e);}




try {
Process c = Runtime.getRuntime().exec("diff org/students/ www.cs.rmit.edu./students/");
BufferedReader stdInputindex = new BufferedReader(new InputStreamReader(c.getInputStream()));
            while ((error_mes = stdInputindex.readLine()) != null) {
             mesg=mesg.concat(error_mes);
                  
              }
}
catch (Exception e) {System.err.println(e);}


if (mesg.length()>0) { sendEmail(mesg); }

try { Thread.sleep(60*60*24*1000);
 } catch(Exception e) { }
}





public void sendEmail(String message) {
{
String reciever = "@cs.rmit.edu.";
String sender = "WATCHDOG@cs.rmit.edu.";

    try {

                        SmtpClient smtp = new SmtpClient();
                        smtp.from(sender);
                        smtp.to(reciever);
                        PrintStream msg = smtp.startMessage();
                        msg.println(message);
                        smtp.closeServer();
                }

                catch (Exception e) {}

        }
}
}