package com.chat.common.netty.handler.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import com.chat.common.netty.handler.CodeAndEncodeConstant;
import com.chat.common.scan.MsgScan;
import com.google.protobuf.MessageLite;

public class ByteToMessageDecode extends ByteToMessageDecoder{
	
	private static final boolean HAS_PARSER;
	
	static {
		boolean hasParser = false;
		try {
			// MessageLite.getParsetForType() is not available until protobuf
			// 2.5.0.
			MessageLite.class.getDeclaredMethod("getParserForType");
			hasParser = true;
		} catch (Throwable t) {
			// Ignore
		}

		HAS_PARSER = hasParser;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		in.markReaderIndex();
		if(in.readableBytes() < 4){
			return;
		}
		
		short msgFlag = in.readShort();
		if(msgFlag != CodeAndEncodeConstant.HEAD_FLAG){
			in.resetReaderIndex();
			return;
		}
		
		short nameLength = in.readShort();
		if(in.readableBytes() < nameLength){
			in.resetReaderIndex();
			return;
		}
		
		byte[] nameArr = new byte[nameLength];
		for (int i = 0; i < nameLength; i++) {
			nameArr[i] = in.readByte();
		}
		
		String name = new String(nameArr);
		
		if(in.readableBytes() < CodeAndEncodeConstant.MSG_LENGTH){
			in.resetReaderIndex();
			return;
		}
		
		short msgShort = in.readShort();
		if(in.readableBytes() < msgShort){
			in.resetReaderIndex();
			return;
		}
		
		byte[] msgArr = new byte[msgShort];
		for (int i = 0; i < msgShort; i++) {
			msgArr[i] = in.readByte();
		}
		
		MessageLite lite = MsgScan.getLiteByName(name);
		if(lite == null){
			in.discardReadBytes();
			return;
		}
		
		if (HAS_PARSER) {
			out.add(lite.getParserForType().parseFrom(msgArr, 0, msgShort));
		} else {
			out.add(lite.newBuilderForType().mergeFrom(msgArr, 0, msgShort).build());
		}
	}
	
}
