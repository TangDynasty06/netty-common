package com.chat.common.netty.handler.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

public class DelimiterFrameDecoder extends DelimiterBasedFrameDecoder{

	private static final int MAX_FRAME_LENGTH = 1024;
	
	private static final ByteBuf DELIMITER = Unpooled.copiedBuffer("@$_F_F".getBytes()); 
	
	
	public DelimiterFrameDecoder(int maxFrameLength, ByteBuf delimiter) {
		super(maxFrameLength, delimiter);
		// TODO Auto-generated constructor stub
	}
	
}
