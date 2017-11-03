package com.jierong.share.util;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * @author qingf
 */
public class StringUtil {
	
	/** 
     * 判断是否为正确的邮件格式 
     * @param str 
     * @return boolean 
     */  
    public static boolean isEmail(String str){  
        if(isEmpty(str))  
            return false;  
        return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");  
    }  
    
    /** 
     * 判断字符串是否为合法手机号 11位 13 14 15 17 18开头
     * @param str 
     * @return boolean 
     */  
    public static boolean isMobile(String str){  
        if (isEmpty(str)) return false;  
        return str.matches("^(13|14|15|17|18)\\d{9}$");
    }  
    
    
    /** 
     * 判断是否为数字 
     * @param str 
     * @return 
     */  
    public static boolean isNumber(String str) {  
        try{  
            Integer.parseInt(str);  
            return true;  
        }catch(Exception ex){  
            return false;  
        }  
    } 
    
    
    /** 
     * 判断是否为浮点数或者整数 
     * @param str 
     * @return true Or false 
     */  
    public static boolean isNumeric(String str){  
          Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");  
          Matcher isNum = pattern.matcher(str);  
          if( !isNum.matches() ){  
                return false;  
          }  
          return true;  
    }  
    
    
    /** 
     * 判断字符串是否为非空(包含null与"") 
     * @param str 
     * @return 
     */  
    public static boolean isNotEmpty(String str){  
        if(str == null || "".equals(str))  
            return false;  
        return true;  
    }  
    
    /** 
     * 判断字符串是否为非空(包含null与"","    ") 
     * @param str 
     * @return 
     */  
    public static boolean isNotEmptyIgnoreBlank(String str){  
        if(str == null || "".equals(str) || "".equals(str.trim()))  
            return false;  
        return true;  
    }  
      
    /** 
     * 判断字符串是否为空(包含null与"") 
     * @param str 
     * @return 
     */  
    public static boolean isEmpty(String str){  
        if (str == null || "".equals(str)) return true;  
        return false; 
    }  
      
    /** 
     * 判断字符串是否为空(包含null与"","    ") 
     * @param str 
     * @return 
     */  
    public static boolean isEmptyIgnoreBlank(String str){  
        if(str == null || "".equals(str) || "".equals(str.trim()))  
            return true;  
        return false;  
    }  
    
	/**
	 * 格式化数据类型Double ,输出格式化后字符串
	 * @param Data 需要格式化的数字
	 * @param FormatRules 格式规则
	 * @return string: 返回格式化后字符串
	 */
	public static String DoubleToString(double Data, String FormatRules){
		DecimalFormat formatter = new DecimalFormat(FormatRules);
		return formatter.format(Data);
	}
	
	//禁止实例化  
	@SuppressWarnings("unused")
	private void ValidatorUtil(){}
}