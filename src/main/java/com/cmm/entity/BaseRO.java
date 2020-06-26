package com.cmm.entity;

import java.beans.PropertyEditor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;

@SuppressWarnings("serial")
public class BaseRO implements Serializable {

	@SuppressWarnings("serial")
	protected Map<Class<?>, PropertyEditor> defaultEditors = new HashMap<Class<?>, PropertyEditor>() {
		{
			//Numeric
			put(boolean.class, new CustomBooleanEditor(false));
			put(Boolean.class, new CustomBooleanEditor(true));

			put(byte.class, new CustomNumberEditor(Byte.class, false));
			put(Byte.class, new CustomNumberEditor(Byte.class, true));

			put(int.class, new CustomNumberEditor(Integer.class, false));
			put(Integer.class, new CustomNumberEditor(Integer.class, true));

			put(long.class, new CustomNumberEditor(Long.class, false));
			put(Long.class, new CustomNumberEditor(Long.class, true));

			put(float.class, new CustomNumberEditor(Float.class, false));
			put(Float.class, new CustomNumberEditor(Float.class, true));

			put(double.class, new CustomNumberEditor(Double.class, false));
			put(Double.class, new CustomNumberEditor(Double.class, true));

			put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, false));

			//Datetime
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.setLenient(false);

			SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			datetimeFormat.setLenient(false);

			put(java.util.Date.class, new CustomDateEditor(dateFormat, true));
			put(java.util.Date.class, new CustomDateEditor(datetimeFormat, true));
		}
	};

}