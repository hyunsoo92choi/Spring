package com.eBayJP.kuromoji.common.exception;
/**
 * <pre>
 * com.eBayJP.kuromoji.common.exception_JwtAuthorizationException.java
 * </pre>
 * @date : 2019. 6. 13. 오후 3:01:09
 * @author : hychoi
 */
public class JwtAuthorizationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtAuthorizationException () {
		super();
	}
	
	public JwtAuthorizationException (String message) {
		super(message);
	}
	
}
