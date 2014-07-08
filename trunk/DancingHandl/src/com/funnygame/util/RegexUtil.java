package com.funnygame.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

	public static String find(String content, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		if (m.find())
			return m.group();
		return null;
	}
}
