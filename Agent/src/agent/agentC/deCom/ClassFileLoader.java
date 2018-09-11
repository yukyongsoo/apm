package agent.agentC.deCom;

import java.io.DataInputStream;
import java.io.File;

import jd.core.loader.LoaderException;

public class ClassFileLoader extends BaseLoader{

	protected ClassFileLoader(File file) {
		super(file);
	}

	@Override
	public boolean canLoad(String internalPath) {
		
		return false;
	}

	@Override
	public DataInputStream load(String internalPath) throws LoaderException {
		
		return null;
	}

}
