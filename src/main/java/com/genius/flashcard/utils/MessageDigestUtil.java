package com.genius.flashcard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtil {
	public static String getMD58(String str) {
		String MD5 = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			byte byteData8[] = new byte[8];

			// 8바이트로 축약
			for (int i = 0; i < byteData.length; i++) {
				int iDst = i % 8;

				// 비어 있으면 넣는다. 아니면 AND 비트 연산을 사용...
				if (byteData8[iDst] == 0) {
					byteData8[iDst] = byteData[i];
				} else {
					byteData8[iDst] ^= byteData[i];
				}
			}

			for (int i = 0; i < byteData8.length; i++) {
				sb.append(Integer.toString((byteData8[i] & 0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			MD5 = null;
		}
		return MD5;
	}

	public static String getMD5(String str) {
		String MD5 = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			MD5 = null;
		}
		return MD5;
	}

	public static String getSHA256(String str) {
		String SHA = "";
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(str.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			SHA = null;
		}
		return SHA;
	}
}
