package com.jsoft.oa.core.pinyin;

import java.util.Arrays;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 汉字转拼音工具类
 * @author jack
 * @email wujunwei6@163.com
 * @date 2016年9月7日 下午8:22:19 
 * @version V1.0
 */
public final class PinyinTools {
	/**
	 * 汉字转为拼音
	 * @param str 汉字字符串
	 */
	public static String toPinyin(String str)
	{
		String res="";
		for(int i = 0; i < str.length(); i++)
		{
			char temp = str.charAt(i);
			/** 多音字返回的是数组,"谁" >> [shei2, shui2] */
			String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(temp);
			System.out.println(Arrays.toString(pinyinArr));
			/** 取第一个读音 */
			res += pinyinArr[0];
		}
		return res.replaceAll("\\d+", "");
	}
	public static void main(String[] args) {
		System.out.println(toPinyin("中国"));
		System.out.println(toPinyin("谁"));
		/** 汉字字符的十六进制表现形式（即unicode） 中 >> 4e2d */
		System.out.println(Integer.toHexString('中'));
		/** unicode */
		char ch = '\u4e2d';
		System.out.println("\u4e2d" + ">>>" + ch);
		
	}
}
