package yuk.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import yuk.config.BaseProperty;
import yuk.network.NetAccepter;
import yuk.parser.DataParser;
import yuk.util.CommonLogger;

public class ServerHandler implements ChannelInboundHandler {
	DataParser dataParser;
	NetAccepter parent;
	DateTimeFormatter format = DateTimeFormat.forPattern("mm:dd:ss.SSS");

	public void init(DataParser parser, NetAccepter parent) {
		this.dataParser = parser;
		this.parent = parent;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
		
		
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
		
		
	}

	@Override
	public void channelActive(ChannelHandlerContext arg0) throws Exception {
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext arg0) throws Exception {
		parent.getManager().removeSession(arg0.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext arg0, Object arg1) throws Exception {
		String sJsonData  = (String) arg1;		
		dataParser.parse(sJsonData, arg0.channel());
		if(BaseProperty.TRACE){
			System.out.println(format.print(new DateTime()) +  " RECEIVE:" + sJsonData);
		}
		sJsonData = null;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext arg0) throws Exception {
		arg0.flush();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext arg0) throws Exception {
		
		
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext arg0) throws Exception {
		
		
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext arg0) throws Exception {
		
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, Throwable arg1) throws Exception {
		CommonLogger.getLogger().error(getClass(), "bis logic error", new Exception(arg1));		
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext arg0, Object arg1) throws Exception {
		
		
	}
}
