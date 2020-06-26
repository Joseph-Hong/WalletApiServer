package com.cmm.util;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonUtil {
	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 *
	 * @param obj
	 * @return
	 */
	public static String nvl(Object obj) {
		return nvl(obj, "");
	}

	public static String nvl(String strVal, String strDefaultVal) {
		if (StringUtils.isBlank(strVal) || strVal == null || strVal.equals("null") || strVal == "") {
			strVal = strDefaultVal;
		}

		if(strVal == null) {
			return "";
		}

		strVal = strVal.trim();

		return strVal;
	}

	public static String nvl(Object obj, String strDefaultValue) {
		String strVal = strDefaultValue;

		if (obj != null) {
			strVal = nvl(obj.toString(), strDefaultValue);
		}

		strVal = strVal.trim();

		return strVal;
	}

	public static int nvl(Object obj, int defaultValue) {
		int iVal = defaultValue;

		if (obj != null) {
			iVal = nvl(obj.toString(), defaultValue);
		}

		return iVal;
	}

	public static int nvl(String strVal, int strDefaultVal) {
		int result = 0;

		try {
			if (strVal == null || strVal.equals("null")) {
				result = strDefaultVal;
			} else {
				result = Integer.parseInt(strVal.trim());
			}
		} catch (Exception e) {
			result = strDefaultVal;
		}

		return result;
	}

	public static double nvl(Object obj, double defaultValue) {
		double sValue = 0;
		if (obj != null) {
			sValue = Double.parseDouble(obj.toString());
		}
		return sValue;
	}

	public static double nvl(String strVal, double defaultValue) {
		double result = 0.0;
		if (strVal == null || strVal.equals("null")) {
			result = defaultValue;
		} else {
			result = Double.parseDouble(strVal);
		}
		return result;
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String)
			return obj == null || "".equals(obj.toString().trim());
		else if (obj instanceof List)
			return obj == null || ((List) obj).isEmpty();
		else if (obj instanceof Map)
			return obj == null || ((Map) obj).isEmpty();
		else if (obj instanceof Object[])
			return obj == null || Array.getLength(obj) == 0;
		else
			return obj == null;
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	public static String getClientIP(HttpServletRequest request) {

		String clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
		if (null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")) {
			clientIp = request.getHeader("REMOTE_ADDR");
		}
		if (null == clientIp || clientIp.length() == 0 || clientIp.toLowerCase().equals("unknown")) {
			clientIp = request.getRemoteAddr();
		}

		return clientIp;
	}

	public static long getClientIP(String clientIP) {
		long IPNum = 0;

		logger.debug("clientIP : {}", clientIP);

		String[] addrArray = clientIP.split("\\.");

		if (addrArray.length == 4) {
			for (int i = 0; i < addrArray.length; i++) {
				int power = 3 - i;
				int ip = Integer.parseInt(addrArray[i]);
				IPNum += ip * Math.pow(256, power);
			}
		}

		return IPNum;
	}

	/**
	 * Generate random number
	 *
	 * @param nDigit
	 *            digit of random number
	 * @return String created random value
	 */
	public static String getVerificationNumber(int nDigit) {
		Random m_rnd = new Random();
		double dblNo = m_rnd.nextGaussian();
		double dblNo2;
		int pos2;

		String strNo = null;
		String strNo2 = null;

		// make positive number
		if (dblNo < 0) {
			dblNo = dblNo * (-1);
		}

		strNo = Double.toString(dblNo / 10);
		strNo = Double.toString(dblNo / 10);

		int pos = strNo.indexOf('.') + 1;

		if ((strNo.length() - pos) > nDigit) {
			strNo = strNo.substring(pos, pos + nDigit);
		} else {
			dblNo2 = m_rnd.nextGaussian();
			if (dblNo2 < 0) {
				dblNo2 = dblNo2 * (-1);
			}

			strNo2 = Double.toString(dblNo2 / 10);
			pos2 = strNo2.indexOf('.') + 1;
			strNo = strNo.substring(pos, strNo.length()) + strNo2.substring(pos2, strNo2.length());

			if(strNo.length() < nDigit) {
				strNo = strNo + getVerificationNumber(nDigit - strNo.length());
			} else {
				strNo = strNo.substring(0, nDigit);
			}
		}

		return strNo;
	}

	public static String randomUUID(int length) {
		return randomUUID(length, false, null);
	}

	public static String randomUUID(int length, boolean exceptDash) {
		return randomUUID(length, exceptDash, null);
	}

	public static String randomUUID(int length, boolean exceptDash, String initDateTimeFormat) {
		return randomUUID(length, exceptDash, initDateTimeFormat, false);
	}

	public static String randomUUID(int length, boolean exceptDash, String initDateTimeFormat, boolean numberOnly) {

		String uuid = "";

		if (numberOnly) {
			int count = length;

			if (isNotEmpty(initDateTimeFormat)) {
				count = length - initDateTimeFormat.length();
			}

			uuid = getVerificationNumber(count);

		} else {

			StringBuffer sb = new StringBuffer();
			for(int i=0 ; i < (int)(length - 1 / 32) + 1 ; i++) {
				sb.append(UUID.randomUUID().toString());
			}
			uuid = sb.toString();
		}

		if (isNotEmpty(initDateTimeFormat)) {
			// 날짜형식으로 시작하기
			// 날짜형식으로 시작하기
			LocalDateTime today = LocalDateTime.now();
			uuid = today.toString(initDateTimeFormat) + uuid;
		}

		if (exceptDash) {
			// dash 제거
			uuid = uuid.replaceAll("-", "");
		}

		if (uuid.length() > length) {
			uuid = uuid.substring(0, length);
		}

		return uuid;
	}

	public static String randomNumeric(int count, long start, long end) {
		String randomNumeric = "";

		while (true) {
			randomNumeric = RandomStringUtils.randomNumeric(count);
			long lngRandomNumeric = Long.parseLong(randomNumeric);

			if (start < 0 && end < 0) {
				break;
			} else if (end < start) {
				break;
			} else if (start <= lngRandomNumeric && lngRandomNumeric <= end) {
				break;
			} else if (0 <= start && start <= lngRandomNumeric && end < 0) {
				break;
			} else if (0 <= end && lngRandomNumeric <= end && start < 0) {
				break;
			}
		}

		return randomNumeric;
	}

	public static Map getHeaders(HttpServletRequest request) {
		Map headers = new HashMap();

		Enumeration<String> keys = request.getHeaderNames();

		if (keys.hasMoreElements()) {
			headers = new HashMap();

			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				headers.put(key, request.getHeader(key));
			}
		}

		return headers;
	}

	public static MapBuilder map() {
		return MapBuilder.map();
	}
}