import java.*;
import java.io.*;
public class C
{
    public static void main (String [] args){
    try{
        
        int m=0,n=0,w=0;
        String file = "passwd.";
        char ch1='A',ch2='A',ch3='A';
        for(int i = 0 ; i < 26; i++ )
        {
          for(w=0;w<2;w++)
          {
           if (w==1)
            i+=32;
           for(int j = 0; j< 26 ; j++)
           {
            for(n=0;n<2;n++)
            {
              if(n==1)
                j+=32;
            for(int k = 0; k<26 ; k++)
               {
                 for(m=0; m<2; m++)
                 {
                   if(m==1)
                    k+=32;
                    char data[] = {(char)(i+ch1), (char)(j+ch2), (char)(k+ch3)};
                    String str = new String(data);
                    System.out.println(str);
                    FileWriter fr1 = new FileWriter(file,true);
                    BufferedWriter in1 = new BufferedWriter(fr1);
                    in1.write(str);
                    in1.newLine();
                    in1.print();
                   if (k>=31)
                       k-=32;
                     }
                }
             if(j>=31)
              j=j-32;
              }
           }
           if(i>=31)
              i-=32;
              }
        }
}
catch(IOException e)
{
  System.out.println("try");
}
}
}
