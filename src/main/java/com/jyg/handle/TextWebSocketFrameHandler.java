package com.jyg.handle;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.HashSet;
import java.util.Set;

import com.jyg.bean.ConnectEvent;
import com.jyg.enums.SocketEventType;
import com.jyg.mrg.ChannelMrg;
import com.jyg.util.GlobalQueue;

/**
 * created by jiayaoguang at 2017年12月6日
 */
public class TextWebSocketFrameHandler extends
		SimpleChannelInboundHandler<WebSocketFrame> {

	public static Set<Channel> channels = new HashSet<>();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx,
			WebSocketFrame frame) throws Exception { // (1)
		
		//|| frame instanceof BinaryWebSocketFrame
		if(frame instanceof TextWebSocketFrame ) {
			//System.out.println("recive read action");
			Channel incoming = ctx.channel();
//			msg.retain();
//			String request = msg.text();
//			TextWebSocketFrame responseFrame = new TextWebSocketFrame(frame.content());
			
			String text = ((TextWebSocketFrame) frame).text();
			
			long seq = GlobalQueue.ringBuffer.next();
			try {
				ConnectEvent event = GlobalQueue.ringBuffer.get(seq);
				event.setEventType(SocketEventType.ON_MESSAGE_COME);
				event.setChannel(ctx.channel());
				event.setData(text);
			}finally {
				GlobalQueue.ringBuffer.publish(seq);
			}
			return;
		}
		
		if(frame instanceof PingWebSocketFrame) {
//			incoming.write(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		
		if(frame instanceof CloseWebSocketFrame) {
			
			return;
		}
		
		System.out.println("other frame.........");
		
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
		super.handlerAdded(ctx);
//		Channel incoming = ctx.channel();
//		for (Channel channel : channels) {
//			channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - "
//					+ incoming.remoteAddress() + " 加入"));
//		}
//		channels.add(ctx.channel());
//		System.out.println("Client:" + incoming.remoteAddress() + "加入");
		System.out.println("add");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		super.handlerRemoved(ctx);
//		Channel incoming = ctx.channel();
//		for (Channel channel : channels) {
//			channel.writeAndFlush(new TextWebSocketFrame("[SERVER] - "
//					+ incoming.remoteAddress() + " 离开"));
//		}
//		System.out.println("Client:" + incoming.remoteAddress() + "离开");
//		channels.remove(ctx.channel());
		System.out.println("remove");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "在线");
		
		long seq = GlobalQueue.ringBuffer.next();
		try {
			ConnectEvent event = GlobalQueue.ringBuffer.get(seq);
			event.setEventType(SocketEventType.ACTIVE);
			event.setChannel(ctx.channel());
		}finally {
			GlobalQueue.ringBuffer.publish(seq);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "掉线");
		
		long seq = GlobalQueue.ringBuffer.next();
		try {
			ConnectEvent event = GlobalQueue.ringBuffer.get(seq);
			
			event.setChannel(ctx.channel());
			
			event.setEventType(SocketEventType.INACTIVE);
		}finally {
			GlobalQueue.ringBuffer.publish(seq);
		}
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}

}