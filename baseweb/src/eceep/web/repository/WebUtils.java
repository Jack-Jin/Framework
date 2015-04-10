package eceep.web.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

public class WebUtils {
	public static <T> T request2Bean(HttpServletRequest request, Class<T> beanClass){
		try {
			//create bean object.
			T bean = beanClass.newInstance();
			
			//request --> bean
			Enumeration e = request.getParameterNames();
			while(e.hasMoreElements()){
				String name = (String) e.nextElement();
				String value = request.getParameter(name);
				BeanUtils.setProperty(bean, name, value);
			}
			
			return bean;
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public static void copyBean(Object src, Object dest) {
		
		//Date converter
		ConvertUtils.register(new Converter() {
			@Override
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				}
				if (!(value instanceof String)) {
					throw new ConversionException(
							"Do not support this type convert.");
				}
				String str = (String) value;
				if (str.trim().equals("")) {
					return null;
				}

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

				try {
					return df.parse(str);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}

		}, Date.class);
		
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String generateID() {
		//generate Global ID
		
		return UUID.randomUUID().toString();
	}

}
