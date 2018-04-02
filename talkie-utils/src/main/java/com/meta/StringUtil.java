package com.meta;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 描述：字符串操作工具类 
 */
public class StringUtil {

	/**
	 * 随机指定范围内N个不重复的数
	 * 最简单最基本的方法
	 * @param min 指定范围最小值
	 * @param max 指定范围最大值
	 * @param n 随机数个数
	 */
	public static int[] randomCommon(int min, int max, int n){
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while(count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if(num == result[j]){
					flag = false;
					break;
				}
			}
			if(flag){
				result[count] = num;
				count++;
			}
		}
		return result;
	}


	public static boolean isInteger(Integer num){
		
		if(num!=null&&num>0){
			return true;
		}
		return false;
	}
	
	public static  boolean isDouble(Double dnum){
		
		if(dnum!=null&&dnum>0){
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为Null或者为空字符串
	 *
	 * @author Tod.xie
	 * 2010-11-1 下午01:57:33
	 * StringUtil.java
	 * @param obj
	 * @return
	 * TODO : 
	 *
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null || obj.toString().trim().equals(""))
			return true;
		return false;
	}

	/**
	 * 判断是否为Null或者为空字符串或者未“null”
	 *
	 * @author Tod.xie
	 * 2010-11-1 下午01:57:25
	 * StringUtil.java
	 * @param obj
	 * @return
	 * TODO : 
	 *
	 */
	public static boolean isNullEmpty(Object obj) {
		if (obj == null || obj.toString().trim().equals("") || obj.toString().toLowerCase().trim().equals("null"))
			return true;
		return false;
	}

	/**
	 * 判断是否为空字符串
	 *
	 * @author Tod.xie
	 * 2010-11-4 上午02:02:02
	 * StringUtil.java
	 * @param obj
	 * @return
	 * TODO : 
	 *
	 */
	public static boolean isBlank(String obj){
		if (obj == null || obj.trim().equals("")){
			return true;
		}
		return false;
	}

	/**
	 * Return empty string if parameter is null 
	 * 
	 * @param s
	 *            the parameter to check
	 * @return String return the parameter if empty or if the string == null
	 */
	public static String null2Empty(String s) {
		if (s == null || s.equalsIgnoreCase("null")) {
			return "";
		} else {
			return s;
		}
	}

	/**
	 * Return null if parameter is empty 
	 *
	 * @author Tod.xie
	 * 2010-11-1 下午04:18:07
	 * StringUtil.java
	 * @param s
	 * @return
	 * TODO : 
	 *
	 */
	public static String empty2Null(Object s) {
		if(isNullOrEmpty(s))
			return "null";
		else
			return String.valueOf(s);
	}
	
	/*
	 * 如果null，返回字符串0
	 */
	public static String null2Zero(Object s) {
		if (s == null || (String.valueOf(s)).equalsIgnoreCase("null"))
			return "0";
		else
			return String.valueOf(s);
	}
	/*
     * 如果null，返回数字0
     */
	public static Integer nullToZero(Object s) {
		if (StringUtil.isNullOrEmpty(s) || (String.valueOf(s)).equalsIgnoreCase("null"))
			return 0;
		else
			return Integer.valueOf(s.toString());
	}

	/**
	 * 判断字符str是否包含在strArray字符组中，不区分大小写
	 * @param str
	 * @param strArray
	 * @param isCaseSensitive 是否区分大小写，true：区分；false：不区分
	 * @return
	 */
	public static boolean strIsInArray(String str, String[] strArray, boolean isCaseSensitive){
		if (str == null || strArray == null || strArray.length <= 0){
			return false;
		}

		str = isCaseSensitive ? str : str.toLowerCase();

		for (String temp : strArray){
			if (str.indexOf(temp) != -1){
				return true;
			}
		}

		return false;
	}
	/**
	 * 判断字符str是否包含在strArray字符组中，不区分大小写
	 * @author Tod.xie
	 * 2010-11-1 下午04:03:30
	 * StringUtil.java
	 * @param str
	 * @param strArray
	 * @return
	 * TODO : 
	 */
	public static boolean strIsInArray(String str, String[] strArray){
		return strIsInArray(str, strArray, false);
	}

	/**
	 * 把字符窜数组转换成字符串
	 * @author Tod.xie
	 * 2010-11-1 下午04:04:29
	 * StringUtil.java
	 * @param strArray
	 * @param sep
	 * @return
	 * TODO : 
	 */
	public static String strArr2Str(String[] strArray, String sep){
		if (strArray == null || strArray.length <= 0){
			return null;
		}
		StringBuffer result = new StringBuffer(strArray[0]);
		int length = strArray.length;
		for (int i = 1; i < length; i++){
			result.append(sep).append(strArray[i]);
		}
		return result.toString();
	}

	/**
	 * 把y、yes、true、1等转换成True
	 *
	 * @author Tod.xie
	 * 2010-11-1 下午04:05:19
	 * StringUtil.java
	 * @param theString
	 * @return
	 * TODO : 
	 *
	 */
	public static boolean toBoolean(String theString) {
		if (theString == null) {
			return false;
		}

		theString = theString.trim();

		if (theString.equalsIgnoreCase("y")
				|| theString.equalsIgnoreCase("yes")
				|| theString.equalsIgnoreCase("true")
				|| theString.equalsIgnoreCase("1")) {
			return true;
		}

		return false;
	}

	/**
	 * XML中的转义字符
	 *
	 * @author Tod.xie
	 * 2010-11-1 下午04:11:02
	 * StringUtil.java
	 * @param ch
	 * @return
	 * TODO : 
	 *
	 */
	protected static String getEntityRef(int ch) {
		// Encode special XML characters into the equivalent character
		// references.
		// These five are defined by default for all XML documents.
		switch (ch) {
		case '<':
			return "lt";

		case '>':
			return "gt";

		case '"':
			return "quot";

		case '\'':
			return "apos";

		case '&':
			return "amp";
		}
		return null;
	}

	/**
	 * Compare 2 decimals string 
	 * 
	 * @param decim1
	 *            First string to compare
	 * @param decim2
	 *            Second string to compare
	 * @return int return 1 if decim1 > decim2<BR>
	 *         retourne 0 if decim1 == decim2<BR>
	 *         return -1 if decim1 < decim2
	 * @throws ParseException
	 *             if wrong parameters.
	 */
	public static int compareDecimals(String decim1, String decim2)
	throws ParseException {
		BigDecimal dec1 = new BigDecimal(decim1);
		BigDecimal dec2 = new BigDecimal(decim2);
		return dec1.compareTo(dec2);
	}

	/**
	 * Compare 2 integers string. 
	 * 
	 * @param int1
	 *            first string to compare
	 * @param int2
	 *            second string to compare
	 * @return int return 1 if decim1 > decim2<BR>
	 *         return 0 if decim1 == decim2<BR>
	 *         return -1 if decim1 < decim2
	 * @throws ParseException
	 *             if wrong parameters.
	 */
	public static int compareIntegers(String int1, String int2)
	throws ParseException {
		BigInteger dec1 = new BigInteger(int1);
		BigInteger dec2 = new BigInteger(int2);
		return dec1.compareTo(dec2);
	}

	/**
	 * Check if string is alphanumeric or not. 
	 * 
	 * @param s
	 *            String to check.
	 * @return boolean true if alphanumeric, false if not.
	 */
	public static boolean isAlphaNumeric(String s) {
		return isAlphaNumeric(s, "");
	}

	/**
	 * Check if string is alphanumeric with addons chararcters or not. 
	 * 
	 * @param str
	 *            string to check
	 * @param otherChars
	 *            extra characters to check with
	 * @return boolean true if parameter string contains only alpha numerics,<BR>
	 *         plus addons characters and false if not.
	 */
	public static boolean isAlphaNumeric(String str, String otherChars) {
		String alphaNum = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ otherChars;
		for (int i = 0; i < str.length(); i++) {
			if (alphaNum.indexOf(str.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check if decimal number <p/> <p/> $Date: 2008/11/20 10:08:02 $
	 * 
	 * @param s
	 *            string to check.
	 * @return boolean true if the value is decimal number false if not
	 */
	public static boolean isDecimal(String s) {
		try {
			new BigDecimal(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * Check if integer string <p/> $Date: 2008/11/20 10:08:02 $
	 * 
	 * @param str
	 *            string to check
	 * @return boolean true if all characters is digit
	 */
	public static boolean isInteger(String str) {
		try {
			new BigInteger(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check if string pass is a vlaid email address 
	 * 
	 * @param email
	 *            string to check
	 * @return int 0 if valid address, 1 more than 2 tokens (".", "@")<BR>
	 *         and 2 if the second token is not "." .
	 */
	public static int isEmail(String email) {
		StringTokenizer st = new StringTokenizer(email, "@");

		if (st.countTokens() != 2) {
			return 1;
		}

		st.nextToken();
		if (st.nextToken().indexOf(".") == -1) {
			return 2;
		}

		return 0;
	}

	/**
	 * Check if number
	 * 
	 * @param str
	 *            to check
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	public static boolean isNumber(String str) {
		double number=0;
		try {
			number = Double.parseDouble(str);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * 把字符串转换成Double类型
	 *
	 * @author Tod.xie
	 * 2010-11-1 下午04:21:03
	 * StringUtil.java
	 * @param str
	 * @return
	 * TODO : 
	 *
	 */
	public static double toDouble(String str) {
		double lpResult = 0;

		try {
			lpResult = Double.parseDouble(null2Zero(str));
		} catch (NumberFormatException nfe) {
		}
		return lpResult;
	}

	/**
	 * Reformat string by converting "," by "." 
	 * 
	 * @param str
	 *            string to reformat
	 * @return String return reformatted string.
	 */
	public static String reformatDecimalString(String str) {
		// replace all ',' by '.'
		str = str.replace(',', '.');
		try {
			Double.parseDouble(str);
			return str;
		} catch (NumberFormatException nbe) {
			return null;
		}
	}

	/**
	 * Replace all dots by comma 
	 * 
	 * @param str
	 *            string to change
	 * @return String reformatted
	 */
	public static String convertDotToComma(String str) {
		return str.replace('.', ',');
	}
	/**
	 * Replace all comma by dots
	 * 
	 * @param str
	 *            string to change
	 * @return String reformatted
	 */
	public static String convertCommaToDot(String str) {
		return str.replace(',', '.');
	}

	/**
	 * Replace all occurences of key by value in text. 
	 * 
	 * @param text
	 *            string in
	 * @param key
	 *            occurence to replace
	 * @param value
	 *            string to use
	 * @return String with the replace value
	 */
	public static String replaceAll(String text, String key, String value) {
		String buffer = text;
		if (buffer != null && key != null && value != null) {
			int length = key.length();
			for (int start = buffer.indexOf(key); start != -1; start = buffer
			.indexOf(key, start + value.length())) {
				buffer = buffer.substring(0, start) + value
				+ buffer.substring(start + length);
			}
		}
		return buffer;
	}

	/**
	 * Substituate once str1 by str2 in text Commentaire Anglais 
	 * 
	 * @param text
	 *            search and replace in
	 * @param str1
	 *            to search for
	 * @param str2
	 *            to replace with
	 * @return String replaced
	 */
	static public String replaceStringOnce(String text, String str1, String str2) {
		return replaceString(text, str1, str2, 1);
	}

	/**
	 * Substituate all occurence of str1 by str2 in text 
	 * 
	 * @param text
	 *            search and replace in
	 * @param str1
	 *            search for
	 * @param str2
	 *            replace with
	 * @return String with all values replaced
	 */
	static public String replaceString(String text, String str1, String str2) {
		return replaceString(text, str1, str2, -1);
	}

	/**
	 * Replace n occurences of str1 in text by str2. if n = -1 all occurrences
	 * are replaced 
	 * 
	 * @param text
	 *            search and replace in
	 * @param str1
	 *            search for
	 * @param str2
	 *            replace with
	 * @param max
	 *            int values of occrrences to replace
	 * @return String replaced
	 */
	static public String replaceString(String text, String str1, String str2, int max) {
		if (text == null) {
			return null;
		}

		StringBuffer buffer = new StringBuffer(text.length());
		int start = 0;
		int end = 0;
		while ((end = text.indexOf(str1, start)) != -1) {
			buffer.append(text.substring(start, end)).append(str2);
			start = end + str1.length();
			if (--max == 0) {
				break;
			}
		}
		buffer.append(text.substring(start));
		return buffer.toString();
	}

	/**
	 * Convert string to list <p/> $Date: 2008/11/20 10:08:02 $
	 * 
	 * @param s
	 *            String comma delimited string to format
	 * @return List
	 */
	public static List<String> string2List(String s) {
		return string2List(s, ",");
	}

	/**
	 * Convert string to list using sep separator to divide $Date: 2008/06/05
	 * 01:27:05 $
	 * 
	 * @param s
	 *            String comma delimited string to format
	 * @param sep
	 *            a string containing the seprator characters
	 * @return List
	 */
	public static List<String> string2List(String s, String sep) {
		return string2List(s, sep, s != null ? s.length() : Integer.MAX_VALUE);
	}

	/**
	 * Convert string to list using sep separator to divide
	 * 
	 * @param s
	 *            String comma delimited string to format
	 * @param sep
	 *            a string containing the seprator characters
	 * @param maxSize
	 *            the maximum size of the list
	 * @return List
	 */
	public static List<String> string2List(String s, String sep, int maxSize) {
		List<String> l = null;
		if (s != null) {
			l = new Vector<String>();
			for (int i = 0; i < maxSize;) {
				int index = s.indexOf(sep, i);
				String token;
				if (index != -1) {
					token = s.substring(i, index);
				} else {
					token = s.substring(i);
				}
				if (token.length() > 0 && !token.equals(sep)) {
					l.add(token.trim());
				}
				i += token.length() + sep.length();
			}
		}
		return l;
	}

	/**
	 * convert the first character of string in lower case 
	 * 
	 * @param str
	 *            String to un-upper case the first character
	 * @return String
	 */
	static public String unUpperFirstChar(String str) {
		StringBuffer fsb = new StringBuffer();
		try {
			fsb.append(Character.toLowerCase(str.charAt(0)));
			fsb.append(str.substring(1));
			return fsb.toString();
		} finally {
		}
	}

	/**
	 * convert the first character of the string upper case 
	 * 
	 * @param str
	 *            String to make the first character upper.
	 * @return String
	 */
	static public String upperFirstChar(String str) {

		StringBuffer fsb = new StringBuffer();
		try {
			fsb.append(Character.toTitleCase(str.charAt(0)));
			fsb.append(str.substring(1));
			return fsb.toString();
		} finally {
		}
	}

	/**
	 * Repeat str n time to format another string. 
	 * 
	 * @param str
	 *            String to repeat
	 * @param n
	 *            int n repeat
	 * @return String
	 */
	static public String repeatString(String str, int n) {
		StringBuffer buffer = new StringBuffer();
		try {
			// StringBuffer is preallocated for 1K, so we may not
			// need to do any memory allocation here.
			int val = n * str.length();
			if (val > buffer.capacity()) {
				buffer.ensureCapacity(val);
			}
			for (int i = 0; i < n; i++) {
				buffer.append(str);
			}
			return buffer.toString();
		} finally {
		}
	}

	/**
	 * Enclosed the string with padding character. Space is padding character
	 * 
	 * @param str
	 *            String str string to center padding
	 * @param n
	 *            int n size of the new string
	 * @return String Result
	 */
	static public String centerPad(String str, int n) {
		return centerPad(str, n, " ");
	}

	/**
	 * Enclosed the string with padding character 
	 * 
	 * @param str
	 *            String str string to pad with
	 * @param n
	 *            int n size of the final string
	 * @param delim
	 *            String delim padding character
	 * @return String result of the center padding
	 */
	static public String centerPad(String str, int n, String delim) {
		int sz = str.length();
		int p = n - sz;
		if (p < 1) {
			return str;
		}
		str = leftPad(str, sz + p / 2, delim);
		str = rightPad(str, n, delim);
		return str;
	}

	/**
	 * Right padding with delimiter 
	 * 
	 * @param str
	 *            String
	 * @param n
	 *            int size of the final string
	 * @param delim
	 *            padding character
	 * @return String padding string
	 */
	static public String rightPad(String str, int n, String delim) {
		int sz = str.length();
		n = (n - sz) / delim.length();
		if (n > 0) {
			str += repeatString(delim, n);
		}
		return str;
	}

	/**
	 * Right padding delimiter is space 
	 * 
	 * @param str
	 *            String
	 * @param n
	 *            int size of the new string
	 * @return String
	 */
	static public String rightPad(String str, int n) {
		return rightPad(str, n, " ");
	}

	/**
	 * Left padding padding character is space 
	 * 
	 * @param str
	 *            String
	 * @param n
	 *            int size of the new string
	 * @return String
	 */
	static public String leftPad(String str, int n) {
		return leftPad(str, n, " ");
	}

	/**
	 * Left padding 
	 * 
	 * @param str
	 *            String
	 * @param n
	 *            int size of the new string
	 * @param delim
	 *            padding character
	 * @return String result
	 */
	static public String leftPad(String str, int n, String delim) {
		int sz = str.length();
		n = (n - sz) / delim.length();
		if (n > 0) {
			str = repeatString(delim, n) + str;
		}
		return str;
	}

	/**
	 * Reverse the String.
	 * 
	 * @param str
	 *            the String to reverse.
	 * @return a reversed string
	 */
	static public String reverseString(String str) {
		StringBuffer fsb = new StringBuffer();
		try {
			fsb.append(str);
			return fsb.reverse().toString();
		} finally {

		}
	}

	/**
	 * Reverse the character case in the string 
	 * 
	 * @param str
	 *            String
	 * @return String
	 */
	static public String swapCase(String str) {
		int sz = str.length();
		StringBuffer buffer = new StringBuffer();
		try {
			if (sz > buffer.capacity()) {
				buffer.ensureCapacity(sz);
			}
			boolean whiteSpace = false;
			char ch = 0;
			char tmp = 0;
			for (int i = 0; i < sz; i++) {
				ch = str.charAt(i);
				if (Character.isUpperCase(ch)) {
					tmp = Character.toLowerCase(ch);
				} else if (Character.isTitleCase(ch)) {
					tmp = Character.toLowerCase(ch);
				} else if (Character.isLowerCase(ch)) {
					if (whiteSpace) {
						tmp = Character.toTitleCase(ch);
					} else {
						tmp = Character.toUpperCase(ch);
					}
				}
				buffer.append(tmp);
				whiteSpace = Character.isWhitespace(ch);
			}
			return buffer.toString();
		} finally {
		}
	}

	/**
	 * Create a random string 
	 * 
	 * @param count
	 *            size of string.
	 * @return randomly generated string of size count
	 */
	static public String random(int count) {
		return random(count, false, false);
	}

	/**
	 * Create a random Ascii String 
	 * 
	 * @param count
	 *            the size of the string
	 * @return randomly generated string of size count
	 */
	static public String randomAscii(int count) {
		return random(count, 32, 127, false, false);
	}

	/**
	 * Create a random character only string 
	 * 
	 * @param count
	 *            size of string
	 * @return randomly generated string of size count
	 */
	static public String randomAlphabetic(int count) {
		return random(count, true, false);
	}

	/**
	 * Create a random alpha numeric string 
	 * 
	 * @param count
	 *            the size of the string
	 * @return randomly generated string of size count
	 */
	static public String randomAlphanumeric(int count) {
		return random(count, true, true);
	}

	/**
	 * Create a random numeric string 
	 * 
	 * @param count
	 *            the size of the final string
	 * @return randomly generated string of size count
	 */
	static public String randomNumeric(int count) {
		return random(count, false, true);
	}

	/**
	 * Create a random numeric string where you have control over size, and
	 * whether you want letters, numbers, or both. 
	 * 
	 * @param count
	 *            the size of the string
	 * @param letters
	 *            true if you want letters included
	 * @param numbers
	 *            true if you want numbers included
	 * @return randomly generated string of size count
	 */
	static public String random(int count, boolean letters, boolean numbers) {
		return random(count, 0, 0, letters, numbers);
	}

	/**
	 * Create a random numeric string where you have control over size, and
	 * whether you want letters, numbers, as well as ANSI minimum and maximum
	 * values of the characters. 
	 * 
	 * @param count
	 *            the size of the string
	 * @param start
	 *            int minimum 'value' of the character
	 * @param end
	 *            maximum 'value' of the character
	 * @param letters
	 *            true if you want letters included
	 * @param numbers
	 *            true if you want numbers included
	 * @return randomly generated string of size count
	 */
	static public String random(int count, int start, int end, boolean letters,
			boolean numbers) {
		return random(count, start, end, letters, numbers, null);
	}

	/**
	 * Create a random numeric string where you have control over size, and
	 * whether you want letters, numbers, as well as ANSI minimum and maximum
	 * values of the characters. 
	 * 
	 * @param count
	 *            the size of the string
	 * @param start
	 *            int minimum 'value' of the character
	 * @param end
	 *            maximum 'value' of the character
	 * @param letters
	 *            true if you want letters included
	 * @param numbers
	 *            true if you want numbers included
	 * @param set
	 *            the set of possible characters that you're willing to let the
	 *            string contain. may be null if all values are open.
	 * @return randomly generated string of size count
	 */
	static public String random(int count, int start, int end, boolean letters,
			boolean numbers, char[] set) {
		if ((start == 0) && (end == 0)) {
			end = (int) 'z';
			start = (int) ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}
		Random rnd = new Random();
		StringBuffer buffer = new StringBuffer();
		try {
			int gap = end - start;
			while (count-- != 0) {
				char ch;
				if (set == null) {
					ch = (char) (rnd.nextInt(gap) + start);
				} else {
					ch = set[rnd.nextInt(gap) + start];
				}
				if ((letters && numbers && Character.isLetterOrDigit(ch))
						|| (letters && Character.isLetter(ch))
						|| (numbers && Character.isDigit(ch))
						|| (!letters && !numbers)) {
					buffer.append(ch);
				} else {
					count++;
				}
			}
			return buffer.toString();
		} finally {
		}
	}

	/**
	 * Create a random string 
	 * 
	 * @param count
	 *            the size of the string
	 * @param set
	 *            the set of characters that are allowed
	 * @return randomly generated string of size count
	 */
	static public String random(int count, String set) {
		return random(count, set.toCharArray());
	}

	/**
	 * Create a random string 
	 * 
	 * @param count
	 *            the size of the string
	 * @param set
	 *            the set of characters that are allowed
	 * @return randomly generated string of size count
	 */
	static public String random(int count, char[] set) {
		return random(count, 0, set.length - 1, false, false, set);
	}

	/**
	 * return empty string the string is null 
	 * 
	 * @param str
	 *            The string to split String
	 * @param lg
	 *            the length to subgstring
	 * @return a substring of parameter str.
	 */
	public static String substring(String str, int lg) {
		return substring(str, 0, lg);
	}

	/**
	 * return empty string the string is null 
	 * 
	 * @param str
	 *            The string to split String
	 * @param start
	 *            the location to start
	 * @param end
	 *            the end location of the substring
	 * @return a substring of parameter str.
	 */
	public static String substring(String str, int start, int end) {
		if (str == null || str.length() <= start) {
			return null;
		} else if (str.length() >= end) {
			return str.substring(start, end);
		} else {
			return str.substring(start);
		}
	}

	/**
	 * 把List转换成数组
	 *
	 * @author Tod.xie
	 * 2010-11-1 下午06:17:29
	 * StringUtil.java
	 * @param element
	 * @return
	 * TODO : 
	 *
	 */
	public static String[] listToStrArray(List<String> element) {

		if (element == null || element.size() == 0)
			return null;

		Iterator<String> it = element.iterator();
		String[] strArray = new String[element.size()];
		int i = 0;

		while (it.hasNext()) {
			strArray[i] = String.valueOf(it.next());
			i++;
		}
		return strArray;
	}

	/**
	 * 字符串左边或右边不足为数补value
	 *
	 * @author Tod.xie
	 * 2011-3-22 下午05:24:43
	 * StringUtil.java
	 * @param str
	 * @param strLength
	 * @param value  要填充的值
	 * @param left  true表示补左边，否则补右边
	 * @return
	 * TODO : 
	 *
	 */
	public static String addZeroForNum(String str, int strLength, String value, boolean left) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				if (left){
					sb.append(value).append(str);//左补0
				}
				else {
					sb.append(str).append(value);//右补0
				}
				str = sb.toString();
				strLen = str.length();
			}
		}

		return str;
	}
	
	public static String Html2Text(String inputString) {
		   //过滤html标签
		   String htmlStr = inputString; // 含html标签的字符串
		   String textStr = "";
		   Pattern p_script;
		   Matcher m_script;
		   Pattern p_style;
		   Matcher m_style;
		   Pattern p_html;
		   Matcher m_html;
		   Pattern p_cont1;
		   Matcher m_cont1;
		   Pattern p_cont2;
		   Matcher m_cont2;
		   try {
		    String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
		    // }
		    String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
		    // }
		    String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		    String regEx_cont1 = "[\\d+\\s*`~!@#$%^&*\\(\\)\\+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘：”“’_]"; // 定义HTML标签的正则表达式
		    String regEx_cont2 = "[\\w[^\\W]*]"; // 定义HTML标签的正则表达式[a-zA-Z]
		    p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		    m_script = p_script.matcher(htmlStr);
		    htmlStr = m_script.replaceAll(""); // 过滤script标签
		    p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		    m_style = p_style.matcher(htmlStr);
		    htmlStr = m_style.replaceAll(""); // 过滤style标签
		    p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		    m_html = p_html.matcher(htmlStr);
		    htmlStr = m_html.replaceAll(""); // 过滤html标签
		    p_cont1 = Pattern.compile(regEx_cont1, Pattern.CASE_INSENSITIVE);
		    m_cont1 = p_cont1.matcher(htmlStr);
		    htmlStr = m_cont1.replaceAll(""); // 过滤其它标签
		    p_cont2 = Pattern.compile(regEx_cont2, Pattern.CASE_INSENSITIVE);
		    m_cont2 = p_cont2.matcher(htmlStr);
		    htmlStr = m_cont2.replaceAll(""); // 过滤html标签
		    textStr = htmlStr;
		   } catch (Exception e) {
		    System.err.println("Html2Text: " + e.getMessage());
		   }
		   return textStr;// 返回文本字符串
	}

	public static String delHTMLTag(String htmlStr){
		if(htmlStr==null||htmlStr.length()==0)
			return htmlStr;


        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式


        Pattern p_html= Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串 
    }
	
	public static String replaceHexToInteger(String str){
		String regEx = "[A-Za-z0-9]+";
		Pattern pat = Pattern.compile(regEx);  
		Matcher mat = pat.matcher(str);  
		String hex=null;
		while (mat.find()) {
			 hex=mat.group(0);
	    }
		if(hex!=null)
			return	str.replaceAll(hex, Integer.parseInt(hex, 16)+"");
		else
			return str;
	}
	
	
	/**
	 * 功能说明：字符串是否为空 空的定义如下： <br/>
	 * 1、为null <br/>
	 * 2、为不可见字符（如空格）<br/>
	 * 3、""<br/>
	 * 4、字符串"null"<br/>
	 * @author ducc
	 * @created 2014年6月13日 上午11:24:12
	 * @updated 
	 * @param str 被检测的字符串
	 * @return 是否为空
	 */
	public static boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0) || ("null".equals(str));
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	/**
	 * 功能说明：获得一个随机的字符串
	 * @author ducc
	 * @created 2014年6月13日 上午11:28:28
	 * @updated 
	 * @param length 字符串的长度
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();

		if (length < 1) {
			length = 1;
		}
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	/**
	 * 功能说明：去掉首部指定长度的字符串并将剩余字符串首字母小写<br/>
	 *    例如：str=setName, preLength=3 -> return name
	 * @author ducc
	 * @created 2014年6月13日 上午11:31:40
	 * @updated 
	 * @param str 被处理的字符串 
	 * @param preLength 去掉的长度
	 * @return 处理后的字符串，不符合规范返回null
	 */
	public static String cutPreAndLowerFirst(String str, int preLength) {
		if(str == null) {
			return null;
		}
		if(str.length() > preLength) {
			char first = Character.toLowerCase(str.charAt(preLength));
			if(str.length() > preLength +1) {
				return first +  str.substring(preLength +1);
			}
			return String.valueOf(first);
		}
		return null;
	}
	
	/**
	 * 功能说明： 原字符串首字母大写并在其首部添加指定字符串
	 *   例如：str=name, preString=get -> return getName
	 * @author ducc
	 * @created 2014年6月13日 上午11:33:00
	 * @updated 
	 * @param str 被处理的字符串
	 * @param preString 添加的首部
	 * @return 处理后的字符串
	 */
	public static String upperFirstAndAddPre(String str, String preString) {
		if(str == null || preString == null) {
			return null;
		}
		return preString + Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	/**
	 * 功能说明：切分字符串<br/>
	 *    a#b#c -> [a,b,c]
	 *    a##b#c -> [a,"",b,c]
	 * @author ducc
	 * @created 2014年6月13日 上午11:39:26
	 * @updated 
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator) {
		return split(str, separator, 0);
	}
	
	/**
	 * 切分字符串
	 * @param str 被切分的字符串
	 * @param separator 分隔符字符
	 * @param limit 限制分片数
	 * @return 切分后的集合
	 */
	public static List<String> split(String str, char separator, int limit){
		if(str == null) {
			return null;
		}
		List<String> list = new ArrayList<String>(limit == 0 ? 16 : limit);
		if(limit == 1) {
			list.add(str);
			return list;
		}
		
		boolean isNotEnd = true;	//未结束切分的标志
		int strLen = str.length();
		StringBuilder sb = new StringBuilder(strLen);
		for(int i=0; i < strLen; i++) {
			char c = str.charAt(i);
			if(isNotEnd && c == separator) {
				list.add(sb.toString());
				//清空StringBuilder
				sb.delete(0, sb.length());
				
				//当达到切分上限-1的量时，将所剩字符全部作为最后一个串
				if(limit !=0 && list.size() == limit-1) {
					isNotEnd = false;
				}
			}else {
				sb.append(c);
			}
		}
		list.add(sb.toString());
		return list;
	}
	
	/**
	 * 功能说明：重复某个字符
	 * @author ducc
	 * @created 2014年6月13日 上午11:40:14
	 * @updated 
	 * @param c 被重复的字符
	 * @param count 重复的数目
	 * @return 重复字符字符串
	 */
	public static String repeat(char c, int count) {
		char[] result = new char[count];
		for (int i = 0; i < count; i++) {
			result[i] = c;
		}
		return new String(result);
	}
	
	/**
	 * 功能说明：给定字符串转换字符编码<br/>
	 *    如果参数为空，则返回原字符串，不报错。
	 * @author ducc
	 * @created 2014年6月13日 上午11:40:47
	 * @updated 
	 * @param str 被转码的字符串
	 * @param sourceCharset 原字符集
	 * @param destCharset 目标字符集
	 * @return 转换后的字符串
	 */
	public static String transCharset(String str, String sourceCharset, String destCharset) {
		if(StringUtil.isEmpty(str) || StringUtil.isEmpty(sourceCharset) || StringUtil.isEmpty(destCharset)) {
			return str;
		}
		try {
			return new String(str.getBytes(sourceCharset), destCharset);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	
	/**
	 * 截取一段字符的长度(汉、日、韩文字符长度为2),不区分中英文,如果数字不正好，则少取一个字符位
	 * 
	 * @param str
	 *            原始字符串
	 * @param specialCharsLength
	 *            截取长度(汉、日、韩文字符长度为2)
	 * @return
	 */
	public static String trim(String str, int specialCharsLength) {
		if (str == null || "".equals(str) || specialCharsLength < 1) {
			return "";
		}
		char[] chars = str.toCharArray();
		int charsLength = getCharsLength(chars, specialCharsLength);
		return new String(chars, 0, charsLength);
	}

	/**
	 * 功能说明:去掉字符串2端空格或空白。如果参数字符串为null，那么返回结果为空白字符串，即"";
	 * 
	 * @param s
	 *            需要过滤的字符串
	 * @return 创建日期: 2011-04-28 修改人： 修改日期: 修改内容:
	 * 
	 */
	public static String trim(String s) {
		return trim(s, false);
	}

	/**
	 * 功能说明： 去掉字符串2端空格或空白。如果参数字符串为null，那么返回结果为空白字符串，即""
	 * @author ducc
	 * @created 2014年6月12日 下午9:36:51
	 * @updated 
	 * @param s 需要过滤的字符串
	 * @param isTransferred  是否对html特殊字符转义
	 * @return
	 */
	public static String trim(String s, boolean isTransferred) {
		if (isTransferred) {
			return s == null ? "" : s.trim();
		} else {
			return s == null ? "" : s.trim();
		}
	}

	/**
	 * 功能说明：获取一段字符的长度(个数)
	 *     输入长度中汉、日、韩文字符长度为2，输出长度中所有字符均长度为1
	 * @author ducc
	 * @created 2014年6月12日 下午9:33:48
	 * @updated 
	 * @param chars  一段字符
	 * @param specialCharsLength 输入长度，汉、日、韩文字符长度为2
	 * @return 输出长度，所有字符均长度为1
	 */
	private static int getCharsLength(char[] chars, int specialCharsLength) {
		int count = 0;
		int normalCharsLength = 0;
		for (int i = 0; i < chars.length; i++) {
			int specialCharLength = getSpecialCharLength(chars[i]);
			if (count <= specialCharsLength - specialCharLength) {
				count += specialCharLength;
				normalCharsLength++;
			} else {
				break;
			}
		}
		return normalCharsLength;
	}

	/**
	 * 功能说明：获取字符长度
	 *   汉、日、韩文字符长度为2，ASCII码等字符长度为1
	 * @author ducc
	 * @created 2014年6月12日 下午9:32:46
	 * @updated 
	 * @param charStr 字符
	 * @return 字符长度
	 */
	private static int getSpecialCharLength(char charStr) {
		if (isLetter(charStr)) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * 功能说明：判断一个字符是Ascill字符
	 * @author ducc
	 * @created 2014年6月12日 下午9:30:55
	 * @updated 
	 * @param charStr 需要判断的字符
	 * @return 返回true=Ascill字符，否则是其他字符（如汉，日，韩文字符）
	 */
	private static boolean isLetter(char charStr) {
		int k = 0x80;
		return charStr / k == 0 ? true : false;
	}

	/**
	 * 功能说明：获取字符串长度  
	 *   一个汉字或日韩文长度为2,英文字符长度为1
	 * @author ducc
	 * @created 2014年6月12日 下午9:29:50
	 * @updated 
	 * @param str  需要得到长度的字符串
	 * @return 得到的字符串长度
	 */
	public static int length(String str) {
		if (str == null)
			return 0;
		char[] c = str.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	/**
	 * 功能说明：截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
	 *    比如截取一段字符串，截取长度为10，以...结尾
	 *    如果要截取的字符串长度超过了10，则返回   字符串...
	 *    如果长度没超过10，则返回原字符串
	 * @author ducc
	 * @created 2014年6月12日 下午9:24:42
	 * @updated 
	 * @param origin 原始字符串
	 * @param len 截取长度(一个汉字长度按2算的)
	 * @param suffix 要拼的后缀
	 * @return
	 */
	public static String substring(String origin, int len, String suffix) {

		String resultString = "";
		if (origin == null || origin.equals("") || len < 1) {
			return resultString;
		} else if (origin.length() <= len) {
			return origin;
		} else if (origin.length() > 2 * len) {
			origin = origin.substring(0, 2 * len);
		}

		if (origin.length() > len) {
			char[] chr = origin.toCharArray();
			int strNum = 0;
			int strGBKNum = 0;
			boolean isHaveDot = false;

			for (int i = 0; i < origin.length(); i++) {
				if (chr[i] >= 0xa1) // 0xa1汉字最小位开始
				{
					strNum = strNum + 2;
					strGBKNum++;
				} else {
					strNum++;
				}

				if (strNum == 2 * len || strNum == 2 * len + 1) {
					if (i + 1 < origin.length()) {
						isHaveDot = true;
					}
					break;
				}
			}
			resultString = origin.substring(0, strNum - strGBKNum);
			if (!isHaveDot) {
			}
			resultString = resultString + suffix;
		}

		return resultString;

	}

	

	public static String replaceEx(String str, String subStr, String reStr) {
		if (str == null) {
			return null;
		}
		if ((subStr == null) || (subStr.equals(""))
				|| (subStr.length() > str.length()) || (reStr == null)) {
			return str;
		}
		StringBuilder sb = new StringBuilder();
		int lastIndex = 0;
		while (true) {
			int index = str.indexOf(subStr, lastIndex);
			if (index < 0) {
				break;
			}
			sb.append(str.substring(lastIndex, index));
			sb.append(reStr);

			lastIndex = index + subStr.length();
		}
		sb.append(str.substring(lastIndex));
		return sb.toString();
	}

	public static String javaEncode(String txt) {
		if ((txt == null) || (txt.length() == 0)) {
			return txt;
		}
		txt = replaceEx(txt, "\\", "\\\\");
		txt = replaceEx(txt, "\r\n", "\n");
		txt = replaceEx(txt, "\r", "\\r");
		txt = replaceEx(txt, "\t", "\\t");
		txt = replaceEx(txt, "\n", "\\n");
		txt = replaceEx(txt, "\"", "\\\"");
		txt = replaceEx(txt, "'", "\\'");
		return txt;
	}
	/**
	 * 功能说明： 检验字符串序号是否正确
	 *    例如：num=0001  len=4  返回true
	 *    代表检测长度为4的，以0*开头的字符串
	 *    num=0001  len=5  
	 * @author ducc
	 * @created 2014年8月9日 上午7:09:37
	 * @updated 
	 * @param num
	 * @param len
	 * @return
	 */
	public static  boolean checkNum(String num,Integer len){
		String regex = "%0"+len+"d";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(num);
		return m.matches();
	}
	/**
	 * 功能说明：将对应数字补齐对应长度的0
	 *    比如 num=32  len=4
	 *    return '0032'
	 * @author ducc
	 * @created 2014年8月9日 上午7:12:26
	 * @updated 
	 * @param num
	 * @return
	 */
	public static  String getStringNum(Integer num,Integer len){
		/**  %04d
		 *    0 代表前面补充0    
		 *    4代表长度为4    
		 *    d 代表参数为正数型   
		 */
		return String.format("%0"+len+"d", num);
	}
	public static String leftPad(String srcString, char c, int length) {
		if (srcString == null) {
			srcString = "";
		}
		int tLen = srcString.length();

		if (tLen >= length)
			return srcString;
		int iMax = length - tLen;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < iMax; i++) {
			sb.append(c);
		}
		sb.append(srcString);
		return sb.toString();
	}
	
	/**
     * 如果此字符串为 null 或者为空串（""），则返回 true
     * 
     * @param cs
     *            字符串
     * @return 如果此字符串为 null 或者为空，则返回 true
     */
    public static boolean isEmpty(CharSequence cs) {
        return null == cs || cs.length() == 0;
    }

    /**
     * 如果此字符串为 null 或者全为空白字符，则返回 true
     * 
     * @param cs
     *            字符串
     * @return 如果此字符串为 null 或者全为空白字符，则返回 true
     */
    public static boolean isBlank(CharSequence cs) {
        if (null == cs)
            return true;
        int length = cs.length();
        for (int i = 0; i < length; i++) {
            if (!(Character.isWhitespace(cs.charAt(i))))
                return false;
        }
        return true;
    }

    /**
     * 去掉字符串前后空白字符。空白字符的定义由Character.isWhitespace来判断
     * 
     * @param cs
     *            字符串
     * @return 去掉了前后空白字符的新字符串
     */
    public static String trim(CharSequence cs) {
        if (null == cs)
            return null;
        int length = cs.length();
        if (length == 0)
            return cs.toString();
        int l = 0;
        int last = length - 1;
        int r = last;
        for (; l < length; l++) {
            if (!Character.isWhitespace(cs.charAt(l)))
                break;
        }
        for (; r > l; r--) {
            if (!Character.isWhitespace(cs.charAt(r)))
                break;
        }
        if (l > r)
            return "";
        else if (l == 0 && r == last)
            return cs.toString();
        return cs.subSequence(l, r + 1).toString();
    }


	/**
	 * @方法名 killNull 
	 * @描述 格式化字符串 如果为null，返回“”
	 * @param str
	 * @return String
	 * @时间 2014年4月7日 下午9:46:09
	 */
	public static String killNull(String str) {
		if (isEmpty(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * @方法名 split 
	 * @描述 根据分隔符分割字符串
	 * @param str 原始字符串
	 * @param splitSign 分隔符
	 * @return String[] 返回分割后的字符串数组
	 * @时间 2014年4月7日 下午9:47:13
	 */
	public static String[] split(String str, String splitSign) {
		int index;
		if (str == null || splitSign == null) {
			return null;
		}
		ArrayList<String> al = new ArrayList<String>();
		while ((index = str.indexOf(splitSign)) != -1) {
			al.add(str.substring(0, index));
			str = str.substring(index + splitSign.length());
		}
		al.add(str);
		return (String[]) al.toArray(new String[0]);
	}

	/**
	 * @方法名 replace 
	 * @描述 替换字符串
	 * @param from 原始字符串 即source中需要被替换的字符串
	 * @param to 目标字符串 即替换后的字符串
	 * @param source 母字符串 即源字符串
	 * @return String 替换后的字符串
	 * @时间 2014年4月7日 下午9:49:06
	 */
	public static String replace(String from, String to, String source) {
		if (source == null || from == null || to == null)
			return null;
		StringBuffer str = new StringBuffer("");
		int index = -1;
		while ((index = source.indexOf(from)) != -1) {
			str.append(source.substring(0, index) + to);
			source = source.substring(index + from.length());
		}
		str.append(source);
		return str.toString();
	}
	
	/**
	 * @方法名 htmlEncode 
	 * @描述 替换字符串，替换成能够在HTML页面上直接显示(替换双引号和小于号)
	 * @param str
	 * @return String
	 * @时间 2014年4月7日 下午9:56:50
	 */
	public static String htmlEncode(String str) {
		if (str == null) {
			return null;
		}
		return replace("\"", "&quot;", replace("<", "&lt;", str));
	}

	/**
	 * @方法名 htmlDecode 
	 * @描述 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
	 * @param str
	 * @return String
	 * @时间 2014年4月7日 下午9:57:10
	 */
	public static String htmlDecode(String str) {
		if (str == null) {
			return null;
		}

		return replace("&quot;", "\"", replace("&lt;", "<", str));
	}

	private static final String _BR = "<br/>";

	/**
	 * @方法名 htmlShow 
	 * @描述 功能描述：在页面上直接显示文本内容，替换小于号，空格，回车，TAB
	 * @param str
	 * @return String
	 * @时间 2014年4月7日 下午9:57:48
	 */
	public static String htmlShow(String str) {
		if (str == null) {
			return null;
		}

		str = replace("<", "&lt;", str);
		str = replace(" ", "&nbsp;", str);
		str = replace("\r\n", _BR, str);
		str = replace("\n", _BR, str);
		str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
		return str;
	}

	/**
	 * @方法名 isLetter 
	 * @描述 判断是不是合法字符 
	 * @param str
	 * @return boolean
 
	 * @时间 2014年4月7日 下午10:00:27
	 */
	public static boolean isLetter(String str) {
		if (str == null || str.length() < 0) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w\\.-_]*");
		return pattern.matcher(str).matches();
	}
	
	/**
	 * @方法名 hangeToBig 
	 * @描述 人民币转成大写 精确到分
	 * @param str 数字字符串
	 * 人民币转换成大写后的字符串 
	 * @return String
 
	 * @时间 2014年4月7日 下午10:03:47
	 */
	public static String hangeToBig(String str) {
		double value;
		try {
			value = Double.parseDouble(str.trim());
		} catch (Exception e) {
			return null;
		}
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}

	/**
	 * @方法名 removeSameString 
	 * @描述 去掉字符串中重复的子字符串
	 * @param str
	 * 				原字符串，如果有子字符串则用空格隔开以表示子字符串
	 * @return String
 
	 * @时间 2014年4月7日 下午10:07:49
	 */
	@SuppressWarnings("unused")
	private static String removeSameString(String str) {
		Set<String> mLinkedSet = new LinkedHashSet<String>();// set集合的特征：其子集不可以重复
		String[] strArray = str.split(" ");// 根据空格(正则表达式)分割字符串
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i] + " ");
			}
		}
		System.out.println(mLinkedSet);
		return sb.toString();
	}

	/**
	 * @方法名 encoding 
	 * @描述 过滤特殊字符
	 * @param src
	 * @return String
 
	 * @时间 2014年4月7日 下午10:08:46
	 */
	public static String encoding(String src) {
		if (src == null)
			return "";
		StringBuilder result = new StringBuilder();
		src = src.trim();
		for (int pos = 0; pos < src.length(); pos++) {
			switch (src.charAt(pos)) {
			case '\"':
				result.append("&quot;");
				break;
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '\'':
				result.append("&apos;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '%':
				result.append("&pc;");
				break;
			case '_':
				result.append("&ul;");
				break;
			case '#':
				result.append("&shap;");
				break;
			case '?':
				result.append("&ques;");
				break;
			default:
				result.append(src.charAt(pos));
				break;
			}
		}
		return result.toString();
	}

	/**
	 * @方法名 decoding 
	 * @描述 反过滤特殊字符
	 * @param src
	 * @return String
 
	 * @时间 2014年4月7日 下午10:09:58
	 */
	public static String decoding(String src) {
		if (src == null)
			return "";
		String result = src;
		result = result.replace("&quot;", "\"").replace("&apos;", "\'");
		result = result.replace("&lt;", "<").replace("&gt;", ">");
		result = result.replace("&amp;", "&");
		result = result.replace("&pc;", "%").replace("&ul", "_");
		result = result.replace("&shap;", "#").replace("&ques", "?");
		return result;
	}

	/**
	 * 可以用于判断 Map,Collection,String,Array,Long是否为空
	 * @param o java.lang.Object.
	 * @return boolean.
	 */
	@SuppressWarnings("unused")
	public static boolean isEmpty(Object o)
	{
		if (o == null) return true;
		if (o instanceof String){
			if (((String) o).trim().length() == 0){
				return true;
			}
		}
		else if (o instanceof Collection){
			if (((Collection) o).isEmpty()){
				return true;
			}
		}
		else if (o.getClass().isArray()){
			if (((Object[]) o).length == 0){
				return true;
			}
		}
		else if (o instanceof Map){
			if (((Map) o).isEmpty()){
				return true;
			}
		}
		else if (o instanceof Long){
			Long lEmpty=0L;
			if(o==null || lEmpty.equals(o)){
				return true;
			}
		}
		else if (o instanceof Short){
			Short sEmpty=0;
			if(o==null || sEmpty.equals(o)){
				return true;
			}
		}
		else if (o instanceof Integer){
			Integer sEmpty=0;
			if(o==null || sEmpty.equals(o)){
				return true;
			}
		}

		return false;

	}

	/**
	 * 可以用于判断 Map,Collection,String,Array是否不为空
	 * @param o
	 * @return
	 */
	public static boolean isNotEmpty(Object o)
	{
		return !isEmpty(o);
	}
}
