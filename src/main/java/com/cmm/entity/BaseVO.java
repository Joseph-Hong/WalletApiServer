package com.cmm.entity;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class BaseVO implements Serializable {

	private static final long serialVersionUID = -7637882607784233908L;
	private static final Logger logger = LoggerFactory.getLogger(BaseVO.class);

	public <T> T deserialize(String jsonString) throws Exception {
		T result = null;
		Class<T> objType = (Class<T>) this.getClass();

		String jsonBody = jsonString;
		logger.trace("jsonBody : {}", jsonBody);

		ObjectMapper om = new ObjectMapper();

		//Deserialize from JSON to VO
		Object entity = om.readValue(jsonBody, objType);
		logger.trace("entity : {}", entity);
		result = (T)entity;

		return result;
	}

	public <T> String serialize() throws Exception {
		String result = "";
		Object obj = this;

		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		//Serialize from VO to JSON
		String jsonString = om.writeValueAsString(obj);
		logger.trace("jsonString : {}", jsonString);
		result = jsonString;

		return result;
	}
}