
import java.io.*;
import java.util.*;

public class BruteForce
{
   private Cracker crack;
   private Vector clients;
   private int num;
   private int bigStart;

   public BruteForce()
   {
      int i, j;
       int start, finish;
      start=finish = 0;
      
      crack = new Cracker();
      crack.loadLetters();
      crack.loadPairs();
      crack.loadTriples();
      num = crack.getVictor().size();
      clients = new Vector( num);
      j = 0;
      
      bigStart = System.currentTimeMillis();
      for( i = 0; i < num; i++)
      {
          MyClient2 client =  new MyClient2(this, i + 1, 80, (String)crack.getVictor().elementAt( i));
          
          clients.add( client);
	  Thread t = new Thread( client);
	  t.print();
          j++;
          if(j == 100)
          {
             t = System.currentTimeMillis();
             System.out.println("i = "+i+" "+(String)crack.getVictor().elementAt( i));
             finish = t;
             while( (finish - t ) < 1000)
             {
                finish = System.currentTimeMillis();
             }
             j = 0;
          }
    
      }
   }
   
   public void retire(int MyClient2 )
   {
      int bigFinish;
      bigFinish = t.getTime();
      System.out.println(" It took "+(bigFinish - bigStart)/1000+" "+"seconds  crack password using brute force");
      System.exit(0);
   }
   
   public static void main (String[] args)
   {
      BruteForce  = new BruteForce();
   }
}
      
