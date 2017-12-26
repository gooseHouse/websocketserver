package com.jyg.handle;

import com.jyg.bean.ConnectEvent;

import io.netty.channel.Channel;

/**
 * created by jiayaoguang at 2017年12月16日
 */
public interface WebSocketEventCallBack {

	void call(ConnectEvent event)throws Exception;
	
}

