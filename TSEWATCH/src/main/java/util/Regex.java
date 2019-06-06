package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	// Regex expression for French phone numbers
	public static String telNumber(String content) {
		String pattern = "(\\d{2}\\s){5}";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(content);
		if(m.find()) {
			return m.group(0);
		}
		else
			return null;
	}
	
	// Regex expression for Saint-etienne.fr/node/5659
	// deadline
	public static String deadline(String content)
	{
		String pattern = "((\\d){2}/){2}\\d{2}.*\\sau\\splus\\stard";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(content);
		if(m.find()) {
			return m.group(0);
			
		}
		return null;
	}
	
	
	
	public static void main(String[] args) {
		
	}
}
