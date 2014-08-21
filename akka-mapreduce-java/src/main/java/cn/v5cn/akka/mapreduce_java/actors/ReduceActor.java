package cn.v5cn.akka.mapreduce_java.actors;

import java.util.HashMap;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import cn.v5cn.akka.mapreduce_java.messages.MapData;
import cn.v5cn.akka.mapreduce_java.messages.ReduceData;
import cn.v5cn.akka.mapreduce_java.messages.WordCount;

public class ReduceActor extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof MapData){
			MapData mapData = (MapData)message;
			getSender().tell(reduce(mapData.getDataList()), ActorRef.noSender());
		}else{
			unhandled(message);
		}
	}

	private ReduceData reduce(List<WordCount> dataList) {
		HashMap<String,Integer> reducedMap = new HashMap<String,Integer>();
		for(WordCount wordCount : dataList){
			if(reducedMap.containsKey(wordCount.getWord())){
				Integer value = reducedMap.get(wordCount.getWord());
				value++;
				reducedMap.put(wordCount.getWord(), value);
			}else{
				reducedMap.put(wordCount.getWord(), Integer.valueOf(1));
			}
		}
		return new ReduceData(reducedMap);
	}

}
