package com.pipaw.func;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	public static String getMd5(String str) {
		StringBuilder sb = new StringBuilder();
		String s;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			byte[] hash = md.digest();
			for (byte b : hash) {
				s = Integer.toHexString(0xff & b);
				if (s.length() == 1) {
					sb.append("0");
				}
				sb.append(s);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
