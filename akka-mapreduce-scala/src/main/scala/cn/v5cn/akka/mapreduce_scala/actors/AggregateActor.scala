package cn.v5cn.akka.mapreduce_scala.actors

import akka.actor.Actor
import cn.v5cn.akka.mapreduce_scala.ReduceData
import cn.v5cn.akka.mapreduce_scala.Result
import scala.collection.mutable.HashMap

class AggregateActor extends Actor {
  
  val finalReducedMap = new HashMap[String ,Int]
  
	def receive = {
	  case ReduceData(reduceDataMap) =>
	    aggregateInMemoryReduce(reduceDataMap)
	  case Result =>
	    sender ! finalReducedMap.toString()
	}
  
  def aggregateInMemoryReduce(reducedList:Map[String,Int]):Unit={
    for((key,value) <- reducedList){
      if(finalReducedMap contains key){
        finalReducedMap(key) = (finalReducedMap.get(key).get + value)
      }else{
        finalReducedMap += (key -> value)
      }
    }
  }
}