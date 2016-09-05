package com.chat.common.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MsgEncode extends MessageToByteEncoder<String>{
	//private static final ByteBuf delimiter = Unpooled.copiedBuffer("@$_F_F".getBytes());
	private static final String delimiter = "@$_F_F";
	
	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out)
			throws Exception {
		// TODO Auto-generated method stub
		String tempStr = msg + delimiter;
		int totalLength = tempStr.getBytes().length;
		if(totalLength > 1024){
			System.err.println("msg is too long");
			return;
		}
		out.ensureWritable(totalLength,true);
		out.writeBytes(tempStr.getBytes());
	}

}
