package com.pactera.znzmo.util;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JsonResponseUtils extends org.springframework.beans.BeanUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonResponseUtils.class);

	private JsonResponseUtils(){

	}

	public static <T> T  success(Class<T> classType) {
		return getRespBean(classType, JsonResponseEnum.SUCCESS);
	}

	public static <T> T  success(Class<T> classType,String msg) {
		return getRespBean(classType, JsonResponseEnum.SUCCESS.getCode(),msg);
	}

	public static <T> T  successData(Class<T> classType,Object data) {
		return getRespBean(classType, JsonResponseEnum.SUCCESS.getCode(),JsonResponseEnum.SUCCESS.getMessage(),data);
	}

	public static <T> T  successData(Class<T> classType,String msg,Object data) {
		return getRespBean(classType, JsonResponseEnum.SUCCESS.getCode(),msg,data);
	}

	public static <T> T  failure(Class<T> classType) {
		return getRespBean(classType, JsonResponseEnum.FAILED);
	}

	public static <T> T  failure(Class<T> classType,String msg) {
		return getRespBean(classType, JsonResponseEnum.FAILED.getCode(),msg);
	}
	public static <T> T  wrong(Class<T> classType,String msg) {
		return getRespBean(classType, JsonResponseEnum.WRONG.getCode(),msg);
	}


	public static <T> T  failure(Class<T> classType,int code,String msg) {
		return getRespBean(classType,code,msg);
	}

	public static <T> T  failureData(Class<T> classType,Object data) {
		return getRespBean(classType, JsonResponseEnum.FAILED.getCode(),JsonResponseEnum.FAILED.getMessage(),data);
	}

	public static <T> T  failureData(Class<T> classType,String msg,Object data) {
		return getRespBean(classType, JsonResponseEnum.FAILED.getCode(),msg,data);
	}

	public static <T> T  failureData(Class<T> classType, int code, String msg, Object data) {
		return getRespBean(classType,code,msg,data);
	}

	public static <T> T  getRespBean(Class<T> classType,JsonResponseEnum respEnum) {
        return  getRespBean(classType, respEnum.getCode(), respEnum.getMessage());
    }

	public static <T> T  getRespBean(Class<T> classType,int errcode,String errmsg) {
		return getRespBean(classType, errcode, errmsg,null);
	}

	public static <T> T  getRespBean(Class<T> classType,int errcode,String errmsg,Object data) {
		//实例化对象
        T instObj = null;
		try {

	        //实例化对象
	        instObj = (T) classType.getConstructor(new Class[] {}).newInstance(new Object[] {});

	        //获取对象中属性集合
	        Field[] fields = classType.getDeclaredFields();

	        Field[] supperFields = classType.getSuperclass().getDeclaredFields();

	        //遍历对象中的属性
	        for (Field field:fields) {
	            setFieldVals(classType, errcode, errmsg, data, instObj, field);
	        }

	        //遍历对象中的属性
	        for (Field field:supperFields) {
	            setFieldVals(classType, errcode, errmsg, data, instObj, field);
	        }
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
        return instObj;
    }

	private static <T> void setFieldVals(Class<T> classType, int errcode, String errmsg, Object data, T instObj,Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		String fieldName = field.getName();

		Object value = null;

		//匹配属性名称并赋值
		if (fieldName.equals("errcode")) {
		        value = errcode;
		}

		if (errmsg!=null && fieldName.equals("errmsg")) {
				value = errmsg;
		}

		if (data!=null && fieldName.equals("data")) {
			value = data;
		}

		// 获得属性的首字母并转换为大写，与setXXX对应
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String setMethodName = "set" + firstLetter + fieldName.substring(1);
		Method setMethod = classType.getMethod(setMethodName,new Class[] {field.getType()});
		setMethod.invoke(instObj, new Object[] { value });//调用对象的setXXX方法
	}
}
