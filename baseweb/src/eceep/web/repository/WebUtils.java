package eceep.web.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

public class WebUtils {
	public static <T> T request2Bean(HttpServletRequest request, Class<T> beanClass) {
		try {
			// create bean object.
			T bean = beanClass.newInstance();

			// request --> bean
			Enumeration e = request.getParameterNames();
			while (e.hasMoreElements()) {
				// Parameter: name & value
				String name = (String) e.nextElement();
				String value = request.getParameter(name);
				// Check bean has this name property.
				boolean found = false;
				Field field = null;
				for (Field f : beanClass.getDeclaredFields()) {
					if (f.getName().equalsIgnoreCase(name)) {
						found = true;
						field = f;
						break;
					}
				}
				if (!found)
					continue;

				if (field.getType().equals(Date.class)) {
					// Register BeanUtils date converter
					ConvertUtils.register(new Converter() {
						@Override
						public Object convert(Class type, Object value) {
							if (value == null) {
								return null;
							}
							if (!(value instanceof String)) {
								throw new ConversionException("Do not support this type convert.");
							}
							String str = (String) value;
							if (str.trim().equals("")) {
								return null;
							}

							DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

							try {
								return df.parse(str);
							} catch (ParseException e) {
								throw new RuntimeException(e);
							}
						}

					}, Date.class);

					BeanUtils.setProperty(bean, name, value);
				} else {
					BeanUtils.setProperty(bean, name, value);
				}
			}

			return bean;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public static void copyBean(Object src, Object dest) {

		// Date converter
		ConvertUtils.register(new Converter() {
			@Override
			public Object convert(Class type, Object value) {
				if (value == null) {
					return null;
				}
				if (!(value instanceof String)) {
					throw new ConversionException("Do not support this type convert.");
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
		// generate Global ID

		return UUID.randomUUID().toString();
	}

	public static <E extends Enum<E>> E getEnumId(Class<E> clazz, int id) {
		E result = null;

		try {
			for (E en : EnumSet.allOf(clazz)) {
				Method declaredMethod = clazz.getDeclaredMethod("getId");
				int enId = (int) declaredMethod.invoke(en);
				if (enId == id) {
					result = en;
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
