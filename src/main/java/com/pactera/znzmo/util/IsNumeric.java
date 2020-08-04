package com.pactera.znzmo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsNumeric {
    //是否是数字类型
    public static boolean isNumeric(String str){
        Pattern pattern= Pattern.compile("^[0-9]*|^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match=pattern.matcher(str);
        if(match.matches()==false) {
            return false;
        }
        else {
            return true;
        }
    }

}
