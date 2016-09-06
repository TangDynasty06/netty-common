package com.chat.common.netty.handler.encode;

import com.google.protobuf.MessageLite;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageToByteEncode extends MessageToByteEncoder<MessageLite>{
	
	public final static short MAX_BODY_LENGTH = 6666;
	public final static byte NAME_MAX_LENGTH = 110; 
	
	public final static byte HEAD_LENGTH =2; public final static short HEAD_FLAG = 666;
	public final static byte NAME_LENGTH = 2; 
	public final static byte MSG_LENGTH = 2;
	
	
	
	@Override
	protected void encode(ChannelHandlerContext ctx, MessageLite msg,
			ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
		int msgSize = msg.getSerializedSize();
		if(msgSize > MAX_BODY_LENGTH){
			System.err.println("msg too big,encode error!");
			return;
		}
		
		byte[] nameByteArr = msg.getClass().getName().getBytes();
		int nameLength = nameByteArr.length;
		if(nameLength > NAME_MAX_LENGTH){
			System.err.println("name to long!");
			return;
		}
		
		out.ensureWritable(HEAD_LENGTH + NAME_LENGTH + nameLength + MSG_LENGTH + msgSize);
		//消息头标志
		out.writeShort(HEAD_FLAG);
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
	
	public static void main(String[] args) {
		int a = Integer.parseUnsignedInt("100000000", 2);
		System.err.println(a);
		System.err.println( a >> 8);
		System.err.println(a);
		
		System.err.println("aaaaaa".getBytes().length);
	}
}
