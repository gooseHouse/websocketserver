package com.jyg.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jyg.bean.ConnectEvent;
import com.jyg.consumers.EventConsumer;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * created by jiayaoguang at 2017年12月18日
 */
public class GlobalQueue {

	
	private static final Disruptor<ConnectEvent> disruptor;
	private static final int bufferSize = 1024;
	public static final RingBuffer<ConnectEvent> ringBuffer;
	
	static {

		EventFactory<ConnectEvent> eventFactory = new EventFactory<ConnectEvent>() {
			public ConnectEvent newInstance() {
				return new ConnectEvent();
			}
		};
		
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		
		disruptor = new Disruptor<ConnectEvent>(
				eventFactory, bufferSize, executor, ProducerType.MULTI,
				new BusySpinWaitStrategy());
		
		try {
			EventHandlerGroup<ConnectEvent> handleEventsWith = disruptor.handleEventsWith(new EventConsumer()::onEvent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ringBuffer = disruptor.getRingBuffer();
		
		disruptor.start();
	}
	
}

