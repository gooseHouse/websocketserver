package com.jyg.handle;

import io.netty.channel.Channel;

/**
 * created by jiayaoguang at 2017年12月16日
 */
public interface HttpEventCallBack {

	void call(Channel cahnnel)throws Exception;
	
}

