package com.eBayJP.kuromoji.common.security.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.eBayJP.kuromoji.common.exception.CannotGenerateJwtKeyException;
import com.eBayJP.kuromoji.common.exception.JwtAuthorizationException;
import com.eBayJP.kuromoji.common.exception.UnAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.security.service_JwtService.java
 * </pre>
 * @date : 2019. 6. 13
 * @author : hychoi
 */

@Service
public class JwtService {

	private final Logger log = LoggerFactory.getLogger(JwtService.class);
	
	/* */
	private static final String ISSUER = "hschoi";
    private static final String SECRET = "hschoi Secret";
    public static final String CLAIM_KEY_USER_ID = "userId";
    private static final int HOUR = 60 * 60 * 1000;
    
    private final int expirationTime = 2 * HOUR;
    
    public String createToken(String userId) {
        
    	return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setHeaderParam("algo", SignatureAlgorithm.HS256)
                .setIssuer(ISSUER)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim(CLAIM_KEY_USER_ID, userId)
                .signWith(SignatureAlgorithm.HS256, generateKey())
                .compact();
    }
    /**
     * <pre>
     * 1. 개요 : Key Generate
     * 2. 처리내용 :Key를 생성 함.
     * </pre>
     * @Method Name : generateKey
     * @date : 2019. 6. 13.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 6. 13.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @return key
     */ 
    private byte[] generateKey() {
    	
    	try {
            byte[] key = SECRET.getBytes("UTF-8");
            return key;
        } catch (UnsupportedEncodingException e) {
            throw new CannotGenerateJwtKeyException("GENERATE_JWT_KEY_ERROR");
        }
    }
    
    /**
     * <pre>
     * 1. 개요 : 유효한 토큰인지 체크한다.
     * 2. 처리내용 : 유효한 토큰이면 True, 아니면 False 반환
     * </pre>
     * @Method Name : isUsable
     * @date : 2019. 6. 13.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 6. 13.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @param jwt
     * @return boolean
     */ 
    public boolean isUsable(String jwt) {
		try{
			Jws<Claims> claims = Jwts.parser()
					  .setSigningKey(this.generateKey())
					  .parseClaimsJws(jwt);
			return true;
			
		}catch (Exception e) {
			
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error(e.getMessage());
			}
			throw new UnAuthenticationException("Authentication_ERROR");
		}
	}
    
    /**
     * <pre>
     * 1. 개요 : ServletRequest 의 Header 에서 Authorization을 받아 Decode 하는 메서드
     * 2. 처리내용 : ServletRequest 의 Header 에서 Authorization을 받아 Decode
     * </pre>
     * @Method Name : decode
     * @date : 2019. 6. 13.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 6. 13.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @return 
     */ 
    public String decode() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return decode(request);
    }
    
    /**
     * <pre>
     * 1. 개요 : decode() 의 실제 구현 메서드
     * 2. 처리내용 : ServletRequest 의 Header의 Authorization을 받아 Decode 하여 CLAIM_KEY_USER_ID를 얻어 반환
     * </pre>
     * @Method Name : decode
     * @date : 2019. 6. 13.
     * @author : hychoi
     * @history : 
     *	-----------------------------------------------------------------------
     *	변경일				작성자						변경내용  
     *	----------- ------------------- ---------------------------------------
     *	2019. 6. 13.		hychoi				최초 작성 
     *	-----------------------------------------------------------------------
     * 
     * @param request
     * @return String
     */ 
    public String decode(HttpServletRequest request) {
        
    	String jwt = request.getHeader("Authorization");
        
    	try {
            Jws<Claims> claims = parse(jwt);
            return claims.getBody().get(CLAIM_KEY_USER_ID).toString();
        } catch (Exception e) {
            throw new JwtAuthorizationException("INVALID_TOKEN");
        }
    }

    public Jws<Claims> parse(String jwt) {
        return Jwts.parser()
        		.setSigningKey(generateKey())
                .parseClaimsJws(jwt);
    }

    public String refresh(String jwt) {
        String userId = parseUserId(jwt);
        return createToken(userId);
    }

    public String parseUserId(String jwt) {
        return parse(jwt)
                .getBody()
                .get(CLAIM_KEY_USER_ID)
                .toString();
    }
    
    public Map<String, Object> get(String key) {
		
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String jwt = request.getHeader("Authorization");
		Jws<Claims> claims = null;
		
		try {
			claims = Jwts.parser()
						 .setSigningKey(SECRET.getBytes("UTF-8"))
						 .parseClaimsJws(jwt);
		} catch (Exception e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error(e.getMessage());
			}
			throw new UnAuthenticationException("Authentication_ERROR");
	
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>)claims.getBody().get(key);
		return value;
	}
}
