package cn.v5cn.akka.mapreduce_scala.actors

import akka.actor.Actor
import scala.collection.mutable.ArrayBuffer
import cn.v5cn.akka.mapreduce_scala.MapData
import cn.v5cn.akka.mapreduce_scala.WordCount

class MapActor extends Actor {
  val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at",
		  "be","do", "go", "if", "in", "is", "it", "of", "on", "the", "to")
    val defaultCount: Int = 1
	def receive = {
	  case message:String => 
	    sender ! evaluateExpression(message);
	}
	
	def evaluateExpression(line:String) = MapData{
	  line.split("""\s+""").foldLeft(ArrayBuffer.empty[WordCount]){
	    (conn,item)=>
	      if(!STOP_WORDS_LIST.contains(item.toLowerCase()))
	        conn += WordCount(item.toLowerCase(),1)
	      else
	        conn
	  }
	}
}