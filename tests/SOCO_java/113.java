


import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;






public class MailsendPropertyHelper {

	private static Properties testProps;

	public MailsendPropertyHelper() {
	}


	

	public static String getProperty(String pKey){
		try{
			initProps();
		}
		catch(Exception e){
			System.err.println("Error init'ing the watchddog Props");
			e.printStackTrace();
		}
		return testProps.getProperty(pKey);
	}


	private static void initProps() throws Exception{
		if(testProps == null){
			testProps = new Properties();

			InputStream fis =
				MailsendPropertyHelper.class.getResourceAsStream("/mailsend.properties");
			testProps.load(fis);
		}
	}
}





