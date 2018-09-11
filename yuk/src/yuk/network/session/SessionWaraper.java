package yuk.network.session;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class SessionWaraper {
	private static Map<Channel, SessionWaraper> wrapMap = new HashMap<Channel, SessionWaraper>();
	private Channel channel;
	private SessionWaraper(){};
	
	public static SessionWaraper getSession(Channel channel){
		if(wrapMap.get(channel) == null){
			SessionWaraper sessionWaraper = new SessionWaraper();
			sessionWaraper.channel = channel;
			wrapMap.put(channel, sessionWaraper);
		}
		return wrapMap.get(channel);
	}
	
	public boolean checkChannel(Channel channel){
		if(this.channel.equals(channel))
			return true;
		return false;
	}
	
	public String getId(){
		return channel.id().asShortText();
	}

	public ChannelFuture writeAndFlush(String string) {
		return channel.writeAndFlush(string);
	}

	public boolean isActive() {
		return channel.isActive();
	}

	public boolean isWritable() {
		return channel.isWritable();
	}
}
