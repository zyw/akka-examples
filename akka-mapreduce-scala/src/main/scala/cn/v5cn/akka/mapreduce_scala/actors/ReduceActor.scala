package cn.v5cn.akka.mapreduce_scala.actors

import akka.actor.Actor
import cn.v5cn.akka.mapreduce_scala.MapData
import cn.v5cn.akka.mapreduce_scala.WordCount
import cn.v5cn.akka.mapreduce_scala.ReduceData

class ReduceActor extends Actor {
	def receive = {
	  case MapData(dataList) =>
	    sender ! reduce(dataList)
	}
	
	def reduce(words:IndexedSeq[WordCount]) = ReduceData{
	  words.foldLeft(Map.empty[String,Int]){
	    (index,words) => 
	      if(index contains words.word)
	        index + (words.word -> (index.get(words.word).get + 1))
	      else
	        index + (words.word -> 1)
	  }
	}
}