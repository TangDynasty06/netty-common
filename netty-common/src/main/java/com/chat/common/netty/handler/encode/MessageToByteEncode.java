package com.chat.common.netty.handler.encode;

import com.chat.common.netty.handler.CodeAndEncodeConstant;
import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageToByteEncode extends MessageToByteEncoder<MessageLite>{
	
	@Override
	protected void encode(ChannelHandlerContext ctx, MessageLite msg,
			ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		int msgSize = msg.getSerializedSize();
		if(msgSize > CodeAndEncodeConstant.MAX_BODY_LENGTH){
			System.err.println("msg too big,encode error!");
			return;
		}
		byte[] nameByteArr = msg.getClass().getName().getBytes("utf-8");
		int nameLength = nameByteArr.length;
		if(nameLength > CodeAndEncodeConstant.NAME_MAX_LENGTH){
			System.err.println("name to long!");
			return;
		}
		out.ensureWritable(CodeAndEncodeConstant.HEAD_LENGTH + CodeAndEncodeConstant.NAME_LENGTH + nameLength + CodeAndEncodeConstant.MSG_LENGTH + msgSize);
		//消息头标志
		out.writeShort(CodeAndEncodeConstant.HEAD_FLAG);
		//消息名长度
		out.writeShort(nameLength);
		//消息名
		for (int i = 0; i < nameByteArr.length; i++) {
			out.writeByte(nameByteArr[i]);
		}
		//消息体长度
		out.writeShort(msgSize);
		//消息
		out.writeBytes(msg.toByteArray());
		
	}
}
