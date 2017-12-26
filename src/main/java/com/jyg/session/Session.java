package com.jyg.session;

import io.netty.channel.Channel;

/**
 * created by jiayaoguang at 2017年12月6日
 */
public class Session {

	private long sessionId;
	
	private Channel channel;
	
	public Session(Channel channel,long sessionId){
		this.channel = channel;
		this.sessionId = sessionId;
	}

	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	
	
	
}

