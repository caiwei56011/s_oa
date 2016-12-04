package com.jsoft.oa.core.common.security;

import java.security.MessageDigest;
import java.util.Arrays;
/**
 * 用MD5算法将明文加密为32位16进制密文的工具类
 * @author Administrator
 * @date 2016年8月19日 下午7:46:25 
 * @version V1.0
 */
public final class MD5_32 {
	/* 测试
	public static void main(String[] args) {
        System.out.println(MD5_32.encryption("123456"));
    }
	 */
	
    /**
     * 用MD5算法将明文加密为32位16进制密文
     * @param plainText 明文
     * @return 32位16进制密文
     */
    public static String encryption(String plainText) {
        String re_md5 = new String();
        try {
        	/** 获取MD5加密对象 */
            MessageDigest md = MessageDigest.getInstance("MD5");
            /** 调用加密方法 */
            md.update(plainText.getBytes("utf-8"));
            /** 获取加密后的字节数组(16位) */
            byte md5Bytes[] = md.digest();
            
            System.out.println("==加密前==" + Arrays.toString(plainText.getBytes()));
    		System.out.println("==加密后==" + Arrays.toString(md5Bytes));
            
    		/** 把加密后的16位字位的字节数组转化成32字符串, 把其中一位转化成16进制的两位 
    		 * 如果转化成16进制不够两位前面补零
    		 * */
            int i;
            StringBuffer sb = new StringBuffer("");
            for (int offset = 0; offset < md5Bytes.length; offset++) {
                i = md5Bytes[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                	sb.append("0");
                sb.append(Integer.toHexString(i));
            }
            re_md5 = sb.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re_md5;
    }
}

