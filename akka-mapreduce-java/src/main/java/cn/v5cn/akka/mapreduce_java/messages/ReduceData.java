package cn.v5cn.akka.mapreduce_java.messages;

import java.util.HashMap;

public final class ReduceData {
	private final HashMap<String,Integer> reduceDataList;
	
	public ReduceData(HashMap<String,Integer> reduceDataList){
		this.reduceDataList = reduceDataList;
	}

	public HashMap<String, Integer> getReduceDataList() {
		return reduceDataList;
	}
	
}
