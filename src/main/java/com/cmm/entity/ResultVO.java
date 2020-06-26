package com.cmm.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultVO {

	private static final Logger logger = LoggerFactory.getLogger(ResultVO.class);

	@JsonProperty("status")
	private Integer status;

		@JsonProperty("status")
		public Integer getStatus() {
			return status;
		}
		@JsonProperty("status")
		public ResultVO setStatus(Integer status) {
			this.status = status;
			return this;
		}

	@JsonProperty("result_code")
	private String code;

		@JsonProperty("result_code")
		public String getCode() {
			return code;
		}
		@JsonProperty("result_code")
		public ResultVO setCode(String code) {
			this.code = code;
			return this;
		}
		public ResultVO setCode(Integer code) {
			this.code = String.valueOf(code);
			return this;
		}

	@JsonProperty("result_message")
	private String message;

		@JsonProperty("result_message")
		public String getMessage() {
			return message;
		}
		@JsonProperty("result_message")
		public ResultVO setMessage(String message) {
			this.message = message;
			return this;
		}

	@JsonProperty("data")
	private Object data;

		@JsonProperty("data")
		public Object getData() {
			return data;
		}
		@JsonProperty("data")
		public ResultVO setData(Object data) {
			this.data = data;
			return this;
		}

	public void toModel(Model model) {
		model.addAttribute("status", this.getStatus());
		model.addAttribute("result_code", this.getCode());
		model.addAttribute("result_message", this.getMessage());
		model.addAttribute("data", this.getData());

		if("400".equalsIgnoreCase(code)) {
			logger.info("Check the input validation : {} => {}", message, data);
		} else {
			logger.info("{}", message);
		}
	}
}
