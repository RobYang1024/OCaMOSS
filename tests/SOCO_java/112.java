



import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;




public class BruteForcePropertyHelper {

	private static Properties bruteForceProps;



	public BruteForcePropertyHelper() {
	}


	

	public static String getProperty(String pKey){
		try{
			initProps();
		}
		catch(Exception e){
			System.err.println("Error init'ing the burteforce Props");
			e.printStackTrace();
		}
		return bruteForceProps.getProperty(pKey);
	}


	private static void initProps() throws Exception{
		if(bruteForceProps == null){
			bruteForceProps = new Properties();

			InputStream fis =
				BruteForcePropertyHelper.class.getResourceAsStream("/bruteforce.properties");
			bruteForceProps.load(fis);
		}
	}
}

