package cn.v5cn.akka.mapreduce_scala.actors

import akka.actor.Actor
import cn.v5cn.akka.mapreduce_scala.MapData
import cn.v5cn.akka.mapreduce_scala.ReduceData
import cn.v5cn.akka.mapreduce_scala.Result
import akka.actor.Props
import akka.routing.RoundRobinPool
import akka.routing.RoundRobinPool

class MasterActor extends Actor {
  
  val mapActor = context.actorOf(RoundRobinPool(nrOfInstances=5).props(Props[MapActor]),name="map")
  val reduceActor = context.actorOf(RoundRobinPool(nrOfInstances=5).props(Props[ReduceActor]),name="reduce")
  val aggregateActor = context.actorOf(Props[AggregateActor],name="aggregate")
  
	def receive = {
	  case line:String =>  mapActor ! line
	  case mapData:MapData => reduceActor ! mapData
	  case reduceData:ReduceData => aggregateActor ! reduceData
	  case Result => aggregateActor forward Result
	}
}