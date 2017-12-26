package com.jyg.mrg;

import java.io.IOException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.inject.Inject;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * created by jiayaoguang at 2017年12月16日
 */
public class EventLoopGroupMrg{

	private final EventLoopGroup bossGroup = new NioEventLoopGroup(1,(Runnable r) -> new Thread(r,"ACCEPT_THREAD"));
	
	private final EventLoopGroup workGroup; 
	
	@Inject
	public EventLoopGroupMrg() {
		workGroup = new NioEventLoopGroup(8,new ThreadFactory() {
			
			AtomicInteger threadIndex = new AtomicInteger(0);
			@Override
			public Thread newThread(Runnable r) {
				
				return new Thread(r,  "IO_THREAD_"+threadIndex.incrementAndGet());
			}
		});
	}

	public EventLoopGroup getBossGroup() {
		return bossGroup;
	}

	public EventLoopGroup getWorkGroup() {
		return workGroup;
	}

	public void closeEventGroups(){
		bossGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
	}
	
}

