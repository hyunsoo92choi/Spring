package com.eBayJP.kuromoji.common.exception;
/**
 * <pre>
 * com.eBayJP.kuromoji.common.exception_UnAuthenticationException.java
 * </pre>
 * @date : 2019. 6. 13
 * @author : hychoi
 */
public class UnAuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnAuthenticationException() { 
		super();
	}

	public UnAuthenticationException(String message) {
		super(message);
	}
}
