package yuk.network;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import yuk.config.BaseProperty;
import yuk.model.MonitorData;
import yuk.network.session.SessionWaraper;

import com.google.gson.Gson;

public abstract class SendUtil {
	   private static Gson gson = new Gson();
	   static DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss");
	   
	   public static boolean sendData(final SessionWaraper session,final MonitorData data) throws Exception{
	      return sendData(session, data, false , 0);
	   }
	   
	   public static boolean sendData(final SessionWaraper session,final MonitorData data,boolean sync, final int num) throws Exception{
		  String json = gson.toJson(data);
	      if(session != null && session.isActive()){
	         ChannelFuture future = session.writeAndFlush(json +"\r\n");
	         if(sync){
	        	 final Thread current = Thread.currentThread();
	        	 current.wait(num);
	             future.addListener(new ChannelFutureListener() {
	                 @Override
	                 public void operationComplete(ChannelFuture future) {
	                     if(future.isSuccess() && future.syncUninterruptibly().awaitUninterruptibly(num))
	                    	 current.notify();
	                 }
	             });
	         }
	         if(BaseProperty.TRACE){
	            System.out.println(format.print(new DateTime()) +  " Send:" + json);
	         }
	         json = null;
	         return true;
	      }
	      else{
	         throw new Exception("session is not connected. value :" + session);
	      }
	   }
	}