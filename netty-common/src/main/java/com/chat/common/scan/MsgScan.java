package com.chat.common.scan;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.google.protobuf.MessageLite;

public class MsgScan {
	public static final String SUFFIX = "/**/*.class";
	private ResourcePatternResolver resolver;
	private static final String regex = "/.+?\\$(([A-Za-z]+)([0-9]+)))\\.class]$";
	private Pattern msgPattern;// = Pattern.compile("\\$(([A-Za-z0-9]+)([0-9]+)\\.class)]$");
	private String pathStr;
	private Map<Integer, MessageLite> typeMap = new HashMap<Integer, MessageLite>();
	private Map<Class,Integer> classMap = new HashMap<Class,Integer>();
	
	public MsgScan(String pathStr) {
		this.pathStr = format(pathStr);
		resolver = new PathMatchingResourcePatternResolver();
		msgPattern = Pattern.compile("(" + pathStr + regex);
	}


	public void initMsg(){
		if(isNULLOrEmpt(pathStr)){
			throw new RuntimeException("resolver resource error!,path is empty");
		}
		try {
			Resource[] res = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + pathStr + MsgScan.SUFFIX);
			ClassLoader classLoader = getClass().getClassLoader();
			for (Resource resource : res) {
				String descript = resource.getDescription();/**斜杠方向是不同的*/
				descript = descript.replaceAll("[\\\\]", "/");
				Matcher m = msgPattern.matcher(descript);
				if(m.find() && m.groupCount() >= 3){
					try {
						String className = m.group(1);
						int code = Integer.parseInt(m.group(4));
						
						className = className.replaceAll("/", ".");
						Class<?> claz = Class.forName(className, true, classLoader);
						Method method = claz.getMethod("getDefaultInstance");
						Object obj = method.invoke(claz);
						MessageLite lite = (MessageLite)obj;
						
						typeMap.put(code, lite);
						classMap.put(lite.getClass(), code);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("pathStr error!");
			e.printStackTrace();
		}
	}
	
	private boolean isNULLOrEmpt(String str){
		return (str == null || str.equals(""));
	}
	
	private static String format(String str){
		while(str.endsWith("/")){
			str = str.substring(0, str.length()-1);
		}
		return str;
	}
	
	public MessageLite getLiteByCode(int code){
		return typeMap.get(code);
	}
	
	public int getCodeByClass(Class cla){
		return classMap.get(cla);
	}
	
	public static void main(String[] args) {
//		String str = "abcdef/";
//		System.err.println(format(str));
//		System.err.println(str);
//		person1
		
		/*String str = "Hello,World! in Java.";
		Pattern pattern = Pattern.compile("W(or)(ld!)");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			System.out.println("Group 0:" + matcher.group(0));// 得到第0组——整个匹配
			System.out.println("Group 1:" + matcher.group(1));// 得到第一组匹配——与(or)匹配的
			System.out.println("Group 2:" + matcher.group(2));// 得到第二组匹配——与(ld!)匹配的，组也就是子表达式
			System.out.println("Start 0:" + matcher.start(0) + " End 0:" + matcher.end(0));// 总匹配的索引
			System.out.println("Start 1:" + matcher.start(1) + " End 1:" + matcher.end(1));// 第一组匹配的索引
			System.out.println("Start 2:" + matcher.start(2) + " End 2:" + matcher.end(2));// 第二组匹配的索引
			System.out.println(str.substring(matcher.start(0), matcher.end(1)));// 从总匹配开始索引到第1组匹配的结束索引之间子串——Wor
		} 
		
		
		String str1 = "Hello,World! in Java.";
		Pattern pattern1 = Pattern.compile("(W(or)(ld!))");
		Matcher matcher1 = pattern1.matcher(str1);
		while (matcher1.find()) {
			System.out.println("Group 0:" + matcher1.group(0));// 得到第0组——整个匹配
			System.out.println("Group 1:" + matcher1.group(1));// 得到第一组匹配——与(or)匹配的
			System.out.println("Group 2:" + matcher1.group(2));// 得到第二组匹配——与(ld!)匹配的，组也就是子表达式
			System.out.println("Start 0:" + matcher1.start(0) + " End 0:" + matcher1.end(0));// 总匹配的索引
			System.out.println("Start 1:" + matcher1.start(1) + " End 1:" + matcher1.end(1));// 第一组匹配的索引
			System.out.println("Start 2:" + matcher1.start(2) + " End 2:" + matcher1.end(2));// 第二组匹配的索引
			System.out.println(str1.substring(matcher1.start(0), matcher1.end(1)));// 从总匹配开始索引到第1组匹配的结束索引之间子串——Wor
		} */
		
		
		
		
	/*	String finalStr = "E:\\git\\netty-common\\netty-common\\target\\classes\\com\\chat\\common\\message\\QchatMessage$person1.class";
		Pattern p = Pattern.compile("\\$([a-zA-Z]+[a-zA-Z0-9]+)([0-9]+)\\.class$");
		Matcher m = p.matcher(finalStr);
		System.err.println(finalStr);
		finalStr = finalStr.replaceAll("[\\\\]", "/");*/

		String finalStr = "file [E:/git/netty-common/netty-common/target/classes/com/chat/common/message/QchatMessage$person100.class]";
		Pattern p = Pattern.compile("(com/chat/common/message/.+?\\$(([A-Za-z]+)([0-9]+)))\\.class]$");
		Matcher m = p.matcher(finalStr);
		
		if(m.find()){
			int groupCount = m.groupCount();
			System.out.println(groupCount);
			for (int i = 0; i <= groupCount; i++) {
				System.err.println(m.group(i) + "----------------------------" + i);
			}
		}
		
	}
}
