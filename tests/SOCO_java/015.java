



public class HoldSharedData
{
    private int      numOfConnections = 0;
    private   int   startTime;
    private   int   totalTime = 0;
    private String[] password;
    private int      pwdCount;

    public HoldSharedData( int time, String[] pwd, int count )
    {
        startTime = time;

        password = pwd;
        pwdCount = count;
    }

    public int getPwdCount()
    {
        return pwdCount;
    }

    public void setNumOfConnections( )
    {
        numOfConnections ++;
    }

    public int getNumOfConnections()
    {
        return numOfConnections;
    }

    public int getStartTime()
    {
        return startTime;
    }

    public void setTotalTime( int newTotalTime )
    {
        totalTime = newTotalTime;
    }

    public int  getTotalTime()
    {
        return totalTime;
    }

    public String getPasswordAt( int index )
    {
        return password[index];
    }
}  
