




import java.io.InputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;



public class DictionaryPropertyHelper {

	private static Properties dictProps;



	public DictionaryPropertyHelper() {
	}


	
	public static String getProperty(String pKey){
		try{
			initProps();
		}
		catch(Exception e){
			System.err.println("Error init'ing the dictionary Props");
			e.printStackTrace();
		}
		return dictProps.getProperty(pKey);
	}


	private static void initProps() throws Exception{
		if(dictProps == null){
			dictProps = new Properties();

			InputStream fis =
				DictionaryPropertyHelper.class.getResourceAsStream("/dictionary.properties");
			dictProps.load(fis);
		}
	}
}

