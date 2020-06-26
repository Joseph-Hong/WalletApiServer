package com.cmm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NetUtil {

	private static final Logger logger = LoggerFactory.getLogger(NetUtil.class);

	/**
	 * Convert Generic object to JSON object
	 *
	 * @param obj
	 * @return
	 */
	public static <T> T toJson(Object obj) {

		Object result = null;
		ObjectMapper om = new ObjectMapper();

		try {
			if (obj != null) {
				List param = new ArrayList();
				param.add(obj);
				List json = om.readValue(om.writeValueAsString(param), List.class);
				result = json.get(0);
			}

		} catch (Exception e) {
			logger.error("{}", e.fillInStackTrace());
		}

		return (T) result;
	}

	public static String toJsonString(Object obj) {
		String json_string = "";
		ObjectMapper om = new ObjectMapper();

		try {
			json_string = om.writeValueAsString(toJson(obj));
		} catch (JsonProcessingException e) {
			logger.error("{}", e.fillInStackTrace());
		}

		return json_string;
	}

	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			logger.error("{}", ex.fillInStackTrace());
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					logger.error("{}", ex.fillInStackTrace());
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}

	/**
	 * Convert JSON string to Map object
	 *
	 * @param json
	 * @return
	 */
	public static Map fromJson(String json) {

		Map<String, Object> result = null;
		ObjectMapper om = new ObjectMapper();

		if (CommonUtil.isEmpty(json)) {
			return result;
		}

		try {
			result = om.readValue(json, Map.class);
		} catch (Exception e) {
			logger.error("{}", e.fillInStackTrace());
		}

		/*
		 * try { Gson gson = new Gson(); result = gson.fromJson(json,
		 * Map.class);
		 *
		 * checkAttributesFromMap(result);
		 *
		 * } catch (Exception e) { logger.error("{}", e.fillInStackTrace()); }
		 */

		return result;
	}
}