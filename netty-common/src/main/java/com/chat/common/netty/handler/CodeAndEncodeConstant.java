package com.chat.common.netty.handler;

public class CodeAndEncodeConstant {
	public final static short MAX_BODY_LENGTH = 6666;//消息体的最大长度（byte个数）
	public final static byte NAME_MAX_LENGTH = 110; //消息名的最大长度（byte个数）
	
	public final static byte HEAD_LENGTH =2; //消息头的长度（byte个数）
	public final static short HEAD_FLAG = 666;//消息头的标志信息内容
	
	public final static byte NAME_LENGTH = 2;//消息名的长度（byte个数） 
	public final static byte MSG_LENGTH = 2;//消息长度字段的长度（byte个数）
}
