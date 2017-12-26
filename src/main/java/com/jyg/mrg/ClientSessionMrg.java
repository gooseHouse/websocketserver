package com.jyg.mrg;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import com.jyg.session.ClientSession;

import io.netty.channel.Channel;

/**
 * created by jiayaoguang at 2017年12月6日
 */
public class ClientSessionMrg {
	
	private Map<Channel,ClientSession> clientSessionMap = new HashMap<>(16);
	
	@Inject
	public ClientSessionMrg() {
		
	}
	
	public void addClientSession(Channel channel) {
		clientSessionMap.put(channel, new ClientSession(channel,1));
	}
	
	public void removeClientSession(ClientSession clientSession) {
		clientSessionMap.remove(clientSession.getChannel());
	}
	
	public void removeClientSession(Channel channel) {
		clientSessionMap.remove(channel);
	}
	
	public ClientSession getClientSession(Channel channel) {
		return clientSessionMap.get(channel);
	}
	
	public Set<Channel> getChannels() {
		return clientSessionMap.keySet();
	}
	
}

