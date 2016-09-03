package com.chat.common.scan;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.chat.common.message.QchatMessage.person1;

public class MsgScan {
	public static final String SUFFIX = "/**/*.class";
	private ResourcePatternResolver resolver;
	private static final String regex = "^//$([a-zA-Z]+[a-zA-Z0-9]+)([0-9]+).class$";
	private Pattern msgPattern;
	private String pathStr;
	
	public MsgScan(String pathStr) {
		this.pathStr = format(pathStr);
		resolver = new PathMatchingResourcePatternResolver();
		msgPattern = Pattern.compile(this.pathStr + regex);
	}


	public void initMsg(){
		if(isNULLOrEmpt(pathStr)){
			throw new RuntimeException("resolver resource error!,path is empty");
		}
		try {
			Resource[] res = resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + pathStr + MsgScan.SUFFIX);
			for (Resource resource : res) {
				String descript = resource.getDescription();/**斜杠方向是不同的*/
				descript = descript.replaceAll("\\\\", "/");
				Matcher matcher = msgPattern.matcher(descript);
				if(matcher.find()){
					System.err.println(descript);
					System.err.print(matcher.find(0));
					System.err.print(matcher.find(1));
					System.err.print(matcher.find(2));
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
		
		
		
		
		String finalStr = "E:\\git\\netty-common\\netty-common\\target\\classes\\com\\chat\\common\\message\\QchatMessage$person1.class";
		System.err.println(finalStr);
		String a = finalStr.replaceAll("[\\\\]", "/");
		System.err.println(a);
		
		Pattern p = Pattern.compile( a + "([a-zA-Z0-9]+)\\$([a-zA-Z0-9]+)([0-9]+)\\.class\\$");
		Matcher m = p.matcher(finalStr);
		if(m.find()){
			System.err.println("a");
			System.err.println(m.group(0));
			System.err.println(m.group(1));
		}
		
	}
}
