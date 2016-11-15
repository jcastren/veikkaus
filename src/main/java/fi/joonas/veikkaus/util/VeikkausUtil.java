package fi.joonas.veikkaus.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class VeikkausUtil {
	
	private static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

	public static String getDateAsString(Date date, String pattern) {
		SimpleDateFormat sdf;

		if (pattern == null) {
			sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		} else {
			sdf = new SimpleDateFormat(pattern);
		}
		return sdf.format(date);
	}
	
	public static String getDateAsString(Date date) {
		return getDateAsString(date, null);
	}
	
	public static Date getStringAsDate(String str, String pattern) throws ParseException {
		SimpleDateFormat sdf;

		if (pattern == null) {
			sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		} else {
			sdf = new SimpleDateFormat(pattern);
		}
		return sdf.parse(str);
	}	
	public static Date getStringAsDate(String str) throws ParseException {
		return getStringAsDate(str, null);
	}
	
}
