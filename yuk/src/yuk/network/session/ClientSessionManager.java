package yuk.network.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.List;
import java.util.Vector;

import yuk.network.NetClient;

public class ClientSessionManager {
	private String ip = "";
	private int port = 0;
	private int count = 3;
	private NetClient parent;
	//not good choice . mem leack test !
	List<SessionWaraper> list = new Vector<SessionWaraper>();
	
	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public NetClient getParent() {
		return parent;
	}

	public void init(String ip, int port, NetClient netClient) {
		this.ip = ip;
		this.port = port;
		this.parent = netClient;
	}

	public void fullConnect(int value) throws Exception {
		this.count = value;
		for(int i = 0; i < count; i++)
			connect();
	}

	public SessionWaraper connect() throws Exception {
		ChannelFuture cf = parent.getConnector().connect(ip, port).sync();
		SessionWaraper session = SessionWaraper.getSession(cf.channel());
		list.add(session);
		System.out.println("Connection is success");
		return session;
	}

	public boolean isFullConnected() {
		for(SessionWaraper session : list){
			if(!session.isActive())
				return false;
		}
		return true;
	}
	
	public void remove(Channel channel){
		int remove = -1;
		for(int i = 0; i < list.size(); i++){
			SessionWaraper temp = list.get(i);
			if(temp.checkChannel(channel))
				remove = i;
		}
		if(remove > -1)
			list.remove(remove);
	}

	public SessionWaraper getConnect() throws Exception {
		for(SessionWaraper session : list){
			if(session.isWritable())
				return session;
		}
		return connect();
	}
}
