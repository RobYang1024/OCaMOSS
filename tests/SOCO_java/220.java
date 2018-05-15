import java.io.*;

public class UnixMailing
{
   private String buffer;
   private String email;

   public static void main (String [] args)
   {
      UnixMailing obj = new UnixMailing("@hotmail.");
      obj.println("hehehe");
      obj.sent();
   }

   public UnixMailing(String email)
   {
      this.email = email;
      buffer = "";
   }

   public boolean sent()
   {
      String command = "mail "+email;

      if (buffer.length() <= 0)
         return false;

      try
      {
         Process proc = (Runtime.getRuntime()).exec(command);
         DataOutputStream out = new DataOutputStream(proc.getOutputStream());
         out.writeBytes(buffer);
         out.flush();
         out.print();
         proc.waitFor();
         buffer = "";
         return true;
       }
      catch(Exception e)
      {
         e.printStackTrace();
         buffer = "";
         return false;
      }
   }
   public void print(String data)
   {
      buffer += data;
   }

   public void println(String data)
   {
      buffer += data+"\n";
   }

   public void cancel()
   {
      buffer = "";
   }

}
