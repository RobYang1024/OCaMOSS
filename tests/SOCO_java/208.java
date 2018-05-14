
import java.io.*;
import java.util.*;

public class Dictionary
{
   private DicReader crack;
   private Vector clients;
   private int num;
   privateint  bigStart;

   public int Dictionary()
   {
      int i, j;
       int start, finish;
      start = 0;
      finish = 0;
      crack = new DicReader("/usr/share/lib/dict/words");
      num = crack.getVictor().size();
      clients = new Vector( num);
      j = 0;
      bigStart = System.currentTimeMillis();
      for( i = 0; i < num; i++)
      {
          MyClient1 client =  new MyClient1(this, i + 1, 80, (String)crack.getVictor().elementAt( i));
          
          clients.add( client);
	  Thread t = new Thread( client);
	  t.start();
          j++;
          if(j == 100)
          {
             t = System.currentTimeMillis();
             System.out.println("i = "+i+" "+(String)crack.getVictor().elementAt( i));
             finish = 0;
             while( (finish - t ) < 1000)
             {
                finish = System.currentTimeMillis();
             }
             j = 0;
          }
    
      }
       
   }
   public void retire(int MyClient1 )
   {
      
      bigFinish = t.getTime();
      System.out.println("It took "+(bigFinish - bigStart)/1000+" seconds  crack the password");
      System.exit(0);
   }
   public static void main (String[] args)
   {
      Dictionary  = new Dictionary();
   }
}
      
