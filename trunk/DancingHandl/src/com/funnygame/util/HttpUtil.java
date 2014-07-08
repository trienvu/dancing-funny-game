package com.funnygame.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

	public static String getContentByUrl(String path) {
		URL url;
		try {
			// get URL content
			url = new URL(path);
			URLConnection urlConnection = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream()));

			StringBuffer stringBuffer = new StringBuffer();

			String inputLine;

			while ((inputLine = bufferedReader.readLine()) != null) {
				stringBuffer.append(inputLine);
			}

			return stringBuffer.toString();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}
 
	
}
