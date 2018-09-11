package monServer.log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import monServer.util.ServerUtil;
import yuk.dic.ModelDic;
import yuk.dic.SystemDic;
import yuk.util.NormalUtil;

public class LogIndexer {
	private static HashMap<String, LogIndexer> indexMap = new HashMap<String, LogIndexer>();
	
	public static LogIndexer getIndexer(String name) {
		LogIndexer indexer = indexMap.get(name);
		if(indexer == null){
			indexer = new LogIndexer(name);
			indexMap.put(name, indexer);
		}
		return indexer;
	}
	
	long lastIndexTime = 0;
	String name = "";
	
	public LogIndexer(String name) {
		this.name = name;
	}
	
	public void indexing(long time, long filePointer) throws Exception {
		long cal = time - lastIndexTime;
		if(cal >= SystemDic.PCODE_MIN){
			long indexTime = NormalUtil.SysmileToDateLong(time, ModelDic.Min);
			BufferedWriter bw = new BufferedWriter(new FileWriter(ServerUtil.getLogFilePath(name) + "_index", true));
			bw.newLine();
			bw.write(indexTime + "$%^" + filePointer);
			bw.close();
			lastIndexTime = indexTime;
		}		
	}
	
	public IndexPoint getIndexedPoint(long start, long end) throws Exception{
		String path = ServerUtil.getLogFilePath(start, name);
		IndexPoint point = new IndexPoint();
		String sStart = String.valueOf(start);
		String sEnd = String.valueOf(end);
		File file = new File(path);
		boolean fStart = false;
		boolean fEnd = false;
		long lastStartApprox = 999999999L;
		long lastEndApprox = 999999999L;
				
		if(!file.exists())
			throw new Exception("index file not exist...it is error");
		BufferedReader br = new BufferedReader(new FileReader(ServerUtil.getLogFilePath(name) + "_index"));
		//skip first row
		br.readLine();
		String row = "";
		while((row = br.readLine()) != null){
			String[] temp = row.split("[$%^]+");
			long time = Long.parseLong(temp[0]);
			long ptr = Long.parseLong(temp[1]);			
			if(!fStart){
				if(sStart.equals(temp[0])){
					point.start = time;
					fStart = true;
				}
				else{
					long approx = time - start;
					if(approx > 0 && approx < lastStartApprox){
						point.start = ptr;
						lastStartApprox = approx;
					}
				}
			}
			if(!fEnd){
				if(sEnd.equals(temp[1])){
					point.end = ptr;
					fEnd = true;
				}
				else{
					long approx = end - time;
					if(approx > 0 && approx < lastEndApprox){
						point.end = ptr;
						lastEndApprox = approx;
					}
				}
			}
		}
		br.close();
		return point;
	}

	public class IndexPoint{
		public long start = 2;
		public long end = 0;
	}
}
