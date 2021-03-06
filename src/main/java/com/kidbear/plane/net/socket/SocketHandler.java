package com.kidbear.plane.net.socket;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/**
 * @ClassName: HttpServerHandler
 * @Description: netty处理器
 * @author 何金成
 * @date 2015年12月18日 下午6:27:06
 * 
 */
public class SocketHandler extends ChannelHandlerAdapter {

	public SocketHandlerImp handler = new SocketHandlerImp();

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		handler.channelRead(ctx, msg);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		handler.disconnect(ctx, promise);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
	}

	public static void writeJSON(ChannelHandlerContext ctx, Object msg) {
		SocketHandlerImp.writeJSON(ctx, msg);
	}
}
