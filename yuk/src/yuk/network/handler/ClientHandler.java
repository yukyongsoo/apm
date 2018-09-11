package yuk.network.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import yuk.config.BaseProperty;
import yuk.dic.ModelDic;
import yuk.model.MonitorData;
import yuk.model.etc.SessionData;
import yuk.network.NetClient;
import yuk.network.SendUtil;
import yuk.network.session.SessionWaraper;
import yuk.parser.DataParser;
import yuk.util.CommonLogger;

public class ClientHandler implements ChannelInboundHandler {
	DataParser parser;
	NetClient parent;
	boolean disable = false;
	DateTimeFormatter format = DateTimeFormat.forPattern("mm:dd:ss.SSS");

	public void init(DataParser parser, NetClient netClient) {
		this.parser = parser;
		this.parent = netClient;
	}
	
	private void sendSessionData(SessionWaraper channel,boolean remove) throws Exception{
		SessionData data = new SessionData(BaseProperty.MYNAME, channel.getId());
		data.remove = remove;
		MonitorData<SessionData> mData = new MonitorData<SessionData>(ModelDic.SERVER, ModelDic.TYPE_SESSION,null);
		mData.dataList.add(data);
		SendUtil.sendData(channel, mData);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext arg0) throws Exception {
		SessionWaraper session = SessionWaraper.getSession(arg0.channel());
		sendSessionData(session, false);
		parent.sendInit();
	}

	@Override
	public void channelInactive(final ChannelHandlerContext arg0) throws Exception {
		parent.getClientSessionManager().remove(arg0.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext arg0, Object arg1) throws Exception {
		String sJsonData = (String) arg1;
		if (BaseProperty.TRACE) {
			System.out.println(format.print(new DateTime()) + " RECEIVE:" + sJsonData);
		}
		parser.parse(sJsonData, arg0.channel());
		sJsonData = null;
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext arg0)throws Exception {
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

	@Override
	public void handlerAdded(ChannelHandlerContext arg0) throws Exception {
		

	}

	@Override
	public void handlerRemoved(ChannelHandlerContext arg0) throws Exception {
		

	}
}
