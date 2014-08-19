package com.isuper.littleframliy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils {

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {

			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * 得到当前的时间 描述 : <描述函数实现的功能>. <br>
	 * <p>
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return dateFormater.get().format(new Date());
	}

	/**
	 * date转换成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date) {
		return dateFormater2.get().format(date);
	}

	/**
	 * 将毫秒数字符串转换成 yyyy-MM-dd HH:mm:ss 格式的字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String strToDataStr(String time) {
		Date date = new Date(toLong(time, 0));
		return dateFormater.get().format(date);
	}

	/**
	 * 将毫秒数字符串转换成 yyyy-MM-dd 格式的字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String strToDataStr2(String time) {
		Date date = new Date(toLong(time, 0));
		return dateFormater2.get().format(date);
	}

	public static String longToDataStr(Long time) {
		Date date = new Date(time);
		return dateFormater.get().format(date);
	}

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date toDateYMD(String sdate) {
		try {
			return dateFormater2.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	public static boolean isOneDay(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return false;
		}
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		return curDate.equals(paramDate);

	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		return friendly_time(time);
	}

	public static String friendly_time(Date time) {
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;

	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static long toLong(String str, int defValue) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 对象转浮点
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static float toFloat(String obj) {
		try {
			return Float.parseFloat(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}
	 /**
     * 描述：将null转化为“”.
     *
     * @param str 指定的字符串
     * @return 字符串的String类型
     */
    public static String parseEmpty(String str) {
        if(str==null || "null".equals(str.trim())){
        	str = "";
        }
        return str.trim();
    }
    
    /**
     * 获取字符串中文字符的长度（每个中文算2个字符）.
     *
     * @param str 指定的字符串
     * @return 中文字符的长度
     */
    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if(!isEmpty(str)){
	        for (int i = 0; i < str.length(); i++) {
	            /* 获取一个字符 */
	            String temp = str.substring(i, i + 1);
	            /* 判断是否为中文字符 */
	            if (temp.matches(chinese)) {
	                valueLength += 2;
	            }
	        }
        }
        return valueLength;
    }
    
    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return  字符串的长度（中文字符计2个）
     */
     public static int strLength(String str) {
         int valueLength = 0;
         String chinese = "[\u0391-\uFFE5]";
         if(!isEmpty(str)){
	         //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
	         for (int i = 0; i < str.length(); i++) {
	             //获取一个字符
	             String temp = str.substring(i, i + 1);
	             //判断是否为中文字符
	             if (temp.matches(chinese)) {
	                 //中文字符长度为2
	                 valueLength += 2;
	             } else {
	                 //其他字符长度为1
	                 valueLength += 1;
	             }
	         }
         }
         return valueLength;
     }
     
     /**
      * 描述：获取指定长度的字符所在位置.
      *
      * @param str 指定的字符串
      * @param maxL 要取到的长度（字符长度，中文字符计2个）
      * @return 字符的所在位置
      */
     public static int subStringLength(String str,int maxL) {
    	 int currentIndex = 0;
         int valueLength = 0;
         String chinese = "[\u0391-\uFFE5]";
         //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
         for (int i = 0; i < str.length(); i++) {
             //获取一个字符
             String temp = str.substring(i, i + 1);
             //判断是否为中文字符
             if (temp.matches(chinese)) {
                 //中文字符长度为2
                 valueLength += 2;
             } else {
                 //其他字符长度为1
                 valueLength += 1;
             }
             if(valueLength >= maxL){
            	 currentIndex = i;
            	 break;
             }
         }
         return currentIndex;
     }
     
    /**
     * 描述：手机号格式验证.
     *
     * @param str 指定的手机号码字符串
     * @return 是否为手机号码格式:是为true，否则false
     */
 	public static Boolean isMobileNo(String str) {
 		Boolean isMobileNo = false;
 		try {
			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
			Matcher m = p.matcher(str);
			isMobileNo = m.matches();
		} catch (Exception e) {
			e.printStackTrace();
		}
 		return isMobileNo;
 	}
 	
 	/**
	  * 描述：是否只是字母和数字.
	  *
	  * @param str 指定的字符串
	  * @return 是否只是字母和数字:是为true，否则false
	  */
 	public static Boolean isNumberLetter(String str) {
 		Boolean isNoLetter = false;
 		String expr = "^[A-Za-z0-9]+$";
 		if (str.matches(expr)) {
 			isNoLetter = true;
 		}
 		return isNoLetter;
 	}
 	
 	/**
	  * 描述：是否只是数字.
	  *
	  * @param str 指定的字符串
	  * @return 是否只是数字:是为true，否则false
	  */
 	public static Boolean isNumber(String str) {
 		Boolean isNumber = false;
 		String expr = "^[0-9]+$";
 		if (str.matches(expr)) {
 			isNumber = true;
 		}
 		return isNumber;
 	}
 	
 	/**
	  * 描述：是否是邮箱.
	  *
	  * @param str 指定的字符串
	  * @return 是否是邮箱:是为true，否则false
	  */
 	public static Boolean isEmail(String str) {
 		Boolean isEmail = false;
 		String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 		if (str.matches(expr)) {
 			isEmail = true;
 		}
 		return isEmail;
 	}
 	
 	/**
	  * 描述：是否是中文.
	  *
	  * @param str 指定的字符串
	  * @return  是否是中文:是为true，否则false
	  */
    public static Boolean isChinese(String str) {
    	Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if(!isEmpty(str)){
	         //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
	         for (int i = 0; i < str.length(); i++) {
	             //获取一个字符
	             String temp = str.substring(i, i + 1);
	             //判断是否为中文字符
	             if (temp.matches(chinese)) {
	             }else{
	            	 isChinese = false;
	             }
	         }
        }
        return isChinese;
    }
    
    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return  是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
    	Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if(!isEmpty(str)){
	         //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
	         for (int i = 0; i < str.length(); i++) {
	             //获取一个字符
	             String temp = str.substring(i, i + 1);
	             //判断是否为中文字符
	             if (temp.matches(chinese)) {
	            	 isChinese = true;
	             }else{
	            	 
	             }
	         }
        }
        return isChinese;
    }
 	
 	/**
	  * 描述：从输入流中获得String.
	  *
	  * @param is 输入流
	  * @return 获得的String
	  */
 	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			//最后一个\n删除
			if(sb.indexOf("\n")!=-1 && sb.lastIndexOf("\n") == sb.length()-1){
				sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n")+1);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
 	
 	/**
	  * 描述：标准化日期时间类型的数据，不足两位的补0.
	  *
	  * @param dateTime 预格式的时间字符串，如:2012-3-2 12:2:20
	  * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
	  */
 	public static String dateTimeFormat(String dateTime) {
		StringBuilder sb = new StringBuilder();
		try {
			if(isEmpty(dateTime)){
				return null;
			}
			String[] dateAndTime = dateTime.split(" ");
			if(dateAndTime.length>0){
				  for(String str : dateAndTime){
					if(str.indexOf("-")!=-1){
						String[] date =  str.split("-");
						for(int i=0;i<date.length;i++){
						  String str1 = date[i];
						  sb.append(strFormat2(str1));
						  if(i< date.length-1){
							  sb.append("-");
						  }
						}
					}else if(str.indexOf(":")!=-1){
						sb.append(" ");
						String[] date =  str.split(":");
						for(int i=0;i<date.length;i++){
						  String str1 = date[i];
						  sb.append(strFormat2(str1));
						  if(i< date.length-1){
							  sb.append(":");
						  }
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		return sb.toString();
	}
 	
 	/**
	  * 描述：不足2个字符的在前面补“0”.
	  *
	  * @param str 指定的字符串
	  * @return 至少2个字符的字符串
	  */
    public static String strFormat2(String str) {
		try {
			if(str.length()<=1){
				str = "0"+str;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return str;
	}
    
    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str the str
     * @param length 指定字节长度
     * @return 截取后的字符串
     */
    public static String cutString(String str,int length){
		return cutString(str, length,"");
	}
    
    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str 文本
     * @param length 字节长度
     * @param dot 省略符号
     * @return 截取后的字符串
     */
	public static String cutString(String str,int length,String dot){
		int strBLen = strlen(str,"GBK");
		if( strBLen <= length ){
     		return str;
     	}
		int temp = 0;
		StringBuffer sb = new StringBuffer(length);
		char[] ch = str.toCharArray();
		for ( char c : ch ) {
			sb.append( c );
			if ( c > 256 ) {
	    		temp += 2;
	    	} else {
	    		temp += 1;
	    	}
			if (temp >= length) {
				if( dot != null) {
					sb.append( dot );
				}
	            break;
	        }
		}
		return sb.toString();
    }
	
	/**
	 * 描述：截取字符串从第一个指定字符.
	 *
	 * @param str1 原文本
	 * @param str2 指定字符
	 * @param offset 偏移的索引
	 * @return 截取后的字符串
	 */
	public static String cutStringFromChar(String str1,String str2,int offset){
		if(isEmpty(str1)){
			return "";
		}
		int start = str1.indexOf(str2);
		if(start!=-1){
			if(str1.length()>start+offset){
				return str1.substring(start+offset);
			}
		}
		return "";
    }
	
	/**
	 * 描述：获取字节长度.
	 *
	 * @param str 文本
	 * @param charset 字符集（GBK）
	 * @return the int
	 */
	public static int strlen(String str,String charset){
		if(str==null||str.length()==0){
			return 0;
		}
		int length=0;
		try {
			length = str.getBytes(charset).length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}
    
	/**
	 * 获取大小的描述.
	 *
	 * @param size 字节个数
	 * @return  大小的描述
	 */
    public static String getSizeDesc(long size) {
	   	 String suffix = "B";
	   	 if (size >= 1024){
			suffix = "K";
			size = size>>10;
			if (size >= 1024){
				suffix = "M";
				//size /= 1024;
				size = size>>10;
				if (size >= 1024){
	    		        suffix = "G";
	    		        size = size>>10;
	    		        //size /= 1024;
		        }
			}
	   	}
        return size+suffix;
    }
    
    /**
     * 描述：ip地址转换为10进制数.
     *
     * @param ip the ip
     * @return the long
     */
    public static long ip2int(String ip){ 
    	ip = ip.replace(".", ",");
    	String[]items = ip.split(","); 
    	return Long.valueOf(items[0])<<24 |Long.valueOf(items[1])<<16 |Long.valueOf(items[2])<<8 |Long.valueOf(items[3]); 
    } 
	
//	/**
//	 * 将百度地图得到的地名去掉省市
//	 * 
//	 * @param str
//	 * @return
//	 */
//	public static String placeToString(String str) {
//		if (str == null) {
//			return "";
//		}
//		if (str.indexOf("省") == str.length() - 1) {
//			str = str.substring(0, str.indexOf("省"));
//		}
//		if (str.indexOf("市") == str.length() - 1) {
//			str = str.substring(0, str.indexOf("市"));
//		}
//		if (str.indexOf("区") == str.length() - 1) {
//			str = str.substring(0, str.indexOf("区"));
//		}
//
//		return str;
//	}
//
//	
//
//	

	

}
