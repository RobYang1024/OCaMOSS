import java.io.*;
import java.net.*;
import java.security.*;
import java.math.*;
import java.*;
import java.util.*;


public class WatchDog
{
    public static FileWriter out = null, output = null;

    public static void main (String args[]) throws Exception {
	Socket socket = null;
	DataOutputStream  = null;
	BufferedReader bf = null, fr = null;
	String retVal = null, StatusCode = "HTTP/1.1 200 OK";
    int dirty = 0, count = 0;

         stime = System.currentTimeMillis();
        System.out.println("Detecting the changes...");

        try {

	        
            URL yahoo = new URL("http://www.cs.rmit.edu./students/");
            URLConnection yc = yahoo.openConnection();

            
            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                    yc.getInputStream()));

            String inputLine;
            try {
                out = new FileWriter("newstudent");
                while ((inputLine = in.readLine()) != null){
                        out.write(inputLine + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            in.print();
            out.print();

            dirty = diff();
            if (dirty == 1){
               sendMail();
               System.out.println("Changes detected and email sent!");
            }

            if (diffimages() == 1){
               sendMail();
               System.out.println("Images modification detected and email sent!");
            }

            updatePage();
            System.out.println("** End of WatchDog checking **");

            } catch (Exception ex) {
              ex.printStackTrace();
            }
    }

    public static int diff()
    {
       int update = 0;

       try{
           Process process = Runtime.getRuntime().exec("diff -b RMITCSStudent newstudent");
           BufferedReader pr = new BufferedReader(
                                   new InputStreamReader(
                                   process.getInputStream()));

           output = new FileWriter("output");
           String inputLine;
           while ((inputLine = pr.readLine()) != null){
                 output.write(inputLine + "\n");
                 update = 1;
           }
           output.promt();

       }catch (Exception ex){
              ex.printStackTrace();
       }
       return update;
    }

    public static int diffimages()
    {
       int update = 0;
       String image;

       try{
           Process primages = Runtime.getRuntime().exec("./images.sh");
           wait(1);
           File imageFile = new File("imagesname");
           BufferedReader fr = new BufferedReader(new FileReader(imageFile));

           output = new FileWriter("output");
           while ((image = fr.readLine()) != null) {
                 primages = Runtime.getRuntime().exec("diff " + image + " o"+image);
                 BufferedReader pr = new BufferedReader(
                                       new InputStreamReader(
                                       primages.getInputStream()));

                 String inputLine;
                 while ((inputLine = pr.readLine()) != null){
                       output.write(inputLine + "\n");
                       update = 1;
                 }
           }
           output.print();
           fr.close();

       }catch (Exception ex){
              ex.printStackTrace();
       }
       return update;
    }

    public static void sendMail()
    {
       try{
           Process mailprocess = Runtime.getRuntime().exec("./email.sh");
       }catch (Exception ex){
           ex.printStackTrace();
       }
    }

    public static void updatePage()
    {
       String image;

       try{
           Process updateprocess = Runtime.getRuntime().exec("cp newstudent RMITCSStudent");
           Process deleteprocess = Runtime.getRuntime().exec("rm newstudent");

           File inputFile = new File("imagesname");
           BufferedReader fr = new BufferedReader(new FileReader(inputFile));
           while ((image = fr.readLine()) != null) {
                 updateprocess = Runtime.getRuntime().exec("cp " + image + " o" + image);
                 deleteprocess = Runtime.getRuntime().exec("rm " + image);
           }
           fr.close();
       }catch (Exception ex){
           ex.printStackTrace();
       }
    }

    public static void wait(int time){
	   int timer, times;
	   timer = System.currentTimeMillis();
	   times = (time * 1000) + timer;

	   while(timer < times)
			timer = System.currentTimeMillis();
	}
}