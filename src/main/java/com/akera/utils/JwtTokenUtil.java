package com.akera.utils;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.akera.model.vo.JwtUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author 王欣宇
 * @time 2017年8月29日 下午3:40:55
 * @todo 添加构造jwt及解析jwt的帮助类
 */
@Component
public class JwtTokenUtil implements Serializable {

	private static final String CLAIM_KEY_USERNAME = "sub";

	private static final String CLAIM_KEY_CREATED = "created";
	
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.clientId}")
    private String clientId;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月30日 下午6:28:59
	 * @params 
	 * @todo 解析TOKEN
	 */
	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月30日 下午6:29:15
	 * @params 
	 * @todo 获取名称
	 */
	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年12月8日 上午11:27:07
	 * @todo 
	 * @remark
	 */
	public Short getDrugDealerIdFromToken(String token) {
		Short drugDealerId;
		try {
			final Claims claims = getClaimsFromToken(token);
			drugDealerId = (Short)claims.get("drugDealerId");
		} catch (Exception e) {
			drugDealerId = null;
		}
		return drugDealerId;
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月30日 下午6:29:37
	 * @params 
	 * @todo 获取创建时间
	 */
	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月30日 下午6:29:49
	 * @params 
	 * @todo 获取失效时间
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月30日 下午6:30:02
	 * @params 
	 * @todo 失效时间
	 */
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月30日 下午6:30:39
	 * @params 
	 * @todo TOKEN是否失效
	 */
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月30日 下午6:31:03
	 * @params 
	 * @todo TOKEN是否在重置密码前
	 */
	private static Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月30日 下午6:31:40
	 * @params 
	 * @todo 获取TOKEN
	 */
	public String generateToken(JwtUser userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		claims.put("clientId", clientId);
		claims.put("id", userDetails.getId());
		claims.put("username", userDetails.getUsername());
		return generateToken(claims);
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月31日 上午9:59:27
	 * @params 
	 * @todo 获取TOKEN
	 */
	protected String generateToken(Map<String, Object> claims) {
		// 生成签名密钥
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(signatureAlgorithm, signingKey).compact();
	}
	
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月31日 上午9:59:13
	 * @params 
	 * @todo 验证TOKEN是否可以刷新
	 */
	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = getCreatedDateFromToken(token);
		return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && !isTokenExpired(token);
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月30日 下午6:32:09
	 * @params 
	 * @todo 刷新TOKEN
	 */
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}
	
	/**
	 * 
	 * @author 王欣宇
	 * @time 2017年8月31日 上午9:58:45
	 * @params 
	 * @todo 验证TOKEN
	 */
	public Boolean validateToken(String token, UserDetails userDetails) {
		JwtUser user = (JwtUser) userDetails;
		final String username = getUsernameFromToken(token);
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}
	
	public static void main(String[] args) {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNTczNTkyOTU4OCIsImNsaWVudElkIjoiMDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjYiLCJjcmVhdGVkIjoxNTEzMzMxNDI3ODk3LCJpZCI6MSwiZXhwIjoxNTEzMzMzMjI3LCJ1c2VybmFtZSI6IjE1NzM1OTI5NTg4In0.1rbqQ5UWdYCxYy6MjBmkmxnpuFZCFXlFV6j7FbGRhao";
		JwtTokenUtil jwt = new JwtTokenUtil();
		System.out.println(jwt.getClaimsFromToken(token).get("id"));
	}
}
