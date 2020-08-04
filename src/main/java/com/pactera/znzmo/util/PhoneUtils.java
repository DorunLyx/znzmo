package com.pactera.znzmo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(PhoneUtils.class);
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^1[345789]\\d{9}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            flag = false;

        }
        return flag;
    }
}
