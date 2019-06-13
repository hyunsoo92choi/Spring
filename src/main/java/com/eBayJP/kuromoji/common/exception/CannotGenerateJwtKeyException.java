package com.eBayJP.kuromoji.common.exception;

/**
 * <pre>
 * com.eBayJP.kuromoji.common.exception_CannotGenerateJwtKeyException.java
 * </pre>
 * 
 * @date : 2019. 6. 13
 * @author : hychoi
 */
public class CannotGenerateJwtKeyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CannotGenerateJwtKeyException() {
		super();
	}

	public CannotGenerateJwtKeyException(String message) {
		super(message);
	}
}
