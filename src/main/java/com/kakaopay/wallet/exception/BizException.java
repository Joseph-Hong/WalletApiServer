package com.kakaopay.wallet.exception;

import egovframework.rte.fdl.cmmn.exception.EgovBizException;

public class BizException extends EgovBizException {

	private static final long serialVersionUID = 552311071790368713L;

	public BizException(String defaultMessage) {
		super(defaultMessage, null, null);
		this.result_code = "500";
		this.data = null;
		this.http_status = 200;
	}

	public BizException(String defaultMessage, String resultCode) {
		super(defaultMessage, null, null);
		this.result_code = resultCode;
		this.data = null;
		this.http_status = 200;
	}

	public BizException(String defaultMessage, String resultCode, Object data) {
		super(defaultMessage, null, null);
		this.result_code = resultCode;
		this.data = data;
		this.http_status = 200;
	}

	public BizException(String defaultMessage, String resultCode, Object data, Integer httpStatus) {
		super(defaultMessage, null, null);
		this.result_code = resultCode;
		this.data = data;
		this.http_status = httpStatus;
	}

	private String result_code;

		public String getResultCode() {
			return result_code;
		}
		public void setResultCode(String result_code) {
			this.result_code = result_code;
		}

	private Integer http_status;

		public Integer getHttpStatus() {
			return http_status;
		}
		public void setHttpStatus(Integer http_status) {
			this.http_status = http_status;
		}

	private Object data;

		public Object getData() {
			return data;
		}
		public void setData(Object data) {
			this.data = data;
		}
}
