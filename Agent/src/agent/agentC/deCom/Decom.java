package agent.agentC.deCom;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jd.core.loader.Loader;
import jd.core.model.classfile.ClassFile;
import jd.core.model.layout.block.LayoutBlock;
import jd.core.model.reference.ReferenceMap;
import jd.core.process.analyzer.classfile.ClassFileAnalyzer;
import jd.core.process.analyzer.classfile.ReferenceAnalyzer;
import jd.core.process.deserializer.ClassFileDeserializer;
import jd.core.process.layouter.ClassFileLayouter;
import jd.core.process.writer.ClassFileWriter;
import yuk.util.CommonLogger;

public class Decom {
	private static Map<String, Decom> codeCache = new ConcurrentHashMap<String, Decom>();
	
	public static Map<String, Decom> getCodeCache() {
		return codeCache;
	}

	public long time = System.currentTimeMillis();
	public String code = "";
	
	private Decom(){
		
	}
	
	public static Decom getClassName(String className){
		Decom de = codeCache.get(className);
		if(de == null){
			de = new Decom();
			try {
				String temp = className;
				int i = temp.lastIndexOf(".");
				if(i > -1) 
					temp = temp.substring(i + 1);
				temp = temp + ".class";
				URL resource = Class.forName(className).getResource(temp);
				Loader loader = null;
				String filepath = "";
				if ("jar".equalsIgnoreCase(resource.getProtocol())){
					temp = resource.getPath();
					temp = temp.replace("file:/", "");
					String[] tmp = temp.split("!");
					loader = new JarLoader(new File(tmp[0])); 
					filepath = tmp[1].replaceFirst("/", "");
				}
				else{
					String tmp = resource.getPath();
					tmp = tmp.replace("file:/", "");
					int index = tmp.lastIndexOf("/");
					tmp = tmp.substring(0, index);
					loader = new DirectoryLoader(new File(tmp));	
					filepath = temp;
				}				
				de.code = deCompile(filepath,loader);
			} catch (Exception e) {
				CommonLogger.getLogger().warn(Decom.class, "find file path for decompile." + className, e);
			}
		}
		return de;
	}
	
	private static String deCompile(String file,Loader loader) {
		String code = "";
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);		   
			CommonPreferences pre = new CommonPreferences(false, false, true, false, false, false);
			PlainTextPrinter printer = new PlainTextPrinter(pre, ps);		  
			ClassFile classFile = ClassFileDeserializer.Deserialize(loader, file);
			ReferenceMap referenceMap = new ReferenceMap();
			ClassFileAnalyzer.Analyze(referenceMap, classFile);
			ReferenceAnalyzer.Analyze(referenceMap, classFile);
			ArrayList<LayoutBlock> layoutBlockList = new ArrayList<LayoutBlock>();
			int maxLineNumber = ClassFileLayouter.Layout(pre, referenceMap, classFile, layoutBlockList);
			ClassFileWriter.Write(loader, printer, referenceMap, maxLineNumber,classFile.getMajorVersion(), classFile.getMinorVersion(), layoutBlockList);
			code = new String(baos.toByteArray());	
		} catch (Exception e) {
			CommonLogger.getLogger().warn(Decom.class, "decompile " + file + " fail.", e);
		}
		return code;
	}
	
	
}
