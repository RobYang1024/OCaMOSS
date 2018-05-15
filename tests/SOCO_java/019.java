



import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class WatchDog
{
    public static void main ( String args[] )
    {
        String input = JOptionPane.showInputDialog("Enter time range(seconds)");
        if(  input == null  )
           System.exit(0);

        int range = Integer.parseInt( input );

        Timer timer = new Timer();
        WatchDogTask watchDogTask = new WatchDogTask();
        timer.schedule( watchDogTask, new Date(), range*1000 );
    }
}  

