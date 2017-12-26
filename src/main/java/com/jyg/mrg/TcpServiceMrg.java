package com.jyg.mrg;

import java.net.InetSocketAddress;

import com.google.inject.Inject;
import com.jyg.handle.HTTPServerInitializer;
import com.jyg.handle.WebsocketChatServerInitializer;
import com.jyg.util.Constants;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * created by jiayaoguang at 2017年12月16日
 */
public class TcpServiceMrg {
	
	private final EventLoopGroupMrg eventLoopGroupMrg;

	@Inject
	public TcpServiceMrg(EventLoopGroupMrg eventLoopGroupMrg){
		this.eventLoopGroupMrg = eventLoopGroupMrg;
	}
	
	/**
	 *  start http service
	 * @return
	 * @throws InterruptedException
	 */
	public InetSocketAddress startWebSocketService() throws InterruptedException  {
		
		System.out.println("websocket service is starting,port:"+Constants.SERVER_WEBSOCKET_PORT);
		
		return startService(Constants.SERVER_WEBSOCKET_PORT,new WebsocketChatServerInitializer());
	}
	
	/**
	 *  start websocket service
	 * @return
	 * @throws InterruptedException
	 */
	public InetSocketAddress startHttpSocketService() throws InterruptedException {
		
		System.out.println("http service is starting,port:"+Constants.SERVER_HTTP_PORT);
		
		return startService(Constants.SERVER_HTTP_PORT,new HTTPServerInitializer());
	}
	
	private InetSocketAddress startService(int port,ChannelInitializer<SocketChannel> initializer) throws InterruptedException {
		 ServerBootstrap b = new ServerBootstrap(); 
		 
		 b.group(eventLoopGroupMrg.getBossGroup(), eventLoopGroupMrg.getWorkGroup());
		 
		 b.channel(NioServerSocketChannel.class);
		 
		 b.handler(new LoggingHandler(LogLevel.INFO));
		 
         b.childHandler(initializer);

         b.option(ChannelOption.SO_REUSEADDR, true);
         
         b.option(ChannelOption.SO_BACKLOG, 400);
          
         b.option(ChannelOption.SO_KEEPALIVE, false);
         
         b.option(ChannelOption.TCP_NODELAY, true);
         
         b.option(ChannelOption.SO_RCVBUF, 64 * 1024);
         
         b.option(ChannelOption.SO_SNDBUF, 64 * 1024);
         
         
         b.childOption(ChannelOption.SO_LINGER, 0);
          
         b.childOption(ChannelOption.SO_KEEPALIVE, false);
         
         b.childOption(ChannelOption.TCP_NODELAY, true);
         Channel channel = b.bind(port).sync().channel(); // (7)
         
         return (InetSocketAddress)channel.localAddress();
	}
	
}

