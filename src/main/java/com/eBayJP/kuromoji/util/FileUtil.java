package com.eBayJP.kuromoji.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.springframework.util.ResourceUtils;

/**
 * <pre>
 * com.eBayJP.kuromoji.util FileUtil.java 
 * </pre>
 * @date : 2019. 5. 23. 오전 10:02:53
 * @description : File IO Util Class
 * @author : hychoi
 */
public class FileUtil {
	
	/**
	 * <pre>
	 * 1. 개요 : 커스텀 사전 파일을 읽어와 String 형태로 return 하는 method
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : getUserDictionary
	 * @date : 2019. 5. 23.
	 * @author : hychoi
	 * @history : 
	 *	-----------------------------------------------------------------------
	 *	변경일				작성자						변경내용  
	 *	----------- ------------------- ---------------------------------------
	 *	2019. 5. 23.		hychoi				최초 작성 
	 *	-----------------------------------------------------------------------
	 * 
	 * @return String
	 */ 	
	public static String getUserDictionary() {

		String ls = System.getProperty("line.separator");
		String result = null;
		
		try {

			File file = ResourceUtils.getFile("classpath:userDic.csv");

			StringBuilder sb = new StringBuilder((int) file.length());
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));

			String line = br.readLine();

			while (line != null) {
				sb.append(line.trim());
				sb.append(ls);
				line = br.readLine();
			}

			result = sb.toString();
		} catch (FileNotFoundException fileException) {
			fileException.printStackTrace();
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			unsupportedEncodingException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		System.out.println(result);
		return result;
	}
}
