package com.pactera.znzmo.util;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

/** 
 * 类描述：单据编号生成工具类
 * @author  Liu GuiChao 
 * @date    2018年7月11日 上午11:04:32 
 * @version 1.0 
 * @parameter  
 * @return  
 */
 public class NumGenerationUtil {

	 /**
	* @Title: generateNum
	* @Description: TODO 生成单据编号
	* @param @return
	* @return String
	* @throws
	*/
	public static String generateNum(String numType) {
		 String timeStamp = DateUtils.DateToString(new Date(),DateUtils.LONG_DATE_COMPACT_FORMAT);
		 //获取后五位随机数
		 String num = RandomStringUtils.randomNumeric(5);
		 StringBuilder numStr = new StringBuilder();
		 numStr.append(numType);
		 numStr.append(timeStamp);
		 numStr.append(num);

		 return numStr.toString();
	 }

	/**
	 * @Title: generateNum
	 * @Description: TODO 获取五位随机数
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getrandom(){
		String randomNumeric = RandomStringUtils.randomNumeric(5);
		return randomNumeric;
	 }

	/**
	 * 获取16进制随机数
	 * @param len
	 * @return
	 * @throws CoderException
	 */
	public static String randomHexString(int len)  {
		StringBuffer result = new StringBuffer();
		for(int i=0;i<len;i++) {
			result.append(Integer.toHexString(new Random().nextInt(16)));
		}
		return result.toString().toUpperCase();
	}

	//测试
	/*public static void main(String[] args) {
		System.out.println(NumGenerationUtil.generateNum("DB"));
	}*/

}
