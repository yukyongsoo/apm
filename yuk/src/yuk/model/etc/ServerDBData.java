package yuk.model.etc;

import java.util.ArrayList;
import java.util.List;

import yuk.model.AbsFake;

public class ServerDBData extends AbsFake{
	public String query = "";
	public String dbType = "";
	public List<String> columnList = new ArrayList<String>();
	public List<String[]> dataList = new ArrayList<String[]>();
}
