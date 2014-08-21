package cn.v5cn.akka.mapreduce_scala

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Await
import akka.actor.ActorSystem
import akka.actor.Props
import cn.v5cn.akka.mapreduce_scala.actors.MasterActor

sealed trait MapReduceMessage

case class WordCount(word:String,count:Int) extends MapReduceMessage

case class MapData(dataList:ArrayBuffer[WordCount]) extends MapReduceMessage

case class ReduceData(reduceDataMap:Map[String,Int]) extends MapReduceMessage

case class Result extends MapReduceMessage

object MapReduceApplication extends App {
	val _system = ActorSystem("MapReduceApp");
	val master = _system.actorOf(Props[MasterActor],name="master")
		
	master !"The quick brown fox tried to jump over the lazy dog and fell on the dog"
	master ! "Dog is man's best friend"
	master ! "Dog and Fox belong to the same family"
	
	Thread.sleep(500)
	/*-------------------------------------------------------*/
	import akka.pattern.ask				//(master ? Result) 需要导入的包
	import akka.util.Timeout
	import scala.concurrent.duration._		//Timeout(5.seconds) 中的5.seconds或者(5 seconds)需要导入
	implicit val timeout = Timeout(5.seconds)
	val future = (master ? Result).mapTo[String]
	/*-------------------------------------------------------*/
	val result = Await.result(future, timeout.duration)
	println(result)
	
	_system.shutdown
}