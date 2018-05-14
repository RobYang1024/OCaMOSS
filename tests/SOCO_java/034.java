

class LoginAttemptResults
{
    
    private boolean success = false;
    
    private int passwordsTried = 0;
    

	
    public LoginAttemptResults()
    {
    }
    
	
    public void setSuccess (boolean inSuccess)
    {
        success = inSuccess;
    }
    
	
    public boolean getSuccess()
    {
        return success;
    }
    
	
    public void setPasswordsTried ( int inPasswordsTried)
    {
        passwordsTried = inPasswordsTried;
    }
    
	
    public int getPasswordsTried()
    {
        return passwordsTried;
    }
}   
