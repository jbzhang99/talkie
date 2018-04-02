package com.meta.controller.terminal;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryptDecrypt {

	
	public static byte[] encryptPad(byte[] plain, byte[] aeskey) throws Exception {
		byte[] tempArr;
		int i = 0;
		int srcSize = plain.length;
		
		if (srcSize < 16) {	//小于16字节，填充16字节，后面填充0x06
			tempArr = new byte[16];
	
			for (i = 0; i < 16; i++) {
				if (i < srcSize) {
					tempArr[i] = plain[i];
				} else {
					//input[i] = 0x06;
					int dif = 16 - srcSize;
					tempArr[i] = (byte) dif;
				}
			}
			//加密

		} else {	//如果是16的倍数，填充16字节，后面填充0x10
			int group = srcSize / 16;
			int size = 0;
			int remainder = srcSize % 16;

			size = 16 * (group + 1);
			
			tempArr = new byte[size];


			for (i = 0; i < size; i++) {
				if (i < srcSize) {
					tempArr[i] = plain[i];
				} else {
					if (remainder == 0) {
						tempArr[i] = 0x10;
					} else {	//如果不足16位 少多少位就补几个几  如：少4为就补4个4 以此类推
						int dif = size - srcSize;
						tempArr[i] = (byte)dif;
					}
				}
			}
			//加密
		}
		
		// 调用加密方法，对数据进行加密，加密后的数据存放到encryBuf字节数组中
		byte[] encryBuf = encrypt(aeskey, tempArr);

		return encryBuf;
	}
	
	
	public static byte[] decryptUnPad(byte[] cypher, byte[] aeskey) throws Exception {
		byte[] decryBuf = decrypt(aeskey, cypher);
		return decryBuf;
	}

	// 加密方法
	private static byte[] encrypt(byte[] key, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		//Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	// 解密方法
	private static byte[] decrypt(byte[] key, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		//Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

}
