/*
The MIT License

Copyright (c) 2016-2020 kong <congcoi123@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package com.tenio.core.network.netty.ws;

import com.tenio.core.configuration.BaseConfiguration;
import com.tenio.core.configuration.constant.ConnectionType;
import com.tenio.core.event.IEventManager;
import com.tenio.core.message.codec.MsgPackConverter;
import com.tenio.core.network.netty.BaseNettyHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

/**
 * Receive all messages sent from clients. It converts serialize data to a
 * system's object for convenience and easy to use. It also handles the logic
 * for the processing of players and connections.
 * 
 * @see BaseNettyHandler
 * 
 * @author kong
 * 
 */
public class NettyWSHandler extends BaseNettyHandler {

	public NettyWSHandler(int index, IEventManager eventManager, BaseConfiguration configuration) {
		super(eventManager, index, ConnectionType.WEB_SOCKET);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		_channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// only allow this type of frame
		if (msg instanceof BinaryWebSocketFrame) {
			// convert the BinaryWebSocketFrame to bytes' array
			var buffer = ((BinaryWebSocketFrame) msg).content();
			var bytes = new byte[buffer.readableBytes()];
			buffer.getBytes(buffer.readerIndex(), bytes);
			buffer.release();

			// create a new message
			var message = MsgPackConverter.unserialize(bytes);
			if (message == null) {
				return;
			}

			_channelRead(ctx, message, null);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		_exceptionCaught(ctx, cause);
	}

}
