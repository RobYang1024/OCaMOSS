

class WebPage
{
    
    
    private boolean success = false;
    
    private String pageContents= "";
    

	
    public WebPage()
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
    
	
    public void setPageContents (String inPage)
    {
        pageContents = inPage;
    }
    
	
    public String getPageContents()
    {
        return pageContents;
    }
}   
