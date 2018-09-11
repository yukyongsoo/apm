package yuk.network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.compression.FastLzFrameDecoder;
import io.netty.handler.codec.compression.FastLzFrameEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import yuk.config.BaseProperty;
import yuk.model.MonitorData;
import yuk.network.handler.ClientHandler;
import yuk.network.session.ClientSessionManager;
import yuk.parser.DataParser;

public abstract class NetClient  {
  	private Bootstrap connector;	
	private ClientSessionManager clientSessionManager;
	private ClientHandler clientHandler;
	public abstract void sendInit() throws Exception;
	
	public Bootstrap getConnector() {
		return connector;
	}

	public ClientSessionManager getClientSessionManager() {
		return clientSessionManager;
	}

	public ClientHandler getClientHandler() {
		return clientHandler;
	}

		
	public NetClient() {
		this.clientHandler = new ClientHandler();
		this.clientSessionManager = new ClientSessionManager();
		int cores = Runtime.getRuntime().availableProcessors();
		EventLoopGroup workerGroup = new NioEventLoopGroup(cores);
		connector = new Bootstrap(); // (1)
		connector.group(workerGroup); // (2)
		connector.channel(NioSocketChannel.class); // (3)
		connector.option(ChannelOption.SO_KEEPALIVE, true);
		connector.option(ChannelOption.TCP_NODELAY, true);
		connector.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()));
				ch.pipeline().addLast(new StringDecoder(),new StringEncoder());
				if(BaseProperty.COMPRESS)
				ch.pipeline().addLast(new FastLzFrameDecoder(), new FastLzFrameEncoder());
				ch.pipeline().addLast(clientHandler);
			}
		});
    }
	
	/**
	 * start client
	 * @param ip
	 * @param port
	 * @param parser
	 * @throws Exception
	 */
	public void init(String ip, int port, DataParser parser) throws Exception{
			this.clientHandler.init(parser, this);
			this.clientSessionManager.init(ip,port,this);
			this.clientSessionManager.fullConnect(3);
			sendInit();
	}
    
    public <T> boolean syncSend(MonitorData<T> object,int num) throws Exception{
    	return SendUtil.sendData(clientSessionManager.getConnect(), object,true,num);
    }
    
    public <T> void send(MonitorData<T> object) throws Exception{
    	SendUtil.sendData(clientSessionManager.getConnect(), object);
    }
  
    public void dispose() throws Exception{
    	connector.group().shutdownGracefully();
    }     
    
    public boolean isConnected() throws Exception{ 
    	return clientSessionManager.isFullConnected();
    }
}
