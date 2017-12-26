package com.jyg.consumers;

import java.util.EnumMap;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jyg.bean.ConnectEvent;
import com.jyg.enums.SocketEventType;
import com.jyg.handle.WebSocketEventCallBack;
import com.jyg.handle.TextWebSocketFrameHandler;
import com.jyg.module.CoreModule;
import com.jyg.server.WebsocketServer;
import com.jyg.world.World;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * created by jiayaoguang at 2017年12月6日
 */
public class EventConsumer implements EventHandler<ConnectEvent>, WorkHandler<ConnectEvent> {

	private static final Map<SocketEventType, WebSocketEventCallBack> eventAndHandlerMap = new EnumMap<>(
			SocketEventType.class);

	protected static final World world;
	static {
		world = WebsocketServer.injetor.getInstance(World.class);
		
		eventAndHandlerMap.put(SocketEventType.ACTIVE, world::as_on_client_come_on);
		eventAndHandlerMap.put(SocketEventType.INACTIVE, world::as_on_client_leave);
		eventAndHandlerMap.put(SocketEventType.ON_MESSAGE_COME, world::as_on_message_come);
	}

	public EventConsumer() {

	}

	public void onEvent(ConnectEvent event, long sequence, boolean endOfBatch) throws Exception {
		this.onEvent(event);
	}

	public void onEvent(ConnectEvent event) throws Exception {

//		System.out.println(event.getChannel());

		WebSocketEventCallBack handler = eventAndHandlerMap.get(event.getEventType());
		if(handler == null) {
			throw new Exception();
		}
		handler.call(event);
	}

}
