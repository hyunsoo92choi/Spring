package com.eBayJP.kuromoji.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

/**
 * <pre>
 * com.eBayJP.kuromoji.util FileUtil.java 
 * </pre>
 * @date : 2019. 5. 23. 오전 10:02:53
 * @description : File IO Util Class
 * @author : hychoi
 */
public class FileUtil {
	public static String ebayJapanDictionary = getUserDictionary();
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
			
			ClassPathResource cpr = new ClassPathResource("userDic.csv");

			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(cpr.getInputStream(), StandardCharsets.UTF_8));

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
	
	public static List<String> getGoodsNmList() {
		
		List<String> resultList = new ArrayList<String>();
		String ls = System.getProperty("line.separator");
		
		try {
			
			ClassPathResource cpr = new ClassPathResource("GoodsList.csv");
			BufferedReader br = new BufferedReader(new InputStreamReader(cpr.getInputStream(), StandardCharsets.UTF_8));

			String line = br.readLine();

			while (line != null) {
				resultList.add(line.trim());
				line = br.readLine();
			}
		} catch (FileNotFoundException fileException) {
			fileException.printStackTrace();
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			unsupportedEncodingException.printStackTrace();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
		
		return resultList;
	}
}
