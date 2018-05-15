


import java.io.*;

class WatchDog {

	public static void main(String args[]){
		Calls c = new Calls();
		
		c.retrieveFile("students/");
        c.retrieveFile("images/newcsitlogo.jpg");
        c.retrieveFile("images/rmitcsit.jpg");
        c.retrieveFile("images/helix.jpg");

        
		String checksum = c.getChecksum("index.html");
		String imgchecksum1 = c.getChecksum("newcsitlogo.jpg");

        String imgchecksum3 = c.getChecksum("rmitcsit.jpg");

        String imgchecksum5 = c.getChecksum("helix.jpg");

        System.out.println("Checksum of original file " +checksum);
        System.out.println("Checksum of image 1 " +imgchecksum1);

        System.out.println("Checksum of image 2" +imgchecksum3);

        System.out.println("Checksum of image 3" +imgchecksum5);

        for(int x = 0; x < 3;x++){
		
		System.out.println("Watchdog switching  sleep state -> 24hrs");
        try{
            Thread.sleep(86400000);
		}
		catch(Exception e){
		    System.out.println("Exception in sleep: "+e);
        }
        System.out.println("Watchdog switching  running state");

		
		c.retrieveFile("students/");
        c.retrieveFile("images/newcsitlogo.jpg");

        c.retrieveFile("images/rmitcsit.jpg");

        c.retrieveFile("images/helix.jpg");

		
	    String newchecksum = c.getChecksum("index.html.1");


        String newimgchecksum1 = c.getChecksum("newcsitlogo.jpg.1");

        String newimgchecksum3 = c.getChecksum("rmitcsit.jpg.1");

        String newimgchecksum5 = c.getChecksum("helix.jpg.1");

        System.out.println("Checksum of new  " +newchecksum);
        System.out.println("Checksum of new image 1 " +newimgchecksum1);

        System.out.println("Checksum of new image 2" +newimgchecksum3);

        System.out.println("Checksum of new image 3" +newimgchecksum5);


        
        if(newchecksum.equals(checksum) && newimgchecksum1.equals(imgchecksum1) && newimgchecksum3.equals(imgchecksum3)  && newimgchecksum5.equals(imgchecksum5))
	        System.out.println(" Changes Detected ");
        else{
            
            System.out.println("Changes Detected ");
            c.spotDiff();
            
            c.mail();
        }

        


        
	    c.deletePage("index.html.1");
	    c.deletePage("newcsitlogo.jpg.1*");
          c.deletePage("rmitcsit.jpg.1*");
            c.deletePage("helix.jpg.1*");
       }

    }

}



class Calls{
	public void retrieveFile(String file){
		try{
			Process p = Runtime.getRuntime().exec("wget -nv http://www.cs.rmit.edu./" +file);

			InputStream is = p.getErrorStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));

			String tempLine="";
			tempLine = bf.readLine();
			System.out.println(tempLine);

		}

		catch(Exception e){
			System.out.println("Exc in retrievePage() " +e);
		}
	}
	public String getChecksum(String cadena) {
		String tempLine="";
		try{
			Process p = Runtime.getRuntime().exec("/usr/local//md5sum " + cadena);

			InputStream is = p.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));
			tempLine = bf.readLine();

		}
		catch(Exception e){
			System.out.println(e);
		}
		return tempLine.substring(0,32);
	}

	public void deletePage(String temp) {
		String [] cad= new String[3];
        cad[0]="//sh";
        cad[1]="-c";
        cad[2]="rm " + temp;
        String tempLine="";
		try{
			Process p = Runtime.getRuntime().exec();
		}
		catch(Exception e){
			System.out.println(e);
		}

	}

    public void spotDiff(){
        String tempLine="";

        try{
            FileWriter fwr = new FileWriter("report.txt");
            BufferedWriter bwr =  new BufferedWriter(fwr);
            PrintWriter pwr = new PrintWriter(bwr);

            Process p = Runtime.getRuntime().exec("diff .html .html.1");
            InputStream is = p.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));

            pwr.println("Changes in index.html");
            pwr.flush();

            while((tempLine = bf.readLine())!=null)
			{
			    System.out.println(tempLine);
                pwr.println(tempLine);
                pwr.flush();
            }
            pwr.println("------------------------------------------------");
            pwr.flush();
            pwr.println("Changes in Images");
            pwr.flush();

            Process p1 = Runtime.getRuntime().exec("diff newcsitlogo.jpg newcsitlogo.jpg.1");
            InputStream ist = p1.getInputStream();
			BufferedReader bre = new BufferedReader(new InputStreamReader(ist));

            while((tempLine = bre.readLine())!=null)
			{
			    System.out.println(tempLine);
                pwr.println(tempLine);
                pwr.flush();
            }



            Process p3 = Runtime.getRuntime().exec("diff rmitcsit.jpg rmitcsit.jpg.1");
            InputStream ist2 = p3.getInputStream();
			BufferedReader bre2 = new BufferedReader(new InputStreamReader(ist2));

            while((tempLine = bre2.readLine())!=null)
			{
			    System.out.println(tempLine);
                pwr.println(tempLine);
                pwr.flush();
            }



            Process p5 = Runtime.getRuntime().exec("diff helix.jpg helix.jpg.1");
            InputStream ist4 = p5.getInputStream();
			BufferedReader bre4 = new BufferedReader(new InputStreamReader(ist4));

            while((tempLine = bre4.readLine())!=null)
			{
			    System.out.println(tempLine);
                pwr.println(tempLine);
                pwr.flush();
            }


        }
        catch(Exception e){
            System.out.println("Exception Spot Difference : " +e);
        }
    }

    public void mail(){
           String mail[] = new String[3];
           mail[0]="//sh";
           mail[1]="-c";
           mail[2]="mailx @cs.rmit.edu. < report.txt";
           try{
               Process p = Runtime.getRuntime().exec(mail);
           }
           catch(Exception e){
                System.out.println("Exception mail: " +e);
           }
    }


}