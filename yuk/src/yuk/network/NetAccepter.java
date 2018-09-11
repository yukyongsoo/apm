package yuk.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.compression.FastLzFrameDecoder;
import io.netty.handler.codec.compression.FastLzFrameEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import yuk.config.BaseProperty;
import yuk.network.handler.ServerHandler;
import yuk.network.session.ServerSessionManager;
import yuk.parser.DataParser;

public class NetAccepter {
	private ServerBootstrap acceptor;
	private ServerSessionManager manager;
	private ServerHandler handler;
	
	public ServerHandler getHandler() {
		return handler;
	}

	public ServerSessionManager getManager() {
		return manager;
	}
	
	public NetAccepter() throws Exception{	
			manager = new ServerSessionManager();
			handler = new ServerHandler();
			int cores = Runtime.getRuntime().availableProcessors();
			EventLoopGroup bossGroup = new NioEventLoopGroup(cores); // (1)
			EventLoopGroup workerGroup = new NioEventLoopGroup(cores);
			acceptor = new ServerBootstrap();
			acceptor.group(bossGroup, workerGroup);
			acceptor.channel(NioServerSocketChannel.class);
			acceptor.option(ChannelOption.SO_BACKLOG, 1000);
			acceptor.option(ChannelOption.SO_KEEPALIVE, true);
			acceptor.option(ChannelOption.TCP_NODELAY, true);
			acceptor.childHandler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()));
					ch.pipeline().addLast(new StringDecoder(),new StringEncoder());
					if(BaseProperty.COMPRESS)
					ch.pipeline().addLast(new FastLzFrameDecoder(), new FastLzFrameEncoder());
					ch.pipeline().addLast(handler);
				}
			});
	}
	
	/**
	 * start bind
	 * @param ip
	 * @param port
	 * @param parser
	 * @return
	 * @throws InterruptedException 
	 */
	public void init(String ip,int port,final DataParser parser) throws InterruptedException{
		this.handler.init(parser, this);
		this.manager.init(this);
		ChannelFuture f = acceptor.bind(ip,port).sync();
	}

    public void dispose() throws Exception{    	
    	acceptor.group().shutdownGracefully();
    }     
    
    
}
