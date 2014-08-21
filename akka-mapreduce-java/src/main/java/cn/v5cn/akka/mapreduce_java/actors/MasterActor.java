package cn.v5cn.akka.mapreduce_java.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;
import cn.v5cn.akka.mapreduce_java.messages.MapData;
import cn.v5cn.akka.mapreduce_java.messages.ReduceData;
import cn.v5cn.akka.mapreduce_java.messages.Result;

public class MasterActor extends UntypedActor {
	
	ActorRef mapActor = getContext().actorOf(new RoundRobinPool(5).props(Props.create(MapActor.class)),"map");//getContext().actorOf(Props.create(MapActor.class).withRouter(new RoundRobinRouter(5)),"map");
	ActorRef reduceActor = getContext().actorOf(new RoundRobinPool(5).props(Props.create(ReduceActor.class)),"reduce");//.withRouter(new RoundRobinRouter(5)),"reduce");
	ActorRef aggregateActor = getContext().actorOf(Props.create(AggregateActor.class),"aggregate");
	
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof String){
			mapActor.tell(message, getSelf());
		}else if(message instanceof MapData){
			reduceActor.tell(message, getSelf());
		}else if(message instanceof ReduceData){
			aggregateActor.tell(message, ActorRef.noSender());
		}else if(message instanceof Result){
			aggregateActor.forward(message, getContext());
		}else{
			unhandled(message);
		}
	}

}
