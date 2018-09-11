package agent.agentC.deCom;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import jd.core.loader.LoaderException;

public class JarLoader extends BaseLoader
{
  private ZipFile zipFile;
  
  protected JarLoader(File file)
    throws LoaderException
  {
    super(file);
    
    if ((!file.exists()) || (!file.isFile()))
    {
      throw new LoaderException("'" + this.codebase + "' is not a directory");
    }
    
    try
    {
      this.zipFile = new ZipFile(this.codebase);
    }
    catch (IOException e)
    {
      throw new LoaderException("Error reading from '" + this.codebase + "'");
    }
  }
  
  public DataInputStream load(String internalPath)
    throws LoaderException
  {
    ZipEntry zipEntry = this.zipFile.getEntry(internalPath);
    
    if (zipEntry == null)
    {
      throw new LoaderException("Can not read '" + internalPath + "'");
    }
    
    try
    {
      return new DataInputStream(this.zipFile.getInputStream(zipEntry));
    }
    catch (IOException e)
    {
      throw new LoaderException("Error reading '" + internalPath + "'");
    }
  }
  
  public boolean canLoad(String internalPath)
  {
    return this.zipFile.getEntry(internalPath) != null;
  }
}
