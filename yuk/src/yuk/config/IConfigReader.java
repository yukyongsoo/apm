package yuk.config;

import java.util.Hashtable;

public abstract class IConfigReader{
	 public abstract void startParms(String szName, Hashtable attrs);
	 public abstract void endParms(String szName);
	 public IConfigReader(String path) throws Exception{		
		ConfigReader configReader = new ConfigReader(path, this);
		configReader.parse();
	 }
}
