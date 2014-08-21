package cn.v5cn.akka.mapreduce_java.actors;

import java.util.HashMap;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import cn.v5cn.akka.mapreduce_java.messages.ReduceData;
import cn.v5cn.akka.mapreduce_java.messages.Result;

public class AggregateActor extends UntypedActor {

	private Map<String,Integer> finalReducedMap = new HashMap<String,Integer>();
	
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof ReduceData){
			ReduceData reduceData = (ReduceData)message;
			aggregateInMemoryReduce(reduceData.getReduceDataList());
		}else if(message instanceof Result){
			getSender().tell(finalReducedMap.toString(),ActorRef.noSender());
		}else{
			unhandled(message);
		}
	}

	private void aggregateInMemoryReduce(HashMap<String, Integer> reduceDataList) {
		Integer count = null;
		for(String key : reduceDataList.keySet()){
			if(finalReducedMap.containsKey(key)){
				count = reduceDataList.get(key) + finalReducedMap.get(key);
				finalReducedMap.put(key, count);
			}else{
				finalReducedMap.put(key, reduceDataList.get(key));
			}
		}
	}

}
