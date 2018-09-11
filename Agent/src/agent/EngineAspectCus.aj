package agent;

import org.aspectj.lang.Signature;

import agent.agentC.Agent;
import agent.agentC.tracer.ThreadTracer;
import yuk.model.single.ThreadHotSpotData;


public abstract aspect EngineAspectCus  {
	private static Agent agent = Agent.getInstance();
	abstract pointcut init();
	abstract pointcut end();
	abstract pointcut transStart();
	abstract pointcut transEnd();
	abstract pointcut microTrans();
	abstract pointcut log();
	
	after() : init() {
		agent.init();
	}

	before() : end() {
		agent.end();
	}

	after() returning() : log() {
		if (Agent.init) 
			agent.log(thisJoinPoint.getArgs());
	}
	
	after() : transStart() {
		if(Agent.init)
			agent.transStart(thisJoinPoint.getTarget(),	thisJoinPoint.getArgs());
	}

	Object around() throws Exception : transEnd() {
		try {
			Object obj = proceed();
			if (Agent.init) 				
				agent.transEnd(thisJoinPoint.getTarget(),obj,thisJoinPoint.getArgs());
			return obj;
		} catch (Exception e) {
			if (Agent.init) 				
				agent.transEnd(thisJoinPoint.getTarget(),null,thisJoinPoint.getArgs());
			throw e;
		}
	}
	
	Object around() throws Exception : microTrans() {
		if(Agent.init) {
			Signature sig = thisJoinPoint.getSignature();
			String shortName = sig.toShortString();
			ThreadHotSpotData traceIn = null;
			try {
				traceIn = ThreadTracer.traceIn(shortName,sig.toLongString());
				Object obj = proceed();
				ThreadTracer.traceOut(shortName,traceIn);
				return obj;
			} catch (Exception e) {
				ThreadTracer.traceOut(shortName,traceIn);
				throw e;
			}
		}
		else{
			Object obj = proceed();
			return obj;
		}
	}	
}
