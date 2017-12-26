package com.jyg.world;

import com.google.inject.Inject;
import com.jyg.bean.ConnectEvent;
import com.jyg.mrg.ClientSessionMrg;
import com.jyg.mrg.EventLoopGroupMrg;
import com.jyg.mrg.TcpServiceMrg;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * created by jiayaoguang at 2017年12月17日
 */
public class World {

	private final TcpServiceMrg tcpServiceMrg;
	
	private final ClientSessionMrg clientSessionMrg;
	private final EventLoopGroupMrg eventLoopGroupMrg;
	
	@Inject
	public World(TcpServiceMrg tcpServiceMrg, ClientSessionMrg clientSessionMrg,EventLoopGroupMrg eventLoopGroupMrg) {
		this.tcpServiceMrg = tcpServiceMrg;
		this.clientSessionMrg = clientSessionMrg;
		this.eventLoopGroupMrg = eventLoopGroupMrg;
	}
	
	public void startWorld() throws InterruptedException {
		startWorldImpl();
	}
	
	protected void startWorldImpl() throws InterruptedException {
		tcpServiceMrg.startHttpSocketService();
		tcpServiceMrg.startWebSocketService();
	}
	
	
	protected void stop(Channel channel) throws InterruptedException {
		eventLoopGroupMrg.closeEventGroups();
		System.exit(0);
	}
	
	public void as_on_client_come_on(ConnectEvent event) {
		clientSessionMrg.addClientSession(event.getChannel());
		System.out.println("online:"+clientSessionMrg.getChannels().size());
	}
	
	public void as_on_client_leave(ConnectEvent event) {
		clientSessionMrg.removeClientSession(event.getChannel());
	}
	
	public void as_on_message_come(ConnectEvent event) {
		//clientSessionMrg.removeClientSession(channel);
		String text =  (String)event.getData();
		for(Channel c:clientSessionMrg.getChannels()) {
//			frame.retain();
			c.writeAndFlush(new TextWebSocketFrame(text));
		}
		
	}
	
}

