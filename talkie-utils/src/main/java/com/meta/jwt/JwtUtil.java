package com.meta.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtUtil {


	private static final  String profiles="xm_meta";

	/**
	 * 由字符串生成加密key
	 * @return
	 */
	public  static SecretKey generalKey(){
		String stringKey = profiles+Constant.JWT_SECRET;
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

	/**
	 * 创建jwt
	 * @param id
	 * @param subject
	 * @param ttlMillis
	 * @return
	 * @throws Exception
	 */
	public  static String createJWT(String id, String subject, long ttlMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setId(id)
				.setIssuedAt(now)
				.setSubject(subject)
//				// 签发者
//				.setIssuer("MeTa")
				.signWith(signatureAlgorithm, key);
//				.setId(id)                                      // JWT_ID
//				.setAudience("")                                // 接受者
//				.setClaims(null)                                // 自定义属性
//				.setSubject(subject)                                 // 主题
//				.setIssuedAt(now)                        // 签发时间
//				.setNotBefore(new Date())                       // 失效时间
//				.setExpiration(Long)                                // 过期时间
//                .signWith(signatureAlgorithm, key);           // 签名算法以及密匙
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	/**
	 * 解密jwt
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jwt) throws Exception{
		SecretKey key = generalKey();
		Claims claims = Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(jwt).getBody();


		return claims;
	}

	/**
	 * 生成subject信息
	 * @param user
	 * @return
	 */
//	public static String generalSubject(User user){
//		JSONObject jo = new JSONObject();
//		jo.put("userId", user.getUserId());
//		jo.put("roleId", user.getRoleId());
//		return jo.toJSONString();
//	}
}
