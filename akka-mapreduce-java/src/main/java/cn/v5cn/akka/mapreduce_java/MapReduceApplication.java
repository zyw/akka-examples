package cn.v5cn.akka.mapreduce_java;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import cn.v5cn.akka.mapreduce_java.actors.MasterActor;
import cn.v5cn.akka.mapreduce_java.messages.Result;

public class MapReduceApplication {

	public static void main(String[] args) throws Exception {
		Timeout timeout = new Timeout(FiniteDuration.apply(5, "seconds"));
		
		ActorSystem _system = ActorSystem.create("MapReduceApp");
		
		ActorRef master = _system.actorOf(Props.create(MasterActor.class),"master");
		
		master.tell("The quick brown fox tried to jump over the lazy dog and fell on the dog", ActorRef.noSender());
		
		master.tell("Dog is man's best friend", ActorRef.noSender());
		
		master.tell("Dog and Fox belong to the same family", ActorRef.noSender());
		
		Thread.sleep(5000);
		
		Future<Object> future = Patterns.ask(master, new Result(), timeout);
		
		String result = (String)Await.result(future, timeout.duration());
		
		System.out.println(result);
		
		_system.shutdown();
	}

}
