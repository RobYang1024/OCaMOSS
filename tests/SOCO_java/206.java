
import java.util.*;

public class WatchDog
{
   private Timer t;

   public WatchDog()
   {
     t  = new Timer();
      TimerTask task = new TimerTask()
      {
         public void run()
	 {
	    Dog doggy = new Dog();
	 }
      };
      
      t.schedule(task, 0, 86400000);
   }
   public static void main( String[] args)
   {
      WatchDog wd = new WatchDog();
   }
}
