package eceep.web.international;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageText {
	private static LanguageText language;

	public static LanguageText getInstance() {
		if (language == null)
			language = new LanguageText();

		return language;
	}
	
	private Locale locale;
	
	public String getString(String key){
		if(locale == null || key.isEmpty())
			return "";

		ResourceBundle bundle = ResourceBundle.getBundle("text", locale);
		boolean isFound = false;
		Enumeration<String> keys = bundle.getKeys();
		while(keys.hasMoreElements()){
			String value = keys.nextElement();
			if(value.equalsIgnoreCase(key)){
				isFound = true;
			}
		}
				
		return isFound? bundle.getString(key) : "eCeepText: " + locale.getLanguage() + ": " + key;
	}
}
