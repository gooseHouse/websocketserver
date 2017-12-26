package com.jyg.bean;

import com.jyg.enums.SocketEventType;
import com.jyg.handle.TextWebSocketFrameHandler;

import io.netty.channel.Channel;

/**
 * created by jiayaoguang at 2017年12月6日
 */
public class ConnectEvent {

	private Channel channel;
	
	private SocketEventType eventType;

	private Object data;
	
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
		
//		TextWebSocketFrameHandler.channels.add(channel);
	}

	public SocketEventType getEventType() {
		return eventType;
	}

	public void setEventType(SocketEventType eventType) {
		this.eventType = eventType;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}

