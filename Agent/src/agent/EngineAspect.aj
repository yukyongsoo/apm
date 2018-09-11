package agent;

import java.io.PrintStream;

import agent.agentC.Agent;
import agent.agentC.tracer.DbTracer;
import agent.agentC.tracer.ExceptionTracer;

public aspect EngineAspect {
	// private static Agent agent;
	// for test
	private static Agent agent = Agent.getInstance();
	
	pointcut makeExcpt(): handler(Exception+) && !within(agent..*);
	pointcut connectionPrepare() : call(* java.sql.Connection.prepareStatement(..));
	pointcut connectionCall() : call(* java.sql.Connection.prepareCall(..));
	pointcut db(): call(* java.sql.Statement.execute(..))
				  || call(* java.sql.Statement.executeQuery(..)) ||  call(* java.sql.Statement.executeUpdate(..));
	pointcut prepareDb() :  call(* java.sql.PreparedStatement.execute(..)) ||  call(* java.sql.PreparedStatement.executeQuery(..))
		||  call(* java.sql.PreparedStatement.executeUpdate(..));
	pointcut systemLog() : call(* java.io.PrintStream.print*(..)) && !within(agent..*);

	Object around() : systemLog() {
		PrintStream ps = (PrintStream)thisJoinPoint.getTarget();
		if (Agent.init && (ps.equals(System.out) || ps.equals(System.err))) {
			Object[] args = thisJoinPoint.getArgs();
			for(Object o : args){
				if(o instanceof String)
				agent.log("System", (String) o);
			}
		}
		return proceed();
	}

	Object around() : connectionPrepare() || connectionCall() {
		Object obj = proceed();
		if (Agent.init) {
			Object[] args = thisJoinPoint.getArgs();
			String query = (String) args[0];
			DbTracer.dbStart(obj, query);
		}
		return obj;
	}

	Object around() :  prepareDb() {
		Object obj;
		if (Agent.init) {
			Object o = thisJoinPoint.getTarget();
			long start = System.currentTimeMillis();
			obj = proceed();
			long excute = System.currentTimeMillis() - start;
			DbTracer.dbEnd(o, excute);
		}
		else{
			obj = proceed();
		}
		return obj;
	}

	Object around() : db(){
		Object obj;
		if (Agent.init) {
			Object[] args = thisJoinPoint.getArgs();
			String query = (String) args[0];
			long start = System.currentTimeMillis();
			obj = proceed();
			long excute = System.currentTimeMillis() - start;
			DbTracer.dbFin(query, excute);
		}
		else{
			 obj = proceed();
		}
		return obj;
	}
	
	 before() : makeExcpt(){
		 for(Object o : thisJoinPoint.getArgs()){
			 if(Agent.init && o instanceof Exception){
				 ExceptionTracer.TraceIn((Exception)o); 
			 }
		 }
	}
}