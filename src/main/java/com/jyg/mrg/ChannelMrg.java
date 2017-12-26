package com.jyg.mrg;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.google.inject.Inject;
import com.jyg.bean.ConnectEvent;
import com.jyg.consumers.EventConsumer;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * created by jiayaoguang at 2017年12月6日
 */
public class ChannelMrg {
	

	@Inject
	public ChannelMrg() {

	}
	
	
	
	public Channel connect(String ip,String port){
		
		
		
		return null;
	}

}
