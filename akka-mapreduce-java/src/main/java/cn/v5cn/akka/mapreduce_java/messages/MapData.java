package cn.v5cn.akka.mapreduce_java.messages;

import java.util.List;

public final class MapData {
	private final List<WordCount> dataList;
	
	public MapData(List<WordCount> dataList){
		this.dataList = dataList;
	}
	
	public List<WordCount> getDataList(){
		return dataList;
	}
}
